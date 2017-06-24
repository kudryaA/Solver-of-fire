package core;

import java.util.*;
import core.struct.*;
import javafx.scene.control.TextInputDialog;
import core.struct.Point;

/**
 *
 * @author kam
 */
public class Changer {

    public static double[] remove(double[] symbols, int index) {
        if (index >= 0 && index < symbols.length)  {
            double[] copy = new double[symbols.length-1];
            System.arraycopy(symbols, 0, copy, 0, index);
            System.arraycopy(symbols, index+1, copy, index, symbols.length-index-1);
            return copy;
        }
        return symbols;
    }

    public static double getLength() {
        TextInputDialog dialog = new TextInputDialog("0");
        dialog.setTitle("Відстань");
        dialog.setHeaderText("Поле необхідно заповнити!");
        dialog.setContentText("Введіть відстань:");
        Optional<String> result = dialog.showAndWait();
        return  Double.parseDouble(result.get());
    }

    public static Point[] getVariable(double x, double y, double r) {
        Point[] t = new Point[4];
        for (int i = 0; i < t.length; i++) {
            t[i] = new Point();
        }

        t[0].setX(x + r);
        t[0].setY(y);

        t[1].setX(x);
        t[1].setY(y + r);

        t[2].setX(x + r * 2);
        t[2].setY(y + r);

        t[3].setX(x + r);
        t[3].setY(y + r * 2);

        return t;
    }

    public static Point[] getResPoint(Point[] t1, Point[] t2) {
        double min = Const.INF;
        Point p1 = new Point(), p2 = new Point();
        for (int i = 0; i < t1.length; i++) {
            for (int j = 0; j < t2.length; j++) {
                double l = lengthPoint(t1[i], t2[j]);
                if (min >= l) {
                    min = l;
                    p1 = t1[i];
                    p2 = t2[j];
                }
            }
        }
        Point[] res = {p1, p2};
        return res;
    }

    public static double lengthPoint(Point t1, Point t2) {
        return Math.sqrt(Math.pow(t1.getX() - t2.getX(), 2) + Math.pow(t1.getY() - t2.getY(), 2));
    }

    public static ArrayList newListR(ArrayList mas, ArrayList v) {
        for (int i = 0; i < mas.size(); i++) {
            Rebro r = (Rebro) mas.get(i);
            boolean s1 = false, s2 = false;
            if (r != null)
                for (int j = 0; j < v.size(); j++) {
                    FireStation f = (FireStation) v.get(j);
                    if (f != null) {
                        if (r.getV1() == f.getId() || r.getV1() == 0) {
                            s1 = true;
                        }

                        if (r.getV2() == f.getId() || r.getV2() == 0) {
                            s2 = true;
                        }
                    }
                }
            if(!(s1 == true && s2 == true)) mas.remove(i);
        }

        return mas;
    }

    public static ArrayList newListV(ArrayList mas) {
        for (int i = 0; i < mas.size(); i++) {
            if (mas.get(i) == null) {
                mas.remove(i);
            }
        }
        return mas;
    }

}
