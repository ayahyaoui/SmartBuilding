package com.paracamplus.ilp1.test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import com.paracamplus.ilp1.interfaces.IASTsequence;
import com.paracamplus.ilp1.interfaces.IASTvariable;


public class GlobalFunctionAst {

	private static final GlobalFunctionAst instance = new GlobalFunctionAst();
	HashMap<String, MyFunction> functions;
	
	class MyFunction{
		IASTsequence body;
		IASTvariable[] parameters;
	}
	
	private	 GlobalFunctionAst() {
		functions = new HashMap<>();
	}
	
	public static GlobalFunctionAst getInstance() {
		return instance;
	}
	
	public HashMap<String, MyFunction> getFunctions() {
		return functions;
	}
	
	public MyFunction getFunction(String name) {
		return functions.get(name);
	}
	
	public IASTsequence getBody(String name) {
		return functions.get(name).body;
	}

	public IASTvariable[] getParameters(String name) {
		return functions.get(name).parameters;
	}

	public boolean addFunction(String name, IASTsequence body, IASTvariable[] parameters) {
		if (functions.containsKey(name))
			return false;
		MyFunction f = new MyFunction();
		f.body = body;
		f.parameters = parameters;
		functions.put(name, f);
		return true;
	}

	public Boolean contains(String name) {
		return functions.containsKey(name);
	}

	/*
	 * Verify if the function exist and if the number of parameters is correct
	 * todo: verify if the type of parameters is correct too
	 */
	public boolean verifyFunctionParameters(String name, String[] parameters) {
		if (!functions.containsKey(name))
			return false;
		if (functions.get(name).parameters.length != parameters.length)
			return false;
		
		return true;
	}
}
