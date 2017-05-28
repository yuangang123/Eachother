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

import info.MyPushedTaskItem;
import info.MypushedTaskAdpter;

/**
 * Created by 袁刚 on 2017/5/27.
 */

public class WePushFragmentUnFinished extends Fragment {

    public MypushedTaskAdpter adpter;
    public List<MyPushedTaskItem> myPushedTaskItems = new ArrayList<>();
    public RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.my_push_fragment_unfinished,container,false);
        recyclerView = (RecyclerView)view.findViewById(R.id.my_push_fragment_unfinished) ;

        inition();
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        adpter = new MypushedTaskAdpter(myPushedTaskItems);
        recyclerView.setAdapter(adpter);
        return view;
    }

    public void inition(){
        AVQuery<AVObject> avQuery = new AVQuery<>("Task");
        avQuery.whereEqualTo("pushUser", AVUser.getCurrentUser());

        AVQuery<AVObject> avQuery1 = new AVQuery<>("Task");
        avQuery1.whereEqualTo("isReceived",false);

        AVQuery<AVObject>avObjectAVQuery = AVQuery.and(Arrays.asList(avQuery,avQuery1));

        avObjectAVQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e==null){
                    for (AVObject avObject:list) {
                        String taskTitle = avObject.getString("taskTitle");
                        String price =  avObject.getNumber("taskPrice").toString();
                        String location = avObject.getString("taskPushLocation");
                        String receiveTime = avObject.getDate("createdAt").toString();
//                        String UserHeadImag = avObject.getAVFile("UserHeadImag").getUrl();
                        MyPushedTaskItem myPushedTaskItem = new MyPushedTaskItem(taskTitle,location,price,receiveTime);
                        myPushedTaskItems.add(myPushedTaskItem);
                    }
                    adpter.notifyDataSetChanged();
                }else{
                    Toast.makeText(getContext(),"不好意思，出现点问题",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
