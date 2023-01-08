package capteurs;

public class PorteVerouille implements CapteurI {

	protected Boolean verouille ;

	public PorteVerouille(Boolean verouille) {
		this.verouille = verouille;
	}

	public Boolean getVerouille() {
		return verouille;
	}

	public void setVerouille(Boolean verouille) {
		this.verouille = verouille;
	}
	
}
