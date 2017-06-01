package com.example.eachother;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.AVIMMessageHandler;
import com.avos.avoscloud.im.v2.AVIMMessageManager;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCreatedCallback;
import com.avos.avoscloud.im.v2.callback.AVIMMessagesQueryCallback;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import info.Msg;
import info.MsgAdapter;

public class Message_chat_activity extends BaseActivity {


    public String   TAG = "messagefragment";


    private List<Msg> msgList = new ArrayList<>();

    private EditText inputtext;
    private Button send;
    private RecyclerView recyclerView;
    private MsgAdapter msgAdapter;
    public String ChatToUserName;
    public TextView chattitle;

    public String publicAvimConversationid;

    /**
     * 接收到聊天信息
     */
    public  class CustomMessageHandler extends AVIMMessageHandler {
        //接收到消息后的处理逻辑
        @Override
        public void onMessage(AVIMMessage message, AVIMConversation conversation, AVIMClient client){
            if(message instanceof AVIMTextMessage){
//                Log.d("Tom & Jerry",((AVIMTextMessage)message).getText());
                Msg msg = new Msg(((AVIMTextMessage)message).getText(),Msg.TYPE_RECEIVED);
                msgList.add(msg);
                msgAdapter.notifyItemInserted(msgList.size()-1);
                recyclerView.scrollToPosition(msgList.size()-1);
            }
        }

        public void onMessageReceipt(AVIMMessage message,AVIMConversation conversation,AVIMClient client){

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_chat_activity);

        Intent intent = getIntent();
        ChatToUserName = intent.getStringExtra("ChatUserName");
        /**
         * 注册 开始时刻监听接受信息
         */
        AVIMMessageManager.registerDefaultMessageHandler(new Message_chat_activity.CustomMessageHandler());
        jerryReceiveMsgFromTom();

        /**
         * 设置聊天界面的顶部聊天的人的名称
         */
        chattitle = (TextView)findViewById(R.id.chat_user_name);
        chattitle.setText(ChatToUserName);


        getConverstionID();

        inputtext = (EditText) findViewById(R.id.message_chat_input_editText);
        send = (Button)findViewById(R.id.message_send_button);
        recyclerView =(RecyclerView)findViewById(R.id.message_chat_recycleView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        msgAdapter = new MsgAdapter(msgList);
        recyclerView.setAdapter(msgAdapter);

        send.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String content = inputtext.getText().toString();
                if(!"".equals(content)){

                    sendMessageToJerryFromTom();
                }

            }
        });


        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        /**
         * 初始化历史聊天记录
         */

        initMsgs();

        }


    /**
     * 发送信息到对方
     */
    public void sendMessageToJerryFromTom() {
        // Tom 用自己的名字作为clientId，获取AVIMClient对象实例
        AVIMClient tom = AVIMClient.getInstance(AVUser.getCurrentUser().getUsername());
        // 与服务器连接
        tom.open(new AVIMClientCallback() {
            @Override
            public void done(AVIMClient client, AVIMException e) {
                if (e == null) {
                    // 创建与Jerry之间的对话
                    client.createConversation(Arrays.asList(ChatToUserName), AVUser.getCurrentUser().getUsername()+" & "+ChatToUserName, null,false,true,
                            new AVIMConversationCreatedCallback() {

                                @Override
                                public void done(AVIMConversation conversation, AVIMException e) {
                                    if (e == null) {
                                        AVIMTextMessage msg = new AVIMTextMessage();
                                        msg.setText(inputtext.getText().toString());
                                        // 发送消息
                                        conversation.sendMessage(msg, new AVIMConversationCallback() {

                                            @Override
                                            public void done(AVIMException e) {
                                                if (e == null) {
                                                    Msg msg = new Msg(inputtext.getText().toString(),Msg.TYPE_SEND);
                                                    msgList.add(msg);
                                                    msgAdapter.notifyItemInserted(msgList.size()-1);
                                                    recyclerView.scrollToPosition(msgList.size()-1);
                                                    inputtext.setText("");
                                                    Log.d("Tom & Jerry", "发送成功！");
                                                }else {
                                                    e.printStackTrace();
                                                }
                                            }
                                        });
                                    }
                                    else {
                                        e.printStackTrace();
                                    }
                                }
                            });
                }
                else {
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     * 接受信息
     */
    public void jerryReceiveMsgFromTom(){
        //Jerry登录
        AVIMClient jerry = AVIMClient.getInstance(AVUser.getCurrentUser().getUsername());
        jerry.open(new AVIMClientCallback(){

            @Override
            public void done(AVIMClient client,AVIMException e){
                if(e==null){
                    //登录成功后的逻辑
                }
            }
        });
    }
    /**
     * 初始化聊天界面，聊天的历史纪录，最长1000条
     */

    public void initMsgs(){

        Log.d(TAG, "initMsgs: "+"聊天历史纪录");

        AVIMClient tom = AVIMClient.getInstance(AVUser.getCurrentUser().getUsername());
        tom.open(new AVIMClientCallback() {

            @Override
            public void done(AVIMClient avimClient, AVIMException e) {
                if (e==null){
                    /**
                     * 如何获取聊天记录的问题了
                     */
                    AVIMConversation conversation = avimClient.getConversation(publicAvimConversationid);
                    final int limit=1000;
                    conversation.queryMessages(limit, new AVIMMessagesQueryCallback() {
                        @Override
                        public void done(List<AVIMMessage> list, AVIMException e) {
                            if (e==null){
                                for (AVIMMessage m:list){

                                    Log.d(TAG, "done: "+list.size());

                                    try{
                                        JSONObject jsonObject = new JSONObject(m.getContent());
                                        int type =1;
                                        if (!AVUser.getCurrentUser().getUsername().equals(m.getFrom())){
                                            type=0;
                                        }
                                        String content = jsonObject.get("_lctext")+m.getFrom();
                                        Msg msg= new Msg(content,type);
                                        msgList.add(msg);
                                        msgAdapter.notifyItemInserted(msgList.size()-1);
                                        recyclerView.scrollToPosition(msgList.size()-1);

                                    }catch (org.json.JSONException e1){
                                        e1.printStackTrace();
                                    }
                                }
                            }else {
                                Log.d(TAG, "done: 获取记录"+"e!=null");
                            }
                        }
                    });
                }else {
                    Log.d(TAG, "done:最外面这一个 "+"e!=null");
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 获取会话的id
     */
    public void getConverstionID(){

        String user1 = AVUser.getCurrentUser().getUsername();
        String user2 = ChatToUserName;
        AVQuery<AVObject> avObjectAVQuery = new AVQuery<>("_Conversation");
        avObjectAVQuery.whereContainsAll("m",Arrays.asList(user1,user2));
        avObjectAVQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                for (int i=0;i<list.size();i++){
                    publicAvimConversationid= list.get(i).getObjectId();
                    Log.d(TAG, "done: "+publicAvimConversationid);
                }
            }
        });
    }
}

