package com.paracamplus.bcm.components;

import java.util.ArrayList;


import com.paracamplus.bcm.connector.DesktopRoomConnector;
import com.paracamplus.bcm.ibp.CoordonatorIBP;
import com.paracamplus.bcm.interfaces.ScriptManagementCI;
import com.paracamplus.bcm.obp.DesktopRoomOBP;
import com.paracamplus.bcm.utils.Utils;
import com.paracamplus.ilp1.interpreter.GlobalEnvFile;
import com.paracamplus.ilp1.test.GlobalFunctionAst;

import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.cvm.AbstractCVM;
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;
import fr.sorbonne_u.components.exceptions.ComponentStartException;
import fr.sorbonne_u.components.ports.PortI;


@OfferedInterfaces(offered = {ScriptManagementCI.class})
@RequiredInterfaces(required = {ScriptManagementCI.class})
public class Coordonator extends AbstractComponent{

	protected CoordonatorIBP coordonatorIBP;
	
	protected ArrayList<DesktopRoomOBP>  desktopOBPList;// maybe just one desktopRoomOBP?
	protected String[] DesktopInboundPortURIs;
	
	protected Coordonator(String reflectionInboundPortURI, String[] desktopIbpUris) throws Exception {
		super(2, 1);
		this.coordonatorIBP = new CoordonatorIBP(reflectionInboundPortURI, this);
		this.coordonatorIBP.publishPort();

		this.DesktopInboundPortURIs = desktopIbpUris;
		this.desktopOBPList = new ArrayList<DesktopRoomOBP>();
		for (int i = 0; i < desktopIbpUris.length; i++) {
			DesktopRoomOBP desktopRoomOBP = new DesktopRoomOBP(this);
			desktopRoomOBP.publishPort();
			//System.out.println("Coordonator:(" + desktopRoomOBP.getPortURI() + ", " + reflectionInboundPortURI + ", " + ScriptManagementCI.class.getCanonicalName() + ")");
			desktopOBPList.add(desktopRoomOBP);
		}
		
	}

	@Override
	public synchronized void start() throws ComponentStartException {
		super.start();
		try {
			for (int i = 0; i < DesktopInboundPortURIs.length; i++) {
				this.doPortConnection(
						desktopOBPList.get(i).getPortURI(),
						DesktopInboundPortURIs[i],
						DesktopRoomConnector.class.getCanonicalName());
			}
				
			
		} catch (Exception e) {
			
			throw new ComponentStartException(e);
		}
	}

	@Override
	public void execute() throws Exception {
		super.execute();
	}

	@Override
	public void			shutdown() throws ComponentShutdownException
	{
		// the shutdown is a good place to unpublish inbound ports.
		try {
			this.coordonatorIBP.unpublishPort() ;
		} catch (Exception e) {
			throw new ComponentShutdownException(e) ;
		};
		super.shutdown();
	}

	@Override
	public void			shutdownNow() throws ComponentShutdownException
	{
		// the shutdown is a good place to unpublish inbound ports.
		try {
			this.coordonatorIBP.unpublishPort() ;
		} catch (Exception e) {
			throw new ComponentShutdownException(e) ;
		};
		super.shutdownNow();
	}

	public GlobalEnvFile executeScript(GlobalEnvFile env) throws Exception {
		if (Utils.DEBUG_MODE || Utils.DEBUG_MODE_BCM)
			System.out.println("[Coordonator][executeScript] " + " HAS TO EXECUTE FILE with " + env.getNameFunction() + " as name function");
		
		String firstVaribleName = GlobalFunctionAst.getInstance().getParameters(env.getNameFunction())[0].getName();
		return executeScript(env, (String)env.getGlobalVariableEnvironment().get(firstVaribleName));
	}

	
	public GlobalEnvFile executeScript(GlobalEnvFile env, String uri) throws Exception {
		if (Utils.DEBUG_MODE || Utils.DEBUG_MODE_BCM)
			System.out.println("[Coordonator][executeScript] " + " HAS TO EXECUTE FILE with " + env.getNameFunction() + " as name function and " + uri + " as uri");
		for (int i = 0; i < DesktopInboundPortURIs.length; i++) {
			if (DesktopInboundPortURIs[i].equals(uri)) {
				desktopOBPList.get(i).executeScript(env);
			}
			
		}
		return env;
	}
}
