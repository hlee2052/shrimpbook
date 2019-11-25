package com.github.shrimpbook.shrimp;

/**
 * Created by Lee on 11/23/2019.
 */

public class Bee extends Shrimp {

    public Bee() {
        super.setPH(new double[]{5.5,7.0});
        super.setGH(new double[]{4,6});
        super.setKH(new double[]{0,1});
        super.setTDS(new double[]{80,200});
        super.setTEMP(new double[]{18,25});
        super.setName("Bee");
    }
}
