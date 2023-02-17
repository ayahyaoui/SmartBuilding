package com.paracamplus.bcm.components;

import static org.junit.Assert.assertTrue;

import java.io.StringWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import com.paracamplus.bcm.connector.CoordonatorConnector;
import com.paracamplus.bcm.connector.RoomConnector;
import com.paracamplus.bcm.ibp.DesktopRoomIBP;
import com.paracamplus.bcm.interfaces.DesktopRoomCI;
import com.paracamplus.bcm.interfaces.RoomI;

import com.paracamplus.bcm.interfaces.ScriptManagementCI;
import com.paracamplus.bcm.obp.CoordonatorOBP;
import com.paracamplus.bcm.obp.RoomOBP;
import com.paracamplus.bcm.sensor.ISensor;
import com.paracamplus.bcm.utils.Utils;
import com.paracamplus.ilp1.ast.ASTstring;
import com.paracamplus.ilp1.interfaces.IASTsequence;
import com.paracamplus.ilp1.interfaces.IASTvariableAssign;
import com.paracamplus.ilp1.interpreter.GlobalEnvFile;
import com.paracamplus.ilp1.interpreter.GlobalVariableEnvironment;
import com.paracamplus.ilp1.interpreter.GlobalVariableStuff;
import com.paracamplus.ilp1.interpreter.Interpreter;
import com.paracamplus.ilp1.interpreter.OperatorEnvironment;
import com.paracamplus.ilp1.interpreter.OperatorStuff;
import com.paracamplus.ilp1.interpreter.interfaces.EvaluationException;
import com.paracamplus.ilp1.interpreter.interfaces.IGlobalVariableEnvironment;
import com.paracamplus.ilp1.interpreter.interfaces.IOperatorEnvironment;
import com.paracamplus.ilp1.test.GlobalFunctionAst;

import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.cyphy.tools.aclocks.AcceleratedClock;
import fr.sorbonne_u.components.cyphy.tools.aclocks.ClockServer;
import fr.sorbonne_u.components.cyphy.tools.aclocks.ClockServerCI;
import fr.sorbonne_u.components.cyphy.tools.aclocks.ClockServerConnector;
import fr.sorbonne_u.components.cyphy.tools.aclocks.ClockServerOutboundPort;
import java.awt.Dimension;
import java.awt.Toolkit;

/*
 * 
 import fr.sorbonne_u.components.cyphy.tools.aclocks.AcceleratedClock;
 import fr.sorbonne_u.components.cyphy.tools.aclocks.ClockServer;
 import fr.sorbonne_u.components.cyphy.tools.aclocks.ClockServerConnector;
 import fr.sorbonne_u.components.exceptions.ComponentStartException;
 import fr.sorbonne_u.components.cyphy.tools.aclocks.ClockServerOutboundPort;
 import fr.sorbonne_u.exceptions.PreconditionException;
 */
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;
import fr.sorbonne_u.components.exceptions.ComponentStartException;
import fr.sorbonne_u.components.helpers.TracerWindow;

@OfferedInterfaces(offered = {ScriptManagementCI.class})
@RequiredInterfaces(required={ClockServerCI.class})
public class DesktopRoom extends AbstractComponent implements DesktopRoomCI, ScriptManagementCI{
		GlobalEnvFile env;
	    protected  Interpreter interpreter;
		protected final String reflectionInboundPortURI;
		//protected FenetreInstantanee FenetreInstantanee;
		protected final String				clockURI = Utils.CLOCK_URI; // todo : change it initialise in the constructor
		protected AcceleratedClock			clock;
		protected ClockServerOutboundPort	clockServerOBP;
		

