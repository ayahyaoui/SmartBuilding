package capteurs;

public class PresenceInstantanee implements CapteurI {
	
	protected Boolean presence ;
	
	public Boolean getPresence() {
		return presence;
	}

	public void setPresence(Boolean presence) {
		this.presence = presence;
	}

	public PresenceInstantanee (Boolean presence) {
		this.presence = presence ;
	}

}
