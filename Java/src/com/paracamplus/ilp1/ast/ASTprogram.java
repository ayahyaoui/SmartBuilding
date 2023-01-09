/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp1.ast;


import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp1.interfaces.IASTfunctionDefinition;
import com.paracamplus.ilp1.interfaces.IASTprogram;
import com.paracamplus.ilp1.utils.Utils;

public class ASTprogram extends AST implements IASTprogram {
    public ASTprogram(IASTfunctionDefinition function, IASTexpression expression) {
        this.expression = expression;
        this.function = function;
    }

    protected IASTexpression expression;
    protected IASTfunctionDefinition function;
    
   
    @Override
	public IASTexpression getBody() {
        return this.expression;
    }

    public IASTfunctionDefinition getFunction() {
		return function;
	}
    
    @Override
    public void show(String start) {
    	super.show(start);
    	System.out.println("<<<<Start Fonction");
    	this.function.show(start + Utils.PADDING);
    	System.out.println("Fin Fonction>>>>");
    	System.out.println("<<<<Start Sequence");
    	this.expression.show(start + Utils.PADDING);
    	System.out.println("Fin Sequence>>>>");
    }


}
