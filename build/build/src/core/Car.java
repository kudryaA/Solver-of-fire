package core;

import core.struct.*;
import javafx.scene.control.Alert;
import java.util.*;

import static core.Const.strMessage;

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
        double[] dist = Arrays.copyOf(fire.path, fire.path.length);
        int m1 = fire.m1, m2 = fire.m2, m3 = fire.m3;
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
                buf.r1 = r(m1, buf.m1);
                buf.r2 = r(m2, buf.m2);
                buf.r3 = r(m3, buf.m3);
                m1 -= buf.r1;
                m2 -= buf.r2;
                m3 -= buf.r3;
            }
            dist[k] = Const.INF;
        }

        if (m1 > 0 || m2 > 0 || m2 > 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(strMessage);
            alert.setContentText(Const.errorFire);
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
