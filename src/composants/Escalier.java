package composants;

import fr.sorbonne_u.components.AbstractComponent;

public class Escalier extends AbstractComponent {


	protected int nbFenetres ;
	
	protected Escalier(int nbThreads, int nbSchedulableThreads) {
		super(nbThreads, nbSchedulableThreads);
	}

}
