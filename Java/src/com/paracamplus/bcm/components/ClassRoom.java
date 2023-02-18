package com.paracamplus.bcm.components;


import java.util.HashMap;
import com.paracamplus.bcm.connector.CoordonatorConnector;
import com.paracamplus.bcm.connector.RoomConnector;
import com.paracamplus.bcm.ibp.RoomIBP;
import com.paracamplus.bcm.interfaces.ScriptManagementCI;
import com.paracamplus.bcm.obp.RoomOBP;
import com.paracamplus.bcm.sensor.ISensor;
import com.paracamplus.ilp1.interpreter.GlobalEnvFile;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.cyphy.tools.aclocks.ClockServer;
import fr.sorbonne_u.components.cyphy.tools.aclocks.ClockServerCI;
import fr.sorbonne_u.components.cyphy.tools.aclocks.ClockServerConnector;
import fr.sorbonne_u.components.cyphy.tools.aclocks.ClockServerOutboundPort;
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;
import fr.sorbonne_u.components.exceptions.ComponentStartException;
import fr.sorbonne_u.components.helpers.TracerWindow;
import fr.sorbonne_u.exceptions.PreconditionException;
import java.awt.Dimension;
import java.awt.Toolkit;

@OfferedInterfaces(offered = {ScriptManagementCI.class})
@RequiredInterfaces(required={ClockServerCI.class})
public class ClassRoom extends  AbstractRoom{
	protected GlobalEnvFile env;
    protected HashMap<String, ISensor> sensors;
	protected RoomIBP RoomIBP;


    protected ClassRoom(String reflectionInboundPortURI, String clockURI, String coordonatorIBPURI, String []neighboursURI) throws Exception {
		super(3,3, reflectionInboundPortURI, clockURI, coordonatorIBPURI, neighboursURI);
		assert	clockURI != null && !clockURI.isEmpty() :
				new PreconditionException("clockURI != null && !clockURI.isEmpty()");
		this.RoomIBP = new RoomIBP(reflectionInboundPortURI, this);
		this.RoomIBP.publishPort();

		// create tracing windows
		int tmp = reflectionInboundPortURI.charAt(reflectionInboundPortURI.length()-1) - 1;
		int xRelativePos = tmp >= '0' && tmp < '9' ? (tmp - '0') % 4 : 0;
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = screenSize.width / 4;
		int screenHeight = screenSize.height * 2 / 5;
		this.tracer.set(new TracerWindow("ClassRoom component " + this.reflectionInboundPortURI, 0, 0, screenWidth, screenHeight, xRelativePos, 3));
		this.toggleTracing();
		logMessage(this.toString());

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
	
			} catch (Exception e) {
				throw new ComponentStartException(e) ;
			}
			this.logMessage("start.");
		}
		
	public void			execute() throws Exception
	{
		super.execute();
	}
	
	@Override
	public synchronized void	finalise() throws Exception
	{

		//this.doPortDisconnection(this.clockServerOBP.getPortURI());
		super.finalise();
	}
	
	@Override
	public synchronized void	shutdown() throws ComponentShutdownException
	{
		try {
			this.RoomIBP.unpublishPort();
            this.coordinatorOBP.unpublishPort();
            this.clockServerOBP.unpublishPort();
			for (RoomOBP roomOBP : this.neighboursOBP) {
				roomOBP.unpublishPort();
			}	
		} catch (Exception e) {
			throw new ComponentShutdownException(e) ;
		}
		this.logMessage("shutdown.");
		super.shutdown();
	}

	@Override
	public String toString(){
		return "ClassRoom" + super.toString();
	}

} 

