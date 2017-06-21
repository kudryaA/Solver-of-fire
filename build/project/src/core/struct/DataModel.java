package core.struct;

/**
 * Created by kam12 on 03.06.2017.
 */
public class DataModel {

    private String fireStation, length, m1, m2, m3;

    public DataModel() {
    }

    public DataModel(String fireStation, String length, String m1, String m2, String m3) {
        this.fireStation = fireStation;
        this.length = length;
        this.m1 = m1;
        this.m2 = m2;
        this.m3 = m3;
    }

    public String getFireStation() {
        return fireStation;
    }

    public void setFireStation(String name) {
        this.fireStation = name;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String surname) {
        this.length = surname;
    }

    public String getM1() {
        return m1;
    }

    public void setM1(String city) {
        this.m1 = city;
    }

    public String getM2() {
        return m2;
    }

    public void setM2(String city) {
        this.m2 = city;
    }

    public String getM3() {
        return m3;
    }

    public void setM3(String city) {
        this.m3 = city;
    }
}