		protected final DesktopRoomIBP		desktopRoomIBP;
		protected String coordonatorIBPURI;
		protected CoordonatorOBP coordinatorOBP;
		protected String[] neighboursURI;
		protected ArrayList<RoomOBP> neighboursOBP;
		protected HashMap<String, ISensor> sensors;

		
    protected DesktopRoom(String reflectionInboundPortURI, String clockURI, String coordonatorIBPURI, String []neighboursURI) throws Exception {
    	super(reflectionInboundPortURI,1, 1);
    	//assert	clockURI != null && !clockURI.isEmpty() :
		//	new PreconditionException(
		//			"clockURI != null && !clockURI.isEmpty()");
		this.reflectionInboundPortURI = reflectionInboundPortURI;
		this.desktopRoomIBP = new DesktopRoomIBP(reflectionInboundPortURI, this);
		this.desktopRoomIBP.publishPort();
		this.coordonatorIBPURI = coordonatorIBPURI;
		this.coordinatorOBP = new CoordonatorOBP(this);
		this.coordinatorOBP.publishPort();
		this.neighboursURI = neighboursURI;
		this.neighboursOBP = new ArrayList<RoomOBP>();
		for (int i = 0; i < neighboursURI.length; i++) {
			this.neighboursOBP.add(new RoomOBP(this));
			this.neighboursOBP.get(i).publishPort();
		}
		this.sensors = new HashMap<String, ISensor>();
		this.initialiseInterpreter();

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
	
	private void initialiseInterpreter() throws EvaluationException {
		StringWriter stdout = new StringWriter();
		IGlobalVariableEnvironment gve = new GlobalVariableEnvironment();
		GlobalVariableStuff.fillGlobalVariables(gve, stdout);
		IOperatorEnvironment oe = new OperatorEnvironment();
		OperatorStuff.fillUnaryOperators(oe);
		OperatorStuff.fillBinaryOperators(oe);
		this.interpreter = new Interpreter(gve, oe,this);
		
	}
    
	public void addSensor(String sensorName, ISensor sensor) {
		this.sensors.put(sensorName, sensor);
	}
    
	/* */
	
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
		/*
		this.clock = this.clockServerOBP.getClock(this.clockURI);
		System.out.println("clock = " + this.clock + " " + this.clock.getStartEpochNanos());
		//this.clock.setDebugLevel(2);
		//this.clock.start();
		//this.clockServerOBP.setClock(this.clockURI, this.clock);
		//this.clockServerOBP.startClock(this.clockURI);
		//this.clockServerOBP.startClock// get the centralised clock from the clock server.
		Thread.sleep(this.clock.waitUntilStartInMillis());
		*/
		/*this.scheduleTask(
				o -> {
					// TODO: every 10 seconds, eval all the sensors

					for (ISensor sensor : ((DesktopRoom)o).sensors.values()) {
						sensor.eval(((Object) clock).getCurrentTimeNanos());
						((DesktopRoom)o).logMessage("evaluating sensor " + sensor.getSensorName());
					}					
					
				},1, TimeUnit.SECONDS);
				*/
		//this.logMessage("continue.");
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
			this.desktopRoomIBP.unpublishPort();
			//this.clockServerOBP.unpublishPort();
		} catch (Exception e) {
			throw new ComponentShutdownException(e) ;
		}

