package com.github.shrimpbook.shrimp;

/**
 * Created by Lee on 11/23/2019.
 */

public class CRS extends Shrimp {

    public CRS() {
        super.setPH(new double[]{1,2});
        super.setGH(new double[]{5,6});
        super.setKH(new double[]{1,2});
        super.setTDS(new double[]{1,2});
        super.setTEMP(new double[]{25,33});
        super.setName("Bee");
    }
}
