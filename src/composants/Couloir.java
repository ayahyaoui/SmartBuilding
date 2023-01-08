package composants;

import fr.sorbonne_u.components.AbstractComponent;

public class Couloir extends AbstractComponent {

	
	protected int nbFenetres ;
	
	
	protected Couloir(int nbThreads, int nbSchedulableThreads) {
		super(nbThreads, nbSchedulableThreads);
	}

}
