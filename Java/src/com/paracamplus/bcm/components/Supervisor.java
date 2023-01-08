package com.paracamplus.bcm.components;

import java.io.File;

import com.paracamplus.ilp1.interpreter.test.InterpreterTest;

import fr.sorbonne_u.components.AbstractComponent;

public class Supervisor extends AbstractComponent{

	protected Supervisor(String reflectionInboundPortURI, int nbThreads, int nbSchedulableThreads) {
		super(reflectionInboundPortURI, nbThreads, nbSchedulableThreads);
		// TODO Auto-generated constructor stub
	}
 	protected static String[] samplesDirName = { "SamplesILP1" }; 

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
