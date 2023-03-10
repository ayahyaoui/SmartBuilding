package com.paracamplus.ilp1.interpreter;

import com.paracamplus.ilp1.interfaces.*;
import com.paracamplus.ilp1.interpreter.interfaces.EvaluationException;
import com.paracamplus.ilp1.interpreter.interfaces.ILexicalEnvironment;
import com.paracamplus.ilp1.interpreter.interfaces.ISmartEnvironment;
import com.paracamplus.ilp1.interpreter.primitive.ToString;
import com.paracamplus.ilp1.test.GlobalFunctionAst;
import com.paracamplus.ilp1.utils.Utils;


import java.util.HashMap;
import java.util.Map;



public class GlobalEnvFile implements ISmartEnvironment{

		protected final String id;
		protected int indexNode;
		protected String nameFunction;
		protected String nextComponentUri;
		protected boolean isFinished;
		protected boolean isAccepted;
		protected boolean found;

		protected HashMap<String, Boolean> visited;
		private final Map<String, Object> globalVariableEnvironment;

		boolean checkFormat(String nameFunction, HashMap<String, String> parameters) {
			GlobalFunctionAst f = GlobalFunctionAst.getInstance();

			if (nameFunction == null || parameters == null) {
				System.err.println("nameFunction or parameters is null");
				return false;
			}
			if (!f.contains(nameFunction)) {
				System.err.println("nameFunction is doesn't exist:" + nameFunction);
				return false;
			}
			if (f.getParameters(nameFunction).length != parameters.size()) {
				System.err.println("parameters is wrong");
				return false;
			}
			IASTvariable[] p = f.getParameters(nameFunction);
			for (int i = 0; i < p.length; i++) {
				if (!parameters.containsKey(p[i].getName())) {
					System.err.println("parameters is wrong");
					return false;
				}
			}
			return true;
		}
		

		boolean checkFormat(String nameFunction, String[] parameters) {
			GlobalFunctionAst f = GlobalFunctionAst.getInstance();

			if (nameFunction == null || parameters == null) {
				System.err.println("nameFunction or parameters is null");
				return false;
			}
			if (!f.contains(nameFunction)) {
				System.err.println("nameFunction is doesn't exist:" + nameFunction);
				return false;
			}
			if (f.getParameters(nameFunction).length != parameters.length) {
				System.err.println("parameters is wrong");
				return false;
			}
			return true;
		}


		public GlobalEnvFile(String id, String nameFunction, HashMap<String, String> parameters) {
			if (!checkFormat(nameFunction, parameters)) {
				throw new IllegalArgumentException("Wrong format");
			}
			this.id = id;
			this.globalVariableEnvironment = new HashMap<>();
			this.indexNode = 0;
			this.nameFunction = nameFunction;
			this.isFinished = false;
			globalVariableEnvironment.putAll(parameters);
			this.isAccepted = false;
			this.visited = new HashMap<>();
			found = false;
		}
		
		public GlobalEnvFile(String id, String nameFunction, String []parameters) {
			if (!checkFormat(nameFunction, parameters)) {
				throw new IllegalArgumentException("Wrong format");
			}
			this.id = id;
			this.globalVariableEnvironment = new HashMap<>();
			this.indexNode = 0;
			this.nameFunction = nameFunction;
			IASTvariable[] params = GlobalFunctionAst.getInstance().getParameters(nameFunction);
			for (int i = 0; i < params.length; i++) {
				globalVariableEnvironment.put(params[i].getName(), parameters[i]);
			}
			this.isAccepted = false;
			this.isFinished = false;
			this.visited = new HashMap<>();
			found = false;
		}

		public String getId() {
			return id;
		}

	
		public String getNameFunction() {
			return nameFunction;
		}

		public Map<String, Object> getGlobalVariableEnvironment() {
			return globalVariableEnvironment;
		}

		public int getIndexNode() {
			return indexNode;
		}
	
		public void setIsFinished(boolean isFinished) {
			this.isFinished = isFinished;
		}

		public boolean isFinished() {
			return isFinished;
		}

		public void setNextComponentUri(String nextComponentUri) {
			this.nextComponentUri = nextComponentUri;
		}

		public String getNextComponentUri() {
			return nextComponentUri;
		}

		public void setIsAccepted(boolean isAccepted) {
			this.isAccepted = isAccepted;
		}

		public boolean isAccepted() {
			return isAccepted;
		}

		public void setFound(boolean found) {
			this.found = found;
		}

		public boolean isFound() {
			return found;
		}
		public void setVisited(String key, boolean value) {
			visited.put(key, value);
		}
		public boolean isVisited(String key) {
			if (!visited.containsKey(key)) {
				return false;
			}
			return visited.get(key);
		}

		public void clearVisited() {
			visited.clear();
		}
		
		@Override
		public String toString() {
			String result = "Env :\n";
			result += "name function : " + nameFunction + "\n";
			result += "indexNode : " + indexNode + "\n";
			result += "globalVariableEnvironment : \n";
			for (String key : globalVariableEnvironment.keySet()) {
				result += (Utils.PADDING +  key + " : " + globalVariableEnvironment.get(key) + "\n");
			}
			result += isFinished ? "is Finished\n" : "not Finished\n";
			result += "nextComponentUri : " + nextComponentUri + "\n";
			result += isAccepted ? "is Accepted\n" : "not Accepted\n";
			return result;
		}

		public void printEnv() {
			System.out.println("variable globale Env :");
		}
	
		//TODO
		
		@Override
		public ILexicalEnvironment extend(IASTvariable variable, Object value) {
			System.out.println("NOT IMPLMENTED YET !!!! (don't have to extend env)");
			this.globalVariableEnvironment.put(variable.getName(), value);
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
			return null;//variables[0].getVariable();
		}
				
		public boolean isPresent(IASTvariable key) {
			return globalVariableEnvironment.containsKey(key.getName());
		}
		
		public boolean isPresent(String key) {
			return globalVariableEnvironment.containsKey(key);
		}
		
		@Override
		public Object getValue(IASTvariable key) throws EvaluationException {
			if (isPresent(key) || isPresent(key.getName())) {
				return globalVariableEnvironment.get(key.getName());
			}
			System.out.println("Variable don't even exist !");
			return null;
		}
		
		@Override
		public void update(IASTvariable key, Object value) throws EvaluationException {
			if (isPresent(key)) 
				globalVariableEnvironment.put(key.getName(), value);
			else
			{
				System.out.println("Variable don't exist ! New variable create !");
				globalVariableEnvironment.put(key.getName(), value);
			}
		}
		

		@Override
		public boolean isEmpty() {
			return false;
		}

		@Override
		public int size() {
			return globalVariableEnvironment.size();
		}


		@Override
		public void setIndexNode(int index) {
			this.indexNode = index;
			
		}


}
