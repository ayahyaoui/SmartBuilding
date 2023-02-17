package com.paracamplus.ilp1.ast;

import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp1.interfaces.IASTinvocSensor;
import com.paracamplus.ilp1.interfaces.IASTvisitor;

public class ASTinvocSensor  extends ASTexpression implements IASTinvocSensor {
    
    public ASTinvocSensor (IASTexpression function) {
        this.function = function;
    }
    private final IASTexpression function;
    
    @Override
	public IASTexpression getFunction () {
        return function;
    }

    @Override
	public <Result, Data, Anomaly extends Throwable> 
    Result accept(IASTvisitor<Result, Data, Anomaly> visitor, Data data)
            throws Anomaly {
        return visitor.visit(this, data);
    }
}
