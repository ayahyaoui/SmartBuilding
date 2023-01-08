package composants;

import fr.sorbonne_u.components.AbstractComponent;

public class SalleDeReunion extends AbstractComponent {

	protected int nbPersonnes ;
	protected int nbFenetres ;
	
	
	protected SalleDeReunion(int nbThreads, int nbSchedulableThreads) {
		super(nbThreads, nbSchedulableThreads);
	}

}
