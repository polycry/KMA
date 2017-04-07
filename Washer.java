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
public class Washer {

    private Geschirrspueler gs;
    private GUI gui;
    private Boolean active = false;

    public Washer(Geschirrspueler gs, GUI gui) {
        this.gs = gs;
        this.gui = gui;
    }

    public void prewash() {
        int ii = 0;

        for (int i = 10; i >= 0; i--) {
            System.out.println(i);
            gui.setPb(ii * 10);
            gs.showCountdown(i);
            ii++;
        }
    }

    public void wash() {
        int ii = 0;

        for (int i = 20; i >= 0; i--) {
            System.out.println(i);
            gui.setPb((ii / 2) * 10);
            gs.showCountdown(i);
            ii++;
        }
    }

}
