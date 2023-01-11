package com.paracamplus.bcm.components;

import java.util.ArrayList;

import com.paracamplus.bcm.obp.DesktopRoomOBP;

import fr.sorbonne_u.components.AbstractComponent;

public class Coordonator extends AbstractComponent{

	protected ArrayList<DesktopRoomOBP>  desktopOBPList;
	
	protected Coordonator(int nbThreads, int nbSchedulableThreads) throws Exception {
		super(nbThreads, nbSchedulableThreads);
		// TODO Auto-generated constructor stub
		for (int i = 0; i < 2; i++) {
			DesktopRoomOBP desktopRoomOBP = new DesktopRoomOBP(this);
			desktopOBPList.add(desktopRoomOBP);
			desktopRoomOBP.publishPort();
		}
	
	}

}
