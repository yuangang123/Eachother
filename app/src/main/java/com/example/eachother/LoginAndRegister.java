package com.example.eachother;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.RequestPasswordResetCallback;
import com.avos.avoscloud.SaveCallback;
import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class LoginAndRegister extends BaseActivity implements View.OnClickListener{

    public EditText loginAccount ;
    public EditText loginPassword;
    public Button loginButton;
    public TextView canNotLogin;
    public TextView newUser;
    public CircleImageView circleImageView;
    public SharedPreferences prefs;
    public  boolean isForce;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_and_register);

    /**
        // 测试 SDK 是否正常工作的代码
        AVObject testObject = new AVObject("TestObject");
        testObject.put("words","Hello World!");
        testObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if(e == null){
                    Log.d("saved","success!");
                }
            }
        });
            */

        loginAccount = (EditText)findViewById(R.id.login_account);
        loginPassword = (EditText)findViewById(R.id.login_password);
        loginButton = (Button)findViewById(R.id.login_button);
        canNotLogin =(TextView)findViewById(R.id.login_cannotlogin_textView);
        newUser = (TextView)findViewById(R.id.login_new_user);
        circleImageView = (CircleImageView)findViewById(R.id.login_circle_image);
        isForce = false;


        Intent intent = getIntent();
        String data = intent.getStringExtra("foceoffline");
        Log.d("comefromregister", "onCreate: "+data);
        if (data!=null){
            isForce=true;
        }

        String accountfromregister = intent.getStringExtra("account");
        String passwordfromregister = intent.getStringExtra("password");
        byte [] userheadimg = intent.getByteArrayExtra("userheadimg");
        boolean comefromregister = intent.getBooleanExtra("comefromregister",false);
        Log.d("comefromregister", "onCreate: ");
        if (comefromregister==true){
            Log.d("comefromregister", "onRestart: "+accountfromregister);
            Log.d("comefromregister", "onRestart: "+passwordfromregister);
            loginAccount.setText(accountfromregister);
            loginPassword.setText(passwordfromregister);
            circleImageView.setImageBitmap(getPicFromBytes(userheadimg,null));
        }



        loginButton.setOnClickListener(this);
        newUser.setOnClickListener(this);
        canNotLogin.setOnClickListener(this);


        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        final String loginAccountPres = prefs.getString("loginAccount",null);
        if (comefromregister==false&&loginAccountPres!=null){
            loginAccount.setText(loginAccountPres);
            String loginPasswordPres = prefs.getString("loginPassword",null);
            loginPassword.setText(loginPasswordPres);
            if (isForce==false){
                attemLogin();
            }
        }


        /**
         * 还确实一个部署头像的功能实现
         */loginAccount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b){
                    /**
                     * 得到焦点事件
                     */
                    Log.d("focus", "onFocusChange: "+"loginaccount 获得到焦点");
                }
                else {
                    /**
                     * 失去焦点事件
                     */
                    Log.d("focus", "onFocusChange: "+"loginaccount 失去焦点");


                    if (TextUtils.isEmpty(loginAccount.getText().toString().trim())){
                        /**
                         * 填写为空不需要
                         */
                        Log.d("focus", "onFocusChange: "+"loginAccount没有内容不要改预置图片");
                    }else {
                        Log.d("focus", "onFocusChange: "+"loginAccount有内容要改预置图片");

                        if (RegisterActivity.isMobileNumberValid(loginAccount.getText().toString().trim())){
                            AVQuery<AVObject> avQuery = new AVQuery<>("_User");
                            avQuery.whereEqualTo("mobilePhoneNumber",loginAccount.getText().toString().trim());
                            Log.d("focus", "onFocusChange: "+"是电话");
                            avQuery.findInBackground(new FindCallback<AVObject>() {
                                @Override
                                public void done(List<AVObject> list, AVException e) {
                                    if (e==null){
                                        Log.d("focus", "done: "+"list判断");
                                        if (list!=null){
                                            /**
                                             * glide图片加载
                                             */
                                            Log.d("focus", "done: "+"glide图片加载");
                                            Glide.with(LoginAndRegister.this).load(list.get(0).getAVFile("image").getUrl()).into(circleImageView);
                                        }
                                    }else {
                                        Log.d("focus", "done: "+"fuck");
                                        Log.d("focus", "done: "+e.getLocalizedMessage());
                                    }
                                }
                            });
                        }else {
                            AVQuery<AVObject> avQuery = new AVQuery<>("User");
                            avQuery.whereEqualTo("username",loginAccount.getText().toString().trim());
                            avQuery.findInBackground(new FindCallback<AVObject>() {
                                @Override
                                public void done(List<AVObject> list, AVException e) {
                                    if (e==null){
                                        if (list!=null){
                                            /**
                                             * 同样glide图片加载
                                             */
                                            Log.d("focus", "done: "+"glide图片加载");
                                            Glide.with(LoginAndRegister.this).load(list.get(0).getAVFile("image").getUrl()).into(circleImageView);
                                        }
                                    }else {
                                        Log.d("loginfindheadimh", "done: "+e.getLocalizedMessage());
                                    }
                                }
                            });

                        }
                    }

                }
            }
        });

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login_button:
                attemLogin();
                break;
            case R.id.login_new_user:
                Intent intent2 = new Intent(LoginAndRegister.this,PhoneCertificationOfRegister.class);
                intent2.putExtra("forWhat","newUser");
                startActivity(intent2);
                break;
            case R.id.login_cannotlogin_textView:
                ShowChoise();
                break;
            default:
                break;
        }
    }

    private void ShowChoise()
    {

        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
                final android.app.AlertDialog dialog = builder.create();
                View view = View.inflate(this, R.layout.dialog_select_photo, null);
                final TextView findbackpassword = (TextView) view.findViewById(R.id.tv_select_gallery);
                TextView phonelogin = (TextView) view.findViewById(R.id.tv_select_camera);
                findbackpassword.setText("找回密码");
                phonelogin.setText("手机登陆");
                findbackpassword.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                /**
                 * 使用验证码找回sdk
                 */
//                Toast.makeText(LoginAndRegister.this,"找回密码",Toast.LENGTH_SHORT).show();
                dialog.dismiss();

               Intent intent = new Intent(LoginAndRegister.this,FindBackPasswordActivity.class);
               startActivity(intent);


            }
        });
        phonelogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 * 使用验证码登陆接口sdk
                 */
                dialog.dismiss();
                Intent intent =new Intent(LoginAndRegister.this,PhoneCertificationOfRegister.class);
                intent.putExtra("forWhat","phoneLogin");
                startActivity(intent);
            }
        });
        builder.setView(view);
        builder.show();
    }

    /**
     * @param //将字节数组转换为ImageView可调用的Bitmap对象
     * @param bytes
     * @param opts
     * @return Bitmap
     */
    public static Bitmap getPicFromBytes(byte[] bytes, BitmapFactory.Options opts) {
        if (bytes != null)
            if (opts != null)
                return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, opts);
            else
                return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        return null;
    }

    public void attemLogin(){
        if (TextUtils.isEmpty(loginAccount.getText())){
            loginAccount.setError("账户不能为空");
            loginAccount.setFocusable(true);
            return;
        }
        if (TextUtils.isEmpty(loginPassword.getText())){
            loginPassword.setError("密码不能为空");
            loginPassword.setFocusable(true);
            return;
        }

        if (RegisterActivity.isMobileNumberValid(loginAccount.getText().toString().trim())){
            AVUser.loginByMobilePhoneNumberInBackground(loginAccount.getText().toString().trim(), loginPassword.getText().toString().trim(), new LogInCallback<AVUser>() {
                @Override
                public void done(AVUser avUser, AVException e) {
                    if (e==null){
                        /**
                         * 缓存正确的账号
                         */
                        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(LoginAndRegister.this).edit();
                        editor.putString("loginAccount",loginAccount.getText().toString());
                        editor.putString("loginPassword",loginPassword.getText().toString());
                        editor.apply();

                        LoginAndRegister.this.finish();
                        startActivity(new Intent(LoginAndRegister.this,MainActivity.class));
                    }
                    else {
                        Toast.makeText(LoginAndRegister.this,"请检查帐号密码是否错误",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else {

            AVUser.logInInBackground(loginAccount.getText().toString().trim(), loginPassword.getText().toString().trim(), new LogInCallback<AVUser>() {
                @Override
                public void done(AVUser avUser, AVException e) {
                    if (e==null){
                        /**
                         * 缓存正确的账号
                         */
                        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(LoginAndRegister.this).edit();
                        editor.putString("loginAccount",loginAccount.getText().toString());
                        editor.putString("loginPassword",loginPassword.getText().toString());
                        editor.apply();

                        LoginAndRegister.this.finish();
                        startActivity(new Intent(LoginAndRegister.this,MainActivity.class));
                    }else {
                        Toast.makeText(LoginAndRegister.this,"请检查帐号密码是否错误",Toast.LENGTH_SHORT).show();
                    }

                }
            });

        }


    }
}
