package com.github.shrimpbook.compatibility;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.github.shrimpbook.R;
import com.github.shrimpbook.Utility;
import com.github.shrimpbook.adapter.ShrimpAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Lee on 11/23/2019.
 */

public class CompatibilityFragmentResult extends Fragment implements  View.OnClickListener {

    ListView listView;
    ListAdapter listAdapter;

    Button resetButton;

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        return super.onContextItemSelected(item);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_compatibility_result, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();

        resetButton = getView().findViewById(R.id.compatibilityReset);
        resetButton.setOnClickListener(this);

        Map<String, List<String>>  res = (Map<String, List<String>>) bundle.getSerializable("resultKey");

        // convert to list for using list adapter;
        List<String> name = new ArrayList<>();
        List<List<String>> reasons = new ArrayList<>();

        for (Map.Entry<String, List<String>> entry : res.entrySet()) {
            name.add(entry.getKey());
            reasons.add(entry.getValue());
        }

        listView = (ListView) getView().findViewById(R.id.shrimpList);
        listAdapter = new ShrimpAdapter(getActivity(), name, reasons);
        listView.setAdapter(listAdapter);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.compatibilityReset) {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            Toast.makeText(getActivity(), "Reset input!" , Toast.LENGTH_SHORT).show();
            Utility.moveToAnotherFragment(new CompatibilityFragment(), fragmentManager);
        }
    }
}
