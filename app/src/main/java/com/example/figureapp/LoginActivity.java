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

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {

    EditText editTextUsername, editTextEmail,editTextPassword;
    Button btnSignup;
    TextView txtLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
        }

        editTextUsername =findViewById(R.id.editTextUsername);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        btnSignup = findViewById(R.id.btn_signup);
        txtLogin = findViewById(R.id.textLogin);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Lay du lieu tu EditText
                String username= editTextUsername.getText().toString().trim();
                String password= editTextPassword.getText().toString().trim();
                String email= editTextEmail.getText().toString().trim();

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
                if(email.isEmpty()){
                    Toast.makeText(LoginActivity.this,"Vui long nhap Email",Toast.LENGTH_SHORT).show();
                    editTextEmail.requestFocus();
                    return;
                }
            }
        });
    }
}