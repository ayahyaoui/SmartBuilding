/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp1.interpreter.interfaces;

import com.paracamplus.ilp1.interfaces.IASTvariable;
import com.paracamplus.ilp1.interfaces.IEnvironment;

public interface ILexicalEnvironment 
extends IEnvironment<IASTvariable, Object, EvaluationException> {
    @Override
	ILexicalEnvironment extend(IASTvariable variable, Object value);
    @Override
	ILexicalEnvironment getNext() throws EvaluationException;
	
    int size(); // nbVariable or nbNode in sequence ?? 
	//IASTvariable findVariable(String s);
	int getIndexNode(); // position
	void setIndexNode(int index);
	void setIsFinished(boolean isFinished);
	boolean isFinished();


	// needed to avoid round trip
	public String getNextComponentUri(); 
	public void setNextComponentUri(String nextComponentUri);
	
}
