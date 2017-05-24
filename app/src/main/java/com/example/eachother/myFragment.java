package com.example.eachother;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.zip.Inflater;

/**
 * Created by 袁刚 on 2017/5/1.
 */

public class myFragment extends Fragment implements View.OnClickListener{


    public LinearLayout mypushed;
    public LinearLayout myfinished;
    public LinearLayout mydo;
    public LinearLayout myzan;
    public LinearLayout help;
    public LinearLayout setting;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view  = inflater.inflate(R.layout.fragment_my,container,false);

        Button forceOffline = (Button)view.findViewById(R.id.my_forceOffline_button);
        forceOffline.setOnClickListener(this);


        return view;

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.my_forceOffline_button:
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("亲，您确定要退出当前账号？");
                builder.setTitle("Eachother");
                builder.setCancelable(false);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ActivityCollector.finishAll();
                        Intent intent = new Intent(getContext(),LoginAndRegister.class);
                        String data = "FORCEOFFLINE";
                        intent.putExtra("foceoffline",data);
                        getActivity().startActivity(intent);
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //不做任何事情
                    }
                });
                builder.show();
                break;
            case
        }
    }
}
