package capteurs;

public class FenetreRecente implements CapteurI {

	

	protected Boolean ouverte ;
	
	public FenetreRecente(Boolean ouverte) {
		this.ouverte = ouverte;
	}


	public Boolean getOuverte() {
		return ouverte;
	}

	public void setOuverte(Boolean ouverte) {
		this.ouverte = ouverte;
	}
}
