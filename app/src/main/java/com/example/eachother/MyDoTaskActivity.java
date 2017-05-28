package com.example.eachother;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MyDoTaskActivity extends AppCompatActivity implements View.OnClickListener {

    public Button receivedFinished;
    public Button receivedUnFinished;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_do_task);

        receivedFinished = (Button)findViewById(R.id.my_received_finished);
        receivedUnFinished=(Button)findViewById(R.id.my_received_unfinished);

        receivedFinished.setOnClickListener(this);
        receivedUnFinished.setOnClickListener(this);

        replaceFragment(new MyReceivedTaskFinishedFragment());

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.my_received_finished:
                replaceFragment(new MyReceivedTaskFinishedFragment());
                break;
            case R.id.my_received_unfinished:
                replaceFragment(new MyReceivedTaskUnFinishedFragment());
                break;
            default:
                break;
        }
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.my_received_task_fragment,fragment);
        transaction.commit();
    }
}
