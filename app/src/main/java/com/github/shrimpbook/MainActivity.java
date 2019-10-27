package com.github.shrimpbook;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.ParseInstallation;
import com.parse.SaveCallback;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String appId = "";
        String clientKey = "";

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(appId)
                // if defined
                .clientKey(clientKey)
                .server(getString(R.string.back4app_server_url))
                .build()
        );


        ParseInstallation.getCurrentInstallation().saveInBackground();

        ParseObject object = new ParseObject("Meow");
        object.put("test1", "asdf22");
        object.put("test2", "asdf22");

        object.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException ex) {
                if (ex == null) {
                    Log.i("Result", "work!");
                } else {
                    Log.i("Failed", "Failed");
                }
            }
        });


        ParseUser.enableAutomaticUser();

        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        defaultACL.setPublicWriteAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);


        // For bottom navigation
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        //I added this if statement to keep the selected fragment when rotating the device
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeFragment()).commit();
        }
    }



    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.nav_home:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.nav_favorites:
                            selectedFragment = new FavoritesFragment();
                            break;
                        case R.id.nav_upload:
                            selectedFragment = new UploadFragment();
                            break;
                        /*case R.id.nav_account:
                            selectedFragment = new AccountFragment();
                            break;*/
                        case R.id.nav_login:
                            if (ParseUser.getCurrentUser() != null && ParseUser.getCurrentUser().getUsername() !=null) {
                                selectedFragment = new AccountFragment();
                            } else {
                                selectedFragment = new LoginFragment();
                            }

                            break;
                        default:
                            selectedFragment = new HomeFragment();
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };
}
