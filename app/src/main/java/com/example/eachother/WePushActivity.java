package com.example.eachother;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

public class WePushActivity extends AppCompatActivity implements View.OnClickListener{

    public FrameLayout frameLayout;
    public Button finished;
    public Button unfinished;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_we_push);

        frameLayout = (FrameLayout)findViewById(R.id.we_pushed_frameLayout);
        finished = (Button)findViewById(R.id.we_push_finished);
        unfinished = (Button)findViewById(R.id.we_push_unfinished);

        finished.setOnClickListener(this);
        unfinished.setOnClickListener(this);

        replaceFragment(new WePushFragmentFinished());

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.we_push_finished:
                replaceFragment(new WePushFragmentFinished());
                break;
            case R.id.we_push_unfinished:
                replaceFragment(new WePushFragmentUnFinished());
                break;
            default:
                break;
        }
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.we_pushed_frameLayout,fragment);
        transaction.commit();
    }
}
