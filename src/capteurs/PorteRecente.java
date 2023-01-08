package capteurs;

public class PorteRecente implements CapteurI {

	protected Boolean ouverte ;

	public PorteRecente(Boolean ouverte) {
		super();
		this.ouverte = ouverte;
	}

	public Boolean getOuverte() {
		return ouverte;
	}

	public void setOuverte(Boolean ouverte) {
		this.ouverte = ouverte;
	}
}
