package com.paracamplus.bcm.components;

import java.util.ArrayList;


import com.paracamplus.bcm.connector.DesktopRoomConnector;
import com.paracamplus.bcm.connector.SupervisorConnector;
import com.paracamplus.bcm.ibp.CoordonatorIBP;
import com.paracamplus.bcm.interfaces.ScriptManagementCI;
import com.paracamplus.bcm.obp.CoordonatorOBP;
import com.paracamplus.bcm.obp.DesktopRoomOBP;
import com.paracamplus.bcm.obp.SupervisorOBP;
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
	protected String reflectionInboundPortURI;
	
	protected ArrayList<DesktopRoomOBP>  desktopOBPList;// maybe just one desktopRoomOBP?
	protected String[] desktopInboundPortURIs;
	protected ArrayList<CoordonatorOBP> coordonatorOBPList;
	protected String[] coordonatorInboundPortURIs;
	protected SupervisorOBP supervisorOBP;
	protected String supervisorInboundPortURI;

	protected Coordonator(String reflectionInboundPortURI, String supervisorInboundPortURI, String[] desktopIbpUris, String[] coordinatorIbpUris) throws Exception {
		super(2, 1);
		this.coordonatorIBP = new CoordonatorIBP(reflectionInboundPortURI, this);
		this.coordonatorIBP.publishPort();
		this.reflectionInboundPortURI = reflectionInboundPortURI;
		this.desktopInboundPortURIs = desktopIbpUris;
		this.desktopOBPList = new ArrayList<DesktopRoomOBP>();
		for (int i = 0; i < desktopIbpUris.length; i++) {
			DesktopRoomOBP desktopRoomOBP = new DesktopRoomOBP(this);
			desktopRoomOBP.publishPort();
			//System.out.println("Coordonator:(" + desktopRoomOBP.getPortURI() + ", " + reflectionInboundPortURI + ", " + ScriptManagementCI.class.getCanonicalName() + ")");
			desktopOBPList.add(desktopRoomOBP);
		}
		this.coordonatorInboundPortURIs = coordinatorIbpUris;
		this.coordonatorOBPList = new ArrayList<CoordonatorOBP>();
		for (int i = 0; i < coordinatorIbpUris.length; i++) {
			CoordonatorOBP coordonatorOBP = new CoordonatorOBP(this);
			coordonatorOBP.publishPort();
			//System.out.println("Coordonator:(" + coordonatorOBP.getPortURI() + ", " + reflectionInboundPortURI + ", " + ScriptManagementCI.class.getCanonicalName() + ")");
			coordonatorOBPList.add(coordonatorOBP);
		}
		this.supervisorInboundPortURI = supervisorInboundPortURI;
		this.supervisorOBP = new SupervisorOBP(this);
		this.supervisorOBP.publishPort();
		

		this.tracer.get().setTitle("Coordinator component " + reflectionInboundPortURI);
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
			for (int i = 0; i < desktopInboundPortURIs.length; i++) {
				System.out.println("do port connection " + i + " " + desktopOBPList.get(i).getPortURI() + " " + desktopInboundPortURIs[i] + " " + DesktopRoomConnector.class.getCanonicalName());
				this.doPortConnection(
						desktopOBPList.get(i).getPortURI(),
						desktopInboundPortURIs[i],
						DesktopRoomConnector.class.getCanonicalName());
			}
			for	(int i = 0; i < coordonatorInboundPortURIs.length; i++) {
				System.out.println("do port connection " + i + " " + coordonatorOBPList.get(i).getPortURI() + " " + coordonatorInboundPortURIs[i] + " " + DesktopRoomConnector.class.getCanonicalName());
				this.doPortConnection(
						coordonatorOBPList.get(i).getPortURI(),
						coordonatorInboundPortURIs[i],
						DesktopRoomConnector.class.getCanonicalName());	
			}

			this.doPortConnection(
					supervisorOBP.getPortURI(),
					supervisorInboundPortURI,
					SupervisorConnector.class.getCanonicalName());
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
		logMessage("[executeScript: " + env.getId() + "] HAS TO EXECUTE FILE with " + env.getNameFunction() + " as name function and " + uri + " as uri");

		if (env.isFinished())
		{
			logMessage("[executeScript: " + env.getId() + "] env is finished and the result is " + env.isAccepted());
			// TODO: send the result to the supervisor with runTask
			final GlobalEnvFile finishEnv = env;
			this.runTask(new AbstractComponent.AbstractTask() {
				@Override
				public void run() {
					try {
						((Coordonator)this.getTaskOwner()).supervisorOBP.receiveResult(finishEnv.getId(), finishEnv.isAccepted());;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			return env;
		}

		for (int i = 0; i < desktopInboundPortURIs.length; i++) {
			System.out.println("uri: " + uri + " DesktopInboundPortURIs[i]: " + desktopInboundPortURIs[i]);
			if (desktopInboundPortURIs[i].equals(uri)) {
				logMessage("[executeScript: " + env.getId() + "] found the uri " + uri + " in the list of desktopInboundPortURIs");
				env.setFound(true);
				final int index = i;
				final GlobalEnvFile env2 = env;
				this.runTask(
            		new AbstractComponent.AbstractTask() {
                @Override
                public void run() {
                    try {
                        ((Coordonator)this.getTaskOwner()).
							desktopOBPList.get(index).executeScript(env2);
                            
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }) ;
				logMessage("[executeScript: " + env.getId() + "] " + uri + " has end the script");
				return env;
			}
		}
		env.setVisited(reflectionInboundPortURI, true);
		// DFS (Depth First Search) of the graph of the coordonators
		for (int i = 0; i < coordonatorInboundPortURIs.length; i++) {
			System.out.println("uri: " + uri + " CoordonatorInboundPortURIs[i]: " + coordonatorInboundPortURIs[i]);
			if (!env.isVisited(coordonatorInboundPortURIs[i])) {
				
				logMessage("[executeScript] " + " ask if " + coordonatorInboundPortURIs[i] + " ");
				env = coordonatorOBPList.get(i).executeScript(env, uri);
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
		for (int i = 0; i < desktopInboundPortURIs.length - 1; i++) {
			s += desktopInboundPortURIs[i] + ", ";
		}
		if (desktopInboundPortURIs.length > 0)
			s += desktopInboundPortURIs[desktopInboundPortURIs.length - 1];
		s += "]";
		s += "  \n coordonatorInboundPortURIs=[";
		for (int i = 0; i < coordonatorInboundPortURIs.length - 1; i++) {
			s += coordonatorInboundPortURIs[i] + ", ";
		}
		if (coordonatorInboundPortURIs.length > 0)
			s += coordonatorInboundPortURIs[coordonatorInboundPortURIs.length - 1];
		s += "]";
		return "Coordonator [" + reflectionInboundPortURI + "], " + s;
	}
}
