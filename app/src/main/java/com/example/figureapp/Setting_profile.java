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

public class Setting_profile extends BaseActivity {

    TextView tvName;
    ImageView imUser;
    EditText edtNewEmail, edtRePassWord;
    Button btnReturn, btnAccept;

    private void AnhXA()
    {
        edtNewEmail = findViewById(R.id.edt_newEmail);
        edtRePassWord = findViewById(R.id.edt_OldPassWord);
        btnReturn = findViewById(R.id.btn_return);
        btnAccept = findViewById(R.id.btn_accept);
        tvName = findViewById(R.id.tv_Name);
        imUser = findViewById(R.id.im_avatar);
    }
    private String PassWord()
    {
        String passWord="";
        return passWord;
    }
    private String OldEmail()
    {
        String oldEMail="";
        return oldEMail;
    }
    private void AcceptChangeEmail(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Thông báo");
        alert.setMessage("Bạn có muốn thay đổi email không?");
        alert.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {//lệnh nút có
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
    private void SetNewEmail(String newPass)
    {

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_profile);
        setUpActionBar("Setting profile");
        AnhXA();
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Setting_profile.this, SettingActivity.class));
            }
        });
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtNewEmail.getText().toString().trim().equals(OldEmail())
                        &&!edtNewEmail.getText().toString().trim().equals("")
                        &&edtRePassWord.getText().toString().trim().equals(PassWord())
                        )
                    {
                        SetNewEmail(edtNewEmail.getText().toString().trim());
                        AcceptChangeEmail();
                    }
                else {

                }
            }
        });
    }
}