package com.paracamplus.bcm.components;

import static org.junit.Assert.assertTrue;

import java.io.StringWriter;

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
import fr.sorbonne_u.components.cyphy.tools.aclocks.AcceleratedClock;
import fr.sorbonne_u.components.cyphy.tools.aclocks.ClockServer;
import fr.sorbonne_u.components.cyphy.tools.aclocks.ClockServerConnector;
import fr.sorbonne_u.components.cyphy.tools.aclocks.ClockServerOutboundPort;
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;
import fr.sorbonne_u.components.exceptions.ComponentStartException;
import fr.sorbonne_u.exceptions.PreconditionException;

public class DesktopRoom extends AbstractComponent {
	 	/*protected static String[] samplesDirName = { "SamplesILP1" }; 
	    protected static String pattern = ".*\\.ilpml";
	    protected static String XMLgrammarFile = "XMLGrammars/grammar1.rng";
	    */
		GlobalEnvFile env;
	    protected  Interpreter interpreter;
	    //protected int nbPersonnes ;
		protected int nbFenetres ;
		//protected FenetreInstantanee FenetreInstantanee ;

		protected final String				clockURI;
		protected AcceleratedClock			clock;
		protected ClockServerOutboundPort	clockServerOBP;
	
    protected DesktopRoom(String reflectionInboundPortURI, String clockURI) throws EvaluationException {
    	super(reflectionInboundPortURI, 1, 1);
    	assert	clockURI != null && !clockURI.isEmpty() :
			new PreconditionException(
					"clockURI != null && !clockURI.isEmpty()");

    	this.clockURI = clockURI;
		StringWriter stdout = new StringWriter();
        //run.setStdout(stdout);
    	IGlobalVariableEnvironment gve = new GlobalVariableEnvironment();
        GlobalVariableStuff.fillGlobalVariables(gve, stdout);
        IOperatorEnvironment oe = new OperatorEnvironment();
        OperatorStuff.fillUnaryOperators(oe);
        OperatorStuff.fillBinaryOperators(oe);
        this.interpreter = new Interpreter(gve, oe);
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
    
   
    @Override
	public synchronized void	start() throws ComponentStartException
	{
		super.start();

		try {
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
	
	@Override
	public void			execute() throws Exception
	{
		System.out.println("+--+-+-+-+-+-+-+-+-+-+-+ START Interpret with component +--+-+-+-+-+-+-+-+-+-+-+");
        System.out.println("exec");
		//env.getExpressions().accept(this.interpreter, env);
	}
	
	public GlobalEnvFile executeScript(GlobalEnvFile env) throws EvaluationException
	{
		System.out.println("DesktopRoom has to execute script");
		IASTsequence sequence = GlobalFunctionAst.getInstance().getBody(env.getNameFunction());
		sequence.accept(this.interpreter, env);
		
		return env;
	}
	
	@Override
	public synchronized void	finalise() throws Exception
	{
		this.doPortDisconnection(this.clockServerOBP.getPortURI());
		super.finalise();
	}
	
	
	@Override
	public synchronized void	shutdown() throws ComponentShutdownException
	{
		try {
			this.clockServerOBP.unpublishPort();
		} catch (Exception e) {
			throw new ComponentShutdownException(e) ;
		}

		this.logMessage("shutdown.");
		super.shutdown();
	}

    
}
