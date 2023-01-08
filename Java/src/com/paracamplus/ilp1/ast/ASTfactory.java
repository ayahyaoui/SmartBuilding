/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp1.ast;

import com.paracamplus.ilp1.interfaces.IASTalternative;
import com.paracamplus.ilp1.interfaces.IASTbinaryOperation;
import com.paracamplus.ilp1.interfaces.IASTblock;
import com.paracamplus.ilp1.interfaces.IASTblock.IASTbinding;
import com.paracamplus.ilp1.ast.ASTassignment;
import com.paracamplus.ilp1.interfaces.IASTfactory;
import com.paracamplus.ilp1.interfaces.IASTboolean;
import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp1.interfaces.IASTfloat;
import com.paracamplus.ilp1.interfaces.IASTfunctionDefinition;
import com.paracamplus.ilp1.interfaces.IASTinteger;
import com.paracamplus.ilp1.interfaces.IASTinvocation;
import com.paracamplus.ilp1.interfaces.IASToperator;
import com.paracamplus.ilp1.interfaces.IASTprogram;
import com.paracamplus.ilp1.interfaces.IASTreadField;
import com.paracamplus.ilp1.interfaces.IASTsequence;
import com.paracamplus.ilp1.interfaces.IASTstring;
import com.paracamplus.ilp1.interfaces.IASTunaryOperation;
import com.paracamplus.ilp1.interfaces.IASTvariable;
import com.paracamplus.ilp1.interfaces.IASTvariableAssign;

public class ASTfactory implements IASTfactory {

    @Override
	public IASTprogram newProgram(IASTfunctionDefinition function, IASTexpression expression) {
        return new ASTprogram(function, expression);
    }
    
    @Override
	public IASToperator newOperator(String name) {
        return new ASToperator(name);
    }

    @Override
	public IASTsequence newSequence(IASTexpression[] asts) {
        return new ASTsequence(asts);
    }

    @Override
	public IASTalternative newAlternative(IASTexpression condition) {
        return new ASTalternative(condition);
    }

    @Override
	public IASTvariable newVariable(String name) {
        return new ASTvariable(name);
    }


    @Override
	public IASTunaryOperation newUnaryOperation(IASToperator operator,
                                                IASTexpression operand) {
        return new ASTunaryOperation(operator, operand);
    }

    @Override
	public IASTbinaryOperation newBinaryOperation(IASToperator operator,
            IASTexpression leftOperand, IASTexpression rightOperand) {
        return new ASTbinaryOperation(operator, leftOperand, rightOperand);
    }

    @Override
	public IASTinteger newIntegerConstant(String value) {
        return new ASTinteger(value); 
    }

    @Override
	public IASTfloat newFloatConstant(String value) {
        return new ASTfloat(value);
    }

    @Override
	public IASTstring newStringConstant(String value) {
        return new ASTstring(value);
    }

    @Override
	public IASTboolean newBooleanConstant(String value) {
        return new ASTboolean(value);
    }


    @Override
	public IASTblock newBlock(IASTbinding[] binding,
                              IASTexpression body) {
        return new ASTblock(binding, body);
    }
    @Override
	public IASTbinding newBinding(IASTvariable variable, IASTexpression initialisation) {
        return new ASTblock.ASTbinding(variable, initialisation);
    }
    
    @Override
	public IASTinvocation newInvocation(IASTexpression function,
            IASTexpression[] arguments) {
    	return new ASTinvocation(function, arguments);
    }

	@Override
	public IASTexpression newAssignment(IASTvariable variable, IASTexpression value) {
		return new ASTassignment(variable, value);
	}
	public IASTreadField newReadField(String fieldName, IASTexpression target) {
        return new ASTreadField(fieldName, target);
    }
	@Override
	public IASTvariableAssign newVariableAssign(IASTvariable variable, IASTexpression value) {
		System.out.println("Factory newVariableAssign");
         return  new ASTvariableAssign(variable, value);
		//return null;
	}

	@Override
	public IASTfunctionDefinition newFunctionDefinition(IASTvariable functionVariable, IASTvariable[] variables,
			IASTexpression body) {
		
		return  new ASTfunctionDefinition(functionVariable, variables, body);
	}

}
