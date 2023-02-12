package com.paracamplus.bcm.components;

import static org.junit.Assert.assertTrue;

import java.io.StringWriter;
import java.math.BigInteger;

import com.paracamplus.bcm.connector.CoordonatorConnector;
import com.paracamplus.bcm.ibp.DesktopRoomIBP;
import com.paracamplus.bcm.interfaces.DesktopRoomCI;
import com.paracamplus.bcm.interfaces.RoomI;

import com.paracamplus.bcm.interfaces.ScriptManagementCI;
import com.paracamplus.bcm.obp.CoordonatorOBP;
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

@OfferedInterfaces(offered = {ScriptManagementCI.class})
public class DesktopRoom extends AbstractComponent implements DesktopRoomCI{
	 	/*protected static String[] samplesDirName = { "SamplesILP1" }; 
	    protected static String pattern = ".*\\.ilpml";
	    protected static String XMLgrammarFile = "XMLGrammars/grammar1.rng";
	    */
		GlobalEnvFile env;
	    protected  Interpreter interpreter;
		protected final String reflectionInboundPortURI;
	    //protected int nbPersonnes ;
		//protected int nbFenetres ;
		//protected FenetreInstantanee FenetreInstantanee;
		//protected final String				clockURI;
		//protected AcceleratedClock			clock;
		//protected ClockServerOutboundPort	clockServerOBP;

		protected final DesktopRoomIBP		desktopRoomIBP;
		protected String coordonatorIBPURI;
		protected CoordonatorOBP coordinatorOBP;
		
    protected DesktopRoom(String reflectionInboundPortURI, String clockURI, String coordonatorIBPURI) throws Exception {
    	super(reflectionInboundPortURI,1, 1);
    	//assert	clockURI != null && !clockURI.isEmpty() :
		//	new PreconditionException(
		//			"clockURI != null && !clockURI.isEmpty()");
		this.reflectionInboundPortURI = reflectionInboundPortURI;
		this.desktopRoomIBP = new DesktopRoomIBP(reflectionInboundPortURI, this);
		this.desktopRoomIBP.publishPort();
		this.coordinatorOBP = new CoordonatorOBP(this);
		this.coordinatorOBP.publishPort();
		this.coordonatorIBPURI = coordonatorIBPURI;
    	//this.clockURI = clockURI;
		StringWriter stdout = new StringWriter();
        //run.setStdout(stdout);
    	IGlobalVariableEnvironment gve = new GlobalVariableEnvironment();
        GlobalVariableStuff.fillGlobalVariables(gve, stdout);
        IOperatorEnvironment oe = new OperatorEnvironment();
        OperatorStuff.fillUnaryOperators(oe);
        OperatorStuff.fillBinaryOperators(oe);
        this.interpreter = new Interpreter(gve, oe,this);
		//this.interpreter = new Interpreter(new GlobalVariableEnvironment() , new OperatorEnvironment());
    	
        //this.env = test();
        //IASTexpression[] prog = env.getExpressions();
        //connectToFunction();
        
        //env.getExpressions()[i].visit(new Interpreter(env, new OperatorEnvironment()));
    }
    
    public void connectToFunction()
    {
    	/*IASTvariableAssign[] variables = env.getVariables(); 
    	for (int i = 0; i < variables.length; i++) {
    		variables[i].setExpression(new ASTstring("testParam" + i));
    	}*/
    	
    }
    
	/* */
	
    @Override
	public synchronized void	start() throws ComponentStartException
	{
		super.start();

		try {
			if (Utils.DEBUG_MODE_BCM)
				System.out.println("[DesktopRoom] start()" + "connextion" + this.coordinatorOBP.getPortURI() + " " + coordonatorIBPURI);
				this.doPortConnection(
						this.coordinatorOBP.getPortURI(),
						coordonatorIBPURI,
						CoordonatorConnector.class.getCanonicalName());
			} catch (Exception e) {
				throw new ComponentStartException(e) ;
			}
			
			this.logMessage("start.");
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
	public GlobalEnvFile executeScript(GlobalEnvFile env) throws EvaluationException
	{
		if (Utils.DEBUG_MODE_BCM)
			System.out.println("[DesktopRoom] executeScript()" + " had to execute script index" + env.getIndexNode() );
		env.setNextComponentUri(reflectionInboundPortURI);
		IASTsequence sequence = GlobalFunctionAst.getInstance().getBody(env.getNameFunction());
		if (Utils.DEBUG_MODE_BCM)
			sequence.show("FIRE");
		sequence.accept(this.interpreter, env);
		if (Utils.DEBUG_MODE_BCM)
			System.out.println("[DesktopRoom] executeScript()" + this.reflectionInboundPortURI + " has executed script index" + env.getIndexNode()  + " / " + sequence.getExpressions().length);
			if (!env.isFinished() && !env.getNextComponentUri().equals(reflectionInboundPortURI))
		 	{
				// find the next component to execute the script
			 	System.out.println("I am :" + reflectionInboundPortURI + " had to find the next component to execute the script :" + env.getNextComponentUri());
			 	try
			 	{
					coordinatorOBP.executeScript(env, env.getNextComponentUri());
				}
				catch(Exception e)
				{
					System.out.println("ERROR when executing script : " + e.getMessage() + "");
					return env;
				}
			}
		return env;
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
			//this.clockServerOBP.unpublishPort();
		} catch (Exception e) {
			throw new ComponentShutdownException(e) ;
		}

		this.logMessage("shutdown.");
		super.shutdown();
	}

	@Override
	public boolean hasMethod(String name) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object executeFunction(String name) {
		System.out.println("[DesktopRoom] executeFunction()" + reflectionInboundPortURI +" had to execute script " + name);
		return 42;
	}

	public String getReflectionInboundPortURI() {
		return reflectionInboundPortURI;
	}


    
}
