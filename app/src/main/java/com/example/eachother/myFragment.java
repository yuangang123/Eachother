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
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.feedback.FeedbackAgent;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.Inflater;

import de.hdodenhof.circleimageview.CircleImageView;

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
    public TextView MyName;
    public TextView earnMoney;
    public CircleImageView circleImageView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view  = inflater.inflate(R.layout.fragment_my,container,false);
        Button forceOffline = (Button)view.findViewById(R.id.my_forceOffline_button);
        forceOffline.setOnClickListener(this);

        mypushed = (LinearLayout)view.findViewById(R.id.we_pushed_linearLayout);
        myfinished = (LinearLayout)view.findViewById(R.id.we_finished_linearLayout);
        mydo = (LinearLayout)view.findViewById(R.id.we_wedo_linearLayout);
        myzan = (LinearLayout)view.findViewById(R.id.we_zaned_linearLayout);
        help = (LinearLayout)view.findViewById(R.id.we_question_linearLayout);
        setting = (LinearLayout)view.findViewById(R.id.we_setting_linearLayout);

        MyName= (TextView)view.findViewById(R.id.my_name_text);
        earnMoney = (TextView)view.findViewById(R.id.earnMoney);
        circleImageView = (CircleImageView)view.findViewById(R.id.my_head_image);

        inition();


        mypushed.setOnClickListener(this);
        myfinished.setOnClickListener(this);
        mydo.setOnClickListener(this);
        myzan.setOnClickListener(this);
        help.setOnClickListener(this);
        setting.setOnClickListener(this);


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
            case R.id.we_pushed_linearLayout:
                Intent intent =new Intent(getActivity(),WePushActivity.class);
                getActivity().startActivity(intent);
                break;
            case R.id.we_finished_linearLayout:
                Intent intent1 =new Intent(getActivity(),MyFinishedTasksActivity.class);
                getActivity().startActivity(intent1);
                break;
            case R.id.we_wedo_linearLayout:
                Intent intent2 = new Intent(getActivity(),MyDoTaskActivity.class);
                getActivity().startActivity(intent2);
                break;
            case R.id.we_zaned_linearLayout:
                Intent intent3 = new Intent(getActivity(),MyZanActivity.class);
                getActivity().startActivity(intent3);
                break;
            case R.id.we_question_linearLayout:
                Intent intent4 = new Intent(getActivity(),HelpActivity.class);
                getActivity().startActivity(intent4);
//                FeedbackAgent agent = new FeedbackAgent(getContext());
//                agent.startDefaultThreadActivity();
                break;
            case R.id.we_setting_linearLayout:
                Intent intent5= new Intent(getActivity(),Setting.class);
                getActivity().startActivity(intent5);
                break;

        }
    }
    public void inition(){
        MyName.setText(AVUser.getCurrentUser().getUsername());
        Glide.with(this).load(AVUser.getCurrentUser().getAVFile("image").getUrl()).into(circleImageView);

        AVQuery<AVObject> avQuery = new AVQuery<>("Task");
        avQuery.whereEqualTo("receivedUser",AVUser.getCurrentUser().getObjectId());

        AVQuery<AVObject> avQuery1= new AVQuery<>("Task");
        avQuery1.whereEqualTo("isEnd",true);

        AVQuery<AVObject> avQuery2 = AVQuery.and(Arrays.asList(avQuery,avQuery1));
        avQuery2.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e==null){
                    List<Double> list1= new ArrayList<>();
                    for (AVObject avObject: list) {
                        list1.add(Double.parseDouble(avObject.getNumber("taskPrice").toString()));
                    }
                    int TotalMoney=0;
                    for (Double a: list1) {
                        TotalMoney+=a;
                    }
                    earnMoney.setText("在EachOther赚了 "+TotalMoney+" 元");

                }else {
                    Toast.makeText(getActivity(),"不好意思好像出现了一点问题！",Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            }
        });

    }
}
