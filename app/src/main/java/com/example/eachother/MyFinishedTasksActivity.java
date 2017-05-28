package com.example.eachother;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;


public class MyFinishedTasksActivity extends AppCompatActivity implements View.OnClickListener {
    public FrameLayout frameLayout;
    public Button BeServed;
    public Button Served;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_finished_tasks);

        frameLayout = (FrameLayout)findViewById(R.id.my_finished_task_recycle);
        BeServed = (Button)findViewById(R.id.beitigongfuwu);
        Served = (Button)findViewById(R.id.tigongfuwu);

        BeServed.setOnClickListener(this);
        Served.setOnClickListener(this);

        replaceFragment(new MyfinishedBeServedFragment());

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.beitigongfuwu:
                replaceFragment(new MyfinishedBeServedFragment());
                break;
            case R.id.tigongfuwu:
                replaceFragment(new MyfinishedServedFragment());
                break;
            default:
                break;
        }
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.my_finished_task_recycle,fragment);
        transaction.commit();
    }

}
