package com.paracamplus.bcm.interfaces;

import com.paracamplus.ilp1.interpreter.GlobalEnvFile;

import fr.sorbonne_u.components.interfaces.OfferedCI;
import fr.sorbonne_u.components.interfaces.RequiredCI;

public interface ScriptManagementCI extends	OfferedCI,
RequiredCI{

	public GlobalEnvFile executeScript(GlobalEnvFile env) throws Exception;

}
