package com.github.shrimpbook;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.shrimpbook.adapter.ListAdapter;
import com.github.shrimpbook.items.ViewItem;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


public class AccountFragment extends Fragment implements View.OnClickListener {

    private List<ViewItem> listItems;
    private ListView lView;
    private ListAdapter lAdapter;
    private Button logOutButton;
    private TextView accountInfo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onClick(View view) {
        // log Out
        if (view.getId() == R.id.logOutButton) {
            ParseUser.logOut();
            Toast.makeText(getActivity(), R.string.success_log_out, Toast.LENGTH_SHORT).show();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            Utility.moveToAnotherFragment(new LoginFragment(), fragmentManager);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        logOutButton = getView().findViewById(R.id.logOutButton);
        logOutButton.setOnClickListener(this);
        listItems = new ArrayList<>();
        lView = (ListView) getView().findViewById(R.id.accountVal);
        lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                TextView test = getView().findViewById(R.id.shrimpTypeHome);
                String val = test.getText().toString();
                Toast.makeText(getActivity(), val, Toast.LENGTH_SHORT).show();
            }
        });

        ParseQuery<ParseObject> query = ParseQuery.getQuery(Utility.DB_ENTRIES);
        query.whereEqualTo("userID", ParseUser.getCurrentUser().getObjectId());

        try {
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if (e == null) {
                        for (ParseObject each : objects) {
                            listItems.add(new ViewItem(each));
                        }
                    }
                    lAdapter = new ListAdapter(getActivity(), listItems, Utility.ACCOUNT_FRAGMENT);
                    lView.setAdapter(lAdapter);
                    try {
                        accountInfo = (TextView) getView().findViewById(R.id.accountInfo);
                    } catch (RuntimeException ex) {
                        Log.i("Error", "retry connection before setting view");
                    }

                    if (listItems.size() == 0) {
                        accountInfo.setVisibility(View.VISIBLE);
                        accountInfo.setText(R.string.begin_by_upload);
                    } else {
                        try {
                            accountInfo.setVisibility(View.GONE);
                        } catch (Exception ex) {
                            Log.i("Error", "Account info view not available");
                        }
                    }
                }
            });
        } catch (Exception e) {
            Log.i("Error", "Failed to query - retry DB connection");
        }
    }
}