package com.paracamplus.bcm.utils;

import java.util.HashMap;

public class buildingPlan {
    public static boolean isInit = false;
    public static String[] coords = null;

    public static String[] desktopRooms = new String[]{};
	public static String[] corridors = new String[]{};
	public static String[] classrooms = new String[]{};
	public static String[] meetingRooms = new String[]{};
	public static String[] coffeeBreakRooms = new String[]{};
	public static String[] toilets = new String[]{};
	public static String[] stairs = new String[]{};
	public static String[][] allRooms = null;

    public static final String SUPERVISOR_URI = "supervisor-uri";
	public static final String	COORDONATOR_01_URI = "coordonator-01";
    public static final String	COORDONATOR_02_URI = "coordonator-02";
    public static final String	COORDONATOR_03_URI = "coordonator-03";

    public static final String	DESKTOPROOM_101_URI = "bureau-101";
	public static final String	DESKTOPROOM_102_URI = "bureau-102";
	public static final String	DESKTOPROOM_103_URI = "bureau-103";

	public static final String	CLASSROOM_101_URI = "SalleDeCours-101";
	public static final String CLASSROOM_102_URI = "SalleDeCours-102";
	
	public static final String	CORRIDOR_101_URI = "couloir-101";
	public static final String	CORRIDOR_102_URI = "couloir-102";
    public static final String	CORRIDOR_103_URI = "couloir-103";

	//Salle de réunion
	public static final String	MEETINGROOM_101_URI = "SalleDeReunion-101";
	public static final String	MEETINGROOM_102_URI = "SalleDeReunion-102";
	// Salle de convivialité
	public static final String	COFFEEBREAKROOM_101_URI = "SalleDeConvivialite-101";
	public static final String	COFFEEBREAKROOM_102_URI = "SalleDeConvivialite-102";

	//Toilettes
	public static final String	TOILET_101_URI = "Toilettes-101";
	public static final String	TOILET_102_URI = "Toilettes-102";
    public static final String	TOILET_103_URI = "Toilettes-103";

	//Escaliers
	public static final String	STAIRS_101_URI = "Escaliers-101";
	public static final String	STAIRS_102_URI = "Escaliers-102";
    
    
	public static HashMap<String, String[]> roomsNeighbours = null;
	public static HashMap<String, String[]> roomsCoordonators = null;
	public static HashMap<String, String[]> graphCoordonators = null; // directed graph of coordonators

    private static void buildingPlan1() {
    
        desktopRooms = new String[] {DESKTOPROOM_101_URI, DESKTOPROOM_102_URI};
        coords = new String[] {COORDONATOR_01_URI};
        roomsNeighbours = new HashMap<String, String[]>();
        roomsCoordonators = new HashMap<String, String[]>();
        graphCoordonators = new HashMap<String, String[]>();
        for (String r : desktopRooms) {
            roomsNeighbours.put(r, new String[] {});
        }
        for (String c : coords) {
            roomsCoordonators.put(c, new String[] {});
        }
        roomsCoordonators.put(COORDONATOR_01_URI, new String[] {DESKTOPROOM_101_URI, DESKTOPROOM_102_URI});
        roomsNeighbours.put(DESKTOPROOM_101_URI, new String[] {DESKTOPROOM_102_URI});
        roomsNeighbours.put(DESKTOPROOM_102_URI, new String[] {DESKTOPROOM_101_URI});
        graphCoordonators.put(COORDONATOR_01_URI, new String[] {});

    }
    
