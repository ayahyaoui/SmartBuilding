package com.paracamplus.bcm.obp;

import com.paracamplus.bcm.components.DesktopRoom;
import com.paracamplus.bcm.interfaces.RoomI;
import com.paracamplus.bcm.interfaces.ScriptManagementCI;
import com.paracamplus.ilp1.interpreter.GlobalEnvFile;

import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.examples.basic_cs.interfaces.URIConsumerCI;

import fr.sorbonne_u.components.ports.AbstractOutboundPort;

public class RoomOBP extends AbstractOutboundPort
implements  ScriptManagementCI  {

	
	private static final long serialVersionUID = 1L;

    public RoomOBP(String uri, ComponentI owner)throws Exception {
        super(uri, URIConsumerCI.class, owner) ;
        assert	uri != null && owner != null && owner instanceof RoomI;
        
    }

    public RoomOBP( ComponentI owner)throws Exception {
        super(URIConsumerCI.class, owner) ;
        assert	owner != null && owner instanceof RoomI;
        
    }
	
	@Override
	public GlobalEnvFile executeScript(GlobalEnvFile env) throws Exception {
		return ((ScriptManagementCI)this.getConnector()).executeScript(env);
	}

	@Override
	public GlobalEnvFile executeScript(GlobalEnvFile env, String uri) throws Exception {
		return ((ScriptManagementCI)this.getConnector()).executeScript(env, uri);
	}   
}
