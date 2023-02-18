package com.paracamplus.bcm.components;

import java.time.Instant;
import java.util.concurrent.TimeUnit;
import com.paracamplus.bcm.connector.CoordonatorConnector;
import com.paracamplus.bcm.connector.RoomConnector;
import com.paracamplus.bcm.ibp.RoomIBP;
import com.paracamplus.bcm.interfaces.ScriptManagementCI;
import com.paracamplus.bcm.obp.RoomOBP;
import com.paracamplus.bcm.sensor.ISensor;
import com.paracamplus.bcm.sensor.SensorDoorInstant;
import com.paracamplus.bcm.sensor.SensorWindowInstant;
import com.paracamplus.bcm.sensor.SensorWindowRecent;
import com.paracamplus.bcm.simul.DoorSimul;
import com.paracamplus.bcm.simul.WindowSimul;
import com.paracamplus.bcm.utils.Utils;

import com.paracamplus.ilp1.interpreter.GlobalEnvFile;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.cyphy.tools.aclocks.ClockServer;
import fr.sorbonne_u.components.cyphy.tools.aclocks.ClockServerCI;
import fr.sorbonne_u.components.cyphy.tools.aclocks.ClockServerConnector;
import fr.sorbonne_u.components.cyphy.tools.aclocks.ClockServerOutboundPort;
import java.awt.Dimension;
import java.awt.Toolkit;


import fr.sorbonne_u.components.exceptions.ComponentShutdownException;
import fr.sorbonne_u.components.exceptions.ComponentStartException;
import fr.sorbonne_u.components.helpers.TracerWindow;
import fr.sorbonne_u.exceptions.PreconditionException;

@OfferedInterfaces(offered = {ScriptManagementCI.class})
@RequiredInterfaces(required={ClockServerCI.class})
public class DesktopRoom extends AbstractRoom{
		protected GlobalEnvFile env;
		protected final RoomIBP		RoomIBP;
		protected WindowSimul windowSimul; // multiple windows
		protected DoorSimul doorSimul;

		
    protected DesktopRoom(String reflectionInboundPortURI, String clockURI, String coordonatorIBPURI, String []neighboursURI) throws Exception {
    	super(3, 3, reflectionInboundPortURI, clockURI, coordonatorIBPURI, neighboursURI);
    	assert	clockURI != null && !clockURI.isEmpty() :
			new PreconditionException("clockURI != null && !clockURI.isEmpty()");
			this.RoomIBP = new RoomIBP(reflectionInboundPortURI, this);
			this.RoomIBP.publishPort();
		
		this.initialiseSensors();
		
		// create tracing windows
		int tmp = reflectionInboundPortURI.charAt(reflectionInboundPortURI.length()-1) - 1;
		int xRelativePos = tmp >= '0' && tmp < '9' ? (tmp - '0') % 4 : 0;
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = screenSize.width / 4;
		int screenHeight = screenSize.height * 2 / 5;
		this.tracer.set(new TracerWindow("DesktopRoom component " + this.reflectionInboundPortURI, 0, 0, screenWidth, screenHeight, xRelativePos, 3));
		this.toggleTracing();
		logMessage(this.toString());
    }
	
	private void initialiseSensors() throws Exception {
		this.windowSimul = new WindowSimul(clock);
		this.doorSimul = new DoorSimul(clock);
		this.addSensor("windowOpen", new SensorWindowInstant(clock,windowSimul));
		this.addSensor("windowRecentlyOpen", new SensorWindowRecent(clock, windowSimul , Utils.MARG_WINDOW_RECENTLY_OPEN));
		this.addSensor("doorOpen", new SensorDoorInstant(clock, doorSimul));
	}
	
    @Override
	public synchronized void	start() throws ComponentStartException
	{
		super.start();
		try {
			
			// connect to his coordinator
			this.doPortConnection(
				this.coordinatorOBP.getPortURI(),
				coordonatorIBPURI,
				CoordonatorConnector.class.getCanonicalName());

			// connect to his neighbours
			for (int i = 0; i < neighboursURI.length; i++) {
				this.doPortConnection(
					this.neighboursOBP.get(i).getPortURI(),
					neighboursURI[i],
					RoomConnector.class.getCanonicalName());
			}
			
			// connect to the clock server
			this.clockServerOBP = new ClockServerOutboundPort(this);
			this.clockServerOBP.publishPort();
			this.doPortConnection(
					this.clockServerOBP.getPortURI(),
					ClockServer.STANDARD_INBOUNDPORT_URI,
					ClockServerConnector.class.getCanonicalName());

			this.clock = this.clockServerOBP.getClock(this.clockURI);
			initialiseSensors();
			} catch (Exception e) {
				throw new ComponentStartException(e) ;
			}
			this.logMessage("start.");
		}
		
	public void			execute() throws Exception
	{
		super.execute();

		Instant i0 = this.clock.getStartInstant();
		Instant i1 = i0.plusSeconds(Utils.DEFAULT_SLEEP_TIME_SIMUL * 120);
		long start = this.clock.delayToAcceleratedInstantInNanos(i1);
		this.scheduleTaskAtFixedRate(
			new AbstractComponent.AbstractTask() {
				@Override
				public void run() {
					try {
						long seconds = (clock.getStartInstant().toEpochMilli() + (long)(clock.getStartEpochNanos() * clock.getAccelerationFactor())) / 1000000000;
						logMessage(STANDARD_REQUEST_HANDLER_URI + " : " + seconds + "s refresh sensors");
						// refresh sensors
						for (ISensor sensor : sensors.values()) {
							sensor.eval();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			},
			start,
			Utils.DEFAULT_SLEEP_TIME_SIMUL,
			TimeUnit.SECONDS);

		Instant i2 = i0.plusSeconds(Utils.DEFAULT_SLEEP_TIME_SIMUL * 2);
		long startTask = this.clock.delayToAcceleratedInstantInNanos(i2);
		this.scheduleTask(
			o -> {
				windowSimul.openWindow();
				long seconds = (clock.getStartInstant().toEpochMilli() + (long)(clock.getStartEpochNanos() * clock.getAccelerationFactor())) / 1000000000;
				logMessage( seconds + " : " + "open window");
			},startTask, TimeUnit.SECONDS);

	}
	
	@Override
	public synchronized void	shutdown() throws ComponentShutdownException
	{
		try {
			this.RoomIBP.unpublishPort();
			this.coordinatorOBP.unpublishPort();
			for (RoomOBP roomOBP : this.neighboursOBP) {
				roomOBP.unpublishPort();
			}
			this.clockServerOBP.unpublishPort();

		} catch (Exception e) {
			throw new ComponentShutdownException(e) ;
		}

		this.logMessage("shutdown.");
		super.shutdown();
	}

	@Override
	public String toString() {
		return "DesktopRoom " + super.toString();
	}

}