    private static void buildingPlan2() {
		desktopRooms = new String[] {DESKTOPROOM_101_URI, DESKTOPROOM_102_URI};
        corridors = new String[] {CORRIDOR_101_URI, CORRIDOR_102_URI};
        classrooms = new String[] {CLASSROOM_101_URI};
        meetingRooms = new String[] {MEETINGROOM_101_URI};
        coffeeBreakRooms = new String[] {COFFEEBREAKROOM_102_URI};
        toilets = new String[] {TOILET_101_URI, TOILET_102_URI};
        stairs = new String[] {STAIRS_101_URI};
		coords = new String[] {COORDONATOR_01_URI, COORDONATOR_02_URI};
        allRooms = new String[][] {desktopRooms, corridors, classrooms, meetingRooms, coffeeBreakRooms, toilets, stairs};
		
        roomsNeighbours = new HashMap<String, String[]>();
		roomsCoordonators = new HashMap<String, String[]>();
		graphCoordonators = new HashMap<String, String[]>();
		for (String r : desktopRooms) {
			roomsNeighbours.put(r, new String[] {});
		}
		for (String c : coords) {
			roomsCoordonators.put(c, new String[] {});
		}
        // coordonator 1 take all rooms finished by 01
		roomsCoordonators.put(COORDONATOR_01_URI, new String[] {DESKTOPROOM_101_URI, CORRIDOR_101_URI, CLASSROOM_101_URI, MEETINGROOM_101_URI, TOILET_101_URI, STAIRS_101_URI});
        // coordonator 2 take all rooms finished by 02
        roomsCoordonators.put(COORDONATOR_02_URI, new String[] {DESKTOPROOM_102_URI, CORRIDOR_102_URI, COFFEEBREAKROOM_102_URI, TOILET_102_URI});
        // coordonator 1 is neighbour of coordonator 2
        graphCoordonators.put(COORDONATOR_01_URI, new String[] {COORDONATOR_02_URI});
        // coordonator 2 is neighbour of coordonator 1
        graphCoordonators.put(COORDONATOR_02_URI, new String[] {COORDONATOR_01_URI});
        // corridor 1 is neighbour of all rooms finished by 01
        roomsNeighbours.put(CORRIDOR_101_URI, new String[] {DESKTOPROOM_101_URI, CLASSROOM_101_URI, MEETINGROOM_101_URI, TOILET_101_URI, STAIRS_101_URI});
        // corridor 2 is neighbour of all rooms finished by 02 and stairs 101
        roomsNeighbours.put(CORRIDOR_102_URI, new String[] {DESKTOPROOM_102_URI, COFFEEBREAKROOM_102_URI, TOILET_102_URI, STAIRS_101_URI});
        // classroom 1 is neighbour of corridor 1
        roomsNeighbours.put(CLASSROOM_101_URI, new String[] {CORRIDOR_101_URI});
        // meeting room 1 is neighbour of corridor 1
        roomsNeighbours.put(MEETINGROOM_101_URI, new String[] {CORRIDOR_101_URI});
        // coffee break room 2 is neighbour of corridor 2 and desktop room 2
        roomsNeighbours.put(COFFEEBREAKROOM_102_URI, new String[] {CORRIDOR_102_URI, DESKTOPROOM_102_URI});
        // toilet 1 is neighbour of corridor 1
        roomsNeighbours.put(TOILET_101_URI, new String[] {CORRIDOR_101_URI});
        // toilet 2 is neighbour of corridor 2
        roomsNeighbours.put(TOILET_102_URI, new String[] {CORRIDOR_102_URI});   
        // stairs 101 is neighbour of corridor 1 and corridor 2
        roomsNeighbours.put(STAIRS_101_URI, new String[] {CORRIDOR_101_URI, CORRIDOR_102_URI});
        // desktop room 1 is neighbour of corridor 1
        roomsNeighbours.put(DESKTOPROOM_101_URI, new String[] {CORRIDOR_101_URI});
        // desktop room 2 is neighbour of corridor 2 and coffee break room 2
        roomsNeighbours.put(DESKTOPROOM_102_URI, new String[] {CORRIDOR_102_URI, COFFEEBREAKROOM_102_URI});
        //  

        
        
	}

    /*
     TODO build a plan with 3 coordinators and  all rooms
     and good connections between them
     need corridors between each rooms
     coordonator is NOT a room
     */ 
     private static void buildingPlan3() {
        desktopRooms = new String[] {DESKTOPROOM_101_URI, DESKTOPROOM_102_URI, DESKTOPROOM_103_URI};
        corridors = new String[] {CORRIDOR_101_URI, CORRIDOR_102_URI, CORRIDOR_103_URI};
        classrooms = new String[] {CLASSROOM_101_URI, CLASSROOM_102_URI};
        meetingRooms = new String[] {MEETINGROOM_101_URI, MEETINGROOM_102_URI};
        coffeeBreakRooms = new String[] {COFFEEBREAKROOM_102_URI, COFFEEBREAKROOM_102_URI};
        toilets = new String[] {TOILET_101_URI, TOILET_102_URI, TOILET_103_URI};
        stairs = new String[] {STAIRS_101_URI, STAIRS_102_URI};

        allRooms = new String[][] {desktopRooms, corridors, classrooms, meetingRooms, coffeeBreakRooms, toilets, stairs};
        roomsNeighbours = new HashMap<String, String[]>();
        roomsCoordonators = new HashMap<String, String[]>();
        graphCoordonators = new HashMap<String, String[]>();
        for (String r : desktopRooms) {
            roomsNeighbours.put(r, new String[] {});
        }
        for (String c : coords) {
            roomsCoordonators.put(c, new String[] {});
        }
        // coordonator 1 take all rooms finished by 01 
        roomsCoordonators.put(COORDONATOR_01_URI, new String[] {DESKTOPROOM_101_URI, CORRIDOR_101_URI, CLASSROOM_101_URI, MEETINGROOM_101_URI, TOILET_101_URI, STAIRS_101_URI});
        // coordonator 2 take all rooms finished by 02
        roomsCoordonators.put(COORDONATOR_02_URI, new String[] {DESKTOPROOM_102_URI, CORRIDOR_102_URI, COFFEEBREAKROOM_102_URI, TOILET_102_URI});
        // coordonator 3 take all rooms finished by 03
        roomsCoordonators.put(COORDONATOR_03_URI, new String[] {DESKTOPROOM_103_URI, CORRIDOR_103_URI, TOILET_103_URI, STAIRS_102_URI});
        // to long to write next time 
	}
	
	
}
