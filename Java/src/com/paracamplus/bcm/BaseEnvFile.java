package com.paracamplus.bcm;

import com.paracamplus.ilp1.interfaces.*;

import fr.sorbonne_u.components.examples.basic_cs.ports.URIConsumerOutboundPort;

import java.util.HashMap;
import java.util.Map;



public class BaseEnvFile {

		IASTexpression[] expressions;
		IASTvariable[] variables;
		int indexNode;
		private final Map<String, Object> globalVariableEnvironment;

		boolean checkFormat(IASTprogram program) {
			IASTexpression body = program.getBody();
			if (!(body instanceof IASTfunctionDefinition)) {
				System.err.println(body + "or expected IASTfunctionDefinition" );
				return false;
			}
			IASTfunctionDefinition function = (IASTfunctionDefinition) body;
			if (!(function.getBody() instanceof IASTsequence)) {
				System.err.println(function.getBody() + "or expected IASTsequence" );
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
			IASTfunctionDefinition function = (IASTfunctionDefinition) program.getBody();
			IASTsequence sequence = (IASTsequence) function.getBody();
			this.expressions = sequence.getExpressions();
			this.variables = function.getVariables();
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
				result += expression.toString() + "";			
			}
			result += "variables : \n";
			for (IASTvariable variable : variables) {
				result += variable.toString() + "";			
			}
			return result;
		}
		public void printEnv() {
			System.out.println("variable globale Env :");
		}

		/*public void variableToEnv(String variableName, String ComponentUri) {
			globalVariableEnvironment.put(variableName, ComponentUri);
		}*/
}
