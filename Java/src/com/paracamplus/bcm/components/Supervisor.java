package com.paracamplus.bcm.components;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.StringWriter;

import java.util.Collection;

import java.util.Iterator;
import java.util.Vector;

import com.paracamplus.bcm.connector.CoordonatorConnector;
import com.paracamplus.bcm.ibp.CoordonatorIBP;
import com.paracamplus.bcm.interfaces.ScriptManagementCI;
import com.paracamplus.bcm.obp.DesktopRoomOBP;
import com.paracamplus.bcm.obp.SupervisorOBP;
import com.paracamplus.bcm.utils.Utils;
import com.paracamplus.ilp1.ast.ASTfactory;


import com.paracamplus.ilp1.interfaces.IASTfactory;
import com.paracamplus.ilp1.interfaces.IASTfunctionDefinition;
import com.paracamplus.ilp1.interfaces.IASTprogram;
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
import com.paracamplus.ilp1.interpreter.test.InterpreterRunner;
import com.paracamplus.ilp1.parser.ilpml.ILPMLParser;
import com.paracamplus.ilp1.parser.xml.IXMLParser;
import com.paracamplus.ilp1.parser.xml.XMLParser;
import com.paracamplus.ilp1.test.GlobalFunctionAst;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.cvm.AbstractCVM;
import fr.sorbonne_u.components.exceptions.ComponentStartException;
import fr.sorbonne_u.components.ports.PortI;

@RequiredInterfaces(required = {ScriptManagementCI.class})

public class Supervisor extends AbstractComponent{
	protected static String[] samplesDirName = { "SamplesRequest" }; 
    protected static String pattern = ".*\\.ilpml";
    protected static String XMLgrammarFile = "XMLGrammars/grammar1.rng";
    protected Interpreter interpreter;
	protected SupervisorOBP supervisorOBP;
    protected DesktopRoomOBP deskOBP;
    protected GlobalFunctionAst allGlobalFuction;
	protected String[] coordonatorIBPURIs;
	
	protected Supervisor(String reflectionInboundPortURI, String[] coordonatorIBPURIs) throws Exception  {
		super(reflectionInboundPortURI, 2, 1);
		assert	coordonatorIBPURIs != null && coordonatorIBPURIs.length > 0 ;
		this.supervisorOBP = new SupervisorOBP(this);
		this.supervisorOBP.publishPort();
		this.coordonatorIBPURIs = coordonatorIBPURIs;
		allGlobalFuction = GlobalFunctionAst.getInstance();

	}
	
	
    public static Collection<File[]> getFileList(
    		String[] samplesDirNames,
    		String pattern
    		) throws Exception {
    	Collection<File[]> result = new Vector<>();
    	File[] testFiles = Utils.getFileList(samplesDirNames, pattern);
    	for ( final File f : testFiles ) {
    		result.add(new File[]{ f });
    	}
        return result;
    }
	
