package cvm;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

import composants.Bureau;
import composants.SalleDeConvivalite;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.cvm.AbstractCVM;
import fr.sorbonne_u.components.cyphy.tools.aclocks.ClockServer;

public class CVMSim extends AbstractCVM {

	// -------------------------------------------------------------------------
	// Constants and variables
	// -------------------------------------------------------------------------

	public static final String	START_INSTANT = "2023-01-06T00:00:00.00Z";

	public static final long	SCENARIO_DURATION = 3;

	
	protected static final long	DELAY_TO_START_SCENARIO = 3000L;	// 3 seconds
	
	public static long			EXECUTION_START;
	
	protected static long		EXECUTION_DURATION;
	
	public static final String	CLOCK_URI = "a-clock";
	
	public static final double	ACC_FACTOR = 1200; // 1 hour = 3 seconds

	protected static final String	BUREAU_ID = "bureau-101";
	
	protected static final String Salle_Convivalite_ID = "salle_conv_104" ;

	public CVMSim () throws Exception {
	}
	
	@Override
	public void			deploy() throws Exception {
		// the actual Unix epoch start time for all components, set in
		// the centralised clock
		EXECUTION_START =
				System.currentTimeMillis() + DELAY_TO_START_SCENARIO;

		AbstractComponent.createComponent(
				ClockServer.class.getCanonicalName(),
				new Object[]{CLOCK_URI,		// create the centralised clock
							 TimeUnit.MILLISECONDS.toNanos(EXECUTION_START),
							 Instant.parse(START_INSTANT),
							 ACC_FACTOR});

		AbstractComponent.createComponent(
				Bureau.class.getCanonicalName(),
				new Object[]{BUREAU_ID, CLOCK_URI});
		
		AbstractComponent.createComponent(
				SalleDeConvivalite.class.getCanonicalName(),
				new Object[]{Salle_Convivalite_ID, CLOCK_URI});

		super.deploy();
	}
	public static void main(String[] args) {
		try {
			
			CVMSim sim = new CVMSim();
			EXECUTION_DURATION =
					(long) ((SCENARIO_DURATION*3600*1000)/ACC_FACTOR);
			sim.startStandardLifeCycle(DELAY_TO_START_SCENARIO +
					EXECUTION_DURATION);
			Thread.sleep(100000L);
			System.exit(0);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

}
