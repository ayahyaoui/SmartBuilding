package com.paracamplus.ilp1.test;

import java.util.HashMap;

import com.paracamplus.ilp1.interfaces.IASTsequence;


public class GlobalFunctionAst {

	 private static final GlobalFunctionAst instance = new GlobalFunctionAst();
	HashMap<String, IASTsequence> functions;
	
	private	 GlobalFunctionAst() {
		functions = new HashMap<>();
	}
	
	public static GlobalFunctionAst getInstance() {
		return instance;
	}
}
