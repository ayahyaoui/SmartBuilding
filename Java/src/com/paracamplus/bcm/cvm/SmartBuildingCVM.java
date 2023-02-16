package com.paracamplus.bcm.cvm;

import java.time.Instant;
import java.util.AbstractCollection;
import java.util.HashMap;
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

    public void buildingPlan() throws Exception {
        HashMap<String, String[]> roomsNeighbours = new HashMap<String, String[]>();
        String[] rooms = new String[] {Utils.DESKTOPROOM_101_URI, Utils.DESKTOPROOM_102_URI};
        roomsNeighbours.put(Utils.DESKTOPROOM_101_URI, new String[] {Utils.DESKTOPROOM_102_URI});
        roomsNeighbours.put(Utils.DESKTOPROOM_102_URI, new String[] {Utils.DESKTOPROOM_101_URI});
    }

    public void deploy() throws Exception {
        System.out.println("Deploying components...");
        
        Utils.buildingPlan2();

        AbstractComponent.createComponent(
				ClockServer.class.getCanonicalName(),
				new Object[]{Utils.CLOCK_URI,		// create the centralised clock
							 TimeUnit.MILLISECONDS.toNanos(System.currentTimeMillis() + Utils.DEFAULT_STARTUP_TIME),
							 Instant.parse(Utils.START_INSTANT),
							 Utils.ACC_FACTOR});
                             
        AbstractComponent.createComponent(
                Supervisor.class.getCanonicalName(),
                new Object[] {Utils.SUPERVISOR_URI, Utils.coords});
        
        for (String coord : Utils.coords) {
            AbstractComponent.createComponent(
                    Coordonator.class.getCanonicalName(),
                    new Object[] {coord, Utils.roomsCoordonators.get(coord)});
            /*
            Object[] constructorParams = new Object[] {coord, Utils.roomsCoordonators.get(coord)};
                    Class<?>[] actualsTypes = new Class[constructorParams.length] ;
            for (int i = 0 ; i < constructorParams.length ; i++) {
                actualsTypes[i] = constructorParams[i].getClass() ;
                System.out.println("Param " + i + " is " + constructorParams[i] + " of type " + actualsTypes[i]);
            }
            */
        }

        for (String room : Utils.rooms) {
            String myCoord = null;
            for (String coord : Utils.coords) {
                for (String r : Utils.roomsCoordonators.get(coord)) {
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
            System.out.println("Creating room " + room + " with coord " + myCoord + " and neighbours " + Utils.roomsNeighbours.get(room) + "class " +  DesktopRoom.class.getCanonicalName());
            /*Object[] constructorParams = new Object[] {room, Utils.CLOCK_URI, myCoord, Utils.roomsNeighbours.get(room)};
            if (constructorParams[3]!= null) {
                System.out.println("Adding neighbours " + Utils.roomsNeighbours.get(room)[0]);
            }
            Class<?>[] actualsTypes = new Class[constructorParams.length] ;
            for (int i = 0 ; i < constructorParams.length ; i++) {
                actualsTypes[i] = constructorParams[i].getClass() ;
                System.out.println("Param " + i + " is " + constructorParams[i] + " of type " + actualsTypes[i]);
            }*/
            AbstractComponent.createComponent(
                    DesktopRoom.class.getCanonicalName(),
                    new Object[] {room, Utils.CLOCK_URI, myCoord, Utils.roomsNeighbours.get(room)});
        }
    }
    /*
    @Override
    public void deploy() throws Exception {
        System.out.println("Deploying components..." +  DesktopRoom.class.getCanonicalName() );
        
        AbstractComponent.createComponent(
				ClockServer.class.getCanonicalName(),
				new Object[]{Utils.CLOCK_URI,		// create the centralised clock
							 TimeUnit.MILLISECONDS.toNanos(System.currentTimeMillis() + Utils.DEFAULT_STARTUP_TIME),
							 Instant.parse(Utils.START_INSTANT),
							 Utils.ACC_FACTOR});
                             
        String[] rooms = new String[] {Utils.DESKTOPROOM_101_URI, Utils.DESKTOPROOM_102_URI};
        String[] coords = new String[] {Utils.COORDONATOR_01_URI};
        
        AbstractComponent.createComponent(
        		DesktopRoom.class.getCanonicalName(),
        		new Object[] {Utils.DESKTOPROOM_101_URI, Utils.CLOCK_URI,Utils.COORDONATOR_01_URI});

        AbstractComponent.createComponent(
        		DesktopRoom.class.getCanonicalName(),
        		new Object[] {Utils.DESKTOPROOM_102_URI, Utils.CLOCK_URI,Utils.COORDONATOR_01_URI});
        
        AbstractComponent.createComponent(
                Coordonator.class.getCanonicalName(),
                  new Object[] {Utils.COORDONATOR_01_URI, rooms});

        AbstractComponent.createComponent(
                Supervisor.class.getCanonicalName(),
                  new Object[] {Utils.SUPERVISOR_URI, coords});
        
    }*/

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
