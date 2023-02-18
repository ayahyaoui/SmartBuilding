/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp1.interpreter;

import java.util.List;
import java.util.Vector;

import com.paracamplus.bcm.interfaces.RoomI;
import com.paracamplus.ilp1.ast.ASTvariable;
import com.paracamplus.ilp1.interfaces.IASTalternative;
import com.paracamplus.ilp1.interfaces.IASTassignment;
import com.paracamplus.ilp1.interfaces.IASTbinaryOperation;
import com.paracamplus.ilp1.interfaces.IASTblock;
import com.paracamplus.ilp1.interfaces.IASTblock.IASTbinding;
import com.paracamplus.ilp1.interfaces.IASTboolean;
import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp1.interfaces.IASTfloat;
import com.paracamplus.ilp1.interfaces.IASTfunctionDefinition;
import com.paracamplus.ilp1.interfaces.IASTinteger;
import com.paracamplus.ilp1.interfaces.IASTinvocSensor;
import com.paracamplus.ilp1.interfaces.IASTinvocation;
import com.paracamplus.ilp1.interfaces.IASToperator;
import com.paracamplus.ilp1.interfaces.IASTprogram;
import com.paracamplus.ilp1.interfaces.IASTreadField;
import com.paracamplus.ilp1.interfaces.IASTsequence;
import com.paracamplus.ilp1.interfaces.IASTstring;
import com.paracamplus.ilp1.interfaces.IASTunaryOperation;
import com.paracamplus.ilp1.interfaces.IASTvariable;
import com.paracamplus.ilp1.interfaces.IASTvariableAssign;
import com.paracamplus.ilp1.interfaces.IASTvisitor;
import com.paracamplus.ilp1.interpreter.interfaces.EvaluationException;
import com.paracamplus.ilp1.interpreter.interfaces.IGlobalVariableEnvironment;
import com.paracamplus.ilp1.interpreter.interfaces.ILexicalEnvironment;
import com.paracamplus.ilp1.interpreter.interfaces.IOperator;
import com.paracamplus.ilp1.interpreter.interfaces.IOperatorEnvironment;
import com.paracamplus.ilp1.interpreter.interfaces.Invocable;

