package com.paracamplus.bcm.components;

import java.io.StringWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

import com.paracamplus.bcm.ibp.RoomIBP;
import com.paracamplus.bcm.interfaces.RoomI;
import com.paracamplus.bcm.obp.CoordonatorOBP;
import com.paracamplus.ilp1.interfaces.IASTsequence;
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
import fr.sorbonne_u.components.cyphy.tools.aclocks.AcceleratedClock;
import fr.sorbonne_u.components.cyphy.tools.aclocks.ClockServerOutboundPort;
import fr.sorbonne_u.exceptions.PreconditionException;

import com.paracamplus.bcm.obp.RoomOBP;
import com.paracamplus.bcm.sensor.ISensor;
import com.paracamplus.bcm.utils.Utils;
/*
 * Maybe factorize rooms.
 * 
 */
public abstract class AbstractRoom extends AbstractComponent implements RoomI{

    protected  Interpreter interpreter;
    protected final String reflectionInboundPortURI;

    protected final String				clockURI;
    protected AcceleratedClock			clock;
    protected ClockServerOutboundPort	clockServerOBP;
    protected HashMap<String, ISensor> sensors;
    
    // hasmap of string and function
    //protected final RoomIBP roomIBP;
    protected String coordonatorIBPURI;
	protected CoordonatorOBP coordinatorOBP;
	protected String[] neighboursURI;
	protected ArrayList<RoomOBP> neighboursOBP;
      

    protected AbstractRoom(int nbThreads, int nbSchedulableThreads, String reflectionInboundPortURI, String clockURI, 
            String coordinatorInboundPortURI, String []neighboursURI) throws Exception {
        super(nbThreads, nbSchedulableThreads);
        assert	clockURI != null && !clockURI.isEmpty() :
			new PreconditionException("clockURI != null && !clockURI.isEmpty()");
        this.clockURI = clockURI;
        this.reflectionInboundPortURI = reflectionInboundPortURI;
        this.coordonatorIBPURI = coordinatorInboundPortURI;
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

    	// Unused methods from the interface ???
	@Override
	public GlobalEnvFile executeScript(GlobalEnvFile env, String uri) throws Exception {
		if (uri.equals(reflectionInboundPortURI))
		{
			return executeScript(env);
		}
		this.logMessage("executeScript()" + reflectionInboundPortURI +" had to execute script " + env.getNameFunction() + " on " + uri);
		env.setNextComponentUri(uri);
		return coordinatorOBP.executeScript(env, uri);
	}

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
									((AbstractRoom)this.getTaskOwner()).neighboursOBP.get(j).executeScript(env);
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
                            ((AbstractRoom)this.getTaskOwner()).coordinatorOBP.executeScript(env, env.getNextComponentUri());
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
                            ((AbstractRoom)this.getTaskOwner()).coordinatorOBP.executeScript(env, env.getNextComponentUri());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }) ;
		}
		logMessage("is finished ? " + env.isFinished() + " is accepted ? " + env.isAccepted() + "next component uri : " + env.getNextComponentUri());
		return env;
	}


    /* 
    public GlobalEnvFile executeScript(GlobalEnvFile env) throws EvaluationException
	{
		
		env.setNextComponentUri(reflectionInboundPortURI);

		IASTsequence sequence = GlobalFunctionAst.getInstance().getBody(env.getNameFunction());
		if (Utils.DEBUG_MODE_BCM)
			sequence.show(env.getNameFunction());
		sequence.accept(this.interpreter, env);
		
		if (!env.isFinished() && !env.getNextComponentUri().equals(reflectionInboundPortURI))
		{
			
			try
			{
				for (int i = 0; i < this.neighboursURI.length; i++) {
					if (this.neighboursURI[i].equals(env.getNextComponentUri()))
						return this.neighboursOBP.get(i).executeScript(env);
				}
				// to change => maybe just return env and let the coordinator check if he need to find the next component to execute the script
				this.coordinatorOBP.executeScript(env, env.getNextComponentUri());
			}
			catch(Exception e)
			{
				System.out.println("ERROR when executing script : " + e.getMessage() + "");
				return env;
			}
		}
		return env;
	}
*/
  
    public void addSensor(String sensorName, ISensor sensor) {
        this.sensors.put(sensorName, sensor);
    }

    @Override
    public Object executeFunction(String name) {
        this.logMessage("executeFunction()" + reflectionInboundPortURI +" had to execute script " + name);
        if (sensors.containsKey(name))
        {
            ISensor sensor = sensors.get(name);
            return sensor.getValue();
        }
        return new BigInteger("42");
    }

    public String getReflectionInboundPortURI() {
        return this.reflectionInboundPortURI;
    }

    public String getCoordonatorIBPURI() {
        return this.coordonatorIBPURI;
    }

    public CoordonatorOBP getCoordinatorOBP() {
        return this.coordinatorOBP;
    }

    public String[] getNeighboursURI() {
        return this.neighboursURI;
    }

    public ArrayList<RoomOBP> getNeighboursOBP() {
        return this.neighboursOBP;
    }

    public void setCoordonatorIBPURI(String coordonatorIBPURI) {
        this.coordonatorIBPURI = coordonatorIBPURI;
    }

    public void setCoordinatorOBP(CoordonatorOBP coordinatorOBP) {
        this.coordinatorOBP = coordinatorOBP;
    }

    public void setNeighboursURI(String[] neighboursURI) {
        this.neighboursURI = neighboursURI;
    }

    public void setNeighboursOBP(ArrayList<RoomOBP> neighboursOBP) {
        this.neighboursOBP = neighboursOBP;
    }

    public void setInterpreter(Interpreter interpreter) {
        this.interpreter = interpreter;
    }

    public Interpreter getInterpreter() {
        return this.interpreter;
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
		return "[" + reflectionInboundPortURI + "]" + s;
	}
}