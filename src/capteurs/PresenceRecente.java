package capteurs;

public class PresenceRecente implements CapteurI{

	protected Boolean presence ;

	public PresenceRecente(Boolean presence) {
		this.presence = presence;
	}

	public Boolean getPresence() {
		return presence;
	}

	public void setPresence(Boolean presence) {
		this.presence = presence;
	}
	
	
}
