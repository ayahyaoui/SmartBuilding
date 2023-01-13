package com.paracamplus.bcm.ibp;

import com.paracamplus.bcm.components.Coordonator;
import com.paracamplus.bcm.interfaces.ScriptManagementCI;
import com.paracamplus.ilp1.interpreter.GlobalEnvFile;

import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;

public class DesktopRoomIBP extends AbstractInboundPort implements ScriptManagementCI{

	public DesktopRoomIBP( ComponentI owner) throws Exception {
		super(ScriptManagementCI.class, owner);
		assert	owner instanceof Coordonator;
	}

	@Override
	public GlobalEnvFile executeScript(GlobalEnvFile env) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
