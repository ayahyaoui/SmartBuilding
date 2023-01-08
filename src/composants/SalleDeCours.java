package composants;

import fr.sorbonne_u.components.AbstractComponent;

public class SalleDeCours extends AbstractComponent {

	protected int nbPersonnes ;
	protected int nbFenetres ;
	
	protected SalleDeCours(int nbThreads, int nbSchedulableThreads) {
		super(nbThreads, nbSchedulableThreads);
	}

}
