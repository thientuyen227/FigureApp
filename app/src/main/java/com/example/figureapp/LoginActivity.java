package com.example.figureapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.figureapp.model.SharedPrefManager;
import com.example.figureapp.model.TokenModel;
import com.example.figureapp.model.User;
import com.example.figureapp.service.BaseAPIService;
import com.example.figureapp.service.IUserService;
import com.example.figureapp.service.Response;

import org.w3c.dom.Text;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;

public class LoginActivity extends AppCompatActivity {

    EditText editTextUsername, editTextPassword;
    Button btnLogin;
    TextView txtSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        if(SharedPrefManager.getInstance(this).isLoggedIn()){
//            finish();
//            startActivity(new Intent(LoginActivity.this,MainActivity.class));
//        }

        editTextUsername =findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        btnLogin = findViewById(R.id.btn_login);
        txtSignup = findViewById(R.id.txtSignup);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Lay du lieu tu EditText
                String username= editTextUsername.getText().toString().trim();
                String password= editTextPassword.getText().toString().trim();

                //kiem tra du lieu da nhap vao
                if(username.isEmpty()){
                    Toast.makeText(LoginActivity.this,"Vui long nhap User name",Toast.LENGTH_SHORT).show();
                    editTextUsername.requestFocus();
                    return;
                }
                if(password.isEmpty()){
                    Toast.makeText(LoginActivity.this,"Vui long nhap Password",Toast.LENGTH_SHORT).show();
                    editTextPassword.requestFocus();
                    return;
                }
                BaseAPIService.createService(IUserService.class).logIn(username,password).enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                        if (response.isSuccessful()){
                            Response loginsuceess = response.body();
                            Toast.makeText(LoginActivity.this, "Đăng nhập thanh cong", Toast.LENGTH_SHORT).show();
                            TokenModel token = new TokenModel(
                                    loginsuceess.getToken().getToken()
                            );
                            SharedPrefManager.getInstance(getApplicationContext()).saveToken(token.getToken());
                            finish();
                            Intent intent =new Intent(LoginActivity.this,HomeActivity.class);
                            startActivity(intent);
                        }
                        else {
                                Toast.makeText(LoginActivity.this, "Đã có lỗi xảy ra. Vui lòng thử lại sau.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<Response> call, Throwable t) {
                        Log.e("LoginActivity", "Login request failed", t);
                        if (t instanceof IOException) {
                            Toast.makeText(LoginActivity.this, "Lỗi kết nối. Vui lòng thử lại sau.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginActivity.this, "Lỗi không xác định. Vui lòng thử lại sau.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
        txtSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển sang màn hình Đăng nhập
                Intent intent =new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(intent);
            }
        });
    }
}