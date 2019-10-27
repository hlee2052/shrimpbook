package com.github.shrimpbook;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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


public class AccountFragment extends Fragment implements View.OnClickListener {

    List<ViewItem> listItems;
    ListView lView;
    ListAdapter lAdapter;
    Button button;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account, container, false);
    }














    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        button = getView().findViewById(R.id.logOutButton);
        button.setOnClickListener(this);





        listItems = new ArrayList<>();
        lView = (ListView) getView().findViewById(R.id.accountVal);

        lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


//


                TextView test = getView().findViewById(R.id.shrimpTypeHome);


                String val = test.getText().toString();


                Toast.makeText(getActivity(), val, Toast.LENGTH_SHORT).show();

            }
        });


        ParseQuery<ParseObject> query = ParseQuery.getQuery("entries");
        query.whereEqualTo("userID", ParseUser.getCurrentUser().getObjectId());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    for (ParseObject each: objects) {
                        listItems.add(new ViewItem(each));
                    }

                }
                lAdapter = new ListAdapter(getActivity(), listItems);
                lView.setAdapter(lAdapter);
            }
        });
    }

    @Override
    public void onClick(View view) {

    }
}