package com.github.shrimpbook.shrimp;

/**
 * Created by Lee on 11/23/2019.
 */

public class Cardinal extends Shrimp {

    public Cardinal() {
        super.setPH(new double[]{7.8,8.2});
        super.setGH(new double[]{6,8});
        super.setKH(new double[]{4,8});
        super.setTDS(new double[]{50,150});
        super.setTEMP(new double[]{26,31});
        super.setName("Cardinal");
    }
}
