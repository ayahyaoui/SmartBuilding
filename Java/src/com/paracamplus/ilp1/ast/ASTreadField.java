package com.paracamplus.ilp1.ast;

import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp1.interfaces.IASTreadField;
import com.paracamplus.ilp1.interfaces.IASTvisitor;

public class ASTreadField extends ASTexpression implements IASTreadField {

	private String fieldName;
	private IASTexpression target;
	
	public ASTreadField(String fieldName, IASTexpression target) {
		this.setFieldName(fieldName);
		this.setTarget(target);
	}
	
	@Override
	public <Result, Data, Anomaly extends Throwable> Result accept(IASTvisitor<Result, Data, Anomaly> visitor,
			Data data) throws Anomaly {
		// TODO Auto-generated method stub
		return visitor.visit(this, data);
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public IASTexpression getTarget() {
		return target;
	}
	public void setTarget(IASTexpression target) {
		this.target = target;
	}


}
