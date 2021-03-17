package com.example.session1;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.session1.models.AssetGroups;
import com.example.session1.models.AssetPhotos;
import com.example.session1.models.Departments;
import com.example.session1.models.Locations;
import com.example.session1.services.APIUtilities;
import com.example.session1.services.IWebservice;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoActivity extends AppCompatActivity {

    public IWebservice client = APIUtilities.getData();

    EditText textAssetName, textDescription, textWarranty;
    Spinner spinnerDepartment, spinnerLocation, spinnerAssetGroup, spinnerAccountableParty;
    TextView textDPID, textAGID, textAssetSN;
    Button buttonCapture, buttonBrowse, buttonSubmit, buttonCancel;
    ListView listViewPhoto;

    int CAMERA_PERMISSION_CODE = 123, STORAGE_PERMISSION_CODE = 456;

    String real_path = "", assetsn, assetname, description, warrantydate;
    long departmentid, locationid, departmentlocationid, employeeid, assetgroupid;

    ArrayList<AssetPhotos> arrayPhoto = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Asset Information");
        actionBar.setDisplayHomeAsUpEnabled(true);

        Mapping();

        Intent intent1 = getIntent();
        String content = intent1.getStringExtra("data");
        textAssetName.setText(content);

        GetDepartments();

        GetLocations();

        GetAssetGroups();

        GetAccountableParty();

        spinnerDepartment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint({"SetTextI18n", "DefaultLocale"})
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Departments departments = (Departments) parent.getSelectedItem();
                departmentid = departments.getId();
                textDPID.setText(String.format("%02d", departmentid));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinnerAssetGroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint({"SetTextI18n", "DefaultLocale"})
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                AssetGroups groups = (AssetGroups) parent.getSelectedItem();
                assetgroupid = groups.getId();
                textAGID.setText(String.format("%02d", assetgroupid));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinnerLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Locations locations = (Locations) parent.getSelectedItem();
                locationid = locations.getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        textWarranty.setOnClickListener(v -> {
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            Calendar calendar = Calendar.getInstance();
            int mYear = calendar.get(Calendar.YEAR);
            int mMonth = calendar.get(Calendar.MONTH);
            int mDay = calendar.get(Calendar.DAY_OF_MONTH);
            @SuppressLint("SetTextI18n")
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    (view, year, monthOfYear, dayOfMonth) -> {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
                        textWarranty.setText(sdf.format(newDate.getTime()));
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        });

        // get image with camera
        buttonCapture.setOnClickListener(v -> {
            checkPermission(Manifest.permission.CAMERA, CAMERA_PERMISSION_CODE);

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, CAMERA_PERMISSION_CODE);
        });

        // get image in storage
        buttonBrowse.setOnClickListener(v -> {
            checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE, STORAGE_PERMISSION_CODE);

            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), STORAGE_PERMISSION_CODE);
        });

        buttonSubmit.setOnClickListener(v -> {

            SubmitAssetPhotos();
            //SubmitAssetCatalogues();
        });

        buttonCancel.setOnClickListener(v -> onBackPressed());
    }

    private void Mapping() {
        textAssetName = findViewById(R.id.editTextAssetName);
        textDescription = findViewById(R.id.editTextDescription);
        textWarranty = findViewById(R.id.editTextWarranty);
        spinnerDepartment = findViewById(R.id.spinnerDepartment);
        spinnerLocation = findViewById(R.id.spinnerLocation);
        spinnerAssetGroup = findViewById(R.id.spinnerAssetGroup);
        spinnerAccountableParty = findViewById(R.id.spinnerAccountableParty);
        textDPID = findViewById(R.id.textViewDPID);
        textAGID = findViewById(R.id.textViewAGID);
        textAssetSN = findViewById(R.id.textViewAssetSN);
        buttonCapture = findViewById(R.id.buttonCapture);
        buttonBrowse = findViewById(R.id.buttonBrowse);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        buttonCancel = findViewById(R.id.buttonCancel);
        listViewPhoto = findViewById(R.id.listViewPhoto);
    }

    // Function to check and request permission
    public void checkPermission(String permission, int requestCode) {

        // Checking if permission is not granted
        if (ContextCompat.checkSelfPermission(
                InfoActivity.this,
                permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(
                    InfoActivity.this,
                    new String[]{permission},
                    requestCode);
        } else {
            Log.d("LOG", "Permission already granted");
        }
    }


    // This function is called when user accept or decline the permission.
    // Request Code is used to check which permission called this function.
    // This request code is provided when user is prompt for permission.
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CAMERA_PERMISSION_CODE) {

            // Checking whether user granted the permission or not.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Print log
                Log.d("LOG", "Camera Permission Granted");
            } else {
                Toast.makeText(this, "Camera Permission Denied", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("LOG", "Storage Permission Granted");
            } else {
                Toast.makeText(this, "Storage Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        assert data != null;
        Uri uri = data.getData();

        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                String name = "Front Picture";
                arrayPhoto.add(new AssetPhotos(uri, name));
                listViewPhoto.setAdapter(new AssetPhotoAdapter(this, R.layout.photo_layout, arrayPhoto));
            }
            // else -> Unrecognised request code

        } else if (requestCode == STORAGE_PERMISSION_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                String name = "Front Picture";
                arrayPhoto.add(new AssetPhotos(uri, name));
                listViewPhoto.setAdapter(new AssetPhotoAdapter(this, R.layout.photo_layout, arrayPhoto));
            }
        }

        real_path = getRealPathFromURI(uri);
    }

    private void GetDepartments() {

        Call<List<Departments>> call = client.getDepartments();
        call.enqueue(new Callback<List<Departments>>() {
            @Override
            public void onResponse(Call<List<Departments>> call, Response<List<Departments>> response) {
                ArrayList<Departments> departmentsArrayList = (ArrayList<Departments>) response.body();
                assert departmentsArrayList != null;
                final ArrayAdapter<Departments> adapter = new ArrayAdapter<>(InfoActivity.this,
                        android.R.layout.simple_spinner_dropdown_item, departmentsArrayList);
                spinnerDepartment.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Departments>> call, Throwable t) {
            }
        });
    }

    private void GetLocations() {

//        Call<List<Locations>> call = client.getLocations();
//        call.enqueue(new Callback<List<Locations>>() {
//            @Override
//            public void onResponse(Call<List<Locations>> call, Response<List<Locations>> response) {
//                ArrayList<Locations> locationsArrayList = (ArrayList<Locations>) response.body();
//                assert locationsArrayList != null;
//                final ArrayAdapter<Locations> adapter = new ArrayAdapter<>(InfoActivity.this,
//                        android.R.layout.simple_spinner_dropdown_item, locationsArrayList);
//                spinnerLocation.setAdapter(adapter);
//            }
//
//            @Override
//            public void onFailure(Call<List<Locations>> call, Throwable t) {
//            }
//        });
    }

    private void GetAssetGroups() {

        Call<List<AssetGroups>> call = client.getAssetGroups();
        call.enqueue(new Callback<List<AssetGroups>>() {
            @Override
            public void onResponse(Call<List<AssetGroups>> call, Response<List<AssetGroups>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(InfoActivity.this, "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                }

                ArrayList<AssetGroups> assetGroupsArrayList = (ArrayList<AssetGroups>) response.body();
                assert assetGroupsArrayList != null;
                final ArrayAdapter<AssetGroups> adapter = new ArrayAdapter<>(InfoActivity.this,
                        android.R.layout.simple_spinner_dropdown_item, assetGroupsArrayList);
                spinnerAssetGroup.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<AssetGroups>> call, Throwable t) {
            }
        });
    }

    private void GetAccountableParty() {

//        Call<List<AccountableParty>> call = client.getAccountableParty();
//        call.enqueue(new Callback<List<AccountableParty>>() {
//            @Override
//            public void onResponse(Call<List<AccountableParty>> call, Response<List<AccountableParty>> response) {
//                ArrayList<AccountableParty> accountablePartyArrayList = (ArrayList<AccountableParty>) response.body();
//                assert accountablePartyArrayList != null;
//                final ArrayAdapter<AccountableParty> adapter = new ArrayAdapter<>(InfoActivity.this,
//                        android.R.layout.simple_spinner_dropdown_item, accountablePartyArrayList);
//                spinnerAccountableParty.setAdapter(adapter);
//            }
//
//            @Override
//            public void onFailure(Call<List<AccountableParty>> call, Throwable t) {
//            }
//        });
    }

    // Add asset
    /*private void SubmitAssetCatalogues() {

        Call<List<AssetCatalogues>> call = client.setAssetCatalogues(
                assetsn, assetname, departmentlocationid, employeeid, assetgroupid, description, warrantydate);
    }*/

    private void SubmitAssetPhotos() {

        File file = new File(real_path);
        String file_path = file.getAbsolutePath();
        String[] arrFileName = file_path.split("\\.");
        file_path = arrFileName[0] + System.currentTimeMillis() + "." + arrFileName[1];

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("uploaded_file", file_path, requestBody);

        IWebservice dataClient = APIUtilities.getData();
        Call<String> call = dataClient.uploadAssetPhoto(part);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String message = response.body();
                assert message != null;
                if (message.length() > 0) {
                    IWebservice insertData = APIUtilities.getData();
                    retrofit2.Call<String> callBack = insertData.setAssetPhotos(
                            1, APIUtilities.baseUrl + "images/" + message
                    );
                    callBack.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            String result = response.body();
                            assert result != null;
                            if (result.equals("1")) {
                                Toast.makeText(InfoActivity.this, "Add photo successful", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(InfoActivity.this, "Can't add this photo.", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                        }
                    });
                } else {
                    Toast.makeText(InfoActivity.this, "No photo is selected!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(InfoActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public String getRealPathFromURI(Uri contentUri) {
        String path = null;
        String[] proj = {MediaStore.MediaColumns.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        assert cursor != null;
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            path = cursor.getString(column_index);
        }
        cursor.close();
        return path;
    }

    // Custom adapter for ListView
    public static class AssetPhotoAdapter extends BaseAdapter {

        private Context context;
        private int layout;
        private ArrayList<AssetPhotos> list;

        public AssetPhotoAdapter(Context context, int layout, ArrayList<AssetPhotos> list) {
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
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
                assert inflater != null;
                convertView = inflater.inflate(layout, null);
                holder.imgPhoto = convertView.findViewById(R.id.imageViewPhoto);
                holder.textAssetPhoto = convertView.findViewById(R.id.textViewAssetPhotoName);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            AssetPhotos assetPhotos = list.get(position);
            holder.imgPhoto.setImageURI(assetPhotos.getUri());
            holder.textAssetPhoto.setText(assetPhotos.getName());

            return convertView;
        }

        public static class ViewHolder {
            ImageView imgPhoto;
            TextView textAssetPhoto;
        }
    }
}
