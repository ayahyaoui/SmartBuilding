package com.paracamplus.bcm;

import com.paracamplus.ilp1.ast.ASTvariableAssign;
import com.paracamplus.ilp1.interfaces.*;
import com.paracamplus.ilp1.utils.Utils;

import fr.sorbonne_u.components.examples.basic_cs.ports.URIConsumerOutboundPort;

import java.util.HashMap;
import java.util.Map;



public class BaseEnvFile {

		protected IASTexpression[] expressions;
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
			IASTsequence sequence = (IASTsequence) function.getBody();
			this.expressions = sequence.getExpressions();
			this.variables = new IASTvariableAssign[function.getVariables().length]; 
			for (int i = 0; i < variables.length; i++) {
				variables[i] = new ASTvariableAssign(function.getVariables()[i], null);
			}
		}

		public IASTexpression[] getExpressions() {
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
			for (IASTexpression expression : expressions) {
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

		/*
		public void variableToEnv(String variableName, String ComponentUri) {
			globalVariableEnvironment.put(variableName, ComponentUri);
		}
		*/
}
