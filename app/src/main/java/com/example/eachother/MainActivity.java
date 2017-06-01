package com.example.eachother;


import android.app.Notification;
import android.app.NotificationManager;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.Button;

import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.AVIMMessageHandler;
import com.avos.avoscloud.im.v2.AVIMMessageManager;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;

import info.Msg;

public class MainActivity extends BaseActivity implements View.OnClickListener {



    /**
     * 接收到聊天信息
     */
    public  class CustomMessageHandler extends AVIMMessageHandler {
        //接收到消息后的处理逻辑
        @Override
        public void onMessage(AVIMMessage message, AVIMConversation conversation, AVIMClient client){
            if(message instanceof AVIMTextMessage){
//                Log.d("Tom & Jerry",((AVIMTextMessage)message).getText());
                /**
                 * 如果不在聊天室界面就使用通知栏提醒跳转到聊天室
                 *
                 * 也可以使用服务来刷新消息界面
                 */

                NotificationManager manager =(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                Notification notification = new NotificationCompat.Builder(MainActivity.this)
                        .setContentTitle("Eachother")
                        .setContentText(((AVIMTextMessage)message).getText())
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.drawable.logo)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.logo))
                        .setAutoCancel(true)
                        .setPriority(android.support.v7.app.NotificationCompat.PRIORITY_MAX)
                        .build();
                manager.notify(1,notification);
            }
        }

        public void onMessageReceipt(AVIMMessage message,AVIMConversation conversation,AVIMClient client){

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * 注册 开始时刻监听接受信息
         */
        AVIMMessageManager.registerDefaultMessageHandler(new MainActivity.CustomMessageHandler());
        jerryReceiveMsgFromTom();


        Button task = (Button)findViewById(R.id.task_button);
        Button push = (Button)findViewById(R.id.push_button);
        Button message = (Button)findViewById(R.id.message_button);
        Button my = (Button)findViewById(R.id.my_button);
        task.setOnClickListener(this);
        push.setOnClickListener(this);
        message.setOnClickListener(this);
        my.setOnClickListener(this);
        replaceFragment(new taskFragment());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.task_button:
//                Toast.makeText(this,"you touch me",Toast.LENGTH_SHORT).show();
                replaceFragment(new taskFragment());
                break;
            case R.id.push_button:
//                Toast.makeText(this,"you touch me",Toast.LENGTH_SHORT).show();
                replaceFragment(new pushFragment());
                break;
            case R.id.message_button:
//                Toast.makeText(this,"you touch me",Toast.LENGTH_SHORT).show();
                replaceFragment(new messageFragment());
                break;
            case R.id.my_button:
//                Toast.makeText(this,"you touch me",Toast.LENGTH_SHORT).show();
                replaceFragment(new myFragment());
                break;
    }
}
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_layout,fragment);
        transaction.commit();
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

    }
