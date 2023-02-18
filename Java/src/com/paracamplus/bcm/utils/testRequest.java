package com.paracamplus.bcm.utils;

import java.util.ArrayList;

public class testRequest {

    private static boolean isInit = false;
	// all test requests
	public static String FONCTION_fire = "fire";
	public static String FONCTION_1 = "test01";
	public static String FONCTION_2 = "test02";
	public static String FONCTION_3 = "test03";
	public static String FONCTION_4 = "test04";
	public static String FONCTION_5 = "test05";

	// all real requests
	public static String FORGOTTENLIGHTON = "forgottenLightOn";
	public static String ROOMINTRUSION = "roomIntrusion";
	public static String bUILDINGINTRUSION = "buildingIntrusion";
	public static String CO2ALERT = "co2Alert";
	public static String LOCALFIRE = "localFire";
	public static String GENERALFIRE = "generalFire";
	public static String FIREGLOBAL = "fireGlobal";

    public static ArrayList<Request> request = new ArrayList<Request>();

    public static class Request {
        public String fonction;
        public String[] args;
        public long start;
        public long period;
        public Request(String fonction, String[] args, long start, long period) {
            this.fonction = fonction;
            this.args = args;
            this.start = start;
            this.period = period;
        }
    }

    /*
     * Init the list of requests
     * take  each room from building plan and add it to the list
     * take only real requests
     */
    public static void initSimul() {
        if (isInit) {
            return;
        }
        isInit = true;
        request.add(new Request(FORGOTTENLIGHTON, new String[] {buildingPlan.CLASSROOM_101_URI}, 0, 0));
        request.add(new Request(ROOMINTRUSION, new String[] {buildingPlan.DESKTOPROOM_102_URI}, 0, 0));
        request.add(new Request(bUILDINGINTRUSION, new String[] {buildingPlan.CLASSROOM_101_URI}, 0, 0));
        request.add(new Request(CO2ALERT, new String[] {buildingPlan.STAIRS_101_URI}, 0, 0));
        request.add(new Request(LOCALFIRE, new String[] {buildingPlan.CLASSROOM_101_URI}, 0, 0));
        request.add(new Request(GENERALFIRE, new String[] {buildingPlan.CLASSROOM_101_URI}, 0, 0));
        request.add(new Request(FIREGLOBAL, new String[] {buildingPlan.CLASSROOM_101_URI}, 0, 0));
        request.add(new Request(CO2ALERT, new String[] {buildingPlan.CLASSROOM_102_URI}, 0, 0));
        request.add(new Request(LOCALFIRE, new String[] {buildingPlan.TOILET_101_URI}, 0, 0));
        request.add(new Request(GENERALFIRE, new String[] {buildingPlan.TOILET_101_URI}, 0, 0));
        request.add(new Request(FIREGLOBAL, new String[] {buildingPlan.TOILET_101_URI}, 0, 0));
        request.add(new Request(CO2ALERT, new String[] {buildingPlan.TOILET_101_URI}, 0, 0));
    }
    
}