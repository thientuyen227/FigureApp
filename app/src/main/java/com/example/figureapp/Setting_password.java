package com.example.figureapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Setting_password extends BaseActivity {
    TextView tvName;
    ImageView imUser;
    EditText edtNewPassword, edtReNewPass, edtReOldPass;
    Button btnReturn, btnAccept;

    private void AnhXA()
    {
        edtNewPassword = findViewById(R.id.edt_newPassWord);
        edtReNewPass = findViewById(R.id.edt_reNewPassWord);
        edtReOldPass = findViewById(R.id.edt_OldPassWord);
        btnReturn = findViewById(R.id.btn_return);
        btnAccept = findViewById(R.id.btn_accept);
        tvName = findViewById(R.id.tv_Name);
        imUser = findViewById(R.id.im_avatar);
    }
    private String OldPassWord()
    {
        String oldPass="";
        return oldPass;
    }
    private void AcceptChangePassWord(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Thông báo");
        alert.setMessage("Bạn có muốn thay đổi mật khẩu không?");
        alert.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {//lệnh nút có
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
    private void SetNewPassWord(String newPass)
    {

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_password);
        setUpActionBar("Setting password");
        AnhXA();
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Setting_password.this, SettingActivity.class));
            }
        });
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtNewPassword.getText().toString().trim().equals(edtReNewPass)
                        && edtReOldPass.getText().toString().trim().equals(OldPassWord())
                        &&!edtNewPassword.getText().toString().trim().equals("")
                        &&!edtReNewPass.getText().toString().trim().equals(""))
                    {
                        SetNewPassWord(edtNewPassword.getText().toString().trim());
                        AcceptChangePassWord();
                    }
                else {

                }
            }
        });
    }
}