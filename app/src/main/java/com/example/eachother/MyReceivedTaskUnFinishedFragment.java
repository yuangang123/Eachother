package com.example.eachother;

import android.graphics.drawable.RippleDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import info.MyPushedTaskItem;
import info.MyReceivedTaskAdapter;
import info.MyReceivedTaskItem;

/**
 * Created by 袁刚 on 2017/5/27.
 */

public class MyReceivedTaskUnFinishedFragment extends Fragment {

    public MyReceivedTaskAdapter adapter;
    public RecyclerView recyclerView;
    public List<MyReceivedTaskItem> myReceivedTaskItems=new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view =inflater.inflate(R.layout.my_received_unfinished,container,false);

        inition();

        recyclerView= (RecyclerView)view.findViewById(R.id.my_received_unfinished_recycle);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        adapter =new MyReceivedTaskAdapter(myReceivedTaskItems);
        recyclerView.setAdapter(adapter);
        return  view;
    }

    public void  inition(){
        AVQuery<AVObject> avQuery = new AVQuery<>("Task");
        avQuery.whereEqualTo("receivedUser", AVUser.getCurrentUser().getObjectId());

        AVQuery<AVObject> avQuery1 = new AVQuery<>("Task");
        avQuery1.whereEqualTo("isEnd",false);

        AVQuery<AVObject> avObjectAVQuery = AVQuery.and(Arrays.asList(avQuery,avQuery1));
        avObjectAVQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e==null){
                    for (AVObject avObject:list) {
                        String taskTitle = avObject.getString("taskTitle");
                        String price =  avObject.getNumber("taskPrice").toString();
                        String location = avObject.getString("taskPushLocation");
                        String receiveTime = avObject.getDate("receivedTime").toString();
//                        String UserHeadImag = avObject.getAVFile("UserHeadImag").getUrl();
                        MyReceivedTaskItem myPushedTaskItem = new MyReceivedTaskItem(taskTitle,price,location,receiveTime);
                        myReceivedTaskItems.add(myPushedTaskItem);
                    }
                    adapter.notifyDataSetChanged();
                }else {
                    Toast.makeText(getContext(),"好像出现了一点问题",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
