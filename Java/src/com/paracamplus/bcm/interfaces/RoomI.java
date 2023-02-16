package com.paracamplus.bcm.interfaces;

public interface RoomI extends ScriptManagementCI{
	
	boolean hasMethod(String name);
	Object  executeFunction(String name);
	public String getReflectionInboundPortURI();

}
