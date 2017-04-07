/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package geschirrspueler;

import java.util.Iterator;
import java.util.Vector;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jonas-Breitenberger
 */
public class Controller implements Runnable {

    public static int TASTER_1 = 22;
    public static int TASTER_2 = 18;
    public static int TASTER_3 = 4;
    private final int SERIN = 25;						//GPIO 25
    private final int RCLK = 27;						//GPIO 27
    private final int SCLK = 17;
    private Geschirrspueler gs;
    private JNIWrapper jni;
    private Vector<ControllerListener> listeners;

    public Controller(Geschirrspueler gs, JNIWrapper jni) {
        this.jni = jni;
        this.gs = gs;
        listeners = new Vector<ControllerListener>();
        if (jni.loadMemory()) {
            jni.configPortIO(TASTER_1, JNIWrapper.INPUT);
            jni.configPortIO(TASTER_2, JNIWrapper.INPUT);
            jni.configPortIO(TASTER_3, JNIWrapper.INPUT);

            jni.configPortAlt(3, JNIWrapper.ALT0);
            jni.configPortAlt(2, JNIWrapper.ALT0);

        }
    }
    
    public void addListener(ControllerListener list) {
        listeners.add(list);
    }
    
    private void delayus(int us) {

        try {
            TimeUnit.MICROSECONDS.sleep(us);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void writeShiftReg(int REG) // Shift operations only on variables of type int
    {
        jni.writeOutput(RCLK, 0);

        jni.configPortIO(SERIN, JNIWrapper.OUTPUT);
        jni.configPortIO(SCLK, JNIWrapper.OUTPUT);
        jni.configPortIO(RCLK, JNIWrapper.OUTPUT);

        jni.configPortAlt(3, JNIWrapper.ALT0);
        jni.configPortAlt(2, JNIWrapper.ALT0);

        for (int i = 0; i < 8; i++) {
            jni.writeOutput(SCLK, 0);

            if ((REG & 0b10000000) > 0) {
                jni.writeOutput(SERIN, 1);
            } else {
                jni.writeOutput(SERIN, 0);
            }

            REG = REG << 1;

            jni.writeOutput(SCLK, 1);
            delayus(1);
            jni.writeOutput(SCLK, 0);
        }

        jni.writeOutput(RCLK, 1);
        delayus(1);
        jni.writeOutput(RCLK, 0);
        delayus(1);
    }

    public int decoder(int number, int display) {
        int segments = 0;

        //Lookup Table
         switch (number) {
            //a,b,c,d,e,f,g,display
            case 0:
                segments = 0b00000010;
                break;                                       // 0
            case 1:
                segments = 0b10011110;
                break;                                       // 1
            case 2:
                segments = 0b00100100;
                break;                                       // 2
            case 3:
                segments = 0b00001100;
                break;                                       // 3
            case 4:
                segments = 0b10011000;
                break;                                       // 4
            case 5:
                segments = 0b01001000;
                break;                                       // 5
            case 6:
                segments = 0b01000000;
                break;                                       // 6
            case 7:
                segments = 0b00011110;
                break;                                       // 7
            case 8:
                segments = 0b00000000;
                break;                                       // 8
            case 9:
                segments = 0b00001000;
                break;                                      // 9
            case 10:
                segments = 0b0001_0000;
                break;                                       // A
            case 11:
                segments = 0b1001_0010;
                break;                                       // H
            case 12:
                segments = 0b1101_0100;
                break;                                       // n
            case 13:
                segments = 0b0011_0000;
                break;                                       // P
            case 14:
                segments = 0b1000_0010;
                break;                                       // U
            case 15:
                segments = 0b0111_0000;
                break;                                       // F
            case 16:
                segments = 0b0001_0000;
                break;                                       // R
            default:
                segments = 0b1111_1110;                      // clear
        }

        if (display == 2) // Set bit 0 to switch on display 2														  
        {
            segments = segments | 0b0000_0001;
        }

        return segments;
    }

    @Override
    public void run() {
        while (true)
        {
            int t1 = jni.readInput(TASTER_1);
            int t2 = jni.readInput(TASTER_2);
            int t3 = jni.readInput(TASTER_3);

            if (t1 == 1) {
                gs.changeOn();
            }
            else if (t2 == 1) {
                if (gs.isOn()) {
                    gs.setProgram(1);
                }
            }
            else if (t3 == 1) {
                if (gs.isOn() && gs.getProgram() != 0) {
                    System.out.println("P1 start");
                    Iterator<ControllerListener> it = listeners.iterator();
                    while (it.hasNext()) {
                        ControllerListener l = it.next();
                        l.listen();
                    }
                }
            }
            
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