	@Override
	public synchronized void	start() throws ComponentStartException
	{
		super.start();
		Collection<File[]> testFiles;
		try {
			testFiles = getFileList(samplesDirName, pattern);
			for (Iterator<File[]> iterator = testFiles.iterator(); iterator.hasNext();) {
				File[] files = (File[]) iterator.next();
				for (int i = 0; i < files.length; i++) {
					System.out.println("File:" + files[i].getName());
					test(files[i]); // add all function in GlobalFunctionAst
				}
			}
			for (int i = 0; i < coordonatorIBPURIs.length; i++) {
				//this.coordonatorIBP[i] = new CoordonatorIBP(this);
				//this.coordonatorIBP[i].publishPort();
				System.out.println("CoordonatorIBP:= = = = =" + coordonatorIBPURIs[i] + " " + this.supervisorOBP.getPortURI());
				PortI serverPort =
						AbstractCVM.getFromLocalRegistry(coordonatorIBPURIs[i]);
						if (serverPort != null) 
							System.out.println("yes it is not null" + serverPort);
				this.doPortConnection(
						this.supervisorOBP.getPortURI(),
						coordonatorIBPURIs[i],
						CoordonatorConnector.class.getCanonicalName());
				System.out.println("Is connected ?? " + supervisorOBP.connected());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ComponentStartException(e) ;
		}

		this.logMessage("start.");
	}
	
	// 2 way to call function (list of parameters or hashmap)
	public void callFunction(String functionName, String[] parameters)
	{
		  try {
			  System.out.println("callFunction supervisors");
			  GlobalEnvFile env = new GlobalEnvFile(functionName, parameters);
			  DesktopRoom room = new DesktopRoom("qwerty", "ss");
			  room.executeScript(env);
			  //variables[0].setExpression(new ASTstring(Utils.DESKTOPROOM_101_ID +" firstParam" ));
			  //	variables[1].setExpression(new ASTstring(Utils.DESKTOPROOM_101_ID +" secondParam"));
		  }catch (Exception e) {
			  e.printStackTrace();
			System.out.println("Impossible to call function:" + functionName);
		}
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
	    
	boolean	addFunction(IASTprogram program) throws Exception
	{
		
		//program.show("[AddFunction]");
		IASTfunctionDefinition f = program.getFunction();
		if (!(f instanceof IASTfunctionDefinition) || f == null)
			throw new Exception("The program must contain a function definition");
		if (f.getBody() == null || ! (f.getBody() instanceof IASTsequence))
			throw new Exception("The function must contain a body of type sequence");
		IASTsequence s = (IASTsequence) f.getBody();
		return GlobalFunctionAst.getInstance().addFunction(f.getName(), s, f.getVariables());
	}
	 
	    boolean test(File file)
	    {
	    	assert file.exists();
	        try {    	
	        	//System.out.println("++++++++++++++++++++++++++++++++++++++++" + file.getName());
	        	InterpreterRunner run = new InterpreterRunner();
	        	configureRunner(run);
	        	assertTrue(file.exists());
	            System.out.println("Starting Parsing file:" + file.getName());
	            IASTprogram program = run.getParser().parse(file);
	            return (addFunction(program));
	        } catch(Exception e) {
	            e.printStackTrace();
	            return false;
	        }
	    }

		public void callFunction(GlobalEnvFile env) throws Exception
	{
		final Supervisor vc = this ;
		  try {
			(new Thread() {
				public void run() {
					try {
						// This call blocks because the continuation needs
						// the result to be executed (even using an
						// asynchronous call with a future variable would
						// lead to block this component thread.
						GlobalEnvFile result = vc.supervisorOBP.executeScript(env) ;
						// To avoid perturbing the potential mutual
						// exclusion properties of the component, the
						// continuation must be run by a component
						// thread, hence the handleRequest.
						/*vc.runTask(
							new AbstractComponent.AbstractTask() {
								@Override
								public void run() {
									vc.computeAndThenPrintContinuation(result) ;
								}
							}) ;*/
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}).start() ;
			  //variables[0].setExpression(new ASTstring(Utils.DESKTOPROOM_101_ID +" firstParam" ));
			  //	variables[1].setExpression(new ASTstring(Utils.DESKTOPROOM_101_ID +" secondParam"));
		  }catch (Exception e) {
			  e.printStackTrace();
			System.out.println("Impossible to call function:");
		}
	}
	    
	    @Override
	    public void execute() throws Exception {
	    	super.execute();
	    	// do conction with component deskoptroom with Utils.DESKTOPROOM_101_ID
				//	DesktopRoomOutboundPort d = new DesktopRoomOutboundPort(this);
	    	System.out.println("try to call function");
			String[] parameters = new String[] {Utils.DESKTOPROOM_101_ID, Utils.DESKTOPROOM_102_ID};
			//callFunction("fire",parameters);
			GlobalEnvFile env = new GlobalEnvFile("fire", parameters);
			this.runTask(
			new AbstractComponent.AbstractTask() {
				@Override
				public void run() {
					try {
						((Supervisor)this.getTaskOwner()).
							callFunction(env); ;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}) ;

							}
	    
	    

}
