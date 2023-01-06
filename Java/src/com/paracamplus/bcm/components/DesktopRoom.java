package com.paracamplus.bcm.components;

import java.io.File;


import com.paracamplus.ilp1.interpreter.test.InterpreterTest;

public class DesktopRoom extends AbstractRoom {
	 	protected static String[] samplesDirName = { "SamplesILP1" }; 
	    protected static String pattern = ".*\\.ilpml";
	    protected static String XMLgrammarFile = "XMLGrammars/grammar1.rng";
	    
	
    protected DesktopRoom(int nbThreads, int nbSchedulableThreads) {
        super(nbThreads, nbSchedulableThreads);
        test();
        
    }
 
    void test()
    {
    	File file = new File(samplesDirName[0] + "/u02-1.ilpml");
    	System.out.println(samplesDirName[0]);
    	System.out.println(file.exists());
    	InterpreterTest it = new InterpreterTest( file);
        try {
            it.processFile();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
