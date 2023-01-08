package capteurs;

public class Lumiere implements CapteurI {

	
	protected Boolean allumés ;

	public Boolean getAllumés() {
		return allumés;
	}

	public Lumiere(Boolean allumés) {
		this.allumés = allumés;
	}

	public void setAllumés(Boolean allumés) {
		this.allumés = allumés;
	}
}
