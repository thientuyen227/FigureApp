package com.example.figureapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
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

public class Setting_profile extends BaseActivity {

    TextView tvName;
    ImageView imUser;
    EditText edtName,edtUserName,edtEmail,edtEWallet, edtRePassWord;
    Button btnAccept;
    private static final String SHARED_PREF_NAME = "volleyregisterlogin";
    private static final String KEY_TOKEN = "keytoken";
    private String passWord;
    private void initComponents()
    {
        edtName = findViewById(R.id.edt_name);
        edtUserName = findViewById(R.id.edt_userName);
        edtEmail = findViewById(R.id.edt_email);
        edtEWallet = findViewById(R.id.edt_eWallet);
        edtRePassWord = findViewById(R.id.edt_oldPassWord);
        btnAccept = findViewById(R.id.btn_accept);
        tvName = findViewById(R.id.tv_name);
        imUser = findViewById(R.id.im_avatar);
    }
    private void setProfile(User user){
        Glide.with(getApplicationContext()).load(user.getAvatar()).into(imUser);
        tvName.setText(user.getName().trim());
        edtName.setText(user.getName().trim());
        edtUserName.setText(user.getUserName().trim());
        edtEmail.setText(user.getEmail().trim());
        int temp_eWallet = user.getEWallet();
        setEWallet(temp_eWallet);
    }
    private void setEWallet(int temp_eWallet)
    {
        if(temp_eWallet==0) {
            edtEWallet.setText("Thanh toán trực tiếp");
        }
        else if (temp_eWallet==1) {
            edtEWallet.setText("Thanh toán qua momo");
        }
        else if (temp_eWallet==2)
        {
            edtEWallet.setText("Thanh toán qua thẻ ngân hàng");
        }else
            edtEWallet.setText("Chưa có");
        edtEWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(Setting_profile.this, edtEWallet);
                popupMenu.getMenuInflater().inflate(R.menu.menu_ewallet, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        // Xử lý sự kiện khi một mục menu được chọn
                        switch (item.getItemId()) {
                            case R.id.menu_item0:
                                // Xử lý cho menu item 1
                                return true;
                            case R.id.menu_item1:
                                // Xử lý cho menu item 2
                                return true;
                            case R.id.menu_item2:
                                // Xử lý cho menu item 2
                                return true;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }
    private String GetPassWord(User user)
    {
        String passWord=user.getPassword().trim();
        return passWord;
    }
    private void AcceptChangeProfile(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Thông báo");
        alert.setMessage("Bạn có muốn thay đổi thông tin không?");
        alert.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //lệnh nút có
                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
                String token =  sharedPreferences.getString(KEY_TOKEN, null);
                BaseAPIService.createService(IUserService.class).getProfile("Bearer "+token).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        User user = SetNewProfile();
                        BaseAPIService.createService(IUserService.class).updateProfile(user.getName(), user.getEmail(), user.getEWallet(), "Bearer " + token).enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {
                                Toast.makeText(Setting_profile.this, "Thay đổi thông tin thành công!", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<User> call, Throwable t) {
                                Toast.makeText(Setting_profile.this, "Thay đổi thông tin thất bại!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(Setting_profile.this, "Đã có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
                    }
                });
                startActivity(new Intent(Setting_profile.this, SettingActivity.class));
            }
        });
        alert.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {//lệnh nút không
                startActivity(new Intent(Setting_profile.this, SettingActivity.class));
            }
        });
        alert.show();
    }
    private int ValueEWallet(String e)
    {
        int value = 3;
        if(edtEWallet.getText().toString().trim().equals("Thanh toán trực tiếp")) {
            value = 0;
        }
        else if (edtEWallet.getText().toString().trim().equals("Thanh toán qua momo")) {
            value = 1;
        }
        else if (edtEWallet.getText().toString().trim().equals("Thanh toán qua thẻ ngân hàng")) {
            value = 2;
        }
        return value;
    }
    private User SetNewProfile()
    {
        User tmp_user = new User(
                edtName.getText().toString().trim(),
                edtEmail.getText().toString().trim(),
                Integer.parseInt(edtEWallet.getText().toString().trim())
        );
        return tmp_user;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_profile);
        setUpActionBar("Setting profile");
        setBottomAppBar();
        initComponents();
        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
            String token =  sharedPreferences.getString(KEY_TOKEN, null);
            BaseAPIService.createService(IUserService.class).getProfile("Bearer "+token).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    setProfile(response.body());
                    passWord=GetPassWord(response.body());
                }
                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(Setting_profile.this, "Đã có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
                }
            });
        }
        btnAccept.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (edtName.getText().toString().trim().equals("")) {
                    Toast.makeText(Setting_profile.this, "Không thể để trống Fullname!", Toast.LENGTH_SHORT).show();
                } else if (edtEmail.getText().toString().trim().equals("")) {
                    Toast.makeText(Setting_profile.this, "Không thể để trống Email!", Toast.LENGTH_SHORT).show();
                } else if (edtEWallet.getText().toString().trim().equals("")) {
                    Toast.makeText(Setting_profile.this, "Không thể để trống Phương thức thanh toán!", Toast.LENGTH_SHORT).show();
                } else if (!passWord.equals(edtRePassWord.getText().toString().trim())) {
                    Toast.makeText(Setting_profile.this, "Mật khẩu không chính xác!", Toast.LENGTH_SHORT).show();
                }else
                {
                    AcceptChangeProfile();
                }
            }
        });
    }
}