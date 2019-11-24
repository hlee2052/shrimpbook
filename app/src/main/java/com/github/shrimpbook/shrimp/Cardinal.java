package com.github.shrimpbook.shrimp;

/**
 * Created by Lee on 11/23/2019.
 */

public class Cardinal extends Shrimp {

    public Cardinal() {
        super.setPH(new double[]{1,2});
        super.setGH(new double[]{3,4});
        super.setKH(new double[]{1,2});
        super.setTDS(new double[]{1,2});
        super.setTEMP(new double[]{1,2});
        super.setName("Cardinal");
    }
}
