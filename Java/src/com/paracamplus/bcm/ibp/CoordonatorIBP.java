package com.paracamplus.bcm.ibp;

import com.paracamplus.bcm.components.Coordonator;
import com.paracamplus.bcm.interfaces.ScriptManagementCI;
import com.paracamplus.ilp1.interpreter.GlobalEnvFile;

import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;

public class CoordonatorIBP  extends AbstractInboundPort implements ScriptManagementCI{

	
	public CoordonatorIBP(ComponentI owner) throws Exception {
		super(ScriptManagementCI.class, owner);
		assert	owner instanceof Coordonator;
		// 
	}

	public CoordonatorIBP(String uri, ComponentI owner) throws Exception {
		super(uri, ScriptManagementCI.class, owner);
		assert	owner instanceof Coordonator;
		// 
	}
	
	@Override
	public GlobalEnvFile executeScript(GlobalEnvFile env) throws Exception {
		return this.owner.handleRequest(
			new AbstractComponent.AbstractService<GlobalEnvFile>() {
				@Override
				public GlobalEnvFile call() throws Exception {
					return ((Coordonator)this.getServiceOwner()).executeScript(env);
				}
			}) ;
		
	}
	
	@Override
	public GlobalEnvFile executeScript(GlobalEnvFile env, String uri) throws Exception {
		
		return this.owner.handleRequest(
			new AbstractComponent.AbstractService<GlobalEnvFile>() {
				@Override
				public GlobalEnvFile call() throws Exception {
					return ((Coordonator)this.getServiceOwner()).executeScript(env, uri);
				}
			}) ;
	}
}
