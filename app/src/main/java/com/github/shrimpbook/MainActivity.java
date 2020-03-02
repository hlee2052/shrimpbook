package com.github.shrimpbook;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.github.shrimpbook.compatibility.CompatibilityFragment;
import com.github.shrimpbook.favorites.FavoritesFragment;
import com.github.shrimpbook.favorites.FavoritesFragmentEmpty;
import com.github.shrimpbook.upload.UploadFragment;
import com.github.shrimpbook.upload.UploadFragmentEmpty;
import com.parse.Parse;
import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // This is where user should supplement app-Id and client-key for the Parse Server
        String appId = "";
        String clientKey = "";

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(appId)
                // if defined
                .clientKey(clientKey)
                .server(getString(R.string.back4app_server_url))
                .build()
        );

        // For bottom navigation
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        //I added this if statement to keep the selected fragment when rotating the device
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeFragment()).commit();
        }
    }

    // https://stackoverflow.com/questions/4238921/detect-whether-there-is-an-internet-connection-available-on-android
    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    Fragment selectedFragment = null;

                    if (haveNetworkConnection()) {
                        switch (item.getItemId()) {
                            case R.id.nav_home:
                                selectedFragment = new HomeFragment();
                                break;
                            case R.id.nav_favorites:
                                if (ParseUser.getCurrentUser() != null && ParseUser.getCurrentUser().getUsername() != null) {
                                    selectedFragment = new FavoritesFragment();
                                } else {
                                    selectedFragment = new FavoritesFragmentEmpty();
                                }
                                break;
                            case R.id.nav_upload:
                                if (ParseUser.getCurrentUser() != null && ParseUser.getCurrentUser().getUsername() != null) {
                                    selectedFragment = new UploadFragment();
                                } else {
                                    selectedFragment = new UploadFragmentEmpty();
                                }
                                break;
                            case R.id.nav_login:
                                if (ParseUser.getCurrentUser() != null && ParseUser.getCurrentUser().getUsername() != null) {
                                    selectedFragment = new AccountFragment();
                                } else {
                                    selectedFragment = new LoginFragment();
                                }
                                break;
                            case R.id.nav_compatibility:
                                selectedFragment = new CompatibilityFragment();
                                break;
                            default:
                                selectedFragment = new FavoritesFragment();
                        }
                    } else {
                        selectedFragment = new NoInternetFragment();
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };
}