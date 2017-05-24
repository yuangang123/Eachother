package com.example.eachother;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.CountCallback;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.SaveCallback;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import info.Liuyan;

public class Task_full_infomation extends BaseActivity implements View.OnClickListener{
    private TextView username;
    private TextView location;
    private TextView price;
    private CircleImageView circleImageView;
    private TextView task_information_zan_number;
    private TextView task_information_view_number;
    private TextView describe;

    private RecyclerView liuyanRecycle;
    public List<Liuyan> liuyanList=new ArrayList<>();

    public Button LiuyanButton;
    public Button DianzanButton;
    public Button chatFirst;
    public Button ReceiveTask;


    public LinearLayout commentInput;
    public EditText commentinputEditText;
    public Button commentButton;
    public  LiuyanArrayAdapter arrayAdapter;


    public String taskIdthis;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tash_full_infomation);

        username = (TextView)findViewById(R.id.task_information_name);
        location = (TextView)findViewById(R.id.tak_information_location);
        price = (TextView)findViewById(R.id.task_information_price);
        circleImageView = (CircleImageView)findViewById(R.id.task_information_image);
        task_information_zan_number = (TextView)findViewById(R.id.task_information_zan_number);
        task_information_view_number = (TextView)findViewById(R.id.task_information_view_number);
        describe = (TextView)findViewById(R.id.task_information_described_text);


        LiuyanButton= (Button)findViewById(R.id.task_information_liuyan_button);
        DianzanButton=(Button)findViewById(R.id.task_information_dianzan_button);
        chatFirst=(Button)findViewById(R.id.task_button_chat_first) ;
        ReceiveTask=(Button)findViewById(R.id.task_information_iwantit) ;


        commentInput = (LinearLayout)findViewById(R.id.comment_input);
        commentButton=(Button)findViewById(R.id.comment_button);
        commentinputEditText=(EditText)findViewById(R.id.comment_input_edittext);

        try{



            Intent intent = getIntent();
            final String taskId= intent.getStringExtra("taskId");
            taskIdthis = taskId;
            Log.d("taskId", "onCreate: "+taskId);

            initLiuyan();
            liuyanRecycle=(RecyclerView)findViewById(R.id.task_information_liuyan);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            liuyanRecycle.setLayoutManager(linearLayoutManager);
            arrayAdapter =new LiuyanArrayAdapter(liuyanList);
            liuyanRecycle.setAdapter(arrayAdapter);





            /**
             * 实现浏览的次数
             */
            final AVObject theTask = AVObject.createWithoutData("Task",taskId);
            theTask.put("tasView",0);
            theTask.saveInBackground(new SaveCallback() {
                @Override
                public void done(AVException e) {
                    theTask.increment("taskView");
                    theTask.setFetchWhenSave(true);
                    theTask.saveInBackground();
                }
            });


            AVQuery<AVObject> avQuery = new AVQuery<>("Task");
            avQuery.getInBackground(taskId, new GetCallback<AVObject>() {
                @Override
                public void done(AVObject avObject, AVException e) {
                    if (e==null){
                        String Username =avObject.getString("username");
                        String Location =avObject.getString("taskPushLocation");
                        String Price = avObject.getString("taskPrice");
                        String UserHeadImag = avObject.getAVFile("UserHeadImag").getUrl();
                        String Describe = avObject.getString("taskDescribe");
                        int taskView= (int)avObject.getNumber("taskView");
                        boolean isReceived =avObject.getBoolean("isReceived");

                        username.setText(Username);
                        location.setText(Location);
                        price.setText(Price);
                        Glide.with(Task_full_infomation.this).load(UserHeadImag).into(circleImageView);
                        describe.setText(Describe);
                        task_information_view_number.setText(taskView+"");
                        if (isReceived==true){
                            ReceiveTask.setBackgroundColor(Color.GRAY);
                            ReceiveTask.setClickable(false);
                        }

                    }else {
                        Toast.makeText(Task_full_infomation.this,"好像服务器端出现一点小毛病了，稍后再试",Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            });

            /**
             * 查询点赞的次数
             */

            AVQuery<AVObject> avQuery1 = new AVQuery<>("Dianzan");
            avQuery1.whereEqualTo("taskId",taskId);
            avQuery1.countInBackground(new CountCallback() {
                @Override
                public void done(int i, AVException e) {
                    task_information_zan_number.setText(i+"");
                }
            });


            LiuyanButton.setOnClickListener(this);
            DianzanButton.setOnClickListener(this);
            chatFirst.setOnClickListener(this);
            ReceiveTask.setOnClickListener(this);

        }catch (Exception e){
            e.printStackTrace();
        }





    }

    public void initLiuyan(){
        AVQuery<AVObject> avQuery = new AVQuery<>("Liuyan");
        avQuery.whereEqualTo("taskId",taskIdthis);
        avQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e==null){


                    for (AVObject object:list) {
                        Liuyan liuyan = new Liuyan();
                        liuyan.setLiuyancontent(object.getString("comment"));
                        liuyan.setLiuyaner(object.getString("pushUserName"));
                        liuyan.setLiuyanReceiver(object.getString("receiverUser"));
                        liuyanList.add(liuyan);
                    }
                }else {
                    Toast.makeText(Task_full_infomation.this,"留言加载好像出了一点问题。",Toast.LENGTH_SHORT).show();
                    e.printStackTrace();

                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.task_information_liuyan_button:
                liuyanfunc();
                break;
            case R.id.task_information_dianzan_button:
                dianzanfun();
                break;
            case R.id.task_button_chat_first:
                chatfun();
                break;
            case R.id.task_information_iwantit:
                iwantitfun();
                break;
        }
    }

    public void liuyanfunc(){
        commentInput.setVisibility(View.VISIBLE);
        commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commentInput.setVisibility(View.GONE);
                AVObject comment = new AVObject("Liuyan");
                String inputContent = commentinputEditText.getText().toString();
                comment.put("comment",inputContent);
                comment.put("pushUser",AVUser.getCurrentUser().getObjectId());
                comment.put("pushUserName",AVUser.getCurrentUser().getUsername());
                comment.put("taskId",taskIdthis);
                comment.put("receiverUser","");
                comment.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(AVException e) {
                        if (e==null){
                            Liuyan liuyan = new Liuyan();
                            liuyan.setLiuyancontent(commentinputEditText.getText().toString());
                            liuyan.setLiuyaner(AVUser.getCurrentUser().getUsername() );
                            liuyan.setLiuyanReceiver("");
                            liuyanList.add(liuyan);
                            arrayAdapter.notifyItemInserted(liuyanList.size()-1);
                        }else {
                            Toast.makeText(Task_full_infomation.this,"评论好像出了点问题",Toast.LENGTH_SHORT).show();
                            e.printStackTrace();

                        }
                    }
                });
            }
        });

    }

    public void dianzanfun(){
        AVQuery<AVObject> queryzanuser = new AVQuery<>("Dianzan");
        queryzanuser.whereEqualTo("DianzanUser",AVUser.getCurrentUser().get("objectId"));

        AVQuery<AVObject> queryzantask= new AVQuery<>("Dianzan");
        queryzantask.whereEqualTo("taskId",taskIdthis);

        AVQuery<AVObject> query = AVQuery.and(Arrays.asList(queryzanuser,queryzantask));

        query.countInBackground(new CountCallback() {
            @Override
            public void done(int i, AVException e) {
                if (e==null){
                    if (i==1){
                        Toast.makeText(Task_full_infomation.this,"亲你已经赞过该商品了，看看别的吧！",Toast.LENGTH_SHORT).show();
                    }
                    if (i==0){

                        AVObject dianzan = new AVObject("Dianzan");
                        dianzan.put("taskId",taskIdthis);
                        dianzan.put("DianzanUser",AVUser.getCurrentUser().getString("objectId"));
                        dianzan.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(AVException e) {
                                if (e==null){
                                    int dianzannumber = Integer.parseInt(task_information_zan_number.getText().toString());
                                    dianzannumber++;
                                    task_information_zan_number.setText(dianzannumber+"");
                                }
                                else {
                                    Toast.makeText(Task_full_infomation.this,"貌似出了一点错误。",Toast.LENGTH_SHORT).show();
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }else {
                    Toast.makeText(Task_full_infomation.this,"不好意思好像有点问题出现了！",Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });




    }

    public void chatfun(){
        Toast.makeText(Task_full_infomation.this,"你将跳转到聊天室，注意隐私防止上当受骗！",Toast.LENGTH_SHORT).show();
    }

    public void iwantitfun(){
        AVObject task = AVObject.createWithoutData("Task",taskIdthis);
        task.put("isReceived",true);
        task.saveInBackground();
        ReceiveTask.setBackgroundColor(Color.GRAY);
        ReceiveTask.setClickable(false);
    }

    class LiuyanArrayAdapter extends RecyclerView.Adapter<LiuyanArrayAdapter.ViewHoder> {

        public List<Liuyan> liuyanList = new ArrayList<>();

         class ViewHoder extends RecyclerView.ViewHolder{
            View liuyan;
            TextView liuyanuser;
            TextView liuyancontent;
             TextView liuyanReceiver;
             TextView To;

            public ViewHoder(View itemView) {
                super(itemView);
                this.liuyan= itemView;
                this.liuyanuser = (TextView) itemView.findViewById(R.id.liuyan_user_item);
                this.liuyancontent=(TextView)itemView.findViewById(R.id.liuyan_content_item);
                this.liuyanReceiver= (TextView)itemView.findViewById(R.id.liuyan_receiveruser_item);
                this.To= (TextView)itemView.findViewById(R.id.liuyan_user_to_user);
            }
        }

        public LiuyanArrayAdapter(List<Liuyan> liuyanList) {
            this.liuyanList = liuyanList;
        }

        @Override
        public ViewHoder onCreateViewHolder(final ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.liuyan_item,parent,false);
            final ViewHoder viewHoder = new ViewHoder(view);

            /**
             * 为留言中添加点击功能
             */

            viewHoder.liuyan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    commentInput.setVisibility(View.VISIBLE);
                    commentinputEditText.setText("");
                    commentinputEditText.setHint("@"+viewHoder.liuyanuser.getText().toString());
                    commentButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            commentInput.setVisibility(View.GONE);
                            AVObject comment = new AVObject("Liuyan");
                            String inputContent = commentinputEditText.getText().toString();
                            comment.put("comment",inputContent);
                            comment.put("pushUser",AVUser.getCurrentUser().getObjectId());
                            comment.put("pushUserName",AVUser.getCurrentUser().getUsername());
                            comment.put("taskId",taskIdthis);
                            comment.put("receiverUser",viewHoder.liuyanuser.getText().toString());
                            comment.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(AVException e) {
                                    if (e==null){
                                        Liuyan liuyan = new Liuyan();
                                        liuyan.setLiuyancontent(commentinputEditText.getText().toString());
                                        liuyan.setLiuyaner(AVUser.getCurrentUser().getUsername() );
                                        liuyan.setLiuyanReceiver("");
                                        liuyanList.add(liuyan);
                                        arrayAdapter.notifyItemInserted(liuyanList.size()-1);
                                    }else {
                                        Toast.makeText(Task_full_infomation.this,"评论好像出了点问题",Toast.LENGTH_SHORT).show();
                                        e.printStackTrace();

                                    }
                                }
                            });
                        }
                    });
                }
            });

            viewHoder.liuyanuser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(Task_full_infomation.this,"将会跳入用户详细界面，不过暂时没有开通。",Toast.LENGTH_SHORT).show();
                }
            });

            return viewHoder;
        }

        @Override
        public void onBindViewHolder(ViewHoder holder, int position) {
            Liuyan liuyan= liuyanList.get(position);
            holder.liuyanuser.setText(liuyan.getLiuyaner());

            if (!TextUtils.isEmpty(liuyan.getLiuyanReceiver())){
                holder.liuyanReceiver.setText(liuyan.getLiuyanReceiver());
                holder.To.setText(" 留言给 ");
                holder.liuyancontent.setText("@"+liuyan.getLiuyanReceiver()+" "+liuyan.getLiuyancontent());
            }
            else {
                holder.liuyancontent.setText(liuyan.getLiuyancontent());
            }


        }

        @Override
        public int getItemCount() {
//        return 0;
            return liuyanList.size();
        }
    }


}
