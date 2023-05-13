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

public class Setting_idcard extends BaseActivity {
    TextView tvName;
    ImageView imUser;
    EditText edtNewIdCard, edtRePassWord;
    Button btnReturn, btnAccept;

    private void AnhXA()
    {
        edtNewIdCard = findViewById(R.id.edt_newIdCard);
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
    private String OldIDCard()
    {
        String oldEIDCard="";
        return oldEIDCard;
    }
    private void AcceptChangeIDCard(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Thông báo");
        alert.setMessage("Bạn có muốn thay đổi Số Thẻ thanh toán không?");
        alert.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {//lệnh nút có
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
    private void SetNewIDCard(String newIDCard)
    {

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_idcard);
        setUpActionBar("Setting Idcard");
        AnhXA();
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Setting_idcard.this, SettingActivity.class));
            }
        });
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtNewIdCard.getText().toString().trim().equals(OldIDCard())
                        &&!edtNewIdCard.getText().toString().trim().equals("")
                        &&edtRePassWord.getText().toString().trim().equals(PassWord())
                )
                {
                    SetNewIDCard(edtNewIdCard.getText().toString().trim());
                    AcceptChangeIDCard();
                }
                else {

                }
            }
        });
    }
}