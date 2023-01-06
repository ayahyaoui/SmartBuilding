package com.paracamplus.ilp1.ast;

import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp1.interfaces.IASTvariable;
import com.paracamplus.ilp1.interfaces.IASTvariableAssign;

public class ASTvariableAssign implements IASTvariableAssign {

    private final IASTvariable variable;
    private final IASTexpression expression;
	public ASTvariableAssign(IASTvariable variable, IASTexpression expression) {
        this.variable = variable;
        this.expression = expression;
    }
	@Override
	public IASTvariable getVariable() {
		return variable;
	}

	@Override
	public IASTexpression getExpression() {
		return expression;
	}

}
