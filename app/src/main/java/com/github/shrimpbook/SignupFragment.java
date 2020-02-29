package com.github.shrimpbook;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class SignupFragment extends Fragment implements View.OnClickListener, View.OnKeyListener {

    private Button registerButton;
    private EditText usernameInput;
    private EditText passwordInput;
    private EditText passwordConfirmInput;

    private final String unEqualPasswordText = "Please check two passwords are matched";
    private final String passwordTooShortText = "Password must be at least 6 characters long";
    private final String illegalInput = "Id must be at least 6 characters long!";

    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_USERNAME_LENGTH = 6;

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.submitRegisterButton) {
            ParseUser user = new ParseUser();
            String password = passwordInput.getText().toString();
            String passwordConfirm = passwordConfirmInput.getText().toString();

            if (!password.equals(passwordConfirm)) {
                Toast.makeText(getActivity(), unEqualPasswordText, Toast.LENGTH_SHORT).show();
                return;
            }

            if (password.length() < MIN_PASSWORD_LENGTH) {
                Toast.makeText(getActivity(), passwordTooShortText, Toast.LENGTH_SHORT).show();
                return;
            }

            String username = usernameInput.getText().toString();

            if (username.equals("") || username.length() < MIN_USERNAME_LENGTH) {
                Toast.makeText(getActivity(), illegalInput, Toast.LENGTH_SHORT).show();
                return;
            }

            user.setUsername(username);
            user.setPassword(password);

            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if (e != null) {
                        Log.i("Signed up", "Failed");
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Log.i("Sign Up", "Success");
                        Toast.makeText(getActivity(), "Welcome!", Toast.LENGTH_SHORT).show();
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
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        registerButton = getView().findViewById(R.id.submitRegisterButton);
        registerButton.setOnClickListener(this);

        usernameInput = getView().findViewById(R.id.registerUserInput);
        passwordInput = getView().findViewById(R.id.registerPasswordInput);
        passwordConfirmInput = getView().findViewById(R.id.registerPasswordConfirmInput);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }
}