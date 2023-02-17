package com.paracamplus.bcm.obp;

import com.paracamplus.bcm.interfaces.SupervisorManagementCI;

import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.examples.basic_cs.interfaces.URIConsumerCI;
import fr.sorbonne_u.components.ports.AbstractOutboundPort;

public class SupervisorOBP extends AbstractOutboundPort
implements URIConsumerCI, SupervisorManagementCI{

    	
	private static final long serialVersionUID = 1L;

    public SupervisorOBP(String uri, ComponentI owner) throws Exception {
        super(uri, SupervisorManagementCI.class, owner);
        assert uri != null && owner != null;
    }

    public SupervisorOBP(ComponentI owner) throws Exception {
        super(SupervisorManagementCI.class, owner);
        assert owner != null;
    }

    @Override
    public void receiveResult(String id, boolean result) throws Exception {
        ((SupervisorManagementCI)this.getConnector()).receiveResult(id, result);
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
