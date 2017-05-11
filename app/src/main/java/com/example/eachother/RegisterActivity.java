package com.example.eachother;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import util.SendToServer;
import info.RegisterInfo;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    public final String TAG = "yuangang";

    public Button back;
    public Button register;
    public CircleImageView circleImageView;

    private Bitmap head;// 头像Bitmap
    private Uri imageUri;

    public EditText phonenumber;
    public EditText password;
    public EditText email;
    public EditText idCard;
    public EditText schoolname;
    public EditText schoolnumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        back = (Button) findViewById(R.id.register_back_button);
        register = (Button) findViewById(R.id.register_conmmit_button);
        circleImageView = (CircleImageView) findViewById(R.id.register_circle_view);
        back.setOnClickListener(this);
        register.setOnClickListener(this);
        circleImageView.setOnClickListener(this);

        /**
         * 为了获取用户注册时填入的相关信息*
         * @param savedInstanceState
         */
         phonenumber =(EditText)findViewById(R.id.register_phone_number);
         password =(EditText)findViewById(R.id.register_password);
         email =(EditText)findViewById(R.id.register_mail);
         idCard =(EditText)findViewById(R.id.register_id_card);
         schoolname =(EditText)findViewById(R.id.register_school);
         schoolnumber = (EditText)findViewById(R.id.register_studentId);
    }





    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_back_button:
                backToLogin();
                break;
            case R.id.register_conmmit_button:
                Log.d(TAG, "单机了按钮");
                registerconmmit();
                break;
            case R.id.register_circle_view:
                showTypeDialog();
                break;
        }
    }

    public void backToLogin() {
        Intent intent = new Intent(RegisterActivity.this, LoginAndRegister.class);
        startActivity(intent);
        finish();
    }

    public String editTextToString(EditText editText){
        return editText.getText().toString();
    }

    public void registerconmmit() {
        Log.d(TAG, "registerconmmit方法激活");
        RegisterInfo registerInfo = new RegisterInfo(editTextToString(phonenumber),editTextToString(password),editTextToString(email),editTextToString(idCard),editTextToString(schoolname),editTextToString(schoolnumber));
        SendToServer.send("113.140.11.125",10000,registerInfo);
    }

    private void showTypeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        View view = View.inflate(this, R.layout.dialog_select_photo, null);
        TextView tv_select_gallery = (TextView) view.findViewById(R.id.tv_select_gallery);
        TextView tv_select_camera = (TextView) view.findViewById(R.id.tv_select_camera);
        tv_select_gallery.setOnClickListener(new View.OnClickListener() {// 在相册中选取
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Intent.ACTION_PICK, null);
                intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent1, 1);
                dialog.dismiss();
            }
        });
        tv_select_camera.setOnClickListener(new View.OnClickListener() {// 调用照相机
            @Override
            public void onClick(View v) {
                File outputImage = new File(Environment.getExternalStorageDirectory(), "head.jpg");
                try {
                    if (outputImage.exists()) {
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (Build.VERSION.SDK_INT < 24) {
                    imageUri = Uri.fromFile(outputImage);
                } else {
                    imageUri = FileProvider.getUriForFile(RegisterActivity.this, "com.example.eachother.fileprovider", outputImage);
                    /**
                     * 暂时,android7.0以上系统暂时会爆出问题,程序崩溃,暂时无法解决.
                     */
                }
                // 启动相机程序
                Intent intent2 = new Intent("android.media.action.IMAGE_CAPTURE");
                intent2.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent2, 2);
                dialog.dismiss();
            }

        });
        dialog.setView(view);
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    cropPhoto(data.getData());// 裁剪图片
                }

                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    File temp = new File(Environment.getExternalStorageDirectory() + "/head.jpg");
                    if (Build.VERSION.SDK_INT < 24) {
                        cropPhoto(Uri.fromFile(temp));
                    } else {
                        cropPhoto(FileProvider.getUriForFile(RegisterActivity.this, "com.example.eachother.fileprovider", temp));
                    }
                }
                break;
            case 3:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    head = extras.getParcelable("data");
                    circleImageView.setImageBitmap(head);
                }
                break;
            default:
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 调用系统的裁剪功能
     *
     * @param uri
     */
    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3);
    }

}