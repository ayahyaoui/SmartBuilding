package com.paracamplus.ilp1.interpreter;

import com.paracamplus.ilp1.ast.ASTvariableAssign;
import com.paracamplus.ilp1.interfaces.*;
import com.paracamplus.ilp1.interpreter.interfaces.EvaluationException;
import com.paracamplus.ilp1.interpreter.interfaces.ILexicalEnvironment;
import com.paracamplus.ilp1.interpreter.interfaces.ISmartEnvironment;
import com.paracamplus.ilp1.utils.Utils;

import fr.sorbonne_u.components.examples.basic_cs.ports.URIConsumerOutboundPort;

import java.util.HashMap;
import java.util.Map;



public class BaseEnvFile implements ISmartEnvironment{

		protected IASTsequence expressions;
		protected IASTvariableAssign[] variables;
		protected int indexNode;
		private final Map<String, Object> globalVariableEnvironment;

		boolean checkFormat(IASTprogram program) {
			IASTfunctionDefinition fun = program.getFunction();
			if (!(fun instanceof IASTfunctionDefinition) || fun == null) {
				System.err.println(fun + "or expected IASTfunctionDefinition" );
				return false;
			}
			if (!(fun.getBody() instanceof IASTsequence) || fun.getBody() == null) {
				System.err.println(fun.getBody() + "or expected IASTsequence" );
				return false;
			}
			return true;
		}

		public BaseEnvFile(IASTprogram program) {
			if (!checkFormat(program)) {
				throw new IllegalArgumentException("Wrong format");
			}
			this.globalVariableEnvironment = new HashMap<>();
			this.indexNode = 0;
			IASTfunctionDefinition function = program.getFunction();
			//function.getName();
			this.expressions = (IASTsequence) function.getBody();
			this.variables = new IASTvariableAssign[function.getVariables().length]; 
			for (int i = 0; i < variables.length; i++) {
				variables[i] = new ASTvariableAssign(function.getVariables()[i], null);
			}
		}

		public IASTsequence getExpressions() {
			return expressions;
		}

		public Map<String, Object> getGlobalVariableEnvironment() {
			return globalVariableEnvironment;
		}

		public int getIndexNode() {
			return indexNode;
		}
		public IASTvariableAssign[] getVariables() {
			return variables;
		}
		
		@Override
		public String toString() {
			String result = "Env :\n";
			result += "indexNode : " + indexNode + "\n";
			result += "globalVariableEnvironment : \n";
			for (String key : globalVariableEnvironment.keySet()) {
				result += (key + " : " + globalVariableEnvironment.get(key) + "\n");
			}
			result += "expressions : \n";
			for (IASTexpression expression : expressions.getExpressions()) {
				result += Utils.PADDING + expression.toString() + "\n";			
			}
			result += "variables : \n";
			for (IASTvariableAssign variable : variables) {
				result += Utils.PADDING + variable.toString() + "\n";			
			}
			return result;
		}

		public void printEnv() {
			System.out.println("variable globale Env :");
		}

		//TODO
		
		@Override
		public ILexicalEnvironment extend(IASTvariable variable, Object value) {
			System.out.println("NOT IMPLMENTED YET !!!! (don't have to extend env)");
			return this;
		}

		@Override
		public ILexicalEnvironment getNext() throws EvaluationException {
			System.out.println("NOT IMPLMENTED YET !!!! (don't need because you already have access to all variable)");
			return this	;
		}

		@Override
		public IASTvariable getKey() throws EvaluationException {
			System.out.println("NOT IMPLMENTED YET !!!! (don't need because you already have access to all variable)");
			return variables[0].getVariable();
		}

		@Override
		public int size() {
			return variables.length;
		}

		@Override
		public boolean isPresent(IASTvariable key) {
			for(int i = 0 ; i < variables.length; i++)
			{
				if (key.getName().equals(variables[i].getVariable().getName()))
		            return true;
			}
			return false;
		}


		@Override
		public Object getValue(IASTvariable key) throws EvaluationException {
			for(int i = 0 ; i < variables.length; i++)
			{
				if (key.getName().equals(variables[i].getVariable().getName()))
		            return variables[i].getExpression();
			}
			System.out.println("Variable don't even exist !");
			return null;
		}

		@Override
		public void update(IASTvariable key, Object value) throws EvaluationException {
			for(int i = 0 ; i < variables.length; i++)
			{
				if (key.getName().equals(variables[i].getVariable().getName()))
		            variables[i].setExpression((IASTexpression)value);;
			}
			System.out.println("Variable don't even exist !");
		}

		@Override
		public IASTvariable findVariable(String s) {
			for(int i = 0 ; i < variables.length; i++)
			{
				if (s.equals(variables[i].getVariable().getName()))
					return variables[i].getVariable();
			}
			System.out.println("Variable don't even exist !");
			return null;
		}

		@Override
		public boolean isEmpty() {
			return false;
		}


		/*
		public void variableToEnv(String variableName, String ComponentUri) {
			globalVariableEnvironment.put(variableName, ComponentUri);
		}
		*/
}
