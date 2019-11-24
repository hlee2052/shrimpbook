package com.github.shrimpbook;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Lee on 11/23/2019.
 */

public class CompatibilityFragmentResult extends Fragment {


    public CompatibilityFragmentResult () {

    }

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


        Map<String, List<String>>  res = (Map<String, List<String>>) bundle.getSerializable("resultKey");

        for (Map.Entry<String, List<String>> entry : res.entrySet()) {
            String key = entry.getKey();
            List<String> value = entry.getValue();
            Log.i("wee", key.toString());
            Log.i("weee, ", value.toString());
            // ...
        }

        //Toast.makeText(getActivity(),res.get(0).toString(), Toast.LENGTH_SHORT).show();
    }
}
