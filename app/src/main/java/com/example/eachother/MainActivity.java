package com.example.eachother;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button task = (Button)findViewById(R.id.task_button);
        Button push = (Button)findViewById(R.id.push_button);
        Button message = (Button)findViewById(R.id.message_button);
        Button my = (Button)findViewById(R.id.my_button);
        task.setOnClickListener(this);
        push.setOnClickListener(this);
        message.setOnClickListener(this);
        my.setOnClickListener(this);
        replaceFragment(new taskFragment());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.task_button:
//                Toast.makeText(this,"you touch me",Toast.LENGTH_SHORT).show();
                replaceFragment(new taskFragment());
                break;
            case R.id.push_button:
//                Toast.makeText(this,"you touch me",Toast.LENGTH_SHORT).show();
                replaceFragment(new pushFragment());
                break;
            case R.id.message_button:
//                Toast.makeText(this,"you touch me",Toast.LENGTH_SHORT).show();
                replaceFragment(new messageFragment());
                break;
            case R.id.my_button:
//                Toast.makeText(this,"you touch me",Toast.LENGTH_SHORT).show();
                replaceFragment(new myFragment());
                break;
    }
}
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_layout,fragment);
        transaction.commit();
    }

    }
