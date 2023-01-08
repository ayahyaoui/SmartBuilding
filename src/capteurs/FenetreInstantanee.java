package capteurs;

public class FenetreInstantanee implements CapteurI {

	protected Boolean ouverte ;

	public FenetreInstantanee(Boolean ouverte) {
		this.ouverte = ouverte;
	}

	public Boolean getOuverte() {
		return ouverte;
	}

	public void setOuverte(Boolean ouverte) {
		this.ouverte = ouverte;
	}
}
