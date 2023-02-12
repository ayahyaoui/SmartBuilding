package com.paracamplus.bcm.cvm;

import java.time.Instant;
import java.util.AbstractCollection;
import java.util.concurrent.TimeUnit;

import com.paracamplus.bcm.components.Coordonator;
import com.paracamplus.bcm.components.DesktopRoom;
import com.paracamplus.bcm.components.Supervisor;
import com.paracamplus.bcm.utils.Utils;

import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.cvm.AbstractCVM;
import fr.sorbonne_u.components.cyphy.tools.aclocks.ClockServer;


public class SmartBuildingCVM extends AbstractCVM{
	
	public static long			EXECUTION_START;
    

    public SmartBuildingCVM() throws Exception {
        super();
        //TODO Auto-generated constructor stub
    }

    public void deploy() throws Exception {
        System.out.println("Deploying components..." +  DesktopRoom.class.getCanonicalName() );
        
        /*AbstractComponent.createComponent(
				ClockServer.class.getCanonicalName(),
				new Object[]{Utils.CLOCK_URI,		// create the centralised clock
							 TimeUnit.MILLISECONDS.toNanos(EXECUTION_START),
							 Instant.parse(Utils.START_INSTANT),
							 Utils.ACC_FACTOR});
                             */
        String[] rooms = new String[] {Utils.DESKTOPROOM_101_ID, Utils.DESKTOPROOM_102_ID};
        String[] coords = new String[] {Utils.COORDONATOR_ID};
        
        AbstractComponent.createComponent(
        		DesktopRoom.class.getCanonicalName(),
        		new Object[] {Utils.DESKTOPROOM_101_ID, Utils.CLOCK_URI,Utils.COORDONATOR_ID});

        AbstractComponent.createComponent(
        		DesktopRoom.class.getCanonicalName(),
        		new Object[] {Utils.DESKTOPROOM_102_ID, Utils.CLOCK_URI,Utils.COORDONATOR_ID});
        
        AbstractComponent.createComponent(
                Coordonator.class.getCanonicalName(),
                  new Object[] {Utils.COORDONATOR_ID, rooms});

        AbstractComponent.createComponent(
                Supervisor.class.getCanonicalName(),
                  new Object[] {Utils.SUPERVISOR_URI, coords});
        
          
        //TODO Auto-generated method stub
    }

    public static void main(String[] args) {
        try {
            SmartBuildingCVM cvm = new SmartBuildingCVM();
            cvm.startStandardLifeCycle(Utils.DEFAULT_LIFE_CYCLE + Utils.DEFAULT_STARTUP_TIME);
            Thread.sleep(5000L);
            System.exit(0);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
