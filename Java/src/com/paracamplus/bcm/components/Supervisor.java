package com.paracamplus.bcm.components;


import java.io.File;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import com.paracamplus.bcm.connector.CoordonatorConnector;
import com.paracamplus.bcm.ibp.SupervisorIBP;
import com.paracamplus.bcm.interfaces.ScriptManagementCI;
import com.paracamplus.bcm.interfaces.SupervisorManagementCI;
import com.paracamplus.bcm.obp.CoordonatorOBP;
import com.paracamplus.bcm.utils.Utils;
import com.paracamplus.bcm.utils.testRequest;
import com.paracamplus.bcm.utils.testRequest.Request;
import com.paracamplus.ilp1.interfaces.IASTfunctionDefinition;
import com.paracamplus.ilp1.interfaces.IASTprogram;
import com.paracamplus.ilp1.interfaces.IASTsequence;
import com.paracamplus.ilp1.interpreter.GlobalEnvFile;
import com.paracamplus.ilp1.interpreter.test.InterpreterTest;
import com.paracamplus.ilp1.test.GlobalFunctionAst;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.cyphy.tools.aclocks.AcceleratedClock;
import fr.sorbonne_u.components.cyphy.tools.aclocks.ClockServer;
import fr.sorbonne_u.components.cyphy.tools.aclocks.ClockServerConnector;
import fr.sorbonne_u.components.cyphy.tools.aclocks.ClockServerOutboundPort;
import fr.sorbonne_u.components.exceptions.ComponentStartException;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;

@RequiredInterfaces(required = {ScriptManagementCI.class})
@OfferedInterfaces(offered = {SupervisorManagementCI.class})
public class Supervisor extends AbstractComponent{

	protected final String				clockURI;
	protected AcceleratedClock			clock;
	protected ClockServerOutboundPort	clockServerOBP;
    
    protected SupervisorIBP supervisorIBP;
    protected CoordonatorOBP supervisorOBP;
    protected String[] coordonatorIBPURIs;
    protected GlobalFunctionAst allGlobalFuction;
    
    protected Supervisor(String reflectionInboundPortURI,  String clockURI, String[] coordonatorIBPURIs) throws Exception  {
        super(reflectionInboundPortURI, 2, 1);
        assert	coordonatorIBPURIs != null && coordonatorIBPURIs.length > 0 ;
        this.clockURI = clockURI;
        this.supervisorOBP = new CoordonatorOBP(this);
        this.supervisorOBP.publishPort();
        this.supervisorIBP = new SupervisorIBP(reflectionInboundPortURI, this);
        this.supervisorIBP.publishPort();
        this.coordonatorIBPURIs = coordonatorIBPURIs;
        allGlobalFuction = GlobalFunctionAst.getInstance();
        
        this.tracer.get().setTitle("Supervisor component " + this.reflectionInboundPortURI);
		this.tracer.get().setRelativePosition(1, 0);
		this.toggleTracing();
		logMessage(this.toString());
        logMessage(toString());
    }
    

    private boolean	addFunction(IASTprogram program) throws Exception
    {
        IASTfunctionDefinition f = program.getFunction();
        if (!(f instanceof IASTfunctionDefinition) || f == null)
            throw new Exception("The program must contain a function definition");
        if (f.getBody() == null || ! (f.getBody() instanceof IASTsequence))
            throw new Exception("The function must contain a body of type sequence");
        IASTsequence s = (IASTsequence) f.getBody();
        return GlobalFunctionAst.getInstance().addFunction(f.getName(), s, f.getVariables());
    }


    private void callFunction(GlobalEnvFile env) throws Exception
    {
        final Supervisor vc = this ; 
        vc.runTask(
            new AbstractComponent.AbstractTask() {
                @Override
                public void run() {
                    logMessage(env.toString());
                    GlobalEnvFile result;
                    try {
                        result = vc.supervisorOBP.executeScript(env);
                        logMessage(result.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
    }
        
    
    @Override
    public synchronized void	start() throws ComponentStartException
    {
        super.start();
        try {
            ArrayList<IASTprogram> allProgram = new ArrayList<IASTprogram>();
            allProgram = InterpreterTest.lexerParserPrograms();
            for (IASTprogram iastprogram : allProgram) {
                addFunction(iastprogram);
            }
            
            for (int i = 0; i < coordonatorIBPURIs.length; i++) {
                this.doPortConnection(
                        this.supervisorOBP.getPortURI(),
                        coordonatorIBPURIs[i],
                        CoordonatorConnector.class.getCanonicalName());
            }

            this.clockServerOBP = new ClockServerOutboundPort(this);
			this.clockServerOBP.publishPort();
			this.doPortConnection(
					this.clockServerOBP.getPortURI(),
					ClockServer.STANDARD_INBOUNDPORT_URI,
					ClockServerConnector.class.getCanonicalName());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ComponentStartException(e) ;
        }
        this.logMessage("start.");
    }


    @Override
    public void execute() throws Exception {
        super.execute();
        System.out.println("************************************************************");
        System.out.println("*                        **********                        *");
        System.out.println("*                        Supervisor                        *");
        System.out.println("*                        **********                        *");
        System.out.println("************************************************************");
        
        this.clock = this.clockServerOBP.getClock(this.clockURI);
        Instant i0 = clock.getStartInstant();
        Instant istart = i0.plusSeconds(3600);
        Instant iEnd = istart.plusSeconds(24 * 3600);
        long minimumStart = clock.delayToAcceleratedInstantInNanos(istart) / 1000;
        long maximumEnd = clock.delayToAcceleratedInstantInNanos(iEnd) / 1000;

        final Supervisor vc = this;
        for (Request testRequete : testRequest.request) {
            assert (allGlobalFuction.verifyFunctionParameters(testRequete.fonction, testRequete.args));
            long start = testRequete.start;
            if (testRequete.start < minimumStart || testRequete.start > maximumEnd)
                start = minimumStart;
                this.scheduleTaskAtFixedRate(
                    new AbstractComponent.AbstractTask() {
                        @Override
                        public void run() {
                            try {
                                GlobalEnvFile env = new GlobalEnvFile(testRequete.fonction, testRequete.fonction, testRequete.args);
                                logMessage("executeScript loop + " + env.toString());
                                vc.supervisorOBP.executeScript(env);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    start,
                    testRequete.period,
                    TimeUnit.MICROSECONDS);
        }
 }
        
    @Override
    public String toString() {
        String s = "coordonatorIBPURIs = [";
        for (int i = 0; i < coordonatorIBPURIs.length; i++) {
            s += coordonatorIBPURIs[i] + ", ";
        }
        s += "]";
        return "Supervisor " + reflectionInboundPortURI + " " + s + "";
    }


    public void receiveResult(String id, boolean result) {
        System.out.println("receiveResult " + id + " " + result);
        
        logMessage("Resultat " + id + " " + result);
    }

}
