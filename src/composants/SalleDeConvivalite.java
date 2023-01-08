package composants;



import java.time.Instant;
import java.util.concurrent.TimeUnit;

import capteurs.DetecteurFumee;
import capteurs.FenetreInstantanee;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.cyphy.tools.aclocks.AcceleratedClock;
import fr.sorbonne_u.components.cyphy.tools.aclocks.ClockServer;
import fr.sorbonne_u.components.cyphy.tools.aclocks.ClockServerCI;
import fr.sorbonne_u.components.cyphy.tools.aclocks.ClockServerConnector;
import fr.sorbonne_u.components.cyphy.tools.aclocks.ClockServerOutboundPort;
import fr.sorbonne_u.components.examples.basic_cs.interfaces.URIConsumerCI;
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;
import fr.sorbonne_u.components.exceptions.ComponentStartException;
import fr.sorbonne_u.exceptions.PreconditionException;
import ports.BureauOBP;
import ports.SalleConnector;

@RequiredInterfaces(required={ClockServerCI.class, URIConsumerCI.class})
public class SalleDeConvivalite extends AbstractComponent {

	protected int nbPersonnes ;
	protected int nbFenetres ;
	protected int nbPortes ;
	protected int nbElectromenagers ;
	protected String InboundPortURI ;
	protected DetecteurFumee detecteur ;
	

	protected final String				clockURI;
	protected AcceleratedClock			clock;
	
	protected ClockServerOutboundPort	clockServerOBP;
	protected BureauOBP bureauOBP ;
	
	protected SalleDeConvivalite(String reflectionInboundPortURI,
			String clockURI) {
		super(reflectionInboundPortURI, 1, 1);
		InboundPortURI = reflectionInboundPortURI ;
		
		assert	clockURI != null && !clockURI.isEmpty() :
			new PreconditionException(
					"clockURI != null && !clockURI.isEmpty()");

		this.clockURI = clockURI;
		
		nbFenetres = 1 ;
		detecteur = new DetecteurFumee (DetecteurFumee.Intensitefumee.PASDEFUMEE ) ;
		
		this.tracer.get().setTitle("Salle de convivalité component");
		this.tracer.get().setRelativePosition(2, 0);
		this.toggleTracing();
	}
	
	@Override
	public synchronized void	start() throws ComponentStartException
	{
		super.start();

		try {
			this.clockServerOBP = new ClockServerOutboundPort(this);
			this.clockServerOBP.publishPort();
			this.doPortConnection(
					this.clockServerOBP.getPortURI(),
					ClockServer.STANDARD_INBOUNDPORT_URI,
					ClockServerConnector.class.getCanonicalName());
		} catch (Exception e) {
			throw new ComponentStartException(e) ;
		}

		
		try {
			this.bureauOBP = new BureauOBP(this) ;
			this.bureauOBP.publishPort();
			this.doPortConnection(
								this.bureauOBP.getPortURI(),
								InboundPortURI,
								SalleConnector.class.getCanonicalName());
		} catch (Exception e) {
			throw new ComponentStartException(e) ;
		}
		
		this.logMessage("start.");
	}
	
	@Override
	public void			execute() throws Exception
	{
		// get the centralised clock from the clock server.
		this.clock = this.clockServerOBP.getClock(this.clockURI);
		// wait for the Unix epoch start time to execute the actions
		Thread.sleep(this.clock.waitUntilStartInMillis());
		this.logMessage("salle de convivalité ; pas de fumée");
		Instant i0 = clock.getStartInstant();
		Instant i1 = i0.plusSeconds(1800); // i0 + 0:30 hour
		long delay1 = clock.delayToAcceleratedInstantInNanos(i1);
		
		this.scheduleTask(
				o -> {	((SalleDeConvivalite)o).detecteur.setIntensite(DetecteurFumee.Intensitefumee.INTENSE) ;
						((SalleDeConvivalite)o).logMessage(
								"at " + i1 +
								" Detecteur Fumee retourne " + ((SalleDeConvivalite)o).detecteur.getIntensite() );
					 },
				delay1,
				TimeUnit.NANOSECONDS);
		this.logMessage("continue.");
	}
	
	@Override
	public synchronized void	finalise() throws Exception
	{
		this.doPortDisconnection(this.clockServerOBP.getPortURI());
		this.doPortDisconnection(this.bureauOBP.getPortURI());
		super.finalise();
	}
	
	
	@Override
	public synchronized void	shutdown() throws ComponentShutdownException
	{
		try {
			this.clockServerOBP.unpublishPort();
			this.bureauOBP.unpublishPort();
		} catch (Exception e) {
			throw new ComponentShutdownException(e) ;
		}

		this.logMessage("shutdown.");
		super.shutdown();
	}

}
