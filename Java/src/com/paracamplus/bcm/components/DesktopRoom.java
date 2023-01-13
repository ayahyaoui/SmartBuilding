package com.paracamplus.bcm.components;

import static org.junit.Assert.assertTrue;


import com.paracamplus.ilp1.ast.ASTstring;
import com.paracamplus.ilp1.interfaces.IASTvariableAssign;
import com.paracamplus.ilp1.interpreter.GlobalEnvFile;
import com.paracamplus.ilp1.interpreter.Interpreter;
import com.paracamplus.ilp1.interpreter.interfaces.EvaluationException;

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
	
    protected DesktopRoom(String reflectionInboundPortURI, String clockURI) {
    	super(reflectionInboundPortURI, 1, 1);
    	assert	clockURI != null && !clockURI.isEmpty() :
			new PreconditionException(
					"clockURI != null && !clockURI.isEmpty()");

    	this.clockURI = clockURI;
    	
        //this.env = test();
        //IASTexpression[] prog = env.getExpressions();
        //connectToFunction();
        
            //env.getExpressions()[i].visit(new Interpreter(env, new OperatorEnvironment()));
    }
    
    public void connectToFunction()
    {
    	IASTvariableAssign[] variables = env.getVariables(); 
    	for (int i = 0; i < variables.length; i++) {
    		variables[i].setExpression(new ASTstring("testParam" + i));
    	}
    	
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
        try {
			env.getExpressions().accept(this.interpreter, env);
		} catch (EvaluationException e) {
			e.printStackTrace();
		}
        /*
		// get the centralised clock from the clock server.
		this.clock = this.clockServerOBP.getClock(this.clockURI);
		// wait for the Unix epoch start time to execute the actions
		Thread.sleep(this.clock.waitUntilStartInMillis());
		this.logMessage("bureau ; fenetre fermee");
		Instant i0 = clock.getStartInstant();
		Instant i1 = i0.plusSeconds(3600); // i0 + 1:00 hour
		long delay1 = clock.delayToAcceleratedInstantInNanos(i1);
		
		this.scheduleTask(
				o -> {	((Bureau)o).FenetreInstantanee.setOuverte(true);
						((Bureau)o).logMessage(
								"at " + i1 +
								" Capteur Fenetre Instantanée retourne " + ((Bureau)o).FenetreInstantanee.getOuverte() );
					 },
				delay1,
				TimeUnit.NANOSECONDS);
		this.logMessage("continue.");
		*/
	}
	
	public GlobalEnvFile executeScript(GlobalEnvFile env)
	{
		System.out.println("DesktopRoom has to execute script");
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
