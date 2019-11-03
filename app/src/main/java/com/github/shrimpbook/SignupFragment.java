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
import com.parse.SignUpCallback;

/**
 * Created by Lee on 10/24/2019.
 */


public class SignupFragment extends Fragment implements View.OnClickListener, View.OnKeyListener{

    Button registerButton;
    EditText usernameInput;
    EditText passwordInput;
    EditText passwordConfirmInput;

    final String unEqualPasswordText = "Please check two passwords are matched";
    final String passwordTooShortText = "Password must be at least 6 characters long";
    final String illegalInput = "Id must be at least 6 characters long!";

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.submitRegisterButton) {
            ParseUser user = new ParseUser();
            String password = passwordInput.getText().toString();
            String passwordConfirm = passwordConfirmInput.getText().toString();

            if (!password.equals(passwordConfirm)) {
                Toast.makeText(getActivity(),unEqualPasswordText, Toast.LENGTH_SHORT).show();
                return;
            }

            if (password.length()<6) {
                Toast.makeText(getActivity(),passwordTooShortText, Toast.LENGTH_SHORT).show();
                return;
            }

            String username = usernameInput.getText().toString();

            if (username.equals("") || username.length()< 6) {
                Toast.makeText(getActivity(),illegalInput, Toast.LENGTH_SHORT).show();
                return;
            }

            user.setUsername(username);
            user.setPassword(password);
            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if (e != null) {
                        Log.i("Signed up", "Fail");
                        Toast.makeText(getActivity(),e.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Log.i("Sign Up", "Success");
                        Toast.makeText(getActivity(),"Welcome!", Toast.LENGTH_SHORT).show();
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        Utility.moveToAnotherFragment(new HomeFragment(), fragmentManager);

                    }
                }
            });
        }
    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        return false;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("I am sign up");

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        registerButton = getView().findViewById(R.id.submitRegisterButton);
        registerButton.setOnClickListener(this);

        usernameInput = getView().findViewById(R.id.registerUserInput);
        passwordInput = getView().findViewById(R.id.registerPasswordInput);
        passwordConfirmInput = getView().findViewById(R.id.registerPasswordConfirmInput);
    }

    // This is called sometimes after onCreate
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }
}