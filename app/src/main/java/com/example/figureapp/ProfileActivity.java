package com.example.figureapp;

import android.app.Activity;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.AndroidException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.esafirm.imagepicker.features.ImagePicker;
import com.example.figureapp.model.SharedPrefManager;
import com.example.figureapp.model.User;
import com.example.figureapp.service.BaseAPIService;
import com.example.figureapp.service.IUserService;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Multipart;

public class ProfileActivity extends BaseActivity implements View.OnClickListener {
    TextView tv_name, tv_IdCard, tv_email, tv_eWallet;

    Uri mUri;
    private static final String SHARED_PREF_NAME = "volleyregisterlogin";
    private static final int REQUEST_CODE = 1;
    private static final String KEY_TOKEN = "keytoken";
    Button btn_logout;
    Context context;
    Button btnUploadAvatar;
    ImageView im_profile;
    public static String[] storage_permissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
    };
    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    public static String[] storage_permissions_33 = {
            Manifest.permission.READ_MEDIA_IMAGES,
            Manifest.permission.READ_MEDIA_VIDEO,
            Manifest.permission.READ_MEDIA_AUDIO
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setBottomAppBar();
        setUpActionBar("Profile");
        initComponents();
        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
            String token =  sharedPreferences.getString(KEY_TOKEN, null);
            BaseAPIService.createService(IUserService.class).getProfile("Bearer "+token).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    updateProfile(response.body());
                    btnUploadAvatar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            checkPermission();
                        }
                    });
                }
                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(ProfileActivity.this, "Đã có lỗi xảy ra. Vui lòng thử lại sau.", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
    public void onClick(View view) {
        if (view.equals(btn_logout)) {
            SharedPrefManager.getInstance(getApplicationContext()).logout();
        }
    }
    private void updateProfile(User user){
        Glide.with(getApplicationContext()).load(user.getAvatar()).into(im_profile);
        tv_name.setText(user.getName());
        tv_email.setText(user.getEmail());
        tv_IdCard.setText(user.getIdCard());
        tv_eWallet.setText(String.valueOf(user.getEWallet()));
    }
    private void initComponents() {
        tv_IdCard = findViewById(R.id.tv_IdCard);
        btn_logout = findViewById(R.id.btn_logout);
        tv_name = findViewById(R.id.tv_Name);
        tv_email = findViewById(R.id.tv_Gmail);
        tv_eWallet = findViewById(R.id.tv_eWallet);
        btn_logout = findViewById(R.id.btn_logout);
        im_profile = findViewById(R.id.im_avatar);
        btnUploadAvatar= findViewById(R.id.btn_UploadAvatar);
        btn_logout.setOnClickListener(this);
        btnUploadAvatar.setOnClickListener(this);
    }
    public static String[] permissions() {
        String[] p;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) p = storage_permissions_33;
        else p = storage_permissions;
        return p;
    }

    public void checkPermission(String permission, int requestCode) {
        // Checking if permission is not granted
        if (ContextCompat.checkSelfPermission(ProfileActivity.this, permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(ProfileActivity.this, new String[]{permission}, requestCode);
        } else {
            Toast.makeText(ProfileActivity.this, "Permission already granted", Toast.LENGTH_SHORT).show();
        }
    }
    private void checkPermission() {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            openGallery();
            return;
        }
        if(checkSelfPermission(Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        } else {
            requestPermissions(permissions(), REQUEST_CODE);
        }
    }
    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityIfNeeded(Intent.createChooser(intent, "Select picture"), 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("Profile Tag", "OnActivityResult");
        if(resultCode == Activity.RESULT_OK && requestCode == 100) {
            Uri uri = data.getData();
            mUri = uri;
            String fileName = "";
            try {
                String[] projection = {MediaStore.MediaColumns.DISPLAY_NAME};
                Cursor metaCursor = getContentResolver().query(uri, projection, null, null, null);
                if (metaCursor != null) {
                    try {
                        if (metaCursor.moveToFirst()) {
                            fileName = metaCursor.getString(0);
                        }
                    } finally {
                        metaCursor.close();
                    }
                }
                File temp = new File(getCacheDir().getAbsolutePath(), fileName);
                InputStream inputStream = getContentResolver().openInputStream(uri);
                FileUtils.copyInputStreamToFile(inputStream, temp);
                mUri = Uri.fromFile(temp);
                im_profile.setImageBitmap(MediaStore.Images.Media.getBitmap(getContentResolver(), uri));
                changeAvatar(temp);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void changeAvatar(File image) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        String token =  sharedPreferences.getString(KEY_TOKEN, null);
        // Convert File to RequestBody
        // Chuyển đổi File thành RequestBody
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), image);

        // Tạo MultipartBody.Part từ RequestBody
        MultipartBody.Part avatarPart = MultipartBody.Part.createFormData("avatar", image.getName(), requestFile);

        // Gọi API để đổi avatar
        BaseAPIService.createService(IUserService.class).changeAvatar(avatarPart,"Bearer "+token).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                // Nếu thành công, cập nhật lại thông tin user và hiển thị ảnh mới
                if(response.isSuccessful()) {
                    updateProfile(response.body());
                    Glide.with(getApplicationContext()).load(response.body().getAvatar()).into(im_profile);
                    Toast.makeText(ProfileActivity.this, "Đổi avatar thành công.", Toast.LENGTH_SHORT).show();
                }
                // Nếu không thành công, hiển thị thông báo lỗi
                else {
                    Toast.makeText(ProfileActivity.this, "Đã có lỗi xảy ra. Vui lòng thử lại sau.", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, "Đã có lỗi xảy ra. Vui lòng thử lại sau.", Toast.LENGTH_SHORT).show();
            }
        });
    }


}