		this.logMessage("shutdown.");
		super.shutdown();
	}
	
	/*
	*  TODO: choose the best version
	*  First version, when the script has to be executed by the next component in the graph 
	*	I am calling coordinatorOBP executeScript to find the next component to execute the script 
	*/
	/*
	* Second version, when the script has to be executed by the next component in the graph 
	* I am just returning the env and let the coordinator check if he need to find the next component to execute the script
	*/
	// function must be called by the coordinator or an other room with a thread to execute a script so it can be executed in parallel and not block the coordinator!
	public GlobalEnvFile executeScript(GlobalEnvFile env) throws EvaluationException
	{
		env.setNextComponentUri(reflectionInboundPortURI);
		env.clearVisited();
		IASTsequence sequence = GlobalFunctionAst.getInstance().getBody(env.getNameFunction());
		this.logMessage("executeScript()" + " had to execute script " + env.getNameFunction() + " index: " + env.getIndexNode()  + " / " + sequence.getExpressions().length);
		if (Utils.DEBUG_MODE_BCM)
			{sequence.show(env.getNameFunction());logMessage(sequence.toString(env.getNameFunction()));}
		logMessage(env.toString());
		Object result = sequence.accept(this.interpreter, env);
		logMessage("executeScript()" + " has executed script " + env.getNameFunction() + " index: " + env.getIndexNode()  + " / " + sequence.getExpressions().length);
		if (!env.isFinished() && !env.getNextComponentUri().equals(reflectionInboundPortURI))
		{
			// find the next component to execute the script
			this.logMessage("executeScript() had to find the next component to execute the script :" + env.getNextComponentUri());
			try
			{
				for (int i = 0; i < this.neighboursURI.length; i++) 
					if (this.neighboursURI[i].equals(env.getNextComponentUri()))
					{
						this.logMessage("executeScript() found the next component as a neighbour don't need to ask the coordinator !");
						final int j = i;
						this.runTask(
						new AbstractComponent.AbstractTask() {
							@Override
							public void run() {
								try {
									((DesktopRoom)this.getTaskOwner()).neighboursOBP.get(j).executeScript(env);
								} catch (Exception e) {
									e.printStackTrace();
								}
                    }
                }) ;
						return env;
					}
				// to change => maybe just return env and let the coordinator check if he need to find the next component to execute the script
				// do the same with a scedule task
				this.runTask(
                new AbstractComponent.AbstractTask() {
                    @Override
                    public void run() {
                        try {
                            ((DesktopRoom)this.getTaskOwner()).coordinatorOBP.executeScript(env, env.getNextComponentUri());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }) ;
				//this.coordinatorOBP.executeScript(env, env.getNextComponentUri());
			}
			catch(Exception e)
			{
				System.out.println("ERROR when executing script : " + e.getMessage() + "");
				return env;
			}
		}
		if (env.isFinished() && result instanceof Boolean)
		{
			env.setIsAccepted((Boolean)result);
			this.runTask(
                new AbstractComponent.AbstractTask() {
                    @Override
                    public void run() {
                        try {
                            ((DesktopRoom)this.getTaskOwner()).coordinatorOBP.executeScript(env, env.getNextComponentUri());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }) ;
		}
		logMessage("is finished ? " + env.isFinished() + " is accepted ? " + env.isAccepted() + "next component uri : " + env.getNextComponentUri());
		return env;
	}
	
	

	@Override
	public boolean hasMethod(String name) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object executeFunction(String name) {
		this.logMessage("[DesktopRoom] executeFunction()" + reflectionInboundPortURI +" had to execute script " + name);
		//this.logMessage("[DesktopRoom] executeFunction() clock" + this.clock.getStartEpochNanos());
		if (sensors.containsKey(name))
		{
			ISensor sensor = sensors.get(name);
			return sensor.getValue();
		}
		return new BigInteger("42");
	}

	public String getReflectionInboundPortURI() {
		return reflectionInboundPortURI;
	}

	// Unused methods from the interface ???
	@Override
	public GlobalEnvFile executeScript(GlobalEnvFile env, String uri) throws Exception {
		if (uri.equals(reflectionInboundPortURI))
		{
			return executeScript(env);
		}
		this.logMessage("[DesktopRoom] executeScript()" + reflectionInboundPortURI +" had to execute script " + env.getNameFunction() + " on " + uri);
		env.setNextComponentUri(uri);
		return coordinatorOBP.executeScript(env, uri);
	}

	@Override
	public String toString() {
		String s = "neighboursURI = [";
		for (int i = 0; i < neighboursURI.length - 1; i++) {
			s += neighboursURI[i] + " ";
		}
		if (neighboursURI.length > 0)
			s += neighboursURI[neighboursURI.length - 1];
		s += "]";
		return "DesktopRoom [" + reflectionInboundPortURI + "]" + s;
	}
    
}
