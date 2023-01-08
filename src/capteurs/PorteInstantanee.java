package capteurs;

public class PorteInstantanee implements CapteurI {

	protected Boolean ouverte ;

	public Boolean getOuverte() {
		return ouverte;
	}

	public void setOuverte(Boolean ouverte) {
		this.ouverte = ouverte;
	}

	public PorteInstantanee(Boolean ouverte) {
		this.ouverte = ouverte;
	}
}
