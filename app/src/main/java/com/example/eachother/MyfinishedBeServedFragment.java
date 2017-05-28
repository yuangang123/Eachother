package com.example.eachother;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import info.MyFinishedTask;
import info.MyFinishedTaskAdapter;
import info.MyPushedTaskItem;

/**
 * Created by 袁刚 on 2017/5/27.
 */

public class MyfinishedBeServedFragment extends Fragment {

    private MyFinishedTaskAdapter adapter;
    private List<MyFinishedTask> myFinishedTasks =new ArrayList<>();
    private RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.myfinishtask_fragment_beserved,container,false);

        inition();
        recyclerView = (RecyclerView)view.findViewById(R.id.beserved);
        LinearLayoutManager manager =new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        adapter = new MyFinishedTaskAdapter(myFinishedTasks);
        recyclerView.setAdapter(adapter);

        return view;
    }

    public void inition(){
        AVQuery<AVObject> avQuery = new AVQuery<>("Task");
        avQuery.whereEqualTo("isEnd",true);
        AVQuery<AVObject> avQuery1 = new AVQuery<>("Task");
        avQuery1.whereEqualTo("pushUser", AVUser.getCurrentUser());

        AVQuery<AVObject> avObjectAVQuery =AVQuery.and(Arrays.asList(avQuery,avQuery1));
        avObjectAVQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    for (AVObject avObject:list) {
                        String taskTitle = avObject.getString("taskTitle");
                        String price =  avObject.getNumber("taskPrice").toString();
                        String location = avObject.getString("taskPushLocation");
                        String receiveTime = avObject.getDate("receivedTime").toString();
                        MyFinishedTask myFinishedTask = new MyFinishedTask(taskTitle,price,location,receiveTime);
                        myFinishedTasks.add(myFinishedTask);
                    }
                    adapter.notifyDataSetChanged();

                }
                    else{
                        Toast.makeText(getContext(), "不好意思好像有点问题", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }

        });
    }
}
