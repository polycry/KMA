
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
public class Geschirrspueler implements ControllerListener {

    private Boolean on;
    private int program;
    private JNIWrapper jni;
    private Controller c;
    private Washer w;
    private Heater h;
    private GUI gui;

    public Geschirrspueler() {
        jni = new JNIWrapper();
        c = new Controller(this, jni);
        c.addListener(this);
        Thread th1 = new Thread(c);
        th1.start();
        gui = new GUI(this);
        w = new Washer(this, gui);
        h = new Heater(this);        
        on = false;
    }

    public void startProgram() {
        if (program == 1) {
            showDisplay(13, 16);
            w.prewash();
            showDisplay(11, 14);
            h.heatUp(jni);
            showDisplay(14, 16);
            w.wash();
        }
    }

    public void resetDisplay() {
        c.writeShiftReg(c.decoder(-1, 1));
        c.writeShiftReg(c.decoder(-1, 2));
    }

    public void showTemperature(int tr) {
        int adc = tr * 100 / 1023;
        c.writeShiftReg(c.decoder(adc / 10, 1));
        c.writeShiftReg(c.decoder(adc % 10, 2));
        System.out.println(adc / 10);
        System.out.println(adc % 10);
    }

    public void showCountdown(int cd) {
        if (cd >= 10) {
            for (int i = 0; i < 45; i++) {
                c.writeShiftReg(c.decoder(cd / 10, 1));
                c.writeShiftReg(c.decoder(cd % 10, 2));
                try {
                    Thread.sleep(5);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            for (int i = 0; i < 45; i++) {
                c.writeShiftReg(c.decoder(0, 1));
                c.writeShiftReg(c.decoder(cd, 2));
                try {
                    Thread.sleep(5);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        resetDisplay();
    }

    public void showDisplay(int d1, int d2) {
        for (int i = 0; i < 60; i++) {
            c.writeShiftReg(c.decoder(d1, 1));
            c.writeShiftReg(c.decoder(d2, 2));
            try {
                Thread.sleep(5);
            } catch (InterruptedException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        resetDisplay();
    }

    public int getProgram() {
        return program;
    }
    
    public GUI getGui() {
        return gui;
    }

    public void setProgram(int program) {
        this.program = program;
        System.out.println("P" + program + " ausgewaehlt");
        gui.setActualProgram(gui.getActualProgram().getText() + "\n  \n            P1");
        try {
            for (int i = 0; i < 60; i++) {
                c.writeShiftReg(c.decoder(13, 1)); // P
                c.writeShiftReg(c.decoder(getProgram(), 2)); // 1
                Thread.sleep(10);
            }
            resetDisplay();
        } catch (Exception e) {

        }
    }

    public void changeOn() {
        on = !on;
        try {
            if (isOn()) {
                System.out.println("ON");
                gui.getActualProgram().setVisible(true);
                gui.setActualProgram("Aktuelles Programm");
                gui.getPb().setVisible(true);
                for (int i = 0; i < 60; i++) {
                    c.writeShiftReg(c.decoder(0, 1));
                    c.writeShiftReg(c.decoder(12, 2)); //n
                    Thread.sleep(10);
                }
            } else {
                System.out.println("OFF");
                gui.getActualProgram().setVisible(false);
                gui.getPb().setVisible(false);
                for (int i = 0; i < 60; i++) {
                    c.writeShiftReg(c.decoder(0, 1));
                    c.writeShiftReg(c.decoder(15, 2)); //F
                    Thread.sleep(10);
                }
            }
        } catch (Exception e) {
        }
        resetDisplay();
    }

    public Boolean isOn() {
        return on;
    }

    public JNIWrapper getJNI() {
        return jni;
    }

    public static void main(String[] args) throws InterruptedException {
        Geschirrspueler gs = new Geschirrspueler();
    }

    @Override
    public void listen() {
        startProgram();
    }

}
