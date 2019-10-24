package com.github.shrimpbook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

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


    }
}
