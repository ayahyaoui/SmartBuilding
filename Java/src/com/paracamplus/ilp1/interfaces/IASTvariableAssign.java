package com.paracamplus.ilp1.interfaces;

public interface IASTvariableAssign {
	IASTvariable getVariable();
	IASTexpression getExpression();
	void setExpression(IASTexpression expression);
}
