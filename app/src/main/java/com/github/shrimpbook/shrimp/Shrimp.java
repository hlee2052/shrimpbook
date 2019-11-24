package com.github.shrimpbook.shrimp;

/**
 * Created by Lee on 11/23/2019.
 */

public class Shrimp {

    private double[] PH;
    private double[] GH;
    private double[] KH;
    private double[] TDS;
    private double[] TEMP;
    private String name;


    public void setPH (double[] val) {
        this.PH = val;
    }

    public void setGH (double[] val) {
        this.GH = val;
    }

    public void setKH (double[] val) {
        this.KH = val;
    }

    public void setTDS (double[] val) {
        this.TDS = val;
    }

    public void setTEMP (double[] val) {
        this.TEMP = val;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public double[] getPH () {
        return PH;
    }
    public double[] getGH () {
        return GH;
    }
    public double[] getKH () {
        return KH;
    }
    public double[] getTDS () {
        return TDS;
    }
    public double[] getTEMP () {
        return TEMP;
    }
}
