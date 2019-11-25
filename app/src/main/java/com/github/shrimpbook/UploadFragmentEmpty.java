package com.github.shrimpbook;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Lee on 10/26/2019.
 */

public class UploadFragmentEmpty extends Fragment implements View.OnClickListener {

    Button button;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_upload_empty, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        button = (Button) getView().findViewById(R.id.uploadLogIn);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.uploadLogIn) {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            Utility.moveToAnotherFragment(new LoginFragment(), fragmentManager);

            BottomNavigationView bottomNavigationView;
            bottomNavigationView = (BottomNavigationView) getActivity().findViewById(R.id.bottom_navigation);
            bottomNavigationView.setSelectedItemId(R.id.nav_login);
        }
    }
}

