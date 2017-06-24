package core;

import core.struct.*;
import javafx.scene.control.Alert;
import java.util.*;

/**
 *
 * @author kam
 */
public class Car {
    ArrayList firestations;
    Fire fire;
    public Car(Fire f, ArrayList firestation) {
        fire = f;
        firestations = firestation;
    }
    
    public ArrayList getFirestations() {
        double[] dist = Arrays.copyOf(fire.getPath(), fire.getPath().length);
        int m1 = fire.getM1(), m2 = fire.getM2(), m3 = fire.getM3();
        for (int i = 0; i < dist.length; i++) {
            double min = dist[0];
            int k = 0;
            for (int j = 1; j < dist.length; j++) {
                if (min > dist[j]) {
                    min = dist[j];
                    k = j;
                }
            }
            FireStation buf = (FireStation) firestations.get(k);
            if (min != Const.INF) {
                buf.setR1(r(m1, buf.getM1()));
                buf.setR2(r(m2, buf.getM2()));
                buf.setR3(r(m3, buf.getM3()));
                m1 -= buf.getR1();
                m2 -= buf.getR2();
                m3 -= buf.getR3();
            }
            dist[k] = Const.INF;
        }

        if (m1 > 0 || m2 > 0 || m2 > 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Повідомлення");
            alert.setContentText("Пожежу неможливо потушити");
            alert.showAndWait();
        }
        
        return firestations;
    }

    private int r(int mf, int ms) {
        int res = 0;
        mf -= ms;
        if (mf >= 0) {
            res = ms;
        } else {
            res = mf + ms;
        }

        return res;
    }
}
