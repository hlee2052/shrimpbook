package com.github.shrimpbook;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.github.shrimpbook.shrimp.Amano;
import com.github.shrimpbook.shrimp.Bee;
import com.github.shrimpbook.shrimp.Cardinal;
import com.github.shrimpbook.shrimp.Neocaridina;
import com.github.shrimpbook.shrimp.Shrimp;
import com.github.shrimpbook.shrimp.Tiger;

import java.util.ArrayList;
import java.util.List;


public class Utility extends Fragment {
    public static final String ACCOUNT_FRAGMENT = "accountFragment";
    public static final String FAVORITES_FRAGMENT = "favoritesFragment";

    public static final int PNG_IMAGE_QUALITY = 0;
    public static final String[] SHRIMP_TYPES = new String[]{"Neocaridina", "Bee", "Tiger", "Amano", "Cardinal"};

    public static final String AMANO_SHRIMP = "Amano";
    public static final String BEE_SHRIMP = "Bee";
    public static final String TIGER_SHRIMP = "Tiger";
    public static final String CARDINAL_SHRIMP = "Cardinal";
    public static final String NEOCARIDINIA_SHRIMP = "Neocaridina";

    public static final int AMANO_IMAGE = R.drawable.amano;
    public static final int BEE_IMAGE = R.drawable.bee;
    public static final int TIGER_IMAGE = R.drawable.tiger;
    public static final int CARDINAL_IMAGE = R.drawable.cardinal;
    public static final int NEOCARIDINA_IMAGE = R.drawable.neocaridina;

    // database
    public static final String DB_VIEW_ID = "viewId";
    public static final String DB_USER_ID = "userId";
    public static final String DB_USER_NAME = "userName";
    public static final String DB_FAVORITES = "favorites";
    public static final String DB_ENTRIES = "entries";
    public static final String DB_OBJECT_ID = "objectId";

    // Parse Objects
    public static final String PARSE_IMAGE = "image";
    public static final String PARSE_SHRIMP_TYPE = "shrimpType";
    public static final String PARSE_TANK_SIZE = "tankSize";
    public static final String PARSE_SOIL_TYPE = "soilType";
    public static final String PARSE_PH = "pH";
    public static final String PARSE_GH = "GH";
    public static final String PARSE_KH = "KH";
    public static final String PARSE_TEMP = "temp";
    public static final String PARSE_TDS = "TDS";





    public static int getShrimpImageByName(String name) {
        switch (name) {
            case AMANO_SHRIMP:
                return AMANO_IMAGE;
            case NEOCARIDINIA_SHRIMP:
                return NEOCARIDINA_IMAGE;
            case BEE_SHRIMP:
                return BEE_IMAGE;
            case TIGER_SHRIMP:
                return TIGER_IMAGE;
            case CARDINAL_SHRIMP:
                return CARDINAL_IMAGE;
            default:
                return AMANO_IMAGE;
        }
    }

    public static List<Shrimp> generateShrimpByNames(String[] shrimpName) {
        List<Shrimp> shrimpList = new ArrayList<>();
        for (String name : shrimpName) {
            switch (name) {
                case AMANO_SHRIMP:
                    shrimpList.add(new Amano());
                    break;
                case NEOCARIDINIA_SHRIMP:
                    shrimpList.add(new Neocaridina());
                    break;
                case BEE_SHRIMP:
                    shrimpList.add(new Bee());
                    break;
                case TIGER_SHRIMP:
                    shrimpList.add(new Tiger());
                    break;
                case CARDINAL_SHRIMP:
                    shrimpList.add(new Cardinal());
                    break;
                default:
                    break;
            }
        }
        return shrimpList;
    }

    public static Shrimp getShrimpByName(String name) {
        switch (name) {
            case AMANO_SHRIMP:
                return new Amano();
            case NEOCARIDINIA_SHRIMP:
                return new Neocaridina();
            case BEE_SHRIMP:
                return new Bee();
            case TIGER_SHRIMP:
                return new Tiger();
            case CARDINAL_SHRIMP:
                return new Cardinal();
            default:
                return new Neocaridina();
        }
    }

    public static void moveToAnotherFragment(Fragment fragment, FragmentManager fragmentManager) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
