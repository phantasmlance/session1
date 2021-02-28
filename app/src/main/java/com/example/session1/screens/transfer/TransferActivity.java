package com.example.session1.screens.transfer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.session1.R;
import com.example.session1.models.Departments;
import com.example.session1.models.Locations;
import com.example.session1.services.APIUtilities;
import com.example.session1.services.IDataClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransferActivity extends AppCompatActivity {
    EditText editAssetName, editDepartment, editAssetSN, editNewAssetSN;
    Spinner spinnerDesDepartment, spinnerDesLocation;
    Button buttonSubmit, buttonCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Asset Transfer");
        actionBar.setDisplayHomeAsUpEnabled(true);

        Mapping();

        GetDepartments();
        //GetLocations();

        Intent intent = getIntent();

        String content0 = intent.getStringExtra("data0");
        editAssetName.setText(content0);
        editAssetName.setFocusable(false);

        String content1 = intent.getStringExtra("data1");
        editDepartment.setText(content1);
        editDepartment.setFocusable(false);

        String content2 = intent.getStringExtra("data2");
        editAssetSN.setText(content2);
        editAssetSN.setFocusable(false);

        buttonCancel.setOnClickListener(v -> onBackPressed());
    }

    public void Mapping() {
        editAssetName = findViewById(R.id.editTextAssetName);
        editDepartment = findViewById(R.id.editTextCurrentDepartment);
        editAssetSN = findViewById(R.id.editTextAssetSN);
        editNewAssetSN = findViewById(R.id.editTextNewAssetSN);
        spinnerDesDepartment = findViewById(R.id.spinnerDestinationDepartment);
        spinnerDesLocation = findViewById(R.id.spinnerDestinationLocation);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        buttonCancel = findViewById(R.id.buttonCancel);
    }

    private void GetDepartments() {

        IDataClient client = APIUtilities.getData();
        Call<List<Departments>> call = client.getDepartments();
        call.enqueue(new Callback<List<Departments>>() {
            @Override
            public void onResponse(Call<List<Departments>> call, Response<List<Departments>> response) {
                ArrayList<Departments> departmentsArrayList = (ArrayList<Departments>) response.body();
                assert departmentsArrayList != null;
                final ArrayAdapter<Departments> adapterDepartments = new ArrayAdapter<>(TransferActivity.this,
                        android.R.layout.simple_spinner_dropdown_item, departmentsArrayList);
                spinnerDesDepartment.setAdapter(adapterDepartments);
            }

            @Override
            public void onFailure(Call<List<Departments>> call, Throwable t) {
            }
        });
    }

//    private void GetLocations() {
//
//        IDataClient client = APIUtilities.getData();
//        Call<List<Locations>> call = client.getLocations();
//        call.enqueue(new Callback<List<Locations>>() {
//            @Override
//            public void onResponse(Call<List<Locations>> call, Response<List<Locations>> response) {
//                ArrayList<Locations> locationsArrayList = (ArrayList<Locations>) response.body();
//                assert locationsArrayList != null;
//                final ArrayAdapter<Locations> adapter = new ArrayAdapter<>(TransferActivity.this,
//                        android.R.layout.simple_spinner_dropdown_item, locationsArrayList);
//                spinnerDesLocation.setAdapter(adapter);
//            }
//
//            @Override
//            public void onFailure(Call<List<Locations>> call, Throwable t) {
//            }
//        });
//    }
}