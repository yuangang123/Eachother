package com.example.eachother;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class LeiBie extends BaseActivity {

//    public LinearLayout backToPush;
    public ListView listView;
    public ArrayAdapter<String> adapter;
    public String[] strings={"跑腿服务","技能服务","学习服务","牛逼服务","专业服务","奇葩服务","祖传贴膜","跳蚤市场","项目组队","一起开趴","郊游组队","交友脱单","其他"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lei_bie);

//        backToPush = (LinearLayout) findViewById(R.id.push_back_button);
        listView = (ListView)findViewById(R.id.push_leiBie_listView);

//        backToPush.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//                Intent intent = new Intent(LeiBie.this,MainActivity.class);
//                startActivity(intent);
//            }
//        });
        adapter = new ArrayAdapter<>(LeiBie.this,android.R.layout.simple_list_item_1,strings);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String string = strings[i];
                Intent intent = new Intent();
                intent.putExtra("leibie_return",string);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
}
