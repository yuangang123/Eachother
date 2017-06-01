package com.example.eachother;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import info.MeseageUserReport;
import util.RecyclerViewDivider;

/**
 * Created by 袁刚 on 2017/5/1.
 */

public class messageFragment extends Fragment {

    public String TAG = "messageFragment";


    class MessageUserListAdapter extends RecyclerView.Adapter<MessageUserListAdapter.ViewHolder> {

        public List<MeseageUserReport> meseageUserReports;

         class ViewHolder extends RecyclerView.ViewHolder{
//            CircleImageView circleImageView;
            TextView name;
            TextView time;
            View view;

            public ViewHolder(View itemView) {
                super(itemView);
//                circleImageView = (CircleImageView)itemView.findViewById(R.id.circleImageView);
                name = (TextView)itemView.findViewById(R.id.message_item_name);
                time = (TextView)itemView.findViewById(R.id.message_item_time);
                view = itemView;

            }
        }

        public MessageUserListAdapter(List<MeseageUserReport> meseageUserReports) {
            this.meseageUserReports = meseageUserReports;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
//        return null;
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_items_views,parent,false);
            final ViewHolder viewHolder = new ViewHolder(view);
            viewHolder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /**
                     * 跳转到聊天室
                     */

                    Intent intent = new Intent(getActivity(),Message_chat_activity.class);
                    intent.putExtra("ChatUserName",viewHolder.name.getText().toString());
                    startActivity(intent);
                }
            });
            return viewHolder;
        }


        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            MeseageUserReport meseageUserReport = meseageUserReports.get(position);
            holder.name.setText(meseageUserReport.getUsername());
            holder.time.setText(meseageUserReport.getTime());
//            Glide.with(getContext()).load(meseageUserReport.getImageUrl()).into(holder.circleImageView);
        }

        @Override
        public int getItemCount() {
            return meseageUserReports.size();
        }
    }

    public List<MeseageUserReport> meseageUserReports= new ArrayList<>();
    public RecyclerView recyclerView;
    public MessageUserListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_message,container,false);



        inition();
        recyclerView = (RecyclerView)view.findViewById(R.id.fragment_message_recycle_view);
        recyclerView.addItemDecoration(new RecyclerViewDivider(getContext(), LinearLayoutManager.VERTICAL));
//        recyclerView.addItemDecoration(new RecycleViewDivider(getContext(), LinearLayoutManager.VERTICAL));
        LinearLayoutManager manager= new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        adapter = new MessageUserListAdapter(meseageUserReports);
        recyclerView.setAdapter(adapter);

        return view;
    }

    public void inition(){
         /**
         * 查询conversation，初始化消息用户的列表
         */

        Log.d(TAG, "inition: "+"初始化消息列表");

        String string = AVUser.getCurrentUser().getUsername();
        Log.d(TAG, "inition: "+string);

        AVQuery<AVObject> avObjectAVQuery = new AVQuery<>("_Conversation");
        avObjectAVQuery.whereContainsAll("m",Arrays.asList(string));

        avObjectAVQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e==null){

                    Log.d(TAG, "done: "+list.size());
                    for (int i=0;i<list.size();i++){
                        String time = list.get(i).getDate("lm").toString();
                        /**
                         * 想办法获取另一个用户名
                         *
                         */
                        String chatUserName="";
                        try{
                            JSONArray jsonArray = list.get(i).getJSONArray("m");

                            Log.d(TAG, "done: jsonArray"+jsonArray.length());

                            for (int j=0;j<jsonArray.length();j++){
                                if (!AVUser.getCurrentUser().getUsername().equals(jsonArray.get(j))){
                                    chatUserName = jsonArray.get(j)+"";
                                    break;
                                }
                            }

                        }catch (JSONException e1)
                        {
                            e1.printStackTrace();
                        }

//                        /**
//                         * 获取用户头像
//                         */
//                        final String imageUrl = "";
//
//                        AVQuery<AVObject> avQuery = new AVQuery<AVObject>("_User");
//                        avQuery.whereEqualTo("username",chatUserName);
//                        avQuery.findInBackground(new FindCallback<AVObject>() {
//                            @Override
//                            public void done(List<AVObject> list, AVException e) {
//                                for (int i=0;i<list.size();i++){
//
//                                    imageUrl = list.get(i).getAVFile("image").getUrl();
//                                }
//                            }
//                        });


                        MeseageUserReport meseageUserReport = new MeseageUserReport(chatUserName,time);
                        meseageUserReports.add(meseageUserReport);
                        adapter.notifyDataSetChanged();

                    }
                }else {
                    Log.d(TAG, "done: e!=null");
                    e.printStackTrace();
                }
            }
        });

    }

}
