package com.paracamplus.bcm.components;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.StringWriter;

import com.paracamplus.bcm.BaseEnvFile;
import com.paracamplus.ilp1.ast.ASTfactory;
import com.paracamplus.ilp1.ast.ASTstring;
import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp1.interfaces.IASTfactory;
import com.paracamplus.ilp1.interfaces.IASTprogram;
import com.paracamplus.ilp1.interfaces.IASTsequence;
import com.paracamplus.ilp1.interfaces.IASTvariable;
import com.paracamplus.ilp1.interfaces.IASTvariableAssign;
import com.paracamplus.ilp1.interpreter.GlobalVariableEnvironment;
import com.paracamplus.ilp1.interpreter.GlobalVariableStuff;
import com.paracamplus.ilp1.interpreter.Interpreter;
import com.paracamplus.ilp1.interpreter.OperatorEnvironment;
import com.paracamplus.ilp1.interpreter.OperatorStuff;
import com.paracamplus.ilp1.interpreter.interfaces.EvaluationException;
import com.paracamplus.ilp1.interpreter.interfaces.IGlobalVariableEnvironment;
import com.paracamplus.ilp1.interpreter.interfaces.IOperatorEnvironment;
import com.paracamplus.ilp1.interpreter.test.InterpreterRunner;
import com.paracamplus.ilp1.interpreter.test.InterpreterTest;
import com.paracamplus.ilp1.parser.ilpml.ILPMLParser;
import com.paracamplus.ilp1.parser.xml.IXMLParser;
import com.paracamplus.ilp1.parser.xml.XMLParser;

public class DesktopRoom extends AbstractRoom {
	 	protected static String[] samplesDirName = { "SamplesILP1" }; 
	    protected static String pattern = ".*\\.ilpml";
	    protected static String XMLgrammarFile = "XMLGrammars/grammar1.rng";
	    BaseEnvFile env;
	
    protected DesktopRoom(int nbThreads, int nbSchedulableThreads) {
        super(nbThreads, nbSchedulableThreads);
        this.env = test();
        IASTexpression[] prog = env.getExpressions();
        
    
        
    }
    
    public void connectToFunction()
    {
    	IASTvariableAssign[] variables = env.getVariables(); 
    	for (int i = 0; i < variables.length; i++) {
    		variables[i].setExpression(new ASTstring("testParam" + i));
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
        Interpreter interpreter = new Interpreter(gve, oe);        
        run.setInterpreter(interpreter);
    }
    
    BaseEnvFile test()
    {
    	File file = new File(samplesDirName[0] + "/u02-1.ilpml");
    	System.out.println(samplesDirName[0]);
    	System.out.println(file.exists());
    	InterpreterTest it = new InterpreterTest( file);
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
    
    
}
