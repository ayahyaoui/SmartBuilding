package components;

import java.util.ArrayList;

public class DesktopRoom extends AbstractRoom {

    protected DesktopRoom(int nbThreads, int nbSchedulableThreads) {
        super(nbThreads, nbSchedulableThreads);
        
    }
    protected DesktopRoom(int nbThreads, int nbSchedulableThreads, int nbWindows, int nbDoors, int nbLights, int nbHeaters, int nbSensors, int nbActuators, int dimension, int nbPeople, int floor, ArrayList<AbstractRoom> neighbours) {
        super(nbThreads, nbSchedulableThreads, nbWindows, nbDoors, nbLights, nbHeaters, nbSensors, nbActuators, dimension, nbPeople, floor, neighbours);
        //TODO Auto-generated constructor stub
    }
}
