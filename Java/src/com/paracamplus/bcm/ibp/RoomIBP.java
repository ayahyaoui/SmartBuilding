package com.paracamplus.bcm.ibp;

import com.paracamplus.bcm.interfaces.RoomI;
import com.paracamplus.bcm.interfaces.ScriptManagementCI;
import com.paracamplus.ilp1.interpreter.GlobalEnvFile;

import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;

public class RoomIBP extends AbstractInboundPort implements ScriptManagementCI{
        
        private static final long serialVersionUID = 1L;
        public RoomIBP( ComponentI owner) throws Exception {
            super(ScriptManagementCI.class, owner);
            assert	owner instanceof RoomI;
        }
        
        public RoomIBP(String uri, ComponentI owner) throws Exception {
            super(uri, ScriptManagementCI.class, owner);
            assert	owner instanceof RoomI;
        }
    
        @Override
        public GlobalEnvFile executeScript(GlobalEnvFile env) throws Exception {
            return this.owner.handleRequest(
                new AbstractComponent.AbstractService<GlobalEnvFile>() {
                    @Override
                    public GlobalEnvFile call() throws Exception {
                        return ((RoomI)this.getServiceOwner()).executeScript(env);
                    }
                }) ;
        }
        
        @Override
        public GlobalEnvFile executeScript(GlobalEnvFile env, String uri) throws Exception {
            return this.owner.handleRequest(
                new AbstractComponent.AbstractService<GlobalEnvFile>() {
                    @Override
                    public GlobalEnvFile call() throws Exception {
                        return ((RoomI)this.getServiceOwner()).executeScript(env, uri);
                    }
                }) ;
        }

}
