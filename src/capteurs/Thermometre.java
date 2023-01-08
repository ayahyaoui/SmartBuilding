package capteurs;

public class Thermometre implements CapteurI {
	
	protected int temperature ;
	
	public int getTemperature() {
		return temperature;
	}

	public void setTemperature(int temperature) {
		this.temperature = temperature;
	}
	
	public Thermometre (int temperature) {
		this.temperature = temperature ;
	}
	
}
