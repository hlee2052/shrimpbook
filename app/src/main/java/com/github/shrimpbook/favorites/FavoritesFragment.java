package com.github.shrimpbook.favorites;

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

import com.github.shrimpbook.LoginFragment;
import com.github.shrimpbook.R;
import com.github.shrimpbook.Utility;
import com.github.shrimpbook.adapter.ListAdapter;
import com.github.shrimpbook.items.ViewItem;
import com.parse.FindCallback;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class FavoritesFragment extends Fragment implements View.OnClickListener {

    private List<ViewItem> listItems;
    private ListView listView;
    private ListAdapter listAdapter;
    private Button logOutButton;
    private TextView favoritesInfo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    @Override
    public void onClick(View view) {
        // log Out
        if (view.getId() == R.id.testButton) {
            ParseUser.logOut();
            Toast.makeText(getActivity(), R.string.success_log_out, Toast.LENGTH_SHORT).show();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            Utility.moveToAnotherFragment(new LoginFragment(), fragmentManager);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        logOutButton = getView().findViewById(R.id.testButton);
        logOutButton.setOnClickListener(this);
        listItems = new ArrayList<>();
        listView = getView().findViewById(R.id.favoritesList);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView test = getView().findViewById(R.id.shrimpTypeHome);
                String val = test.getText().toString();
                Toast.makeText(getActivity(), val, Toast.LENGTH_SHORT).show();
            }
        });

        final List<String> viewIdList = new ArrayList<>();
        ParseQuery<ParseObject> userQuery = ParseQuery.getQuery(Utility.DB_FAVORITES);
        userQuery.whereEqualTo(Utility.DB_USER_ID, ParseUser.getCurrentUser().getObjectId());

        try {
            userQuery.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if (e == null) {
                        for (ParseObject each : objects) {
                            viewIdList.add(each.getString(Utility.DB_VIEW_ID));
                        }
                        ParseQuery<ParseObject> query = ParseQuery.getQuery(Utility.DB_ENTRIES);
                        query.whereContainedIn(Utility.DB_OBJECT_ID, viewIdList);
                        try {
                            query.findInBackground(new FindCallback<ParseObject>() {
                                @Override
                                public void done(List<ParseObject> objects, ParseException e) {
                                    if (e == null) {
                                        for (ParseObject each : objects) {
                                            listItems.add(new ViewItem(each));
                                        }
                                    }
                                    listAdapter = new ListAdapter(getActivity(), listItems, Utility.FAVORITES_FRAGMENT);
                                    listView.setAdapter(listAdapter);
                                    try {
                                        favoritesInfo = getView().findViewById(R.id.favoritesInfo);
                                    } catch (RuntimeException ex) {
                                        ex.printStackTrace();
                                        return;
                                    }
                                    if (listItems.size() == 0) {
                                        favoritesInfo.setVisibility(View.VISIBLE);
                                        boolean isAnonymous = ParseAnonymousUtils.isLinked(ParseUser.getCurrentUser());
                                        if (ParseUser.getCurrentUser().getUsername() != null && !isAnonymous) {
                                            favoritesInfo.setText(R.string.begin_by_add_fav_);
                                        } else {
                                            favoritesInfo.setText(R.string.log_in_to_fav);
                                        }
                                    } else {
                                        try {
                                            favoritesInfo.setVisibility(View.GONE);
                                        } catch (RuntimeException ex) {
                                            ex.printStackTrace();
                                            Log.i("Error", "Fail to set visibility, retry db");
                                        }
                                    }
                                }
                            });
                        } catch (RuntimeException ex) {
                            ex.printStackTrace();
                            Log.i("Error", "retry db connect");
                        }
                    }
                }
            });
        } catch (RuntimeException e) {
            e.printStackTrace();
            Log.i("Error", "fail to query");
        }
    }
}