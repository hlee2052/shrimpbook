package com.github.shrimpbook;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lee on 10/24/2019.
 */


public class UploadFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {


    private String typeResult;
    private String soilResult;
    private String tankSizeResult;
    private String pHResult;
    private String GHResult;
    private String KHREsult;



    @Override
    public boolean onContextItemSelected(MenuItem item) {
        return super.onContextItemSelected(item);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_upload, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Spinner for shrimp Type
        final List<String> spinnerTypeList = new ArrayList<String>();
        Spinner spinnerType = (Spinner) view.findViewById(R.id.type_spinner);
        spinnerTypeList.add("Caridinia");
        spinnerTypeList.add("Neocaridinia");
        spinnerTypeList.add("Other");

        final ArrayAdapter<String> spinnerTypeAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, spinnerTypeList);
        spinnerTypeAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinnerType.setAdapter(spinnerTypeAdapter);
        spinnerType.setOnItemSelectedListener(this);

        // Spinner for soil type
        final List<String> spinnerSoilList = new ArrayList<String>();
        Spinner spinnerSoil = (Spinner) view.findViewById(R.id.soil_spinner);
        spinnerSoilList.add("Gravel(Inert)");
        spinnerSoilList.add("Aquasoil(acidic)");
        spinnerSoilList.add("Limestone (basic)");
        spinnerSoilList.add("Other");

        final ArrayAdapter<String> soilTypeAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, spinnerSoilList);
        soilTypeAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinnerSoil.setAdapter(soilTypeAdapter);
        spinnerSoil.setOnItemSelectedListener(this);

        // Spinner for tank size
        final List<String> spinnerTankSizeList = new ArrayList<String>();
        Spinner spinnerTankSize = (Spinner) view.findViewById(R.id.tankSize_spinner);
        spinnerTankSizeList.add("< 5 gallons");
        spinnerTankSizeList.add("10 gallons");
        spinnerTankSizeList.add("15 gallons");
        spinnerTankSizeList.add("20 gallons");
        spinnerTankSizeList.add("25 gallons");
        spinnerTankSizeList.add("> 25 gallons");


        final ArrayAdapter<String> TankSizeAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, spinnerTankSizeList);
        TankSizeAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinnerTankSize.setAdapter(TankSizeAdapter);
        spinnerTankSize.setOnItemSelectedListener(this);


    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        adapterView.getItemAtPosition(i);

        switch (adapterView.getId()) {
            case R.id.type_spinner:
                Log.i("type selected ", Integer.toString(i));
                break;
            case R.id.soil_spinner:
                Log.i("soil selected ", Integer.toString(i));
                break;
            case R.id.tankSize_spinner:
                Log.i("size selected ", Integer.toString(i));
                break;
            default:
                break;
        }





    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}