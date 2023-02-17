package com.paracamplus.bcm.sensor;

import com.paracamplus.bcm.simul.LightSimul;

import fr.sorbonne_u.components.cyphy.tools.aclocks.AcceleratedClock;

public class SensorLight extends AbstractSensor implements ISensor{
        
        protected boolean value;
        protected boolean nextValue;
        protected LightSimul lightSim;

        
        public SensorLight(AcceleratedClock clock, LightSimul lightSim){
            super(clock);
            this.value = false;
            this.nextValue = false;
            this.lightSim = lightSim;
        }
    
        @Override
        public void updateValue() {
            value = nextValue;
        }
    
        @Override
        public Boolean getValue() {
            return value;
        }
    
        @Override
        public void eval(){
            nextValue = lightSim.isOn();
        }
    
}
