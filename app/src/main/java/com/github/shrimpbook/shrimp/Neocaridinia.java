package com.github.shrimpbook.shrimp;

/**
 * Created by Lee on 11/23/2019.
 */

public class Neocaridinia extends Shrimp {

    public Neocaridinia() {
        super.setPH(new double[]{1,2});
        super.setGH(new double[]{7,8});
        super.setKH(new double[]{1,2});
        super.setTDS(new double[]{1,2});
        super.setTEMP(new double[]{1,2});
        super.setName("Neocaridinia");
    }
}
