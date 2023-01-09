package com.paracamplus.ilp1.ast;

import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp1.interfaces.IASTvariable;
import com.paracamplus.ilp1.interfaces.IASTvariableAssign;

public class ASTvariableAssign implements IASTvariableAssign {

    private final IASTvariable variable;
    private IASTexpression expression; // TODO set Final
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
	@Override
	public void setExpression(IASTexpression expression) {
		 this.expression = expression;
	}
	

}
