package com.paracamplus.ilp1.ast;

import com.paracamplus.ilp1.ast.ASTexpression;
import com.paracamplus.ilp1.interfaces.IASTassignment;
import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp1.interfaces.IASTvariable;
import com.paracamplus.ilp1.interfaces.IASTvisitor;
import com.paracamplus.ilp1.interfaces.IASTvisitable;

public class ASTassignment extends ASTexpression 
implements IASTassignment, IASTvisitable {

    public ASTassignment (IASTvariable variable, IASTexpression expression) {
        this.variable = variable;
        this.expression = expression;
    }
    private final IASTvariable variable;
    private final IASTexpression expression;
    
    @Override
	public IASTvariable getVariable() {
        return variable;
    }

    @Override
	public IASTexpression getExpression() {
        return expression;
    }
    
	@Override
	public <Result, Data, Anomaly extends Throwable> Result accept(
			com.paracamplus.ilp1.interfaces.IASTvisitor<Result, Data, Anomaly> visitor,
			Data data) throws Anomaly {
		return ((IASTvisitor <Result, Data, Anomaly>) visitor).visit(this, data);
	}
}