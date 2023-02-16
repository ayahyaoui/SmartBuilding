package com.paracamplus.bcm.components;

import java.io.StringWriter;
import java.util.ArrayList;

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
import com.paracamplus.bcm.obp.RoomOBP;
import com.paracamplus.bcm.utils.Utils;
/*
 * Maybe factorize rooms.
 */
public abstract class AbstractRoom extends AbstractComponent implements RoomI{

    protected  Interpreter interpreter;
    protected final String reflectionInboundPortURI;

    // hasmap of string and function
    protected final RoomIBP roomIBP;
    protected String coordonatorIBPURI;
	protected CoordonatorOBP coordinatorOBP;
	protected String[] neighboursURI;
	protected ArrayList<RoomOBP> neighboursOBP;
      

    protected AbstractRoom(int nbThreads, int nbSchedulableThreads, String reflectionInboundPortURI, 
            String coordinatorInboundPortURI, String []neighboursURI) throws Exception {
        super(nbThreads, nbSchedulableThreads);
        this.reflectionInboundPortURI = reflectionInboundPortURI;
        this.roomIBP = new RoomIBP(this);
        this.roomIBP.publishPort();
        this.coordonatorIBPURI = coordinatorInboundPortURI;
        this.coordinatorOBP = new CoordonatorOBP(this);
        this.coordinatorOBP.publishPort();
        this.neighboursURI = neighboursURI;
        this.neighboursOBP = new ArrayList<RoomOBP>();
        for (int i = 0; i < neighboursURI.length; i++) {
			this.neighboursOBP.add(new RoomOBP(this));
			this.neighboursOBP.get(i).publishPort();
		}
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

    public GlobalEnvFile executeScript(GlobalEnvFile env) throws EvaluationException
	{
		if (Utils.DEBUG_MODE_BCM)
			System.out.println("[Room] executeScript()" + " had to execute script index" + env.getIndexNode() );
		env.setNextComponentUri(reflectionInboundPortURI);
		IASTsequence sequence = GlobalFunctionAst.getInstance().getBody(env.getNameFunction());
		if (Utils.DEBUG_MODE_BCM)
			sequence.show(env.getNameFunction());
		sequence.accept(this.interpreter, env);
		if (Utils.DEBUG_MODE_BCM)
			System.out.println("[Room] executeScript()" + this.reflectionInboundPortURI + " has executed script index" + env.getIndexNode()  + " / " + sequence.getExpressions().length);
		if (!env.isFinished() && !env.getNextComponentUri().equals(reflectionInboundPortURI))
		{
			// find the next component to execute the script
			System.out.println("I am :" + reflectionInboundPortURI + " had to find the next component to execute the script :" + env.getNextComponentUri());
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

  
    public String getReflectionInboundPortURI() {
        return this.reflectionInboundPortURI;
    }

    public RoomIBP getRoomIBP() {
        return this.roomIBP;
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
}
