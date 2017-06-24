package core.struct;

/**
 *
 * @author kam
 */
public class Fire {
    private int m1;
    private int m2;
    private int m3;
    private boolean status;
    private String name;
    private double[] path;


    public int getM1() {
        return m1;
    }

    public Fire() {
        m1 = m2 = m3 = 0;
        name = "fire";
        status = false;
    }

    public void setM1(int m1) {
        this.m1 = m1;
    }

    public int getM2() {
        return m2;
    }

    public void setM2(int m2) {
        this.m2 = m2;
    }

    public int getM3() {
        return m3;
    }

    public void setM3(int m3) {
        this.m3 = m3;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double[] getPath() {
        return path;
    }

    public void setPath(double[] path) {
        this.path = path;
    }
}
