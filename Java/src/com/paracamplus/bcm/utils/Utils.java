package com.paracamplus.bcm.utils;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FilenameFilter;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Utils {
	public static final boolean DEBUG_MODE = true;
	public static final boolean DEBUG_MODE_BCM = true;
	public static final boolean DEBUG_MODE_ilp = false;

    public static final long DEFAULT_SLEEP_TIME = 1000L;
	public static final long DEFAULT_SLEEP_TIME_SIMUL = 1000L;
    public static final long DEFAULT_LIFE_CYCLE = 100000L;
    public static final long DEFAULT_STARTUP_TIME = 0L;
	public static final String	CLOCK_URI = "a-clock";
	public static final double	ACC_FACTOR = 1200; // 1 hour = 3 seconds
	public static final long	SCENARIO_DURATION = 3;
	public static final long EXECUTION_DURATION = (long) ((SCENARIO_DURATION*3600*1000)/ACC_FACTOR);
	public static final String	START_INSTANT = "2023-01-06T00:00:00.00Z";

	public static final int DEFAULT_NB_THREADS = 1;
	public static final int DEFAULT_NB_SCHEDULABLE_THREADS = 1;
    
	public static final String SUPERVISOR_URI = "supervisor-uri";
	public static final String	COORDONATOR_01_URI = "coordonator-01";
    public static final String	COORDONATOR_02_URI = "coordonator-02";

    public static final String	DESKTOPROOM_101_URI = "bureau-101";
	public static final String	DESKTOPROOM_102_URI = "bureau-102";
	public static final String	DESKTOPROOM_103_URI = "bureau-103";
	public static final String	DESKTOPROOM_201_URI = "bureau-201";
	public static final String	DESKTOPROOM_202_URI = "bureau-202";

	public static String[] rooms = null;
	public static String[] coords = null;
	public static HashMap<String, String[]> roomsNeighbours = null;
	public static HashMap<String, String[]> roomsCoordonators = null;
	


	private static boolean isInit = false;
	
	public static void buildingPlan1() {
		if (isInit) {
			return;
		}
		rooms = new String[] {DESKTOPROOM_101_URI, DESKTOPROOM_102_URI, DESKTOPROOM_103_URI, DESKTOPROOM_201_URI, DESKTOPROOM_202_URI};
		coords = new String[] {COORDONATOR_01_URI, COORDONATOR_02_URI};
		roomsNeighbours = new HashMap<String, String[]>();
		roomsCoordonators = new HashMap<String, String[]>();
		for (String r : rooms) {
			roomsNeighbours.put(r, new String[] {});
		}
		for (String c : coords) {
			roomsCoordonators.put(c, new String[] {});
		}
		roomsCoordonators.put(COORDONATOR_01_URI, new String[] {DESKTOPROOM_101_URI, DESKTOPROOM_102_URI, DESKTOPROOM_103_URI});
		roomsCoordonators.put(COORDONATOR_02_URI, new String[] {DESKTOPROOM_201_URI, DESKTOPROOM_202_URI});
		roomsNeighbours.put(DESKTOPROOM_101_URI, new String[] {DESKTOPROOM_102_URI});
		roomsNeighbours.put(DESKTOPROOM_102_URI, new String[] {DESKTOPROOM_101_URI, DESKTOPROOM_103_URI});
		roomsNeighbours.put(DESKTOPROOM_103_URI, new String[] {DESKTOPROOM_102_URI});
		isInit = true;
	}
	
	public static void buildingPlan2()	{
		if (isInit) {
			return;
		}
		rooms = new String[] {DESKTOPROOM_101_URI, DESKTOPROOM_102_URI};
		coords = new String[] {COORDONATOR_01_URI};
		roomsNeighbours = new HashMap<String, String[]>();
		roomsCoordonators = new HashMap<String, String[]>();
		for (String r : rooms) {
			roomsNeighbours.put(r, new String[] {});
		}
		for (String c : coords) {
			roomsCoordonators.put(c, new String[] {});
		}
		roomsCoordonators.put(COORDONATOR_01_URI, new String[] {DESKTOPROOM_101_URI, DESKTOPROOM_102_URI});
		roomsNeighbours.put(DESKTOPROOM_101_URI, new String[] {DESKTOPROOM_102_URI});
		roomsNeighbours.put(DESKTOPROOM_102_URI, new String[] {DESKTOPROOM_101_URI});
		isInit = true;
	}


	public static File[] getFileList(
			String samplesDirName,
			String pattern
			) throws Exception {
		final Pattern p = Pattern.compile("^" + pattern + "$");
		final FilenameFilter ff = new FilenameFilter() {
			@Override
			public boolean accept (File dir, String name) {
				final Matcher m = p.matcher(name);
				return m.matches();
			}
		};
		File samplesDir = new File(samplesDirName);
		final File[] testFiles = samplesDir.listFiles(ff);
		if (testFiles == null) {
			throw new IllegalArgumentException("Directory does not exist : " + samplesDirName);
		}
		assertNotNull(testFiles);
		
		if ( testFiles.length == 0 ) {
			final String msg = "Cannot find a single test like " + pattern + " in " + samplesDirName;
			throw new IllegalArgumentException(msg);
		}
		java.util.Arrays.sort(testFiles,
				(f1, f2) -> f1.getName().compareTo(f2.getName()));
		return testFiles;
	}


	public static File[] getFileList(
    		String[] samplesDirNames,
    		String pattern
    		) throws Exception {
    	List<File> files = new Vector<File>();
    	for (String d : samplesDirNames) {
    		for (File f : getFileList(d, pattern)) {
    			files.add(f);
    		}
    	}
    	return files.toArray(new File[0]);
    }
}
