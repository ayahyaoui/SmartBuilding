package com.paracamplus.bcm.interfaces;

public interface RoomI extends ScriptManagementCI{
	
	Object  executeFunction(String name);
	public String getReflectionInboundPortURI();

}
