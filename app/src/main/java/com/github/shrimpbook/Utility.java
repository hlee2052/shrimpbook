package com.github.shrimpbook;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

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

    public static void moveToAnotherFragment(Fragment fragment, FragmentManager fragmentManager) {

        //Fragment fragment = new SignupFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
