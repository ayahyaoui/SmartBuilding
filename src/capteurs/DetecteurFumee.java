package capteurs;

public class DetecteurFumee implements CapteurI {

	public enum Intensitefumee { 
		PASDEFUMEE ,
		FAIBLE , 
		MOYENNE , 
		MODEREE , 
		INTENSE , 
		EXTREME
	} 
	
	protected Intensitefumee intensite ;

	public DetecteurFumee(Intensitefumee intensite) {
		super();
		this.intensite = intensite;
	}

	public Intensitefumee getIntensite() {
		return intensite;
	}

	public void setIntensite(Intensitefumee intensite) {
		this.intensite = intensite;
	}
	
	
	
	
}

