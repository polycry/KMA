
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package geschirrspueler;

/**
 *
 * @author Jonas-Breitenberger
 */
public class Heater {
    
    private Geschirrspueler gs;
    
    public Heater(Geschirrspueler gs) {
        this.gs = gs;
    }
    
    public void heatUp(JNIWrapper jni) {
        int tr = jni.getADCValue();
        while(tr < 1023/100*40) {
            gs.showTemperature(tr);
            try {
                Thread.sleep(5);
            } catch (InterruptedException ex) {
                Logger.getLogger(Heater.class.getName()).log(Level.SEVERE, null, ex);
            }
            tr = jni.getADCValue();
        }
        
    }
    
}
