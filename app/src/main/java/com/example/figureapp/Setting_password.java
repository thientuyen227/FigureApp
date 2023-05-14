package com.example.figureapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.figureapp.model.SharedPrefManager;
import com.example.figureapp.model.User;
import com.example.figureapp.service.BaseAPIService;
import com.example.figureapp.service.IUserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Setting_password extends BaseActivity {
    TextView tvName;
    ImageView imUser;
    EditText edtNewPassword, edtReNewPass, edtReOldPass;
    Button  btnAccept;
    private static final String SHARED_PREF_NAME = "volleyregisterlogin";
    private static final String KEY_TOKEN = "keytoken";
    private String oldPass;
    private void initComponents()
    {
        edtNewPassword = findViewById(R.id.edt_newPassWord);
        edtReNewPass = findViewById(R.id.edt_reNewPassWord);
        edtReOldPass = findViewById(R.id.edt_oldPassWord);
        btnAccept = findViewById(R.id.btn_accept);
        tvName = findViewById(R.id.tv_name);
        imUser = findViewById(R.id.im_avatar);
    }
    private String GetOldPassWord(User user)
    {

        String oldPass=user.getPassword().toString().trim();
        return oldPass;
    }
    private void AcceptChangePassWord(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Thông báo");
        alert.setMessage("Bạn có muốn thay đổi mật khẩu không?");
        alert.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
                String token =  sharedPreferences.getString(KEY_TOKEN, null);
                BaseAPIService.createService(IUserService.class).getProfile("Bearer "+token).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        SetNewPassWord(response.body());
                        Toast.makeText(Setting_password.this, "Đổi mật khẩu thành công!", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(Setting_password.this, "Đã có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
                    }
                });
                //lệnh nút có
                startActivity(new Intent(Setting_password.this, SettingActivity.class));
            }
        });
        alert.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {//lệnh nút không
                startActivity(new Intent(Setting_password.this, SettingActivity.class));
            }
        });
        alert.show();
    }
    private void SetNewPassWord(User user)
    {
        String newPass = edtNewPassword.getText().toString().trim();
        user.setPassword(newPass.toString().trim());
    }
    private void setProfile(User user){
        Glide.with(getApplicationContext()).load(user.getAvatar()).into(imUser);
        tvName.setText(user.getName().toString().trim());
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_password);
        setUpActionBar("Setting password");
        setBottomAppBar();
        initComponents();
        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
            String token =  sharedPreferences.getString(KEY_TOKEN, null);
            BaseAPIService.createService(IUserService.class).getProfile("Bearer "+token).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    setProfile(response.body());
                    oldPass=GetOldPassWord(response.body());
                }
                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(Setting_password.this, "Đã có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
                }
            });
        }
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtNewPassword.getText().toString().trim().equals(edtReNewPass)
                        && edtReOldPass.getText().toString().trim().equals(oldPass)
                        &&!edtNewPassword.getText().toString().trim().equals("")
                        &&!edtReNewPass.getText().toString().trim().equals(""))
                    {
                        AcceptChangePassWord();
                    }
                else {
                        if(edtNewPassword.getText().toString().trim().equals(""))
                            Toast.makeText(Setting_password.this, "Mật khẩu mới không được để trống!",
                                    Toast.LENGTH_SHORT).show();
                        else {
                            if(!edtNewPassword.getText().toString().trim().equals(edtReNewPass))
                                Toast.makeText(Setting_password.this, "Nhập lại mật khẩu mới không chính xác!",
                                        Toast.LENGTH_SHORT).show();
                            else {
                                if(!edtReOldPass.getText().toString().trim().equals(oldPass))
                                    Toast.makeText(Setting_password.this, "Nhập lại mật khẩu không chính xác!",
                                            Toast.LENGTH_SHORT).show();
                            }
                        }
                }
            }
        });
    }
}