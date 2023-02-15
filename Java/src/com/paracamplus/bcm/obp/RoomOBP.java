public class RoomOBP extends AbstractOutboundPort
implements  ScriptManagementCI  {

	
	private static final long serialVersionUID = 1L;

    public RoomOBP(String uri, ComponentI owner)throws Exception {
        super(uri, URIConsumerCI.class, owner) ;
        assert	uri != null && owner != null && owner instanceof Room;
        
    }

    public RoomOBP( ComponentI owner)throws Exception {
        super(URIConsumerCI.class, owner) ;
        assert	owner != null && owner instanceof Room;
        
    }
	
	@Override
	public GlobalEnvFile executeScript(GlobalEnvFile env) throws Exception {
		return ((ScriptManagementCI)this.getConnector()).executeScript(env);
	}

	@Override
	public GlobalEnvFile executeScript(GlobalEnvFile env, String uri) throws Exception {
		return ((ScriptManagementCI)this.getConnector()).executeScript(env, uri);
	}   
}
