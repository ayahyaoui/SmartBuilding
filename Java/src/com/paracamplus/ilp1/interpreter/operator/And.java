/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp1.interpreter.operator;

import com.paracamplus.ilp1.interpreter.interfaces.EvaluationException;

public class And extends BinaryOperator {
    
    public And () {
        super("&");
    }
    
    @Override
	public Object apply (Object arg1, Object arg2) 
            throws EvaluationException {
        System.out.println("And.apply(" + arg1 + ", " + arg2 + ")");
        if ( arg1 instanceof Boolean ) {
            Boolean b1 = (Boolean) arg1;
            if ( ! b1.booleanValue() ) {
              return Boolean.FALSE;
            }
        }
        // Here, arg1 cannot be false!
        if ( arg2 instanceof Boolean ) {
          Boolean b2 = (Boolean) arg2;
          if ( ! b2.booleanValue() ) {
            return Boolean.FALSE;
          }
        }
        // and here, arg2 cannot be false!
        return arg2;
    }
}

// end of And.java