//todo Verbose
public class Interpreter
implements IASTvisitor<Object, ILexicalEnvironment, EvaluationException> {
    
    protected static final boolean VERBOSE = false;

    public Interpreter (IGlobalVariableEnvironment globalVariableEnvironment,
                        IOperatorEnvironment operatorEnvironment ) {
        this.globalVariableEnvironment = globalVariableEnvironment;
        this.operatorEnvironment = operatorEnvironment;
        this.owner = null;
    }
    
    public Interpreter (IGlobalVariableEnvironment globalVariableEnvironment,
	            IOperatorEnvironment operatorEnvironment , RoomI owner) {
	this.globalVariableEnvironment = globalVariableEnvironment;
	this.operatorEnvironment = operatorEnvironment;
	this.owner = owner;
	}

    protected IGlobalVariableEnvironment globalVariableEnvironment;
    protected IOperatorEnvironment operatorEnvironment;
    protected RoomI owner;

    public IOperatorEnvironment getOperatorEnvironment() {
        return operatorEnvironment;
    }
    
    public IGlobalVariableEnvironment getGlobalVariableEnvironment() {
        return globalVariableEnvironment;
    }
    
    public Object visit(IASTprogram iast, ILexicalEnvironment lexenv) 
            throws EvaluationException {
        try {
            return iast.getBody().accept(this, lexenv);
        } catch (Exception exc) {
            return exc;
        }
    }
    
    @Override
    public Object visit(IASTfunctionDefinition iast, ILexicalEnvironment data)
    		throws EvaluationException {
    	Invocable fun = new Function(iast.getVariables(),
    			iast.getBody(),
    			new EmptyLexicalEnvironment());
    	return fun;
    }
    
    private static Object whatever = "whatever";
            
    @Override
	public Object visit(IASTalternative iast, ILexicalEnvironment lexenv) 
            throws EvaluationException {
    	if (VERBOSE) System.out.println("Visit...alternatssive");
        Object c = iast.getCondition().accept(this, lexenv);
        if (VERBOSE) System.out.println("Visit...alternative...condition" + c);
        if ( c != null && c instanceof Boolean ) {
            Boolean b = (Boolean) c;
            if (VERBOSE) System.out.println("result if" + b);

            if (b.booleanValue() ) { // if true just continue
                return true; //return iast.getConsequence().accept(this, lexenv);
            } else {
                return whatever; // try to finish the block
            }
        } else {
        	if (VERBOSE) System.out.println("Condition bad format..");
        	 return whatever;

        }
    }

    @Override
	public Object visit(IASTunaryOperation iast, ILexicalEnvironment lexenv) 
            throws EvaluationException {
        Object operand = iast.getOperand().accept(this, lexenv);
        IASToperator operator = iast.getOperator();
        IOperator op = getOperatorEnvironment().getUnaryOperator(operator);
        return op.apply(operand);
    }
    
    @Override
	public Object visit(IASTbinaryOperation iast, ILexicalEnvironment lexenv) 
            throws EvaluationException {
        Object leftOperand = iast.getLeftOperand().accept(this, lexenv);
        Object rightOperand = iast.getRightOperand().accept(this, lexenv);
        IASToperator operator = iast.getOperator();	
        IOperator op = getOperatorEnvironment().getBinaryOperator(operator);
        if (VERBOSE) System.out.println("visit binary operation" + leftOperand + " " + rightOperand + " " + operator + " " + op);
        return op.apply(leftOperand, rightOperand);
    }

    /*
     * TODO upgrade
     * version simplifie, on pars du principe qu'il y a un une seule sequence et qu'elle renvoie true ou false
     */
    @Override
	public Object visit(IASTsequence iast, ILexicalEnvironment lexenv) 
            throws EvaluationException {
        IASTexpression[] expressions = iast.getExpressions();
        Object lastValue = null;
        if (VERBOSE) System.out.println("visit Sequence..." + expressions);
        for (int i = lexenv.getIndexNode() ; i < expressions.length; i++) {
        	lexenv.setIndexNode(i);
        	if (VERBOSE) System.out.println("visit Sequence will accept loop" + expressions[i] + "index: " + i + " or " + lexenv.getIndexNode());
        	lastValue = expressions[i].accept(this, lexenv);
        	if (VERBOSE) System.out.println("visit Sequence has accepted loop");
            if (!lexenv.getNextComponentUri().equals(owner.getReflectionInboundPortURI())) // has to change component
            {
                if (VERBOSE) System.out.println("[Interpreter] visit Sequence has to change component " + owner.getReflectionInboundPortURI() + " -> " + lexenv.getNextComponentUri() + " (" + lexenv.getIndexNode() + ")");
                return lastValue;
            }
        	if ((lastValue != null && lastValue.equals(whatever)))
            {   
                if (VERBOSE) System.out.println("visit Sequence will return false" + lexenv.getIndexNode() + " " + lexenv.getNextComponentUri() + " " + owner.getReflectionInboundPortURI() + " ");
                lexenv.setIsFinished(true);
                return (Boolean)false;
            }
		}
        lexenv.setIsFinished(true);
        if (VERBOSE) System.out.println("visit Sequence after loop");
        return (Boolean)true;
    }
    
    @Override
	public Object visit(IASTblock iast, ILexicalEnvironment lexenv) 
            throws EvaluationException {
        ILexicalEnvironment lexenv2 = lexenv;
        for ( IASTbinding binding : iast.getBindings() ) {
            Object initialisation = 
                    binding.getInitialisation().accept(this, lexenv);
            lexenv2 = lexenv2.extend(binding.getVariable(), initialisation);
        }
        return iast.getBody().accept(this, lexenv2);
    }

    @Override
	public Object visit(IASTboolean iast, ILexicalEnvironment lexenv) 
            throws EvaluationException {
        return iast.getValue();
    }
    
    @Override
	public Object visit(IASTinteger iast, ILexicalEnvironment lexenv) 
            throws EvaluationException {
    	if (VERBOSE) System.out.println("find value " + iast.getValue());
        return iast.getValue();
    }
    
    @Override
	public Object visit(IASTfloat iast, ILexicalEnvironment lexenv) 
            throws EvaluationException {
        return iast.getValue();
    }
    
    @Override
	public Object visit(IASTstring iast, ILexicalEnvironment lexenv) 
            throws EvaluationException {
        return iast.getValue();
    }

    @Override
	public Object visit(IASTvariable iast, ILexicalEnvironment lexenv) 
            throws EvaluationException {
        try {
            if (VERBOSE) System.out.println("visit variable");
            if (VERBOSE) System.out.println("find value " + iast.getName() + " " + lexenv.getValue(iast));
            return lexenv.getValue(iast);
        } catch (EvaluationException exc) {
            return getGlobalVariableEnvironment()
                    .getGlobalVariableValue(iast.getName());
        }
    }
    
    @Override
	public Object visit(IASTinvocation iast, ILexicalEnvironment lexenv) 
            throws EvaluationException {
        Object function = iast.getFunction().accept(this, lexenv);
        if ( function instanceof Invocable ) {
            Invocable f = (Invocable)function;
            List<Object> args = new Vector<Object>();
            for ( IASTexpression arg : iast.getArguments() ) {
                Object value = arg.accept(this, lexenv);
                args.add(value);
            }
            return f.apply(this, args.toArray());
        } else {
            String msg = "Cannot apply " + function;
            throw new EvaluationException(msg);
        }
    }
    
    @Override
	public Object visit(IASTinvocSensor iast, ILexicalEnvironment lexenv) 
            throws EvaluationException {
        Object function = iast.getFunction().accept(this, lexenv);
        if ( function instanceof Invocable ) {
            Invocable f = (Invocable)function;
            return f.apply(this, null);
        } else {
            String msg = "Cannot apply " + function;
            throw new EvaluationException(msg);
        }
    }

	@Override
	public Object visit(IASTassignment iast, ILexicalEnvironment lexenv) 
            throws EvaluationException {
        IASTvariable variable = iast.getVariable();
        if (VERBOSE) System.out.println("++visit assign " + variable.getName());
        Object value = iast.getExpression().accept(this, lexenv);
        if (VERBOSE) System.out.println("==visit assign " + variable.getName() + " value " + value + " " + value.getClass());
        try {
            lexenv.update(variable, value);
        } catch (EvaluationException exc) {
            getGlobalVariableEnvironment()
                .updateGlobalVariableValue(variable.getName(), value);
        }
        return value;
    }

	@Override
	public Object visit(IASTvariableAssign asTassignment, ILexicalEnvironment data) throws EvaluationException {
		if (VERBOSE) System.out.println("Not implemented yet");
		return null;
	}

	@Override
	public Object visit(IASTreadField asTreadField, ILexicalEnvironment data) throws EvaluationException {
		if (VERBOSE) System.out.println("Visit readField");
		Object res;
        String cibleUri;
		if (asTreadField != null)
		{
            cibleUri =  (String)data.getValue(new ASTvariable(asTreadField.getFieldName()));
            if (VERBOSE) System.out.println(" infos");
            if (VERBOSE) System.out.println(" " + asTreadField.getFieldName() + "== " + cibleUri);
            //ASTvariable target = (ASTvariable)asTreadField.getTarget();
			if (VERBOSE) System.out.println(" " + asTreadField.getFieldName() + "-> " + ((IASTstring)(asTreadField.getTarget())).getValue());
            if (owner != null)
			{
				if (owner.getReflectionInboundPortURI().equals(cibleUri))
                    res = owner.executeFunction(((IASTstring)(asTreadField.getTarget())).getValue());
                else
                {
                    data.setNextComponentUri(cibleUri);
                    res = cibleUri;
                }
				return res;
			}
			
		}
        if (VERBOSE) System.out.println("Visit readField failed");
		return null;
	}


}
