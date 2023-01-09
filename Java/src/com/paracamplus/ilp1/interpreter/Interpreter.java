/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp1.interpreter;

import java.util.List;
import java.util.Vector;

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

public class Interpreter
implements IASTvisitor<Object, ILexicalEnvironment, EvaluationException> {
    
    public Interpreter (IGlobalVariableEnvironment globalVariableEnvironment,
                        IOperatorEnvironment operatorEnvironment ) {
        this.globalVariableEnvironment = globalVariableEnvironment;
        this.operatorEnvironment = operatorEnvironment;
    }
    protected IGlobalVariableEnvironment globalVariableEnvironment;
    protected IOperatorEnvironment operatorEnvironment;

    public IOperatorEnvironment getOperatorEnvironment() {
        return operatorEnvironment;
    }
    
    public IGlobalVariableEnvironment getGlobalVariableEnvironment() {
        return globalVariableEnvironment;
    }
    
    // 
    
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
    
    // 
    
    private static Object whatever = "whatever";
            
    @Override
	public Object visit(IASTalternative iast, ILexicalEnvironment lexenv) 
            throws EvaluationException {
    	System.out.println("Visit...alternative");
        Object c = iast.getCondition().accept(this, lexenv);
        if ( c != null && c instanceof Boolean ) {
            Boolean b = (Boolean) c;
            System.out.println("result if" + b);

            if ( b.booleanValue() ) { // if true just continue
                return true; //return iast.getConsequence().accept(this, lexenv);
            }/* else if ( iast.isTernary() ) {
                return iast.getAlternant().accept(this, lexenv);                
            }*/ else {
                return whatever; // try to finish the block
            }
        } else {
        	System.out.println("Condition bad format..");
        	 return whatever;
        	//return iast.getConsequence().accept(this, lexenv);
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
        System.out.println("visit Sequence..."+expressions);
        for (int i = lexenv.getIndexNode() ; i < expressions.length; i++) {
        	System.out.println("visit Sequence will accept loop" + expressions[i]);
        	lastValue = expressions[i].accept(this, lexenv);
        	System.out.println("visit Sequence has accepted loop");
        	if (lastValue != null && lastValue.equals(whatever))
        		return false;	
		}
        System.out.println("visit Sequence after loop");
        return lastValue;
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
    	System.out.println("find value " + iast.getValue());
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
		System.out.println("visit assign");
        IASTvariable variable = iast.getVariable();
        Object value = iast.getExpression().accept(this, lexenv);
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
		System.out.println("Not implemented yet");
		return null;
	}

	@Override
	public Object visit(IASTreadField asTreadFiekd, ILexicalEnvironment data) throws EvaluationException {
		System.out.println("Visit readField");
		if (asTreadFiekd != null)
		{
			System.out.println(" " + asTreadFiekd.getFieldName() + "-> " + ((IASTstring)(asTreadFiekd.getTarget())).getValue());
			System.out.println(" value Uri" + ((IASTstring)(data.getValue(data.findVariable(asTreadFiekd.getFieldName())))).getValue());
		}
		return null;
	}


}
