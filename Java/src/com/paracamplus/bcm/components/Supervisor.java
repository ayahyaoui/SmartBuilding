package com.paracamplus.bcm.components;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.StringWriter;
import java.util.ArrayList;

import com.paracamplus.bcm.obp.DesktopRoomOBP;
import com.paracamplus.bcm.utils.Utils;
import com.paracamplus.ilp1.ast.ASTfactory;
import com.paracamplus.ilp1.ast.ASTstring;
import com.paracamplus.ilp1.interfaces.IASTfactory;
import com.paracamplus.ilp1.interfaces.IASTprogram;
import com.paracamplus.ilp1.interfaces.IASTvariableAssign;
import com.paracamplus.ilp1.interpreter.BaseEnvFile;
import com.paracamplus.ilp1.interpreter.GlobalVariableEnvironment;
import com.paracamplus.ilp1.interpreter.GlobalVariableStuff;
import com.paracamplus.ilp1.interpreter.Interpreter;
import com.paracamplus.ilp1.interpreter.OperatorEnvironment;
import com.paracamplus.ilp1.interpreter.OperatorStuff;
import com.paracamplus.ilp1.interpreter.interfaces.EvaluationException;
import com.paracamplus.ilp1.interpreter.interfaces.IGlobalVariableEnvironment;
import com.paracamplus.ilp1.interpreter.interfaces.IOperatorEnvironment;
import com.paracamplus.ilp1.interpreter.test.InterpreterRunner;
import com.paracamplus.ilp1.parser.ilpml.ILPMLParser;
import com.paracamplus.ilp1.parser.xml.IXMLParser;
import com.paracamplus.ilp1.parser.xml.XMLParser;

import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.cyphy.tools.aclocks.ClockServer;
import fr.sorbonne_u.components.cyphy.tools.aclocks.ClockServerConnector;
import fr.sorbonne_u.components.exceptions.ComponentStartException;

public class Supervisor extends AbstractComponent{
	protected static String[] samplesDirName = { "SamplesILP1" }; 
    protected static String pattern = ".*\\.ilpml";
    protected static String XMLgrammarFile = "XMLGrammars/grammar1.rng";
    protected Interpreter interpreter;
    protected ArrayList<BaseEnvFile> allEnv;
    DesktopRoomOBP deskOBP;
	
	protected Supervisor(String reflectionInboundPortURI, int nbThreads, int nbSchedulableThreads) {
		super(reflectionInboundPortURI, nbThreads, nbSchedulableThreads);
		allEnv = new ArrayList<BaseEnvFile>();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public synchronized void	start() throws ComponentStartException
	{
		super.start();
		allEnv.add(test());
		connectToFunction(allEnv.get(0));
		

		/*
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
		 */
		this.logMessage("start.");
	}
	  public void connectToFunction(BaseEnvFile env)
	    {
	    	IASTvariableAssign[] variables = env.getVariables(); 
	    	variables[0].setExpression(new ASTstring(Utils.DESKTOPROOM_101_ID +" firstParam" ));
	    	variables[1].setExpression(new ASTstring(Utils.DESKTOPROOM_101_ID +" secondParam"));
	    }
	 public void  configureRunner(InterpreterRunner run) throws EvaluationException {
	    	// configuration du parseur
	        IASTfactory factory = new ASTfactory();
	        IXMLParser xmlParser = new XMLParser(factory);
	        xmlParser.setGrammar(new File(XMLgrammarFile));
	        run.setXMLParser(xmlParser);
	        run.setILPMLParser(new ILPMLParser(factory));

	        // configuration de l'interprète
	        StringWriter stdout = new StringWriter();
	        run.setStdout(stdout);
	        IGlobalVariableEnvironment gve = new GlobalVariableEnvironment();
	        GlobalVariableStuff.fillGlobalVariables(gve, stdout);
	        IOperatorEnvironment oe = new OperatorEnvironment();
	        OperatorStuff.fillUnaryOperators(oe);
	        OperatorStuff.fillBinaryOperators(oe);
	        this.interpreter = new Interpreter(gve, oe);        
	        run.setInterpreter(interpreter);
	    }
	    
	    BaseEnvFile test()
	    {
	    	File file = new File(samplesDirName[0] + "/u02-1.ilpml");
	    	System.out.println(samplesDirName[0]);
	    	System.out.println(file.exists());
	        try {    	
	        	System.out.println("++++++++++++++++++++++++++++++++++++++++");
	        	InterpreterRunner run = new InterpreterRunner();
	        	configureRunner(run);
	        	assertTrue(file.exists());
	            System.out.println("Starting Parsing");
	            IASTprogram program = run.getParser().parse(file);
	            return (new BaseEnvFile(program));
	        } catch(Exception e) {
	            e.printStackTrace();
	            return null;
	        }
	    }
	    
	    @Override
	    public void execute() throws Exception {
	    	super.execute();
	    	// do conction with component deskoptroom with Utils.DESKTOPROOM_101_ID
				//	DesktopRoomOutboundPort d = new DesktopRoomOutboundPort(this);
	    	this.deskOBP = new DesktopRoomOBP(this) ;
			this.deskOBP.publishPort();
			this.doPortConnection(
								this.deskOBP.getPortURI(),
								Utils.DESKTOPROOM_101_ID,
								DesktopRoom.class.getCanonicalName());
	    }
	    
	    

}
