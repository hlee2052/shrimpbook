package com.github.shrimpbook.compatibility;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.shrimpbook.R;
import com.github.shrimpbook.Utility;
import com.github.shrimpbook.shrimp.Shrimp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CompatibilityFragment extends Fragment implements View.OnClickListener {

    private EditText pHEditText;
    private EditText gHEditText;
    private EditText kHEditText;
    private EditText tempText;
    private EditText tdsText;
    private Button submitButton;

    Bundle bundle = new Bundle();
    Fragment fragment = new CompatibilityFragmentResult();


    //private ArrayList<String> result = new ArrayList<>();

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        return super.onContextItemSelected(item);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_compatibility, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        submitButton = getView().findViewById(R.id.checkCompatibility_submit);
        submitButton.setOnClickListener(this);

        pHEditText = getView().findViewById(R.id.pH_input_com);
        gHEditText = getView().findViewById(R.id.GH_input_com);
        kHEditText = getView().findViewById(R.id.KH_input_com);
        tempText = getView().findViewById(R.id.temp_input_com);
        tdsText = getView().findViewById(R.id.TDS_input_com);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.checkCompatibility_submit) {
            if (!validateInput()) {
                Toast.makeText(getActivity(),
                        "Check that all fields are entered correctly", Toast.LENGTH_SHORT).show();
                return;
            }

            HashMap<String, List<String>> compatibilityResult = analyzeCompatibility();
            bundle.putSerializable("resultKey", compatibilityResult);
            fragment.setArguments(bundle);
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            Utility.moveToAnotherFragment(fragment, fragmentManager);
        }
    }

    private HashMap<String, List<String>> analyzeCompatibility() {
        List<Shrimp> shrimpList = Utility.generateShrimpByNames(Utility.SHRIMP_TYPES);
        double pH = Double.parseDouble(pHEditText.getText().toString());
        double gH = Double.parseDouble(gHEditText.getText().toString());
        double KH = Double.parseDouble(kHEditText.getText().toString());
        double TDS = Double.parseDouble(tdsText.getText().toString());
        double temp = Double.parseDouble(tempText.getText().toString());

        HashMap <String, List<String>> compatiblityResult = new HashMap<>();

        for (Shrimp shrimp : shrimpList) {
            List<String> failReasons = new ArrayList<>();
            String shrimpName = shrimp.getName();

             if(shrimp.getPH()[0] > pH) {
                 failReasons.add("pH too low");
             } else if (shrimp.getPH()[1] < pH) {
                 failReasons.add("pH too high");
             }

            if(shrimp.getGH()[0] > gH) {
                failReasons.add("GH too low");
            } else if (shrimp.getGH()[1] < gH) {
                failReasons.add("GH too high");
            }

            if(shrimp.getKH()[0] > KH) {
                failReasons.add("KH too low");
            } else if (shrimp.getKH()[1] < KH) {
                failReasons.add("KH too high");
            }

            if(shrimp.getTDS()[0] > TDS) {
                failReasons.add("TDS too low");
            } else if (shrimp.getTDS()[1] < TDS) {
                failReasons.add("TDS too high");
            }

            if(shrimp.getTEMP()[0] > temp) {
                failReasons.add("temperature too low");
            } else if (shrimp.getTEMP()[1] < temp) {
                failReasons.add("temperature too high");
            }
            compatiblityResult.put(shrimpName, failReasons);
        }
        return compatiblityResult;
    }

    private boolean validateInput() {
        boolean pH = (pHEditText.getText().toString().isEmpty());
        boolean gH = (gHEditText.getText().toString().isEmpty());
        boolean KH = (kHEditText.getText().toString().isEmpty());
        boolean temp = (tempText.getText().toString().isEmpty());
        boolean tds = (tdsText.getText().toString().isEmpty());
        return !(pH || gH || KH || temp || tds);
    }
}