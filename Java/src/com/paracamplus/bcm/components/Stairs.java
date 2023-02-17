package com.paracamplus.bcm.components;

import fr.sorbonne_u.components.AbstractComponent;

public class Stairs extends AbstractComponent{
    protected int nbWindows;
    protected Corridors bottomCorridors;
    protected Corridors topCorridors;

      
    protected Stairs(int nbThreads, int nbSchedulableThreads) {
        super(nbThreads, nbSchedulableThreads);
    }
    
    protected Stairs(int nbThreads, int nbSchedulableThreads, int nbWindows, Corridors bottomCorridors, Corridors topCorridors) {
        super(nbThreads, nbSchedulableThreads);
        this.nbWindows = nbWindows; 
        this.bottomCorridors = bottomCorridors;
        this.topCorridors = topCorridors;
    }
}
