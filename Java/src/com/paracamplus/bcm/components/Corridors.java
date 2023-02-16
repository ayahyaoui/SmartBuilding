package com.paracamplus.bcm.components;


import java.util.HashMap;
import com.paracamplus.bcm.connector.CoordonatorConnector;
import com.paracamplus.bcm.sensor.ISensor;
import com.paracamplus.bcm.utils.Utils;
import com.paracamplus.ilp1.interpreter.GlobalEnvFile;

import fr.sorbonne_u.components.cyphy.tools.aclocks.AcceleratedClock;
import fr.sorbonne_u.components.cyphy.tools.aclocks.ClockServer;
import fr.sorbonne_u.components.cyphy.tools.aclocks.ClockServerConnector;
import fr.sorbonne_u.components.cyphy.tools.aclocks.ClockServerOutboundPort;
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;
import fr.sorbonne_u.components.exceptions.ComponentStartException;

public class Corridors extends  AbstractRoom{

    protected final String				clockURI;
    protected AcceleratedClock			clock;
    protected ClockServerOutboundPort	clockServerOBP;
    protected HashMap<String, ISensor> sensors;


    protected Corridors(int nbThreads, int nbSchedulableThreads, String reflectionInboundPortURI,
            String coordinatorInboundPortURI, String[] neighboursURI) throws Exception {
        super(nbThreads, nbSchedulableThreads, reflectionInboundPortURI, coordinatorInboundPortURI, neighboursURI);
        this.clockURI = null;
    }

    protected Corridors(String reflectionInboundPortURI, String coordinatorInboundPortURI,
             String[] neighboursURI) throws Exception {
        super(2, 1, reflectionInboundPortURI, coordinatorInboundPortURI, neighboursURI);
        this.clockURI = null;
    }

    protected Corridors(String reflectionInboundPortURI, String coordinatorInboundPortURI,
    String[] neighboursURI, String clockURI) throws Exception {
        super(2, 1, reflectionInboundPortURI, coordinatorInboundPortURI, neighboursURI);
        this.clockURI = clockURI;

    }


    @Override
	public synchronized void	start() throws ComponentStartException
	{
		super.start();

		try {
			if (Utils.DEBUG_MODE_BCM)
				System.out.println("[CorridorRoom] start()" + "connextion" + this.coordinatorOBP.getPortURI() + " " + coordonatorIBPURI);
			
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

			this.clockServerOBP = new ClockServerOutboundPort(this);
			this.clockServerOBP.publishPort();
			this.doPortConnection(
					this.clockServerOBP.getPortURI(),
					ClockServer.STANDARD_INBOUNDPORT_URI,
					ClockServerConnector.class.getCanonicalName());
			} catch (Exception e) {
				throw new ComponentStartException(e) ;
			}
			this.logMessage("start.");
		}
		
	public void			execute() throws Exception
	{
	
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
            this.roomIBP.unpublishPort();
            this.coordinatorOBP.unpublishPort();
            this.clockServerOBP.unpublishPort();	
		} catch (Exception e) {
			throw new ComponentShutdownException(e) ;
		}
		this.logMessage("shutdown.");
		super.shutdown();
	}




    @Override
    public boolean hasMethod(String name) {
        return sensors.containsKey(name);
    }

    @Override
    public Object executeFunction(String name) {
        if (sensors.containsKey(name))
		{
			ISensor sensor = sensors.get(name);
			return sensor.getValue();
		}
		return 21;
    }

    @Override
    public GlobalEnvFile executeScript(GlobalEnvFile env, String uri) throws Exception {
        if (uri.equals(reflectionInboundPortURI))
		{
			return executeScript(env);
		}
		System.out.println("[Room] executeScript()" + reflectionInboundPortURI +" had to execute script " + env.getNameFunction() + " on " + uri);
		env.setNextComponentUri(uri);
		return coordinatorOBP.executeScript(env, uri);
    }

} 

