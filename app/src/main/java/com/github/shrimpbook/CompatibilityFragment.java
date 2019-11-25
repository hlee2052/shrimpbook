package com.github.shrimpbook;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.shrimpbook.shrimp.Shrimp;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompatibilityFragment extends Fragment implements View.OnClickListener {

    EditText pHEditText;
    private EditText GHEditText;
    private EditText KHEditText;
    private EditText tempText;
    private EditText TDSText;
    private Button submitButton;

    private ArrayList<String> result = new ArrayList<>();


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        return super.onContextItemSelected(item);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_compatibility, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        submitButton = getView().findViewById(R.id.checkCompatibility_submit);
        submitButton.setOnClickListener(this);

        pHEditText = getView().findViewById(R.id.pH_input_com);
        GHEditText = getView().findViewById(R.id.GH_input_com);
        KHEditText = getView().findViewById(R.id.KH_input_com);
        tempText = getView().findViewById(R.id.temp_input_com);
        TDSText = getView().findViewById(R.id.TDS_input_com);
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.checkCompatibility_submit) {
            if (!validateInput()) {
                Toast.makeText(getActivity(), "Check that all fields are entered correctly", Toast.LENGTH_SHORT).show();
                return;
            }

            HashMap<String, List<String>> result = analyzeCompatibility();

            Bundle bundle = new Bundle();
            bundle.putSerializable("resultKey", result);

            Fragment fragment = new CompatibilityFragmentResult();
            fragment.setArguments(bundle);

            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            Utility.moveToAnotherFragment(fragment, fragmentManager);
        }
    }

    private HashMap<String, List<String>> analyzeCompatibility() {
        List<Shrimp> shrimpList = Utility.generateShrimpByNames(Utility.SHRIMP_TYPES);
        double pH = Double.parseDouble(pHEditText.getText().toString());
        double gH = Double.parseDouble(GHEditText.getText().toString());
        double KH = Double.parseDouble(KHEditText.getText().toString());
        double TDS = Double.parseDouble(TDSText.getText().toString());
        double temp = Double.parseDouble(tempText.getText().toString());

        HashMap <String, List<String>> result = new HashMap<>();

        for (Shrimp eachShrimp : shrimpList) {
            List<String> failReasons = new ArrayList<>();
            String shrimpName = eachShrimp.getName();


             if(eachShrimp.getPH()[0] > pH) {
                 failReasons.add("pH too low");
             } else if (eachShrimp.getPH()[1] < pH) {
                 failReasons.add("pH too high");
             }

            if(eachShrimp.getGH()[0] > gH) {
                failReasons.add("GH too low");
            } else if (eachShrimp.getGH()[1] < gH) {
                failReasons.add("GH too high");
            }

            if(eachShrimp.getKH()[0] > KH) {
                failReasons.add("KH too low");
            } else if (eachShrimp.getKH()[1] < KH) {
                failReasons.add("KH too high");
            }

            if(eachShrimp.getTDS()[0] > TDS) {
                failReasons.add("TDS too low");
            } else if (eachShrimp.getTDS()[1] < TDS) {
                failReasons.add("TDS too high");
            }

            if(eachShrimp.getTEMP()[0] > temp) {
                failReasons.add("temperature too low");
            } else if (eachShrimp.getTEMP()[1] < temp) {
                failReasons.add("temperature too high");
            }

            result.put(shrimpName, failReasons);
        }
        return result;
    }

    private boolean validateInput() {
        boolean pH = (pHEditText.getText().toString().isEmpty());
        boolean gH = (GHEditText.getText().toString().isEmpty());
        boolean KH = (KHEditText.getText().toString().isEmpty());
        boolean temp = (tempText.getText().toString().isEmpty());
        boolean tds = (TDSText.getText().toString().isEmpty());
        return !(pH || gH || KH || temp || tds);
    }
}