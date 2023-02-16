package com.paracamplus.bcm.components;

import java.util.ArrayList;


import com.paracamplus.bcm.connector.DesktopRoomConnector;
import com.paracamplus.bcm.ibp.CoordonatorIBP;
import com.paracamplus.bcm.interfaces.ScriptManagementCI;
import com.paracamplus.bcm.obp.CoordonatorOBP;
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
	protected ArrayList<CoordonatorOBP> coordonatorOBPList;
	protected String[] CoordonatorInboundPortURIs;

	protected Coordonator(String reflectionInboundPortURI, String[] desktopIbpUris, String[] coordinatorIbpUris) throws Exception {
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
		this.CoordonatorInboundPortURIs = coordinatorIbpUris;
		this.coordonatorOBPList = new ArrayList<CoordonatorOBP>();
		for (int i = 0; i < coordinatorIbpUris.length; i++) {
			CoordonatorOBP coordonatorOBP = new CoordonatorOBP(this);
			coordonatorOBP.publishPort();
			//System.out.println("Coordonator:(" + coordonatorOBP.getPortURI() + ", " + reflectionInboundPortURI + ", " + ScriptManagementCI.class.getCanonicalName() + ")");
			coordonatorOBPList.add(coordonatorOBP);
		}


		this.tracer.get().setTitle("Coordinator component " + this.reflectionInboundPortURI);
		this.tracer.get().setTitle("DesktopRoom component " + this.reflectionInboundPortURI);
		int tmp = reflectionInboundPortURI.charAt(reflectionInboundPortURI.length()-1) - 1;
		int x = tmp >= '0' && tmp <= '9' ? (tmp - '0') % 3 : 0;
		this.tracer.get().setRelativePosition(x+1, 1);
		this.toggleTracing();
		logMessage(this.toString());
		
	}

	@Override
	public synchronized void start() throws ComponentStartException {
		super.start();
		try {
			for (int i = 0; i < DesktopInboundPortURIs.length; i++) {
				System.out.println("do port connection " + i + " " + desktopOBPList.get(i).getPortURI() + " " + DesktopInboundPortURIs[i] + " " + DesktopRoomConnector.class.getCanonicalName());
				this.doPortConnection(
						desktopOBPList.get(i).getPortURI(),
						DesktopInboundPortURIs[i],
						DesktopRoomConnector.class.getCanonicalName());
			}
			for	(int i = 0; i < CoordonatorInboundPortURIs.length; i++) {
				System.out.println("do port connection " + i + " " + coordonatorOBPList.get(i).getPortURI() + " " + CoordonatorInboundPortURIs[i] + " " + DesktopRoomConnector.class.getCanonicalName());
				this.doPortConnection(
						coordonatorOBPList.get(i).getPortURI(),
						CoordonatorInboundPortURIs[i],
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
		logMessage("[executeScript] " + " HAS TO EXECUTE FILE with " + env.getNameFunction() + " as name function");
		
		String firstVaribleName = GlobalFunctionAst.getInstance().getParameters(env.getNameFunction())[0].getName();
		return executeScript(env, (String)env.getGlobalVariableEnvironment().get(firstVaribleName));
	}

	
	public GlobalEnvFile executeScript(GlobalEnvFile env, String uri) throws Exception {
		logMessage("[executeScript] " + " HAS TO EXECUTE FILE with " + env.getNameFunction() + " as name function and " + uri + " as uri");

		if (env.isFinished())
		{
			logMessage("[executeScript] " + " env is finished and the result is " + env.isAccepted());
			// TODO: send the result to the supervisor with an other port !
			return env;
		}

		for (int i = 0; i < DesktopInboundPortURIs.length; i++) {
			System.out.println("uri: " + uri + " DesktopInboundPortURIs[i]: " + DesktopInboundPortURIs[i]);
			if (DesktopInboundPortURIs[i].equals(uri)) {
				final int index = i;
				logMessage("[executeScript] " + " found the uri " + uri + " in the list of desktopInboundPortURIs");
				env.setFound(true);
				this.runTask(
            		new AbstractComponent.AbstractTask() {
                @Override
                public void run() {
                    try {
                        ((Coordonator)this.getTaskOwner()).
							desktopOBPList.get(index).executeScript(env);
                            
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }) ;
				logMessage(uri + " has end the script");
				return env;
			}
		}
		env.setVisited(reflectionInboundPortURI, true);
		for (int i = 0; i < CoordonatorInboundPortURIs.length; i++) {
			System.out.println("uri: " + uri + " CoordonatorInboundPortURIs[i]: " + CoordonatorInboundPortURIs[i]);
			if (!env.isVisited(CoordonatorInboundPortURIs[i])) {
				
				logMessage("[executeScript] " + " ask if " + CoordonatorInboundPortURIs[i] + " ");
				coordonatorOBPList.get(i).executeScript(env, uri);
				if (env.isFound()) {
					logMessage("[executeScript] " + " found the uri " + uri + " in the list of coordonatorInboundPortURIs and let them execute the script");
					return env;
				}
			}
		}

		return env;
	}

	@Override
	public String toString() {
		String s = "\ndesktopInboundPortURIs=[";
		for (int i = 0; i < DesktopInboundPortURIs.length - 1; i++) {
			s += DesktopInboundPortURIs[i] + ", ";
		}
		if (DesktopInboundPortURIs.length > 0)
			s += DesktopInboundPortURIs[DesktopInboundPortURIs.length - 1];
		s += "]";
		s += "\n, coordonatorInboundPortURIs=[";
		for (int i = 0; i < CoordonatorInboundPortURIs.length - 1; i++) {
			s += CoordonatorInboundPortURIs[i] + ", ";
		}
		if (CoordonatorInboundPortURIs.length > 0)
			s += CoordonatorInboundPortURIs[CoordonatorInboundPortURIs.length - 1];
		s += "]";
		return "Coordonator [" + coordonatorIBP + "], " + s;
	}
}
