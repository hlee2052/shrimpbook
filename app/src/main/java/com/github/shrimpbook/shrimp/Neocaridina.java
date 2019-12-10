package com.github.shrimpbook.shrimp;

/**
 * Created by Lee on 11/23/2019.
 */

public class Neocaridina extends Shrimp {

    public Neocaridina() {
        super.setPH(new double[]{6.2,8.0});
        super.setGH(new double[]{4,8});
        super.setKH(new double[]{3,15});
        super.setTDS(new double[]{100,200});
        super.setTEMP(new double[]{18,29});
        super.setName("Neocaridina");
    }
}
