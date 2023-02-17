package com.paracamplus.bcm.components;

import java.util.ArrayList;

import com.paracamplus.bcm.connector.RoomConnector;
import com.paracamplus.bcm.connector.SupervisorConnector;
import com.paracamplus.bcm.ibp.CoordonatorIBP;
import com.paracamplus.bcm.interfaces.ScriptManagementCI;
import com.paracamplus.bcm.obp.CoordonatorOBP;
import com.paracamplus.bcm.obp.RoomOBP;
import com.paracamplus.bcm.obp.SupervisorOBP;
import com.paracamplus.ilp1.interpreter.GlobalEnvFile;
import com.paracamplus.ilp1.test.GlobalFunctionAst;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;
import fr.sorbonne_u.components.exceptions.ComponentStartException;


@OfferedInterfaces(offered = {ScriptManagementCI.class})
@RequiredInterfaces(required = {ScriptManagementCI.class})
public class Coordonator extends AbstractComponent{

	protected CoordonatorIBP coordonatorIBP;
	protected String reflectionInboundPortURI;
	
	protected ArrayList<RoomOBP>  roomOBPList;
	protected String[] roomInboundPortURIs;
	protected ArrayList<CoordonatorOBP> coordonatorOBPList;
	protected String[] coordonatorInboundPortURIs;
	protected SupervisorOBP supervisorOBP;
	protected String supervisorInboundPortURI;

	protected Coordonator(String reflectionInboundPortURI, String supervisorInboundPortURI, String[] roomIbpUris, String[] coordinatorIbpUris) throws Exception {
		super(2, 1);
		this.coordonatorIBP = new CoordonatorIBP(reflectionInboundPortURI, this);
		this.coordonatorIBP.publishPort();
		this.reflectionInboundPortURI = reflectionInboundPortURI;
		this.roomInboundPortURIs = roomIbpUris;
		this.roomOBPList = new ArrayList<RoomOBP>();
		for (int i = 0; i < roomIbpUris.length; i++) {
			RoomOBP RoomOBP = new RoomOBP(this);
			RoomOBP.publishPort();
			roomOBPList.add(RoomOBP);
		}

		this.coordonatorInboundPortURIs = coordinatorIbpUris;
		this.coordonatorOBPList = new ArrayList<CoordonatorOBP>();
		for (int i = 0; i < coordinatorIbpUris.length; i++) {
			CoordonatorOBP coordonatorOBP = new CoordonatorOBP(this);
			coordonatorOBP.publishPort();
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
			for (int i = 0; i < roomInboundPortURIs.length; i++) {
				this.doPortConnection(
						roomOBPList.get(i).getPortURI(),
						roomInboundPortURIs[i],
						RoomConnector.class.getCanonicalName());
			}
			for	(int i = 0; i < coordonatorInboundPortURIs.length; i++) {
				this.doPortConnection(
						coordonatorOBPList.get(i).getPortURI(),
						coordonatorInboundPortURIs[i],
						RoomConnector.class.getCanonicalName());	
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
		try {
			this.coordonatorIBP.unpublishPort() ;
			for (int i = 0; i < roomInboundPortURIs.length; i++) {
				this.doPortDisconnection(roomOBPList.get(i).getPortURI());
			}
			for	(int i = 0; i < coordonatorInboundPortURIs.length; i++) {
				this.doPortDisconnection(coordonatorOBPList.get(i).getPortURI());
			}
			this.doPortDisconnection(supervisorOBP.getPortURI());
		} catch (Exception e) {
			throw new ComponentShutdownException(e) ;
		};
		super.shutdown();
	}

	@Override
	public void			shutdownNow() throws ComponentShutdownException
	{
		try {
			this.coordonatorIBP.unpublishPort() ;
			for (int i = 0; i < roomInboundPortURIs.length; i++) {
				this.doPortDisconnection(roomOBPList.get(i).getPortURI());
			}
			for	(int i = 0; i < coordonatorInboundPortURIs.length; i++) {
				this.doPortDisconnection(coordonatorOBPList.get(i).getPortURI());
			}
			this.doPortDisconnection(supervisorOBP.getPortURI());
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

		for (int i = 0; i < roomInboundPortURIs.length; i++) {
			
			if (roomInboundPortURIs[i].equals(uri)) {
				logMessage("[executeScript: " + env.getId() + "] found the uri " + uri + " in the list of RoomInboundPortURIs");
				env.setFound(true);
				final int index = i;
				final GlobalEnvFile env2 = env;
				this.runTask(
            		new AbstractComponent.AbstractTask() {
                @Override
                public void run() {
                    try {
                        ((Coordonator)this.getTaskOwner()).
							roomOBPList.get(index).executeScript(env2);
                            
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
		String s = "\nRoomInboundPortURIs=[";
		for (int i = 0; i < roomInboundPortURIs.length - 1; i++) {
			s += roomInboundPortURIs[i] + ", ";
		}
		if (roomInboundPortURIs.length > 0)
			s += roomInboundPortURIs[roomInboundPortURIs.length - 1];
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
