package composants;

import fr.sorbonne_u.components.AbstractComponent;

public class Toilettes extends AbstractComponent {

	protected int nbPersonnes ;
	protected int nbFenetres ;
	
	protected Toilettes(int nbThreads, int nbSchedulableThreads) {
		super(nbThreads, nbSchedulableThreads);
	}

}
