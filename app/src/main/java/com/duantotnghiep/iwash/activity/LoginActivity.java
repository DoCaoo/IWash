package com.duantotnghiep.iwash.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.duantotnghiep.iwash.R;
import com.duantotnghiep.iwash.api.ApiService;
import com.duantotnghiep.iwash.api.RetrofitClient;
import com.duantotnghiep.iwash.model.ServerResponse;
import com.duantotnghiep.iwash.staff.StaffMainActivity;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.firebase.messaging.FirebaseMessaging;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    Button btnLogin;
    EditText edtPhoneNumber, edtPassword;
    ImageView imgBack;
    TextView tvCreateAccount;
    SharedPreferences sharedPreferences;
    CheckBox cbSavePassword;
    SharedPreferences.Editor editor;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.w("Fetching", task.getException());
                return;
            }
            RetrofitClient.tokenDevice = task.getResult();
        });
        //action back
        imgBack.setOnClickListener(v -> onBackPressed());
        //button login pressed
        btnLogin.setOnClickListener(v -> login());
        //action to sign up activity
        tvCreateAccount.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, SignUpActivity.class)));

        //shared  preference
        sharedPreferences = getSharedPreferences("SaveAccount", MODE_PRIVATE);
        edtPhoneNumber.setText(sharedPreferences.getString("phoneNumber", ""));
        edtPhoneNumber.setSelection(sharedPreferences.getString("phoneNumber", "").length());
        edtPassword.setText(sharedPreferences.getString("password", ""));
        cbSavePassword.setChecked(sharedPreferences.getBoolean("remember", false));

        //checkbox clicked
        cbSavePassword.setOnClickListener(v -> {
            checkCB();
        });
    }

    private void initView() {
        btnLogin = findViewById(R.id.btnLogin);
        edtPhoneNumber = findViewById(R.id.edtPhoneNumber);
        edtPassword = findViewById(R.id.edtPassword);
        tvCreateAccount = findViewById(R.id.tvCreateAccount);
        imgBack = findViewById(R.id.imgBack);
        cbSavePassword = findViewById(R.id.cbSavePassword);
    }

    private void login() {
        btnLogin.setEnabled(false);
        RetrofitClient.getInstance().create(ApiService.class).login("+84" + edtPhoneNumber.getText().toString().trim(), edtPassword.getText().toString().trim(), RetrofitClient.tokenDevice).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(@NonNull Call<ServerResponse> call, @NonNull Response<ServerResponse> response) {
                if (response.body().success) {
                    RetrofitClient.user = response.body().user;
                    RetrofitClient.JWT = response.body().token;
                    Toast.makeText(LoginActivity.this, response.body().message, Toast.LENGTH_SHORT).show();
                    if (cbSavePassword.isChecked()) {
                        editor = sharedPreferences.edit();
                        editor.putString("phoneNumber", edtPhoneNumber.getText().toString().trim());
                        editor.putString("password", edtPassword.getText().toString().trim());
                        editor.putBoolean("remember", true);
                        editor.apply();
                    }
                    if (response.body().user.getRole().equals("Customer")) {
//                        ProgressDialog dialog = new ProgressDialog(LoginActivity.this);
//                        dialog.setMessage("Đang tải");
//                        dialog.show();
//                        new Handler().postDelayed(() -> {
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
//                            dialog.dismiss();
//                        }, 2000);
                    } else if (response.body().user.getRole().equals("Staff")) {
//                        ProgressDialog dialog = new ProgressDialog(LoginActivity.this);
//                        dialog.setMessage("Đang tải");
//                        dialog.show();
//                        new Handler().postDelayed(() -> {
                            startActivity(new Intent(LoginActivity.this, StaffMainActivity.class));
//                            dialog.dismiss();
//                        }, 2000);
                    }
                    finish();
                } else {
                    btnLogin.setEnabled(true);
                    Toast.makeText(LoginActivity.this, response.body().message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ServerResponse> call, @NonNull Throwable t) {
                btnLogin.setEnabled(true);
                Log.e("onFailure", t.toString());
            }
        });

    }

    private void checkCB() {
        SharedPreferences sharedPreferences = getSharedPreferences("Shared", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String phoneNumber = edtPhoneNumber.getText().toString();
        String password = edtPassword.getText().toString();
        boolean check = cbSavePassword.isChecked();
        if (!check) {
            editor.clear();
        } else {
            editor.putString("phoneNumber", phoneNumber);
            editor.putString("password", password);
            editor.putBoolean("save", check);
        }
        editor.apply();
    }
}