package com.github.shrimpbook.shrimp;

public class Amano extends Shrimp {

    public Amano () {
        super.setPH(new double[]{6.0,7.6});
        super.setGH(new double[]{4,14});
        super.setKH(new double[]{0,10});
        super.setTDS(new double[]{80,400});
        super.setTEMP(new double[]{15.5,27});
        super.setName("Amano");
    }
}
