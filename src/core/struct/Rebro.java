package core.struct;

/**
 *
 * @author kam
 */
public class Rebro {
    private double length;
    private int v1;
    private int v2;
    private int id;

    public Rebro() {
        length = 0;
        v1 = v2 = 0;
        id = 0;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public int getV1() {
        return v1;
    }

    public void setV1(int v1) {
        this.v1 = v1;
    }

    public int getV2() {
        return v2;
    }

    public void setV2(int v2) {
        this.v2 = v2;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
