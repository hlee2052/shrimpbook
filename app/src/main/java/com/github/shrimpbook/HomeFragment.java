package com.github.shrimpbook;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.shrimpbook.adapter.ListAdapter;
import com.github.shrimpbook.items.ViewItem;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    private List<ViewItem> listItems;
    private ListView lView;
    private ListAdapter lAdapter;
    private TextView noInternetView;
    private static final String noInternet = "You need internet connection for the app the work! Try to tap another section after you have internet connection!";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (haveNetworkConnection()) {
            listItems = new ArrayList<>();
            lView = getView().findViewById(R.id.androidList);
            lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    // none
                }
            });

            try {
                ParseQuery<ParseObject> query = ParseQuery.getQuery("entries");
                query.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        if (e == null) {
                            for (ParseObject entry : objects) {
                                listItems.add(new ViewItem(entry));
                            }
                        }
                        lAdapter = new ListAdapter(getActivity(), listItems);
                        lView.setAdapter(lAdapter);
                    }
                });
            } catch (Exception e) {
                Toast.makeText(getActivity(), "Problem with home page when start", Toast.LENGTH_SHORT).show();
                Log.i("exception at home", "issue with home screen");
            }
        } else {
            noInternetView = getView().findViewById(R.id.homeNoInternet);
            noInternetView.setVisibility(View.VISIBLE);
            noInternetView.setText(noInternet);
        }
    }

    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfoList = cm.getAllNetworkInfo();
        for (NetworkInfo netInfo : netInfoList) {
            if (netInfo.getTypeName().equalsIgnoreCase("WIFI"))
                if (netInfo.isConnected())
                    haveConnectedWifi = true;
            if (netInfo.getTypeName().equalsIgnoreCase("MOBILE"))
                if (netInfo.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }
}