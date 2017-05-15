package com.example.eachother;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;

import java.util.ArrayList;
import java.util.List;

import info.TaskArrayAdapter;
import info.TaskItem;

/**
 * Created by 袁刚 on 2017/5/1.
 */

public class taskFragment extends Fragment {


    public static Context context;
    private List<TaskItem> taskItemList = new ArrayList<>();
    private TaskArrayAdapter arrayAdapter;
    public ProgressBar progressBar;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_task,container,false);
        context = getActivity().getApplicationContext();

        progressBar= (ProgressBar)view.findViewById(R.id.task_progressbar);
        progressBar.setVisibility(View.VISIBLE);

        initTaskItem();
//        Log.d("task", "onCreateView: "+taskItemList.size());

//        arrayAdapter = new TaskArrayAdapter(getContext(),R.layout.task_item_view,taskItemList);
//        ListView listView = (ListView)view.findViewById(R.id.task_listView);
//        listView.setAdapter(arrayAdapter);

        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.task_recycleView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        arrayAdapter =new TaskArrayAdapter(taskItemList);
        recyclerView.setAdapter(arrayAdapter);


        return view;
    }

    private void initTaskItem(){
        final AVQuery<AVObject> avQuery = new AVQuery<>("Task");
        avQuery.whereEqualTo("isEnd",false);
        avQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e==null){
                    for (AVObject avObject: list) {

                        String taskTitle = avObject.getString("taskTitle");
                        String price =  avObject.getNumber("taskPrice").toString();
                        String location = avObject.getString("taskPushLocation");
                        String pushTime = avObject.getDate("updatedAt").toString();
                        String UserHeadImag = avObject.getAVFile("UserHeadImag").getUrl();

                        Log.d("task", "done: "+taskTitle+price+location+pushTime);

                        TaskItem taskItem = new TaskItem(taskTitle,price,location,pushTime,UserHeadImag);
                        taskItemList.add(taskItem);
                        arrayAdapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                }else {
                    e.printStackTrace();
                }
            }
        });
    }

}
