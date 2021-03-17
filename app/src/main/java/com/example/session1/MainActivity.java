package com.example.session1;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.session1.adapters.MainAdapter;
import com.example.session1.models.AssetCatalogues;
import com.example.session1.models.AssetGroups;
import com.example.session1.models.Departments;
import com.example.session1.services.APIUtilities;
import com.example.session1.services.IWebservice;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    IWebservice client = APIUtilities.getData();

    Spinner spinnerDepartment, spinnerAssetGroup;
    EditText textStartDate, textEndDate;
    AutoCompleteTextView textSearch;
    ListView listViewAsset;
    TextView textAmount;
    FloatingActionButton buttonAdd;

    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Session1");

        Mapping();

        getDepartments();

        getAssetGroups();

        //GetAssetCatalogues();

        textStartDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int mYear = calendar.get(Calendar.YEAR);
            int mMonth = calendar.get(Calendar.MONTH);
            int mDay = calendar.get(Calendar.DAY_OF_MONTH);
            @SuppressLint("SetTextI18n")
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    (view, year, monthOfYear, dayOfMonth) -> {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
                        textStartDate.setText(sdf.format(newDate.getTime()));
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        });

        textEndDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int mYear = calendar.get(Calendar.YEAR);
            int mMonth = calendar.get(Calendar.MONTH);
            int mDay = calendar.get(Calendar.DAY_OF_MONTH);
            @SuppressLint("SetTextI18n")
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    (view, year, monthOfYear, dayOfMonth) -> {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
                        textEndDate.setText(sdf.format(newDate.getTime()));
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        });

        textSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                searchAssets();
            }
        });

        buttonAdd.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, InfoActivity.class);
            startActivity(intent);
        });
    }

    private void Mapping() {
        spinnerAssetGroup = findViewById(R.id.spinnerAssetGroup);
        spinnerDepartment = findViewById(R.id.spinnerDepartment);
        textStartDate = findViewById(R.id.editTextStartDate);
        textEndDate = findViewById(R.id.editTextEndDate);
        textSearch = findViewById(R.id.editTextSearch);
        listViewAsset = findViewById(R.id.listViewAsset);
        textAmount = findViewById(R.id.textViewAmount);
        buttonAdd = findViewById(R.id.buttonAdd);
    }

    private void getDepartments() {
        Call<List<Departments>> call = client.getDepartments();
        call.enqueue(new Callback<List<Departments>>() {
            @Override
            public void onResponse(Call<List<Departments>> call, Response<List<Departments>> response) {
                ArrayList<Departments> departmentsArrayList = (ArrayList<Departments>) response.body();
                assert departmentsArrayList != null;
                final ArrayAdapter<Departments> adapterDepartments = new ArrayAdapter<>(MainActivity.this,
                        android.R.layout.simple_spinner_dropdown_item, departmentsArrayList);
                spinnerDepartment.setAdapter(adapterDepartments);

                Log.d("AAA", String.valueOf(response));
            }

            @Override
            public void onFailure(Call<List<Departments>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Something went wrong :(", Toast.LENGTH_SHORT).show();
                Log.d("AAA", String.valueOf(call));
                Log.d("AAA", String.valueOf(t.getMessage()));
            }
        });
    }

    private void getAssetGroups() {
        Call<List<AssetGroups>> call = client.getAssetGroups();
        call.enqueue(new Callback<List<AssetGroups>>() {
            @Override
            public void onResponse(Call<List<AssetGroups>> call, Response<List<AssetGroups>> response) {
                ArrayList<AssetGroups> assetGroupsArrayList = (ArrayList<AssetGroups>) response.body();
                assert assetGroupsArrayList != null;
                final ArrayAdapter<AssetGroups> adapterAssetGroups = new ArrayAdapter<>(MainActivity.this,
                        android.R.layout.simple_spinner_dropdown_item, assetGroupsArrayList);
                spinnerAssetGroup.setAdapter(adapterAssetGroups);
            }

            @Override
            public void onFailure(Call<List<AssetGroups>> call, Throwable t) {

            }
        });
    }

//    private void GetAssetCatalogues() {
//
//        Call<List<AssetCatalogues>> call = client.getAssetCatalogues();
//        call.enqueue(new Callback<List<AssetCatalogues>>() {
//            @SuppressLint("SetTextI18n")
//            @Override
//            public void onResponse(Call<List<AssetCatalogues>> call, Response<List<AssetCatalogues>> response) {
//                if (!response.isSuccessful()) {
//                    Toast.makeText(MainActivity.this, response.code(), Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                ArrayList<AssetCatalogues> cataloguesArrayList = (ArrayList<AssetCatalogues>) response.body();
//                final AssetListAdapter adapter = new AssetListAdapter(MainActivity.this,
//                        R.layout.item_layout, cataloguesArrayList);
//                listViewAsset.setAdapter(adapter);
//
//                assert cataloguesArrayList != null;
//                textAmount.setText("3 assets from " + cataloguesArrayList.size());
//
//                assert response.body() != null;
//                textSearch.setAdapter(new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, response.body()));
//            }
//
//            @Override
//            public void onFailure(Call<List<AssetCatalogues>> call, Throwable t) {
//                Toast.makeText(MainActivity.this, "Something went wrong :(", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    private void searchAssets() {
        Call<List<AssetCatalogues>> call = client.getSearch(textSearch.getText().toString());
        call.enqueue(new Callback<List<AssetCatalogues>>() {
            @Override
            public void onResponse(Call<List<AssetCatalogues>> call, Response<List<AssetCatalogues>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                ArrayList<AssetCatalogues> cataloguesArrayList = (ArrayList<AssetCatalogues>) response.body();
                final MainAdapter adapter = new MainAdapter(MainActivity.this,
                        R.layout.item_layout, cataloguesArrayList);
                listViewAsset.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<AssetCatalogues>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
