package com.example.eachother;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SignUpCallback;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import de.hdodenhof.circleimageview.CircleImageView;


public class RegisterActivity extends BaseActivity implements View.OnClickListener {


    public final String TAG = "yuangang";


    public LinearLayout back;
    public Button register;
    public CircleImageView circleImageView;


    private Bitmap head;// 头像Bitmap
    private Uri imageUri;
    public EditText username;

    public EditText phonenumber;
    public EditText password;
    public EditText email;
    public EditText idCard;
    public EditText schoolname;
    public EditText schoolnumber;
    public ProgressBar progressBar;

    /**
     * 用来存储头像的文件
     */
    public byte[] imageBytes;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        back = (LinearLayout) findViewById(R.id.register_back_button);
        register = (Button) findViewById(R.id.register_conmmit_button);
        circleImageView = (CircleImageView) findViewById(R.id.register_circle_view);
        progressBar  = (ProgressBar)findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        back.setOnClickListener(this);
        register.setOnClickListener(this);
        circleImageView.setOnClickListener(this);


        /**

         * 为了获取用户注册时填入的相关信息*

         * @param savedInstanceState

         */

        phonenumber = (EditText) findViewById(R.id.register_phone_number);
        password = (EditText) findViewById(R.id.register_password);
        email = (EditText) findViewById(R.id.register_mail);
        idCard = (EditText) findViewById(R.id.register_id_card);
        schoolname = (EditText) findViewById(R.id.register_school);
        schoolnumber = (EditText) findViewById(R.id.register_studentId);
        username = (EditText)findViewById(R.id.register_user_name);

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



    public void registerconmmit() {

        /**
         * 调用接口注册用户信息
         */
        attemptSignUp();


    }

    private void attemptSignUp()
    {
        String usernameVar = username.getText().toString();
        final String phonevar = phonenumber.getText().toString();
        final String passwordVar  =password.getText().toString();
        String emailVar = email.getText().toString();
        String idCardVar = idCard.getText().toString();
        String schoolnameVar = schoolname.getText().toString();
        String schoolnumberVar = schoolnumber.getText().toString();



        AVUser user  = new AVUser();

        if (TextUtils.isEmpty(usernameVar)){
            username.setError("请填写用户名");
            username.setFocusable(true);
            return;
        }
        if(!isMobileNumberValid(phonevar)){
            phonenumber.setError("电话填写有错误，请仔细检查");
            phonenumber.setFocusable(true);
            return;
        }
        if (!isPasswordValid(passwordVar)){
            password.setError("密码填写有误,至少6位");
            password.setFocusable(true);
            return;
        }
        if (!emailValidation(emailVar)){
            email.setError("邮箱有错误，请检查");
            email.setFocusable(true);
            return;
        }
        if (!isIdCard(idCardVar)){
            idCard.setError("有效身份证18位");
            idCard.setFocusable(true);
            return;
        }
        if(TextUtils.isEmpty(schoolnameVar)){
            schoolname.setError("学校不能为空");
            schoolname.setFocusable(true);
            return;
        }
        if(TextUtils.isEmpty(schoolnumberVar)){
            schoolnumber.setError("学号不能为空");
            schoolnumber.setFocusable(true);
            return;
        }
        if(imageBytes==null){
            Toast.makeText(RegisterActivity.this,"请选择一张照片",Toast.LENGTH_SHORT).show();
            return;
        }

        user.setMobilePhoneNumber(phonevar);
        user.setEmail(emailVar);
        user.setPassword(passwordVar);
        user.setUsername(usernameVar);
        user.put("idCard",idCardVar);
        user.put("schoolname",schoolnameVar);
        user.put("schoolnumber",schoolnumberVar);
        user.put("image",new AVFile("userheadpic",imageBytes));

        progressBar.setVisibility(View.VISIBLE);


        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(AVException e) {
                if (e==null){
                    progressBar.setVisibility(View.GONE);

                    /**
                     * just test
                     */
                    ActivityCollector.finishAll();
                    Intent intent = new Intent(RegisterActivity.this,LoginAndRegister.class);
                    intent.putExtra("account",phonevar);
                    intent.putExtra("password",passwordVar);
                    intent.putExtra("comefromregister",true);
                    intent.putExtra("userheadimg",imageBytes);
                    startActivity(intent);
                }
                else {
                    Log.d("SignUp", e.getLocalizedMessage());
                }
            }
        });




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
                try{
                    if (data != null) {
                        Bundle extras = data.getExtras();
                        head = extras.getParcelable("data");
                        imageBytes =Bitmap2Bytes(head);
                        circleImageView.setImageBitmap(head);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);

    }


    /**
     * 把Bitmap转Byte
     */
    public static byte[] Bitmap2Bytes(Bitmap bm){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
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
    /**
     * 将图片输入变成二进制流
     */
    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, len);
        }
        return byteArrayOutputStream.toByteArray();
    }

    /**
     * 验证手机号是否符合大陆的标准格式
     * @param mobiles
     * @return
     */
    public static boolean isMobileNumberValid(String mobiles) {
        Pattern p = Pattern.compile("^1[3|4|5|7|8][0-9]{9}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 验证手机号是否标准格式
     * @param email
     * @return
     */
    public static boolean emailValidation(String email) {
        String regex = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
        Log.d("123", "emailValidation: "+email.matches(regex));
        return email.matches(regex);
    }

    /**
     * 推荐密码至少长度为 6 位
     * @param password
     * @return
     */
    private static boolean isPasswordValid(String password) {
        return password.length() >= 6;
    }
    private static  boolean isIdCard(String idCard) {
        return idCard.length() == 18;
    }

}