package com.paracamplus.bcm.obp;



import com.paracamplus.bcm.components.DesktopRoom;
import com.paracamplus.bcm.interfaces.ScriptManagementCI;
import com.paracamplus.cps.interfaces.ValueProvidingCI;
import com.paracamplus.ilp1.interpreter.GlobalEnvFile;

import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.examples.basic_cs.interfaces.URIConsumerCI;

import fr.sorbonne_u.components.ports.AbstractOutboundPort;

public class DesktopRoomOBP extends AbstractOutboundPort
implements  ScriptManagementCI  {

	
	private static final long serialVersionUID = 1L;
	public DesktopRoomOBP(String uri, ComponentI owner)throws Exception {
		super(uri, URIConsumerCI.class, owner) ;
		assert	uri != null && owner != null && owner instanceof DesktopRoom ;
		
	}
	
	public DesktopRoomOBP( ComponentI owner)throws Exception {
		super(URIConsumerCI.class, owner) ;
		assert	owner != null && owner instanceof DesktopRoom;
		
	}
/*
	@Override
	public String getURI() throws Exception {
		return ((URIConsumerCI)this.getConnector()).getURI() ;
	}

	@Override
	public String[] getURIs(int numberOfURIs) throws Exception {
		return ((URIConsumerCI)this.getConnector()).getURIs(numberOfURIs) ;
	}
	*/
	@Override
	public GlobalEnvFile executeScript(GlobalEnvFile env) throws Exception {
		System.out.println("Connector ? " + this.getConnector());
		return ((ScriptManagementCI)this.getConnector()).executeScript(env);
	}

	@Override
	public GlobalEnvFile executeScript(GlobalEnvFile env, String uri) throws Exception {
		return ((ScriptManagementCI)this.getConnector()).executeScript(env, uri);
	}

}
