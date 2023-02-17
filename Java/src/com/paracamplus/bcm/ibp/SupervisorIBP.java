package com.paracamplus.bcm.ibp;

import com.paracamplus.bcm.components.Supervisor;
import com.paracamplus.bcm.interfaces.ScriptManagementCI;
import com.paracamplus.bcm.interfaces.SupervisorManagementCI;

import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;

public class SupervisorIBP extends AbstractInboundPort implements SupervisorManagementCI{

	public  SupervisorIBP(ComponentI owner) throws Exception {
		super(SupervisorManagementCI.class, owner);
		assert	owner instanceof Supervisor;
	}

	public SupervisorIBP(String uri, ComponentI owner) throws Exception {
		super(uri, SupervisorManagementCI.class, owner);
		assert	owner instanceof Supervisor;
	}

	@Override
	public void receiveResult(String id, boolean result) throws Exception {
		this.owner.handleRequest(
				new AbstractComponent.AbstractService<Void>() {
					@Override
					public Void call() throws Exception {
						((Supervisor)this.getServiceOwner()).receiveResult(id, result);
						return null;
					}
				}) ;
		
	}
}
