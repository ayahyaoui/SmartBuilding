/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp1.interpreter;

import com.paracamplus.ilp1.interfaces.IASTvariable;
import com.paracamplus.ilp1.interpreter.interfaces.EvaluationException;
import com.paracamplus.ilp1.interpreter.interfaces.ILexicalEnvironment;

public class EmptyLexicalEnvironment implements ILexicalEnvironment {
    
    public EmptyLexicalEnvironment() {}

    @Override
	public boolean isPresent(IASTvariable key) {
        return false;
    }

    @Override
	public IASTvariable getKey() throws EvaluationException {
        throw new EvaluationException("Really empty environment");
    }

    @Override
	public Object getValue(IASTvariable key) throws EvaluationException {
        throw new EvaluationException("No such variable " + key.getName());
    }

    @Override
	public void update(IASTvariable key, Object value)
            throws EvaluationException {
        throw new EvaluationException("Empty environment");
    }

  

    @Override
	public boolean isEmpty() {
        return true;
    }

    @Override
	public ILexicalEnvironment extend(IASTvariable variable, Object value) {
        return new LexicalEnvironment(variable, value, this);
    }

 

    @Override
	public ILexicalEnvironment getNext() throws EvaluationException {
        throw new EvaluationException("Completely empty environment");
    }

	@Override
	public int size() {
		return 0;
	}

	@Override
	public int getIndexNode() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setIndexNode(int index) {
	
		
	}

	@Override
	public String getNextComponentUri() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setNextComponentUri(String nextComponentUri) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setIsFinished(boolean isFinished) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}


}
