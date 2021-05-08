package com.duantotnghiep.iwash.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.duantotnghiep.iwash.R;
import com.duantotnghiep.iwash.api.ApiService;
import com.duantotnghiep.iwash.api.RetrofitClient;
import com.duantotnghiep.iwash.model.ServerResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {
    Toolbar toolbar;
    EditText edtCurPw, edtNewPw;
    Button btnChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        initViews();
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(R.string.textview_changepw);
        }
        toolbar.setNavigationIcon(R.drawable.backicon);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        btnChange.setOnClickListener(v -> {
            RetrofitClient.getInstance().create(ApiService.class).changePassword(edtCurPw.getText().toString().trim(), edtNewPw.getText().toString().trim()).enqueue(new Callback<ServerResponse>() {
                @Override
                public void onResponse(@NonNull Call<ServerResponse> call, @NonNull Response<ServerResponse> response) {
                    if (response.body().success) {
                        Toast.makeText(ChangePasswordActivity.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ChangePasswordActivity.this, response.body().message, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ServerResponse> call, @NonNull Throwable t) {
                    Log.d("onFailureChangePw: ", t.getMessage());
                }
            });
        });
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbarChange);
        edtCurPw = findViewById(R.id.edtCurPw);
        edtNewPw = findViewById(R.id.edtNewPw);
        btnChange = findViewById(R.id.btnChange);
    }

}