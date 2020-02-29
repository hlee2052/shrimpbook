package com.github.shrimpbook.shrimp;

public class Bee extends Shrimp {

    public Bee() {
        super.setPH(new double[]{5.8,6.9});
        super.setGH(new double[]{4,6});
        super.setKH(new double[]{0,1});
        super.setTDS(new double[]{100,200});
        super.setTEMP(new double[]{17,25});
        super.setName("Bee");
    }
}
