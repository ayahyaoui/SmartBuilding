package com.paracamplus.bcm.interfaces;

import fr.sorbonne_u.components.interfaces.OfferedCI;
import fr.sorbonne_u.components.interfaces.RequiredCI;

public interface SupervisorManagementCI extends	OfferedCI,
RequiredCI{
    public void receiveResult(String id, boolean result) throws Exception;
}
