package com.example.figureapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.figureapp.model.ProductModel;
import com.example.figureapp.model.User;
import com.example.figureapp.service.BaseAPIService;
import com.example.figureapp.service.IUserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateUserActivity extends BaseActivity {
    EditText edtName, edtPassword, edtRole, edtIdCard, edtEmail, edtEWallet;
    Button btnUpdate;
    private static final String SHARED_PREF_NAME = "volleyregisterlogin";
    private static final String KEY_TOKEN = "keytoken";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);
        setUpActionBar("Update user");
        initComponents();
        initData();
        Intent intent = getIntent();
        int userId = intent.getIntExtra("userId", 1);
        loadUser(userId);
    }
    private void loadUser(int userId){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        String token =  sharedPreferences.getString(KEY_TOKEN, null);

        BaseAPIService.createService(IUserService.class).getUser(userId).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();
                setProfile(user);
                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        User user1 = updateUser(user.getId());
                        BaseAPIService.createService(IUserService.class).updateUser("Bearer " + token, user1.getName(),user1.getPassword(),
                                user1.getRole(),user1.getEmail(),user1.getIdCard(),user1.getEWallet(),user1.getId()).enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {
                                Toast.makeText(UpdateUserActivity.this, "Update user thành công", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(UpdateUserActivity.this, MainActivity.class));
                            }

                            @Override
                            public void onFailure(Call<User> call, Throwable t) {
                                Toast.makeText(UpdateUserActivity.this, "Đã có lỗi xảy ra", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }
    private User updateUser(int userId){
        User user = new User(
        edtName.getText().toString().trim(),
        edtPassword.getText().toString().trim(),
        edtRole.getText().toString().trim(),
        edtEmail.getText().toString().trim(),
        edtIdCard.getText().toString().trim(),
        Integer.parseInt(edtEWallet.getText().toString().trim())
        );
        user.setId(userId);
        return user;
    }
    private void setProfile(User user){
        edtName.setText(user.getName());
        edtPassword.setText(user.getPassword());
        edtRole.setText(user.getRole());
        edtEmail.setText(user.getEmail());
        edtIdCard.setText(user.getIdCard());
        edtEWallet.setText(String.valueOf(user.getEWallet()));
    }
    private void  initComponents(){
        edtName = findViewById(R.id.edt_name);
        edtPassword = findViewById(R.id.edt_password);
        edtRole = findViewById(R.id.edt_role);
        edtEmail = findViewById(R.id.edt_email);
        edtIdCard = findViewById(R.id.edt_idCard);
        edtEWallet = findViewById(R.id.edt_eWallet);
        btnUpdate = findViewById(R.id.btnUpdate);
    }
    private void initData(){

    }
}