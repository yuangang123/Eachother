package com.example.eachother;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.GetCallback;

import java.util.ArrayList;
import java.util.List;

import info.MyZanTaskAdapter;
import info.MyZanTaskItem;

public class MyZanActivity extends AppCompatActivity {

    public MyZanTaskAdapter adapter;
    public List<MyZanTaskItem> myZanTaskItems = new ArrayList<>();
    public RecyclerView recyclerView;
    public List<String> listoftaskId=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_zan);

        inition();
        recyclerView = (RecyclerView)findViewById(R.id.myzantasks_recyclView);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        adapter= new MyZanTaskAdapter(myZanTaskItems);
        recyclerView.setAdapter(adapter);
    }

    public void inition(){
        AVQuery<AVObject> avQuery = new AVQuery<>("Dianzan");
        avQuery.whereEqualTo("DianzanUser", AVUser.getCurrentUser().getObjectId());
        avQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {

                Log.d("dianzan", "done: "+list.size());

                if (e==null){
                    for (AVObject a:list) {
                        String s = a.getString("taskId");
                        Log.d("dianzan", "done: "+s);

                        AVObject task = AVObject.createWithoutData("Task",s);
                        task.fetchInBackground(new GetCallback<AVObject>() {
                            @Override
                            public void done(AVObject avObject, AVException e) {
                                if (e==null){

                                    Log.d("dianzan", "done: "+(avObject==null));

                                    String taskTitle = avObject.getString("taskTitle");
                                    String price =  avObject.getNumber("taskPrice").toString();
                                    String location = avObject.getString("taskPushLocation");
                                    String receiveTime = avObject.getDate("createdAt").toString();

                                    Log.d("dianzan", "done: "+taskTitle+price+location+receiveTime);

                                    MyZanTaskItem myZanTaskItem = new MyZanTaskItem(taskTitle,price,location,receiveTime);
                                    myZanTaskItems.add(myZanTaskItem);
                                    adapter.notifyDataSetChanged();
                                }else {
                                    e.printStackTrace();
                                }

                            }
                        });
                    }

                }else {
                    Toast.makeText(MyZanActivity.this,"不好意思好像出了一点问题",Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });

    }
}
