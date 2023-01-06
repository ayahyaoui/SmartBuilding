package cvm;

import fr.sorbonne_u.components.cvm.AbstractCVM;
import utils.Utils;

public class SmartBuildingCVM extends AbstractCVM{

    

    public SmartBuildingCVM() throws Exception {
        super();
        //TODO Auto-generated constructor stub
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
