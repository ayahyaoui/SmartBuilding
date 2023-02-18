package com.paracamplus.bcm.utils;

import java.util.ArrayList;
import java.util.HashMap;

public class ScenarioTest {

    private static boolean isInit = false;
    public static final String OPEN_DOOR = "openDoor";
    public static final String CLOSE_DOOR = "closeDoor";
    public static final String OPEN_WINDOW = "openWindow";
    public static final String CLOSE_WINDOW = "closeWindow";
    public static final String TURN_ON_LIGHT = "turnOnLight";
    public static final String TURN_OFF_LIGHT = "turnOffLight";
    public static final String TURN_ON_HEATER = "turnOnHeater";
    public static final String TURN_OFF_HEATER = "turnOffHeater";
    public static final String LOCK_DOOR = "lockDoor";
    public static final String UNLOCK_DOOR = "unlockDoor";
    public static final String SMOCKE_ALARM = "smockeAlarm";
    public static final String STOP_ALARM = "stopAlarm";
    public static final String ADD_PERSON = "addPerson";
    public static final String REMOVE_PERSON = "removePerson";
    public static final String ADD_SMOKE = "addSmoke";
    public static final String ADD_HEAT = "addHeat";
    
    
    public static class Scenario{
            public String action;
            public long start;
            public Scenario(String action, long start) {
                this.action = action;
                this.start = start;
            }
    }

    public static Scenario[] scenarios = null;
    public static HashMap<String , Scenario[]> scenariosMap = new HashMap<String , Scenario[]>();


    

    
    
    /*
     * add some random scenarios for the test
     * with few actions and random start time
     */

    private static void scnenario1() {
		if (isInit) {
			return;
		}
        isInit = true;
        scenariosMap.put(buildingPlan.CLASSROOM_101_URI, new Scenario[] {
                new Scenario(OPEN_DOOR, 0),
                new Scenario(CLOSE_DOOR, 1000),
                new Scenario(OPEN_WINDOW, 2000),
                new Scenario(TURN_ON_LIGHT, 4000),
                new Scenario(TURN_OFF_LIGHT, 5000),
                new Scenario(TURN_ON_HEATER, 6000),
                new Scenario(LOCK_DOOR, 8000),
                new Scenario(UNLOCK_DOOR, 9000),
                new Scenario(SMOCKE_ALARM, 10000),
                new Scenario(STOP_ALARM, 11000),
                new Scenario(REMOVE_PERSON, 13000),
                new Scenario(ADD_SMOKE, 14000),
                new Scenario(ADD_HEAT, 15000),
        });
        scenariosMap.put(buildingPlan.CLASSROOM_102_URI, new Scenario[] {
                new Scenario(OPEN_DOOR, 0),
                new Scenario(CLOSE_DOOR, 1000),
                new Scenario(OPEN_WINDOW, 2000),
                new Scenario(TURN_ON_LIGHT, 4000),
                new Scenario(TURN_OFF_LIGHT, 5000),
                new Scenario(TURN_ON_HEATER, 6000),
                new Scenario(LOCK_DOOR, 8000),
                new Scenario(UNLOCK_DOOR, 9000),
                new Scenario(SMOCKE_ALARM, 10000),
                new Scenario(STOP_ALARM, 11000),
                new Scenario(REMOVE_PERSON, 13000),
                new Scenario(ADD_SMOKE, 14000),
                new Scenario(ADD_HEAT, 15000),
        });

        scenariosMap.put(buildingPlan.MEETINGROOM_101_URI, new Scenario[] {
                new Scenario(OPEN_DOOR, 0),
                new Scenario(CLOSE_DOOR, 1000),
                new Scenario(OPEN_WINDOW, 2000),
                new Scenario(TURN_ON_LIGHT, 4000),
                new Scenario(TURN_OFF_LIGHT, 5000),
                new Scenario(TURN_ON_HEATER, 6000),
                new Scenario(LOCK_DOOR, 8000),
                new Scenario(UNLOCK_DOOR, 9000),
                new Scenario(SMOCKE_ALARM, 10000),
                new Scenario(STOP_ALARM, 11000),
                new Scenario(REMOVE_PERSON, 13000),
                new Scenario(ADD_SMOKE, 14000),
                new Scenario(ADD_HEAT, 15000),
        });

    }
}
