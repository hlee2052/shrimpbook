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
        super.setPH(new double[]{6.0,7.6});
        super.setGH(new double[]{4,14});
        super.setKH(new double[]{0,10});
        super.setTDS(new double[]{80,400});
        super.setTEMP(new double[]{15.5,27});
        super.setName("Amano");
    }
}
