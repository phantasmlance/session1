package com.example.session1;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.session1.models.AssetCatalogues;
import com.example.session1.models.AssetGroups;
import com.example.session1.models.Departments;
import com.example.session1.services.APIUtilities;
import com.example.session1.services.IDataClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    IDataClient client = APIUtilities.getData();

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

        GetDepartments();

        GetAssetGroups();

        GetAssetCatalogues();

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
                SearchAssets();
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

    private void GetDepartments() {

        Call<List<Departments>> call = client.getDepartments();
        call.enqueue(new Callback<List<Departments>>() {
            @Override
            public void onResponse(Call<List<Departments>> call, Response<List<Departments>> response) {
                ArrayList<Departments> departmentsArrayList = (ArrayList<Departments>) response.body();
                assert departmentsArrayList != null;
                final ArrayAdapter<Departments> adapterDepartments = new ArrayAdapter<>(MainActivity.this,
                        android.R.layout.simple_spinner_dropdown_item, departmentsArrayList);
                spinnerDepartment.setAdapter(adapterDepartments);
            }

            @Override
            public void onFailure(Call<List<Departments>> call, Throwable t) {
            }
        });
    }

    private void GetAssetGroups() {

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

    private void GetAssetCatalogues() {

        Call<List<AssetCatalogues>> call = client.getAssetCatalogues();
        call.enqueue(new Callback<List<AssetCatalogues>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<List<AssetCatalogues>> call, Response<List<AssetCatalogues>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                ArrayList<AssetCatalogues> cataloguesArrayList = (ArrayList<AssetCatalogues>) response.body();
                final AssetListAdapter adapter = new AssetListAdapter(MainActivity.this,
                        R.layout.item_layout, cataloguesArrayList);
                listViewAsset.setAdapter(adapter);

                assert cataloguesArrayList != null;
                textAmount.setText("3 assets from " + cataloguesArrayList.size());

                assert response.body() != null;
                textSearch.setAdapter(new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, response.body()));
            }

            @Override
            public void onFailure(Call<List<AssetCatalogues>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Something went wrong :(", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void SearchAssets() {

        Call<List<AssetCatalogues>> call = client.getSearch(textSearch.getText().toString());
        call.enqueue(new Callback<List<AssetCatalogues>>() {
            @Override
            public void onResponse(Call<List<AssetCatalogues>> call, Response<List<AssetCatalogues>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                ArrayList<AssetCatalogues> cataloguesArrayList = (ArrayList<AssetCatalogues>) response.body();
                final AssetListAdapter adapter = new AssetListAdapter(MainActivity.this,
                        R.layout.item_layout, cataloguesArrayList);
                listViewAsset.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<AssetCatalogues>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Custom adapter for ListView
    public class AssetListAdapter extends BaseAdapter {

        private Context context;
        private int layout;
        private List<AssetCatalogues> list;

        public AssetListAdapter(Context context, int layout, List<AssetCatalogues> list) {
            this.context = context;
            this.layout = layout;
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = new ViewHolder();

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                assert inflater != null;
                convertView = inflater.inflate(layout, null);
                holder.imgAsset = convertView.findViewById(R.id.imageViewAsset);
                holder.textAssetName = convertView.findViewById(R.id.textViewAssetName);
                holder.textDepartmentName = convertView.findViewById(R.id.textViewDepartmentName);
                holder.textAssetSN = convertView.findViewById(R.id.textViewAssetSN);
                holder.buttonEdit = convertView.findViewById(R.id.imageButtonEdit);
                holder.buttonTransfer = convertView.findViewById(R.id.imageButtonTransfer);
                holder.buttonHistory = convertView.findViewById(R.id.imageButtonHistory);

                ViewHolder finalHolder = holder;

                holder.buttonEdit.setOnClickListener(v -> {
                    Intent intent = new Intent(MainActivity.this, InfoActivity.class);
                    intent.putExtra("data", finalHolder.textAssetName.getText());
                    startActivity(intent);
                });

                holder.buttonTransfer.setOnClickListener(v -> {
                    Intent intent = new Intent(MainActivity.this, TransferActivity.class);
                    intent.putExtra("data0", finalHolder.textAssetName.getText());
                    intent.putExtra("data1", finalHolder.textDepartmentName.getText());
                    intent.putExtra("data2", finalHolder.textAssetSN.getText());
                    startActivity(intent);
                });

                holder.buttonHistory.setOnClickListener(v -> {
                    Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                    startActivity(intent);
                });

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            final AssetCatalogues ac = list.get(position);
            if (ac.getAssetphoto() != null) {
                holder.imgAsset.setImageBitmap(ac.getAssetphoto());
            } else {
                holder.imgAsset.setImageResource(R.drawable.image_layout);
            }
            holder.textAssetName.setText(ac.getAssetname());
            holder.textDepartmentName.setText(ac.getDepartmentname());
            holder.textAssetSN.setText(ac.getAssetsn());

            return convertView;
        }

        public class ViewHolder {
            ImageView imgAsset;
            TextView textAssetName, textDepartmentName, textAssetSN;
            ImageButton buttonEdit, buttonTransfer, buttonHistory;
        }
    }
}
