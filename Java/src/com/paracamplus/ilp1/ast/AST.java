/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp1.ast;

import com.paracamplus.ilp1.interfaces.IAST;

public abstract class AST implements IAST {
    public String getClassShortName() {
        return this.getClass().getName()
                .replaceFirst("^com.paracamplus.ilp1.", "");
    }
    public void show(String start) {
    	System.out.println(start + "" + getClassShortName());

    }
    
    public String toString(String start) {
        return start + getClassShortName() + "\n";
    }

    @Override
    public String toString() {
        return getClassShortName();
    }
}
