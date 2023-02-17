/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp1.ast;

import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp1.interfaces.IASTsequence;
import com.paracamplus.ilp1.interfaces.IASTvisitor;
import com.paracamplus.ilp1.utils.Utils;

public class ASTsequence extends ASTexpression implements IASTsequence {
    public ASTsequence (IASTexpression[] expressions) {
    	/*
         * 
         for ( IASTexpression e : expressions ) {
             if (e != null)
             System.out.println("expressions AST" + e.toString());
             else
             System.out.println("expressions AST None");
            }
            */
        this.expressions = expressions;
    }
    protected IASTexpression[] expressions;
    
    @Override
	public IASTexpression[] getExpressions() {
        return this.expressions;
    }

    @Override
	public <Result, Data, Anomaly extends Throwable> 
    Result accept(IASTvisitor<Result, Data, Anomaly> visitor, Data data)
            throws Anomaly {
        return visitor.visit(this, data);
    }
    
    @Override
    public void show(String start) {
    	super.show(start);
    	for (IASTexpression e : this.expressions) {
    		if (e != null)
    			e.show(start + Utils.PADDING);
    		else
    			System.out.println(start + Utils.PADDING + "AST None");
         }
    }

    public String toString(String start) {
        String s = super.toString(start);
        for (IASTexpression e : this.expressions) {
            if (e != null)
                s += e.toString(start + Utils.PADDING);
            else
                s += start + Utils.PADDING + "AST None\n";
            }
        return s;
    }
}
