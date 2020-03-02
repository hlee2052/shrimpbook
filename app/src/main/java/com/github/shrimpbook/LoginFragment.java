package com.github.shrimpbook;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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


public class LoginFragment extends Fragment implements View.OnClickListener, View.OnKeyListener {

    private Button loginButton;
    private Button signUpButton;
    private EditText usernameInput;
    private EditText passwordInput;

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.loginButton) {
            ParseUser.logInInBackground(usernameInput.getText().toString(), passwordInput.getText().toString(), new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {
                    if (user != null && e == null && getActivity() != null) {
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        Utility.moveToAnotherFragment(new HomeFragment(), fragmentManager);
                        BottomNavigationView bottomNavigationView;
                        bottomNavigationView = getActivity().findViewById(R.id.bottom_navigation);
                        bottomNavigationView.setSelectedItemId(R.id.nav_home);

                    } else {
                        Toast.makeText(getActivity(), R.string.login_fail_check_input, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        if (view.getId() == R.id.signUpButton && getActivity() != null) {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            Utility.moveToAnotherFragment(new SignUpFragment(), fragmentManager);
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loginButton = getView().findViewById(R.id.loginButton);
        loginButton.setOnClickListener(this);

        signUpButton = getView().findViewById(R.id.signUpButton);
        signUpButton.setOnClickListener(this);

        usernameInput = getView().findViewById(R.id.usernameInput);
        passwordInput = getView().findViewById(R.id.passwordInput);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }
}