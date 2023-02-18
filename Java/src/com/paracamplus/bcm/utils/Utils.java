package com.paracamplus.bcm.utils;

import java.util.ArrayList;
import java.util.HashMap;



public class Utils {
	public static final boolean DEBUG_MODE = true;
	public static final boolean DEBUG_MODE_BCM = true;
	public static final boolean DEBUG_MODE_ilp = false;

	public static final long SECONDS_TO_NANO = 1000000000L;


	public static final long DEFAULT_SLEEP_TIME_SIMUL = 30; // temp pour raffraichir l'interface
    

	
	public static final String	CLOCK_URI = "a-clock";
	public static final double	ACC_FACTOR = 1200; // 1 hour = 3 seconds
	public static final long	SCENARIO_DURATION = 3;
	public static final long EXECUTION_DURATION = (long) ((SCENARIO_DURATION*3600*1000)/ACC_FACTOR); // en ms
    public static final long DEFAULT_STARTUP_TIME = 5 * 1000; // time in ms of the clock offset
	public static final String	START_INSTANT = "2023-01-06T00:00:00.00Z";

	public static final int DEFAULT_NB_THREADS = 1;
	public static final int DEFAULT_NB_SCHEDULABLE_THREADS = 1;
    


	public static final long MARG_WINDOW_RECENTLY_OPEN = 3600000000000L;

	
	
	private static boolean isInit = false;
	


	
	

	

}
