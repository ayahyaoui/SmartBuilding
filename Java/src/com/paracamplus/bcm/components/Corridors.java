package com.paracamplus.bcm.components;

import java.util.ArrayList;

import fr.sorbonne_u.components.AbstractComponent;

public class Corridors extends AbstractComponent{
    protected int nbWindows;
    protected int floor;
    protected ArrayList<AbstractRoom> neighbours;
    //protected 
      
    protected Corridors(int nbThreads, int nbSchedulableThreads) {
        super(nbThreads, nbSchedulableThreads);
    }
    
    protected Corridors(int nbThreads, int nbSchedulableThreads, int nbWindows, int floor,ArrayList<AbstractRoom> neighbours) {
        super(nbThreads, nbSchedulableThreads);
        this.nbWindows = nbWindows;
        this.floor = floor;
        this.neighbours = neighbours;
    }

    protected void connectRoom(AbstractRoom room) {
        this.neighbours.add(room);
    }
    
}
