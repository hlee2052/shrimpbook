package com.github.shrimpbook.shrimp;

public class Tiger extends Shrimp {

    public Tiger() {
        super.setPH(new double[]{6.5,7.5});
        super.setGH(new double[]{4,10});
        super.setKH(new double[]{2,6});
        super.setTDS(new double[]{150,200});
        super.setTEMP(new double[]{18,26});
        super.setName("Tiger");
    }
}
