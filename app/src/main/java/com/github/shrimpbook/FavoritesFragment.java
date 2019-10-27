package com.github.shrimpbook;

import android.os.Bundle;
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

import com.github.shrimpbook.items.ViewItem;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lee on 10/24/2019.
 */


public class FavoritesFragment extends Fragment implements View.OnClickListener {

    List<ViewItem> listItems;
    ListView lView;
    ListAdapter lAdapter;
    Button logOutButton;
    TextView favoritesInfo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    @Override
    public void onClick(View view) {
        // log Out
        if (view.getId() == R.id.test1234) {
            ParseUser.logOut();
            Toast.makeText(getActivity(), "Successfully logged out", Toast.LENGTH_SHORT).show();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            Utility.moveToAnotherFragment(new LoginFragment(), fragmentManager);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        logOutButton = getView().findViewById(R.id.test1234);
        logOutButton.setOnClickListener(this);
        listItems = new ArrayList<>();
        lView = (ListView) getView().findViewById(R.id.favoritesList);

        lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                TextView test = getView().findViewById(R.id.shrimpTypeHome);
                String val = test.getText().toString();
                Toast.makeText(getActivity(), val, Toast.LENGTH_SHORT).show();
            }
        });


        final List<String> viewIdList = new ArrayList<>();

        ParseQuery<ParseObject> userQuery = ParseQuery.getQuery("favorites");
        userQuery.whereEqualTo("userId", ParseUser.getCurrentUser().getObjectId());
        userQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    for (ParseObject each : objects) {
                        viewIdList.add(each.getString("viewId"));
                    }

                    ParseQuery<ParseObject> query = ParseQuery.getQuery("entries");
                    query.whereContainedIn("objectId", viewIdList);
                    query.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> objects, ParseException e) {
                            if (e == null) {
                                for (ParseObject each : objects) {
                                    listItems.add(new ViewItem(each));
                                }
                            }
                            lAdapter = new ListAdapter(getActivity(), listItems, Utility.FAVORITES_FRAGMENT);
                            lView.setAdapter(lAdapter);
                            favoritesInfo = (TextView) getView().findViewById(R.id.favoritesInfo);
                            if (listItems.size() == 0) {

                                favoritesInfo.setVisibility(View.VISIBLE);
                                if (ParseUser.getCurrentUser().getUsername() != null) {
                                    favoritesInfo.setText(Utility.BEGIN_BY_ADD_FAV);
                                } else {
                                    favoritesInfo.setText(Utility.LOG_IN_TO_FAV);
                                }
                            } else {
                                favoritesInfo.setVisibility(View.GONE);
                            }
                        }
                    });
                }
            }
        });
    }
}