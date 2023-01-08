package ports;

import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.examples.basic_cs.interfaces.URIConsumerCI;
import fr.sorbonne_u.components.ports.AbstractOutboundPort;

public class BureauOBP extends AbstractOutboundPort
implements URIConsumerCI {

	private static final long serialVersionUID = 1L;
	
	
	public	BureauOBP( String uri, ComponentI owner) throws Exception {	
		super(uri, URIConsumerCI.class, owner) ;
		assert	uri != null && owner != null ;
	}
	
	
	
	public BureauOBP (ComponentI owner) throws Exception {
		super(URIConsumerCI.class,owner) ;	
		assert owner != null ;
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
