package com.example.eachother;

import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVMobilePhoneVerifyCallback;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.RequestMobileCodeCallback;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneCertificationOfRegister extends BaseActivity {

    public EditText phonenumber;
    public EditText yanzhengmanumber;
    public Button next;
    public TextView get;
    public LinearLayout back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_certification_of_register);

        phonenumber = (EditText) findViewById(R.id.PhoneCertificationOfRegister_phone_number);
        yanzhengmanumber = (EditText) findViewById(R.id.PhoneCertificationOfRegister_yanzhengma_number);
        next = (Button) findViewById(R.id.PhoneCertificationOfRegister_next);
        get = (TextView) findViewById(R.id.PhoneCertificationOfRegister_get_yanzhengma);
        back = (LinearLayout) findViewById(R.id.PhoneCertificationOfRegister_back);

        get.setBackgroundResource(R.drawable.shape_vertify_bth_normal);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PhoneCertificationOfRegister.this, LoginAndRegister.class);
                startActivity(intent);
                finish();
            }
        });

        Intent intent =  getIntent();
        final String forwhat = intent.getStringExtra("forWhat");
        Log.d("forwhat", "onCreate: "+forwhat);
        Log.d("forwhat", "onCreate:+forwhat.equals(\"newUser\") "+forwhat.equals("newUser"));
        Log.d("forwhat", "onCreate:+forwhat.equals(\"phoneLogin\") "+forwhat.equals("phoneLogin"));

//        /**
//         * test ,and delete after
//         */
//
//        get.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                CountDownTimer countDownTimer = new CountDownTimerUtils(get,60000,1000);
//                countDownTimer.start();
//            }
//        });

        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 * 使用sdk调用接口获取验证码
                 */
                final String phone = phonenumber.getText().toString().trim();
                if (!isMobileNumberValid(phone)) {
                    phonenumber.setError("手机号码格式有误，请确认");
                    return;
                }

                AVOSCloud.requestSMSCodeInBackground(phone, new RequestMobileCodeCallback() {
                    @Override
                    public void done(AVException e) {
                        if (e == null) {
                            /**
                             * 表示正确没有问题
                             */
                            CountDownTimer countDownTimer = new CountDownTimerUtils(get,60000,1000);
                            countDownTimer.start();
                            yanzhengmanumber.setFocusable(true);
                        } else {
                            Log.e("Home.OperationVerify", e.getMessage());
                        }
                    }
                });


            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 * 跳到详细注册界面，或者短信验证码登陆
                 */
                if (forwhat.equals("phoneLogin")==true){

                    String smsCode = yanzhengmanumber.getText().toString();
                    Log.d("yanzhengmanumber", "onClick: "+yanzhengmanumber.getText().toString());
                if (isSMSCodeValid(smsCode)) {
                    AVOSCloud.verifyCodeInBackground(smsCode,phonenumber.getText().toString().trim(), new AVMobilePhoneVerifyCallback() {
                        @Override
                        public void done(AVException e) {
                            if (e != null) {
                                yanzhengmanumber.setError("验证码错误，请重新输入！");
                            } else {
                                ActivityCollector.finishAll();
                                Intent intent = new Intent(PhoneCertificationOfRegister.this, MainActivity.class);
                                startActivity(intent);
                            }
                        }
                    });
                }
                }
                if (forwhat.equals("newUser")==true){

                String smsCode = yanzhengmanumber.getText().toString();
                    Log.d("yanzhengmanumber", "onClick: "+yanzhengmanumber.getText().toString());
                if (isSMSCodeValid(smsCode)) {
                    AVOSCloud.verifyCodeInBackground(smsCode, phonenumber.getText().toString().trim(),new AVMobilePhoneVerifyCallback() {
                        @Override
                        public void done(AVException e) {
                            if (e != null) {
                                yanzhengmanumber.setError("验证码错误，请重新输入！");
                            } else {
                                Intent intent = new Intent(PhoneCertificationOfRegister.this, RegisterActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
                }
                }

//                Intent intent = new Intent(PhoneCertificationOfRegister.this, RegisterActivity.class);
//                startActivity(intent);
//                finish();

            }
        });
    }


    /**
     * 验证手机号是否符合大陆的标准格式
     * @param mobiles
     * @return
     */
    public  boolean isMobileNumberValid(String mobiles) {
        Pattern p = Pattern.compile("^1[3|4|5|7|8][0-9]{9}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 判断验证码是否为 6 位纯数字，LeanCloud 统一的验证码均为 6  位纯数字。
     * @param smsCode
     * @return
     */
    public  boolean isSMSCodeValid(String smsCode) {
        String regex = "^\\d{6}$";
        return smsCode.matches(regex);
    }
}
