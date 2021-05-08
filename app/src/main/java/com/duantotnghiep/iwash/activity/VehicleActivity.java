package com.duantotnghiep.iwash.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.airbnb.lottie.LottieAnimationView;
import com.duantotnghiep.iwash.R;
import com.duantotnghiep.iwash.adapter.VehicleAdapter;
import com.duantotnghiep.iwash.api.ApiService;
import com.duantotnghiep.iwash.api.RetrofitClient;
import com.duantotnghiep.iwash.callback.ItemClick;
import com.duantotnghiep.iwash.model.ServerResponse;
import com.duantotnghiep.iwash.model.Vehicle;
import com.google.android.gms.common.api.Api;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VehicleActivity extends AppCompatActivity {
    RecyclerView rvVehicle;
    ImageView imgAddVehicle;
    VehicleAdapter adapter;
    List<Vehicle> vehicles = new ArrayList<>();
    Toolbar toolbar;
    String type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(R.string.schedule);
        }
        toolbar.setTitle("Phương tiện của bạn");
        toolbar.setNavigationIcon(R.drawable.backicon);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        rvVehicle = findViewById(R.id.rvVehicle);
        imgAddVehicle = findViewById(R.id.imgAddVehicle);
        RetrofitClient.getInstance().create(ApiService.class).getVehicle().enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(@NonNull Call<ServerResponse> call, @NonNull Response<ServerResponse> response) {
                if (response.code() == 200) {
                    vehicles = response.body().vehicles;
                    adapter = new VehicleAdapter(VehicleActivity.this, vehicles, new ItemClick() {
                        @Override
                        public void setOnItemClick(View v, int pos) {
                            if (v.getId() == R.id.imgDelete) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(VehicleActivity.this);
                                builder.setTitle("Xóa");
                                builder.setPositiveButton("Ok", (dialog, which) -> {
                                    RetrofitClient.getInstance().create(ApiService.class).deleteVehicle(vehicles.get(pos).getId()).enqueue(new Callback<ServerResponse>() {
                                        @Override
                                        public void onResponse(@NonNull Call<ServerResponse> call, @NonNull Response<ServerResponse> response) {
                                            if (response.body().success) {
                                                Toast.makeText(VehicleActivity.this, response.body().message, Toast.LENGTH_SHORT).show();
                                                Intent intent = getIntent();
                                                finish();
                                                startActivity(intent);
                                            } else {
                                                Toast.makeText(VehicleActivity.this, response.body().message, Toast.LENGTH_SHORT).show();
                                            }

                                        }

                                        @Override
                                        public void onFailure(@NonNull Call<ServerResponse> call, @NonNull Throwable t) {
                                            Log.e("onFailureDeleteVehicle: ", t.getMessage());
                                        }
                                    });
                                });
                                builder.setNegativeButton("Cancel", (dialog, which) -> {
                                    dialog.dismiss();
                                });
                                builder.show();
                            } else if (v.getId() == R.id.imgUpdate) {
                                AlertDialog builder = new AlertDialog.Builder(VehicleActivity.this).create();
                                View dialog = LayoutInflater.from(getApplicationContext()).inflate(R.layout.dialog_update_vehicle, null);
                                Button btnUpdate = dialog.findViewById(R.id.btnUpdateVehicle);
                                EditText edtName = dialog.findViewById(R.id.edtNameOfVehicle);
                                EditText edtBrand = dialog.findViewById(R.id.edtBrand);
                                EditText edtColor = dialog.findViewById(R.id.edtColorOfVehicle);
                                EditText edtLicense = dialog.findViewById(R.id.edtLicense);
                                Spinner spnType = dialog.findViewById(R.id.spnType);
                                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(VehicleActivity.this,
                                        R.array.type_of_vehicle, android.R.layout.simple_spinner_dropdown_item);
                                adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
                                spnType.setAdapter(adapter);
                                spnType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        if (spnType.getSelectedItem().toString().equals("Xe máy")) {
                                            type = "Motorcycle";
                                        } else if (spnType.getSelectedItem().toString().equals("Ô tô")) {
                                            type = "Car";
                                        }
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                                edtName.setText(vehicles.get(pos).getName());
                                edtBrand.setText(vehicles.get(pos).getBrand());
                                edtColor.setText(vehicles.get(pos).getColor());
                                edtLicense.setText(vehicles.get(pos).getLicense());
                                Button btnCancel = dialog.findViewById(R.id.btnCancel);
                                btnCancel.setOnClickListener(v1 -> builder.dismiss());
                                btnUpdate.setOnClickListener(v1 -> RetrofitClient.getInstance().create(ApiService.class).updateVehicle(vehicles.get(pos).getId(),
                                        edtName.getText().toString().trim(),
                                        type,
                                        edtLicense.getText().toString().trim(),
                                        edtColor.getText().toString().trim(),
                                        edtBrand.getText().toString().trim())
                                        .enqueue(new Callback<ServerResponse>() {
                                            @Override
                                            public void onResponse(@NonNull Call<ServerResponse> call1, @NonNull Response<ServerResponse> response1) {
                                                if (response1.body().success) {
                                                    Toast.makeText(VehicleActivity.this, response1.body().message, Toast.LENGTH_SHORT).show();
                                                    finish();
                                                    startActivity(getIntent());
                                                } else {
                                                    Toast.makeText(VehicleActivity.this, response1.body().message, Toast.LENGTH_SHORT).show();
                                                }
                                            }

                                            @Override
                                            public void onFailure(@NonNull Call<ServerResponse> call1, @NonNull Throwable t) {
                                                Log.e("onFailureUpdateVehicle: ", t.getMessage());
                                            }
                                        }));
                                builder.setView(dialog);
                                builder.show();
                            }
                        }
                    });
                    rvVehicle.setLayoutManager(new LinearLayoutManager(VehicleActivity.this, LinearLayoutManager.VERTICAL, false));
                    rvVehicle.setAdapter(adapter);
                } else {
                    Toast.makeText(VehicleActivity.this, response.body().message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ServerResponse> call, @NonNull Throwable t) {
                Log.d("huhu", t.getMessage());
            }
        });
        imgAddVehicle.setOnClickListener(v -> {
            AlertDialog builder = new AlertDialog.Builder(this).create();
            View dialog = LayoutInflater.from(getApplicationContext()).inflate(R.layout.dialog_add_vehicle, null);
            EditText edtNameOfVehicle = dialog.findViewById(R.id.edtNameOfVehicle);
            EditText edtColorOfVehicle = dialog.findViewById(R.id.edtColorOfVehicle);
            EditText edtLicense = dialog.findViewById(R.id.edtLicense);
            EditText edtBrand = dialog.findViewById(R.id.edtBrand);
            Spinner spnType = (Spinner) dialog.findViewById(R.id.spnType);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                    R.array.type_of_vehicle, android.R.layout.simple_spinner_dropdown_item);
            adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
            spnType.setAdapter(adapter);
            Button btnAddVehicle = dialog.findViewById(R.id.btnAddVehicle);
            Button btnCancel = dialog.findViewById(R.id.btnCancel);
            spnType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (spnType.getSelectedItem().toString().equals("Xe máy")) {
                        type = "Motorcycle";
                    } else if (spnType.getSelectedItem().toString().equals("Ô tô")) {
                        type = "Car";
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            btnAddVehicle.setOnClickListener(view -> {
                RetrofitClient.getInstance().create(ApiService.class).addVehicle(edtNameOfVehicle.getText().toString(), type
                        , edtLicense.getText().toString(), edtColorOfVehicle.getText().toString(), edtBrand.getText().toString()).enqueue(new Callback<ServerResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<ServerResponse> call, @NonNull Response<ServerResponse> response) {
                        if (response.code() == 200) {
//                            Toast.makeText(VehicleActivity.this, response.body().message, Toast.LENGTH_SHORT).show();
//                            adapter.notifyDataSetChanged();
//                            ProgressDialog dialog = new ProgressDialog(VehicleActivity.this);
//                            dialog.setMessage("Đang tải");
//                            dialog.show();
//                            new Handler().postDelayed(() -> {
//                                dialog.dismiss();
//                            }, 2000);
//                            finish();
//                            overridePendingTransition(0, 0);
//                            startActivity(getIntent());
//                            overridePendingTransition(0, 0);
//                            Toast.makeText(VehicleActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                            Toast.makeText(VehicleActivity.this, response.body().message, Toast.LENGTH_SHORT).show();
                            finish();
                            overridePendingTransition(0, 0);
                            startActivity(getIntent());
                            overridePendingTransition(0, 0);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ServerResponse> call, @NonNull Throwable t) {
                        Log.d("Error to add vehicle", t.getMessage());
                    }
                });
                builder.dismiss();
            });
            btnCancel.setOnClickListener(view -> {
                builder.dismiss();
            });
            builder.setView(dialog);
            builder.show();
        });
    }
}