package com.example.figureapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.figureapp.model.SharedPrefManager;
import com.example.figureapp.model.User;
import com.example.figureapp.service.BaseAPIService;
import com.example.figureapp.service.IUserService;
import com.example.figureapp.service.Response;


import retrofit2.Call;
import retrofit2.Callback;

public class SignupActivity extends AppCompatActivity {

    EditText editTextUsername, editTextEmail,editTextPassword, editTextName;
    Button btnSignup;
    TextView txtLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(SignupActivity.this,MainActivity.class));
        }

        editTextUsername =findViewById(R.id.editTextUsername);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextName = findViewById(R.id.edtName);

        btnSignup = findViewById(R.id.btn_signup);
        txtLogin = findViewById(R.id.textLogin);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Lay du lieu tu EditText
                String username= editTextUsername.getText().toString().trim();
                String password= editTextPassword.getText().toString().trim();
                String email= editTextEmail.getText().toString().trim();
                String name = editTextName.getText().toString().trim();

                //kiem tra du lieu da nhap vao
                if(username.isEmpty()){
                    Toast.makeText(SignupActivity.this,"Vui long nhap User name",Toast.LENGTH_SHORT).show();
                    editTextUsername.requestFocus();
                    return;
                }
                if(password.isEmpty()){
                    Toast.makeText(SignupActivity.this,"Vui long nhap Password",Toast.LENGTH_SHORT).show();
                    editTextPassword.requestFocus();
                    return;
                }
                if(email.isEmpty()){
                    Toast.makeText(SignupActivity.this,"Vui long nhap Email",Toast.LENGTH_SHORT).show();
                    editTextEmail.requestFocus();
                    return;
                }
                if (password.length()<6){
                    Toast.makeText(SignupActivity.this,"Vui long nhap Password dai hon 6 ky tu",Toast.LENGTH_SHORT).show();
                    editTextPassword.requestFocus();
                    return;
                }
                BaseAPIService.createService(IUserService.class).signUp(username,email,password, name).enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                        if (response.isSuccessful()){
                            Toast.makeText(SignupActivity.this, "Dang ki thanh cong", Toast.LENGTH_SHORT).show();
                            //Luu thong tin tai khoan vao Shared Preferences
                            User user = new User(username,email,password, name);
                            SharedPrefManager.getInstance(SignupActivity.this).userLogin(user);
                            //Chuyen sang Login neu thanh cong
                            Intent intent =new Intent(SignupActivity.this,HomeActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<Response> call, Throwable t) {
                        Toast.makeText(SignupActivity.this,"Dang ky that bai", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển sang màn hình Đăng nhập
                Intent intent =new Intent(SignupActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}