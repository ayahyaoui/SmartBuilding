package capteurs;

public class DetecteurCO2 implements CapteurI {

	
	protected int ppm ;

	public int getPpm() {
		return ppm;
	}

	public void setPpm(int ppm) {
		this.ppm = ppm;
	}
}
