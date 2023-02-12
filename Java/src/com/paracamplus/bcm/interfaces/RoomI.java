package com.paracamplus.bcm.interfaces;

public interface RoomI {
	
	boolean hasMethod(String name);
	Object  executeFunction(String name);
	public String getReflectionInboundPortURI();

}
