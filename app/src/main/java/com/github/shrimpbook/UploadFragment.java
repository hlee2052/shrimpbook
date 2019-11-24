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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lee on 10/24/2019.
 */


public class UploadFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    byte[] byteArray;

    TextView photoDirectory;

    private String typeResult;
    private String soilResult;
    private String tankSizeResult;

    private List<String> spinnerTypeList;
    private List<String> spinnerSoilList;
    private List<String> spinnerTankSizeList;

    private EditText pHEditText;
    private EditText GHEditText;
    private EditText KHEditText;

    private EditText tempText;
    private EditText TDSText;

    private Button submitButton;
    private Button pictureUploadButton;

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


        photoDirectory = getView().findViewById(R.id.photoDirectory);


        pictureUploadButton = getView().findViewById(R.id.picture_upload_button);
        pictureUploadButton.setOnClickListener(this);

        submitButton = getView().findViewById(R.id.uploadInfo);
        submitButton.setOnClickListener(this);
        pHEditText = getView().findViewById(R.id.pH_input);
        GHEditText = getView().findViewById(R.id.GH_input);
        KHEditText = getView().findViewById(R.id.KH_input);

        tempText = getView().findViewById(R.id.temp_input);
        TDSText = getView().findViewById(R.id.TDS_input);

        // Spinner for shrimp Type
        spinnerTypeList = new ArrayList<String>();
        Spinner spinnerType = (Spinner) view.findViewById(R.id.type_spinner);
        spinnerTypeList.add("Caridinia");
        spinnerTypeList.add("Neocaridina");
        spinnerTypeList.add("Other");

        final ArrayAdapter<String> spinnerTypeAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, spinnerTypeList);
        spinnerTypeAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinnerType.setAdapter(spinnerTypeAdapter);
        spinnerType.setOnItemSelectedListener(this);

        // Spinner for soil type
        spinnerSoilList = new ArrayList<String>();
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
        spinnerTankSizeList = new ArrayList<String>();
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

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.uploadInfo) {
            String userObjectId = ParseUser.getCurrentUser().getObjectId();
            String username = ParseUser.getCurrentUser().getUsername();

            if (userObjectId == null || userObjectId.equals("")) {
                userObjectId = "temp id";
                username = "temp user";
            }

            String pHResult = pHEditText.getText().toString();
            String GHResult = GHEditText.getText().toString();
            String KHResult = KHEditText.getText().toString();
            String tempResult = tempText.getText().toString();
            String TDSResult = TDSText.getText().toString();

            ParseObject object = new ParseObject("entries");
            object.put("shrimpType", typeResult);
            object.put("tankSize", tankSizeResult);
            object.put("soilType", soilResult);
            object.put("pH", pHResult);
            object.put("GH", GHResult);
            object.put("KH", KHResult);
            object.put("temp", tempResult);
            object.put("TDS", TDSResult);
            object.put("userID", userObjectId);
            object.put("useName", username);

            if (byteArray != null && byteArray.length > 0) {
                ParseFile file = new ParseFile("image.png", byteArray);
                object.put("image", file);
            }

            object.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        Toast.makeText(getActivity(), "Load Success!", Toast.LENGTH_LONG).show();
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        Utility.moveToAnotherFragment(new HomeFragment(), fragmentManager);
                    } else {
                        Toast.makeText(getActivity(), "Failed to Upload", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        if (view.getId() == R.id.picture_upload_button) {
            if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            } else {
                getPhotoFromStorage();
            }
        }
    }

    public void getPhotoFromStorage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null) {
            Uri imageData = data.getData();

            photoDirectory.setText(imageData.getPath());
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageData);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, Utility.PNG_IMAGE_QUALITY, stream);
                byteArray = stream.toByteArray();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // For spinner
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        adapterView.getItemAtPosition(i);

        switch (adapterView.getId()) {
            case R.id.type_spinner:
                typeResult = spinnerTypeList.get(i);
                break;
            case R.id.soil_spinner:
                soilResult = spinnerSoilList.get(i);
                break;
            case R.id.tankSize_spinner:
                tankSizeResult = spinnerTankSizeList.get(i);
                break;
            default:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}