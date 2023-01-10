package com.paracamplus.bcm.ports;

import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.examples.basic_cs.interfaces.URIConsumerCI;
import fr.sorbonne_u.components.interfaces.RequiredCI;
import fr.sorbonne_u.components.ports.AbstractOutboundPort;

public class DesktopRoomOBP extends AbstractOutboundPort
implements URIConsumerCI {

	
	public DesktopRoomOBP(String uri, ComponentI owner)throws Exception {
		super(uri, URIConsumerCI.class, owner) ;
		assert	uri != null && owner != null ;
		// TODO Auto-generated constructor stub
	}
	public DesktopRoomOBP( ComponentI owner)throws Exception {
		super(URIConsumerCI.class, owner) ;
		assert	owner != null ;
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getURI() throws Exception {
		return ((URIConsumerCI)this.getConnector()).getURI() ;
	}

	@Override
	public String[] getURIs(int numberOfURIs) throws Exception {
		return ((URIConsumerCI)this.getConnector()).getURIs(numberOfURIs) ;
	}
}
