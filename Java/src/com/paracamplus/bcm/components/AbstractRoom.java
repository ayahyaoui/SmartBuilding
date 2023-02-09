package com.paracamplus.bcm.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;

import fr.sorbonne_u.components.AbstractComponent;

/*
 * Maybe factorize rooms.
 */
public abstract class AbstractRoom extends AbstractComponent{

    protected int nbWindows;
    protected int nbDoors;
    protected int nbLights;
    protected int nbHeaters;
    protected int nbSensors;
    protected int nbActuators;
    protected int dimension;
    protected int nbPeople;
    protected int floor;
    protected ArrayList<AbstractRoom> neighbours;
    // hasmap of string and function
    protected HashMap<String, Object> functions;
      

    protected AbstractRoom(int nbThreads, int nbSchedulableThreads) {
        super(nbThreads, nbSchedulableThreads);
    }
    
    protected AbstractRoom(int nbThreads, int nbSchedulableThreads, int nbWindows, int nbDoors, int nbLights, int nbHeaters, int nbSensors, int nbActuators, int dimension, int nbPeople, int floor, ArrayList<AbstractRoom> neighbours) {
        super(nbThreads, nbSchedulableThreads);
        this.nbWindows = nbWindows;
        this.nbDoors = nbDoors;
        this.nbLights = nbLights;
        this.nbHeaters = nbHeaters;
        this.nbSensors = nbSensors;
        this.nbActuators = nbActuators;
        this.dimension = dimension;
        this.nbPeople = nbPeople;
        this.floor = floor;
        this.neighbours = neighbours;
        this.functions = new HashMap<String, Object>();
        //this.functions.put("test", ()->{System.out.println("test");});
    }

    protected void test(){
        System.out.println("test");
    }

    protected void connectRoom(AbstractRoom room) {
        this.neighbours.add(room);
    }

    protected void addFunction(String name, Function function) {
        this.functions.put(name, function);
    }
}
