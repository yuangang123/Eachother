package com.example.eachother;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class HelpActivity extends AppCompatActivity implements View.OnClickListener{

    public TextView phone;
    public TextView email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);


        phone = (TextView)findViewById(R.id.feedback_phone);
        email = (TextView)findViewById(R.id.feedback_phone);

        email.setOnClickListener(this);
        phone.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.feedback_phone:
                if (ContextCompat.checkSelfPermission(HelpActivity.this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(HelpActivity.this,new String[]{Manifest.permission.CALL_PHONE},1);
                }else {
                    call();
                }
                break;
            case R.id.feedback_email:
                email();
                break;
        }
    }

    private void call(){
        try{
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:18173366517"));
            startActivity(intent);
        }catch (SecurityException e){
            e.printStackTrace();
        }
    }

    private void email(){
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:975131172@qq.com"));
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:
                if (grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    call();
                }
                else {
                    Toast.makeText(this,"没有解开绳子，奴家办不到！",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
}
