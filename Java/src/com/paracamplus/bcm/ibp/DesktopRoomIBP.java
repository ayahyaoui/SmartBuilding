package com.paracamplus.bcm.ibp;


import com.paracamplus.bcm.components.DesktopRoom;
import com.paracamplus.bcm.interfaces.ScriptManagementCI;
import com.paracamplus.ilp1.interpreter.GlobalEnvFile;

import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;

public class DesktopRoomIBP extends AbstractInboundPort implements ScriptManagementCI{

	private static final long serialVersionUID = 1L;
	public DesktopRoomIBP( ComponentI owner) throws Exception {
		super(ScriptManagementCI.class, owner);
		assert	owner instanceof DesktopRoom;
	}
	
	public DesktopRoomIBP(String uri, ComponentI owner) throws Exception {
		super(uri, ScriptManagementCI.class, owner);
		assert	owner instanceof DesktopRoom;
	}

	@Override
	public GlobalEnvFile executeScript(GlobalEnvFile env) throws Exception {
		return this.owner.handleRequest(
			new AbstractComponent.AbstractService<GlobalEnvFile>() {
				@Override
				public GlobalEnvFile call() throws Exception {
					return ((DesktopRoom)this.getServiceOwner()).executeScript(env);
				}
			}) ;
	}
	
	@Override
	public GlobalEnvFile executeScript(GlobalEnvFile env, String uri) throws Exception {
		return this.owner.handleRequest(
			new AbstractComponent.AbstractService<GlobalEnvFile>() {
				@Override
				public GlobalEnvFile call() throws Exception {
					return ((DesktopRoom)this.getServiceOwner()).executeScript(env, uri);
				}
			}) ;
	}
}
