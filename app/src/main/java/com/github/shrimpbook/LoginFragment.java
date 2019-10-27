package com.github.shrimpbook;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

/**
 * Created by Lee on 10/24/2019.
 */


public class LoginFragment extends Fragment implements View.OnClickListener, View.OnKeyListener{


    public final String failedLoginText = "Failed to log in, make sure to check";
    Button loginButton;
    Button signUpButton;
    EditText usernameInput;
    EditText passwordInput;


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.loginButton) {

            ParseUser.logInInBackground(usernameInput.getText().toString(),passwordInput.getText().toString() , new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {
                    if (user != null && e == null) {
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        Utility.moveToAnotherFragment(new HomeFragment(), fragmentManager);
                    } else {
                        Toast.makeText(getActivity(), failedLoginText, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }


        if (view.getId() == R.id.signUpButton) {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            Utility.moveToAnotherFragment(new SignupFragment(), fragmentManager);
        }
    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        return false;
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loginButton = getView().findViewById(R.id.loginButton);
        loginButton.setOnClickListener(this);

        signUpButton = getView().findViewById(R.id.signUpButton);
        signUpButton.setOnClickListener(this);

        usernameInput = getView().findViewById(R.id.usernameInput);
        passwordInput = getView().findViewById(R.id.passwordInput);

    }

    // This is called sometimes after onCreate
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }
}