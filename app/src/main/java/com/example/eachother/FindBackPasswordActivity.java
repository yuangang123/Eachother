package com.example.eachother;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.RequestPasswordResetCallback;

public class FindBackPasswordActivity extends BaseActivity implements View.OnClickListener{

    public EditText email;
    public LinearLayout backButton;
    public Button sureButton;
    public ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_back_password);
        email = (EditText)findViewById(R.id.email_verify_emailText);
        backButton = (LinearLayout) findViewById(R.id.email_verify_back);
        sureButton=(Button)findViewById(R.id.email_verify_sure);
        progressBar =(ProgressBar)findViewById(R.id.progressBar);

        backButton.setOnClickListener(this);
        sureButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.email_verify_back:
                finish();
                Intent intent = new Intent(FindBackPasswordActivity.this,LoginAndRegister.class);
                startActivity(intent);
                break;
            case R.id.email_verify_sure:
                if (RegisterActivity.emailValidation(email.getText().toString().trim())){
                    AVUser.requestPasswordResetInBackground(email.getText().toString().trim(), new RequestPasswordResetCallback() {
                        @Override
                        public void done(AVException e) {
                            if (e == null) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(FindBackPasswordActivity.this);
                                builder.setTitle("邮箱找回密码");
                                builder.setMessage("亲，你好，我们已经向你的邮箱发送了一封邮件用于修改密码，请注意查收哦");
                                builder.setPositiveButton("知道啦", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        /**
                                         * 不需要进行操作
                                         */
                                        finish();
                                        Intent intent = new Intent(FindBackPasswordActivity.this,LoginAndRegister.class);
                                        startActivity(intent);
                                    }
                                });
                                builder.show();
                            } else {
                                Toast.makeText(FindBackPasswordActivity.this,"哦哦，不好意思出错了！",Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }
                        }
                    });
                }else{
                    email.setError("邮箱有错误，请检查你的邮箱填写");
                }
        }
    }
}
