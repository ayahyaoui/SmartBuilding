package com.paracamplus.bcm.cvm;

import java.util.AbstractCollection;

import com.paracamplus.bcm.components.DesktopRoom;
import com.paracamplus.bcm.utils.Utils;

import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.cvm.AbstractCVM;


public class SmartBuildingCVM extends AbstractCVM{

    

    public SmartBuildingCVM() throws Exception {
        super();
        //TODO Auto-generated constructor stub
    }

    public void deploy() throws Exception {
        System.out.println("Deploying components..." +  DesktopRoom.class.getCanonicalName() );
        AbstractComponent.createComponent(
              DesktopRoom.class.getCanonicalName(),
                new Object[] {Utils.DEFAULT_NB_THREADS, Utils.DEFAULT_NB_SCHEDULABLE_THREADS});
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
        System.out.println("Hello World!");
        }
    }
}
