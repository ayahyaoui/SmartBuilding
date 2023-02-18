package com.paracamplus.bcm.cvm;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

import com.paracamplus.bcm.components.Coordonator;
import com.paracamplus.bcm.components.DesktopRoom;
import com.paracamplus.bcm.components.Supervisor;
import com.paracamplus.bcm.utils.Utils;
import com.paracamplus.bcm.utils.buildingPlan;

import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.cvm.AbstractCVM;
import fr.sorbonne_u.components.cyphy.tools.aclocks.ClockServer;


public class SmartBuildingCVM extends AbstractCVM{
	
	public static long			EXECUTION_START;
    

    public SmartBuildingCVM() throws Exception {
        super();
    }

    public void deploy() throws Exception {
        System.out.println("Deploying components...");
        
        //Utils.testIntermediary2();

        AbstractComponent.createComponent(
				ClockServer.class.getCanonicalName(),
				new Object[]{Utils.CLOCK_URI,		// create the centralised clock
							 TimeUnit.MILLISECONDS.toNanos(System.currentTimeMillis() + Utils.DEFAULT_STARTUP_TIME),
							 Instant.parse(Utils.START_INSTANT),
							 Utils.ACC_FACTOR});
                             
        AbstractComponent.createComponent(
                Supervisor.class.getCanonicalName(),
                new Object[] {buildingPlan.SUPERVISOR_URI, Utils.CLOCK_URI, buildingPlan.coords});
        
        for (String coord : buildingPlan.coords) {
            AbstractComponent.createComponent(
                    Coordonator.class.getCanonicalName(),
                    new Object[] {coord,buildingPlan.SUPERVISOR_URI ,buildingPlan.roomsCoordonators.get(coord),buildingPlan.graphCoordonators.get(coord)});
        }
        /*  allRooms contains all room next
        public static String[] desktopRooms = null;
	public static String[] corridors = null;
	public static String[] classrooms = null;
	public static String[] meetingRooms = null;
	public static String[] coffeeBreakRooms = null;
	public static String[] toilets = null;
	public static String[] stairs = null;
        */
        buildingPlan.allRooms = new String[][] { buildingPlan.desktopRooms,buildingPlan.corridors,buildingPlan.classrooms,
            buildingPlan.meetingRooms,buildingPlan.coffeeBreakRooms,buildingPlan.toilets,buildingPlan.stairs};

        for (String[] rooms : buildingPlan.allRooms) {
            for (String room : rooms) {
                String myCoord = null;
                for (String coord : buildingPlan.coords) {
                    for (String r : buildingPlan.roomsCoordonators.get(coord)) {
                        if (r.equals(room)) {
                            if (myCoord != null) {
                                throw new Exception("Room " + room + " is assigned to two coordinators: " + myCoord + " and " + coord);
                            }
                            myCoord = coord;
                            break;
                        }
                    }
                }
                if (myCoord == null) {
                    throw new Exception("Room " + room + " is not assigned to any coordinator");
                }
                AbstractComponent.createComponent(
                        DesktopRoom.class.getCanonicalName(),
                        new Object[] {room, Utils.CLOCK_URI, myCoord, buildingPlan.roomsNeighbours.get(room)});
            }
        }      
        
    }

    public static void main(String[] args) {
        try {
            SmartBuildingCVM cvm = new SmartBuildingCVM();
            cvm.startStandardLifeCycle(Utils.EXECUTION_DURATION + Utils.DEFAULT_STARTUP_TIME);
            Thread.sleep(500L);
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
