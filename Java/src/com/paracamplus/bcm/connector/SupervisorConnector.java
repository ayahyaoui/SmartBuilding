package com.paracamplus.bcm.connector;

import com.paracamplus.bcm.interfaces.SupervisorManagementCI;

import fr.sorbonne_u.components.connectors.AbstractConnector;

public class SupervisorConnector  extends AbstractConnector implements  SupervisorManagementCI{

    @Override
    public void receiveResult(String id, boolean result) throws Exception {
        ((SupervisorManagementCI)this.offering).receiveResult(id, result);
    }

}
