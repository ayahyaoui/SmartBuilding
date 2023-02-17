package com.paracamplus.bcm.cvm;

import com.paracamplus.bcm.components.Coordonator;
import com.paracamplus.bcm.components.DesktopRoom;
import com.paracamplus.bcm.components.Supervisor;
import com.paracamplus.bcm.utils.Utils;

import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.cvm.AbstractDistributedCVM;

public class SmartBuildingDistributedCVM extends AbstractDistributedCVM{
    public String JVMURI1 = "JVM1";
	public String JVMURI2 = "JVM2";
	protected static final String	VALUE_PROVIDER_INBOUND_PORT_URI =
												"value-provider-inbound-port";

	public				SmartBuildingDistributedCVM(String[] args)
	throws Exception
	{
		super(args);
	}

    @Override
	public void initialise() throws Exception {
		super.initialise();
		String[] jvmURIs = this.configurationParameters.getJvmURIs();
		boolean JVMURI1_OK = false;
		boolean JVMURI2_OK = false;

		for (int i = 0; i < jvmURIs.length; i++) {
			if (jvmURIs[i].equals(JVMURI1) && !JVMURI1_OK) {
				JVMURI1_OK = true;
			} else if (jvmURIs[i].equals(JVMURI2) && !JVMURI2_OK) {
				JVMURI2_OK = true;
			}
            else {
                throw new Exception("Uknown JVM URI: " + jvmURIs[i] + "or JVM URI already used");
            }

		}

	}

	/**
	 * @see fr.sorbonne_u.components.cvm.AbstractDistributedCVM#instantiateAndPublish()
	 */
	@Override
	public void			instantiateAndPublish() throws Exception
	{
		// ---------------------------------------------------------------------
		// Creation phase
		// ---------------------------------------------------------------------
        String[] rooms = new String[] {Utils.DESKTOPROOM_101_URI, Utils.DESKTOPROOM_102_URI};
        String[] coords = new String[] {Utils.COORDONATOR_01_URI};
        
		if (thisJVMURI.equals(JVMURI1)) {
			String vcURI = 
				AbstractComponent.createComponent(
                    DesktopRoom.class.getCanonicalName(),
                    new Object[] {Utils.DESKTOPROOM_101_URI, Utils.CLOCK_URI,Utils.COORDONATOR_01_URI});
			this.toggleTracing(vcURI);
		} else if (thisJVMURI.equals(JVMURI2)) {
			String rvpURI =AbstractComponent.createComponent(
        		DesktopRoom.class.getCanonicalName(),
        		new Object[] {Utils.DESKTOPROOM_102_URI, Utils.CLOCK_URI,Utils.COORDONATOR_01_URI});
        
        AbstractComponent.createComponent(
                Coordonator.class.getCanonicalName(),
                  new Object[] {Utils.COORDONATOR_01_URI, rooms});

        AbstractComponent.createComponent(
                Supervisor.class.getCanonicalName(),
                  new Object[] {Utils.SUPERVISOR_URI, coords});
				
			this.toggleTracing(rvpURI);
		} else {
			throw new Exception("Uknown JVM URI: " + thisJVMURI);
		}
		super.instantiateAndPublish();
	}

	public static void	main(String[] args)
	{
		try {
			SmartBuildingDistributedCVM a = new SmartBuildingDistributedCVM(args);
			a.startStandardLifeCycle(5000L);
			Thread.sleep(5000L);
			System.out.println("bye.");
			System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
