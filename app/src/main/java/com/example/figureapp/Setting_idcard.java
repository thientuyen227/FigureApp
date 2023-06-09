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

public class Setting_idcard extends BaseActivity {
    TextView tvName;
    ImageView imUser;
    EditText edtNewIdCard, edtPassWord;
    Button btnAccept;
    private static final String SHARED_PREF_NAME = "volleyregisterlogin";
    private static final String KEY_TOKEN = "keytoken";
    String passWord;
    private void initComponents()
    {
        edtNewIdCard = findViewById(R.id.edt_newIdCard);
        edtPassWord = findViewById(R.id.edt_passWord);
        btnAccept = findViewById(R.id.btn_accept);
        tvName = findViewById(R.id.tv_name);
        imUser = findViewById(R.id.im_avatar);
    }
    private void SetProfile(User user){
        Glide.with(getApplicationContext()).load(user.getAvatar()).into(imUser);
        tvName.setText(user.getName().toString().trim());
    }
    private String GetPassWord(User user)
    {

        String passWord=user.getPassword().toString().trim();
        return passWord;
    }
    private void AcceptChangeIDCard(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Thông báo");
        alert.setMessage("Bạn có muốn thay đổi Số Thẻ thanh toán không?");
        alert.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //lệnh nút có
                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
                String token =  sharedPreferences.getString(KEY_TOKEN, null);
                BaseAPIService.createService(IUserService.class).getProfile("Bearer "+token).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        SetNewIDCard(response.body());
                        Toast.makeText(Setting_idcard.this, "Thay đổi số thẻ ngân hàng thành công!", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(Setting_idcard.this, "Đã có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
                    }
                });
                startActivity(new Intent(Setting_idcard.this, SettingActivity.class));
            }
        });
        alert.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {//lệnh nút không
                startActivity(new Intent(Setting_idcard.this, SettingActivity.class));
            }
        });
        alert.show();
    }
    private boolean CheckNewIDCard(String newIDCard)
    {
        if(newIDCard.trim().length()!=9)
            return false;
        for (int i = 0; i < newIDCard.trim().length(); i++)
        {
            if (!Character.isDigit(newIDCard.charAt(i))) {
                return false; // Nếu gặp ký tự không phải chữ số, trả về false
            }
        }
        return true;
    }
    private void SetNewIDCard(User user)
    {
        user.setIdCard(edtNewIdCard.getText().toString().trim());
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_idcard);
        setUpActionBar("Setting Idcard");
        setBottomAppBar();
        initComponents();

        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
            String token =  sharedPreferences.getString(KEY_TOKEN, null);
            BaseAPIService.createService(IUserService.class).getProfile("Bearer "+token).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    SetProfile(response.body());
                    passWord=GetPassWord(response.body());
                }
                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(Setting_idcard.this, "Đã có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
                }
            });
        }
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CheckNewIDCard(edtNewIdCard.getText().toString().trim())) {
                    Toast.makeText(Setting_idcard.this, "Số thẻ ngân hàng không hợp lệ", Toast.LENGTH_SHORT).show();

                } else if (!passWord.equals(edtPassWord.getText().toString().trim())) {
                    Toast.makeText(Setting_idcard.this, "Mật khẩu không chính xác!", Toast.LENGTH_SHORT).show();
                } else {
                    AcceptChangeIDCard();
                }
            }
        });
    }
}