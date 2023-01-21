package com.paracamplus.bcm.utils;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FilenameFilter;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    public static final long DEFAULT_SLEEP_TIME = 1000L;
    public static final long DEFAULT_LIFE_CYCLE = 10000L;
    public static final long DEFAULT_STARTUP_TIME = 0L;
	public static final int DEFAULT_NB_THREADS = 1;
	public static final int DEFAULT_NB_SCHEDULABLE_THREADS = 1;
	public static final String	START_INSTANT = "2023-01-06T00:00:00.00Z";
	public static final long	SCENARIO_DURATION = 3;
	public static final String	CLOCK_URI = "a-clock";
	public static final double	ACC_FACTOR = 1200; // 1 hour = 3 seconds
	public static final long EXECUTION_DURATION = (long) ((SCENARIO_DURATION*3600*1000)/ACC_FACTOR);
    public static final String SUPERVISOR_URI = "supervisor-uri";
    public static final String	COORDONATOR_ID = "coordonator-01";
    public static final String	DESKTOPROOM_101_ID = "bureau-101";
	
	public static final String	DESKTOPROOM_102_ID = "bureau-102";

	public static final String Salle_Convivalite_ID = "salle_conv_104" ;


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
