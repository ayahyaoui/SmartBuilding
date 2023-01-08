/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp1.ast;

import com.paracamplus.ilp1.ast.ASTnamed;

import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp1.interfaces.IASTfunctionDefinition;
import com.paracamplus.ilp1.interfaces.IASTvariable;
import com.paracamplus.ilp1.utils.Utils;

public class ASTfunctionDefinition extends ASTnamed 
implements IASTfunctionDefinition {
    
    public ASTfunctionDefinition (IASTvariable functionVariable,
                                  IASTvariable[] variables,
                                  IASTexpression body ) {
        super(functionVariable.getName());
        this.functionVariable = functionVariable;
        this.variables = variables;
        this.body = body;
		
		System.out.println("function def " + variables[0].getName());
    }
    private final IASTvariable functionVariable;
    private final IASTvariable[] variables;
    private final IASTexpression body;
    
    @Override
	public IASTvariable getFunctionVariable() {
        return functionVariable;
    }

    @Override
	public IASTvariable[] getVariables() {
        return variables;
    }

    @Override
	public IASTexpression getBody() {
        return body;
    }
    @Override   
    public void show(String     start){
        super.show(start);
        System.out.println(start + "  VARIABLES");
        for ( IASTvariable v : variables ) {
            v.show(start +  Utils.PADDING);
        }
        System.out.println(start + "  BODY");
        body.show(start + Utils.PADDING);
    }
}
