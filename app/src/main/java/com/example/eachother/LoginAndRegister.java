package com.example.eachother;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
        loginAccount = (EditText)findViewById(R.id.login_account);
        loginPassword = (EditText)findViewById(R.id.login_password);
        loginButton = (Button)findViewById(R.id.login_button);
        canNotLogin =(TextView)findViewById(R.id.login_cannotlogin_textView);
        newUser = (TextView)findViewById(R.id.login_new_user);
        circleImageView = (CircleImageView)findViewById(R.id.login_circle_image);
        isForce = false;


        Intent intent = getIntent();
        String data = intent.getStringExtra("foceoffline");
        if (data!=null){
            isForce=true;
        }


        loginButton.setOnClickListener(this);
        newUser.setOnClickListener(this);
        canNotLogin.setOnClickListener(this);


        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String loginAccountPres = prefs.getString("loginAccount",null);
        if (loginAccountPres!=null){
            loginAccount.setText(loginAccountPres);
            String loginPasswordPres = prefs.getString("loginPassword",null);
            loginPassword.setText(loginPasswordPres);
            if (isForce==false){

                if (loginAccount.getText().toString().equals("12345")&&loginPassword.getText().toString().equals("12345")){

                    Intent intenttoMain = new Intent(LoginAndRegister.this,MainActivity.class);
                    startActivity(intenttoMain);
                    finish();
                }
            }
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login_button:
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(LoginAndRegister.this).edit();
                editor.putString("loginAccount",loginAccount.getText().toString());
                editor.putString("loginPassword",loginPassword.getText().toString());
                editor.apply();
                if (loginAccount.getText().toString().equals("12345")&&loginPassword.getText().toString().equals("12345")){

                    Intent intent = new Intent(LoginAndRegister.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(LoginAndRegister.this,"账号或密码错误",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.login_new_user:
                Intent intent2 = new Intent(LoginAndRegister.this,RegisterActivity.class);
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

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        final android.app.AlertDialog dialog = builder.create();
        View view = View.inflate(this, R.layout.dialog_select_photo, null);
        TextView findbackpassword = (TextView) view.findViewById(R.id.tv_select_gallery);
        TextView phonelogin = (TextView) view.findViewById(R.id.tv_select_camera);
        findbackpassword.setText("找回密码");
        phonelogin.setText("手机登陆");
        findbackpassword.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginAndRegister.this,"找回密码",Toast.LENGTH_SHORT).show();
            }
        });
        phonelogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginAndRegister.this,"手机登录",Toast.LENGTH_SHORT).show();
            }
        });
        builder.setView(view);
        builder.show();
    }
}
