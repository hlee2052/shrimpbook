package com.github.shrimpbook;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by Lee on 10/25/2019.
 */

public class Utility {
    //FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

    public static void moveToAnotherFragment(Fragment fragment, FragmentManager fragmentManager) {

        //Fragment fragment = new SignupFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
