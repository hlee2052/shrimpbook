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

/**
 * Created by Lee on 10/25/2019.
 */

public class Utility extends Fragment{
    //FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

    public static final String ACCOUNT_FRAGMENT = "accountFragment";
    public static final String FAVORITES_FRAGMENT = "favoritesFragment";
    public static final String BEGIN_BY_UPLOAD = "You haven't uploaded any items. Begin by uploading from Upload Screen";
    public static final String BEGIN_BY_ADD_FAV = "You don't have any favorite items, Begin by add them from Home screen";
    public static final String LOG_IN_TO_FAV = " Please log in to see your favorite bookmarks!";

    public static final int PNG_IMAGE_QUALITY = 50;


    public static final String [] SHRIMP_TYPES = new String[] {"Neocaridina", "Bee", "Tiger", "Amano", "Cardinal"};


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


    public static int getShrimpImageByName (String name) {
        switch (name) {
            case AMANO_SHRIMP:
                return AMANO_IMAGE;
            case NEOCARIDINIA_SHRIMP:
                return  NEOCARIDINA_IMAGE;
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




    public static List<Shrimp> generateShrimpByNames (String[] shrimpName) {

        List<Shrimp> shrimpList = new ArrayList<>();
        for (String name: shrimpName) {

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











    public static void moveToAnotherFragment(Fragment fragment, FragmentManager fragmentManager) {

        //Fragment fragment = new SignupFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
