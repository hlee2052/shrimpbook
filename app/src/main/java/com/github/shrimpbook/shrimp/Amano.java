package com.github.shrimpbook.shrimp;

/**
 * Created by Lee on 11/23/2019.
 */

public class Amano extends Shrimp {


  /*  public static final int [] PH = new int[] {1,2};
    public static final int[] GH = new int[] {1,2};
    public static final int[] KH = new int[] {1,2};
    public static final int[] TDS = new int[] {1,2};
    public static final int[] TEMP = new int[]{3,4};*/


    public Amano () {
        super.setPH(new double[]{6,8});
        super.setGH(new double[]{0,8});
        super.setKH(new double[]{1,2});
        super.setTDS(new double[]{1,2});
        super.setTEMP(new double[]{1,2});
        super.setName("Amano");
    }
}
