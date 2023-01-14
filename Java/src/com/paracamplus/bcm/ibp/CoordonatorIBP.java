package com.paracamplus.bcm.ibp;

import com.paracamplus.bcm.components.Coordonator;
import com.paracamplus.bcm.components.Supervisor;
import com.paracamplus.bcm.interfaces.ScriptManagementCI;
import com.paracamplus.ilp1.interpreter.GlobalEnvFile;
import com.thaiopensource.relaxng.edit.Component;

import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;

public class CoordonatorIBP  extends AbstractInboundPort implements ScriptManagementCI{

	
	public CoordonatorIBP(ComponentI owner) throws Exception {
		super(ScriptManagementCI.class, owner);
		assert	owner instanceof Supervisor;
		// 
	}
	
	@Override
	public GlobalEnvFile executeScript(GlobalEnvFile env) throws Exception {
		return null;
	}
}
