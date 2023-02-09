package com.paracamplus.bcm.connector;

import com.paracamplus.bcm.interfaces.ScriptManagementCI;
import com.paracamplus.ilp1.interpreter.GlobalEnvFile;

import fr.sorbonne_u.components.connectors.AbstractConnector;

public class DesktopRoomConnector extends AbstractConnector implements ScriptManagementCI{

	@Override
	public GlobalEnvFile executeScript(GlobalEnvFile env) throws Exception {
		System.out.println("DesktopConnector execute >>");
		System.out.println(this + " " + this.offering);
		return ((ScriptManagementCI)this.offering).executeScript(env);
	}

	@Override
	public GlobalEnvFile executeScript(GlobalEnvFile env, String uri) throws Exception {
		System.out.println("DesktopConnector execute >>" + uri);
		return ((ScriptManagementCI)this.offering).executeScript(env, uri);
	}
	
}
