package com.example.eachother;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 袁刚 on 2017/5/1.
 */

public class pushFragment extends Fragment {

    public EditText title ;
    public EditText describe;
    public TextView locationtext;
    public EditText price;
    public RelativeLayout leibie;
    public TextView leibietext;
    public Button pushButton;
    public Button locationButton;

    /**
     高德地图
     */
    public AMapLocationClient mapLocationClient;
    public double Latitude;
    public double Longitude;

    @Nullable
    @Override
    public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        /**
         * 高德地图
         */

        mapLocationClient= new AMapLocationClient(getActivity().getApplicationContext());
        AMapLocationListener mapLocationListener = new myLocationListener();
        mapLocationClient.setLocationListener(mapLocationListener);

        /**
         * 连续或者一次定位
         */
        AMapLocationClientOption mapLocationClientOption= new AMapLocationClientOption();
        /**
         * 设置为高精度
         */
        mapLocationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mapLocationClientOption.setOnceLocationLatest(true);//获取最近3s内精度最高的一次定位结果
        //设置是否返回地址信息（默认返回地址信息）
        mapLocationClientOption.setNeedAddress(true);
        mapLocationClient.setLocationOption(mapLocationClientOption);


        View view = inflater.inflate(R.layout.fragment_push,container,false);
        title = (EditText)view.findViewById(R.id.push_title);
        describe=(EditText)view.findViewById(R.id.push_info);
        locationtext=(TextView)view.findViewById(R.id.push_location_text);
        price=(EditText)view.findViewById(R.id.push_price);
        leibietext=(TextView)view.findViewById(R.id.push_leiBie_textview);
        pushButton=(Button)view.findViewById(R.id.push_sure_button);
        leibie = (RelativeLayout) view.findViewById(R.id.push_leiBie_RelativeLayout);

        List<String> permissionList = new ArrayList<>();

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()){
            String [] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(getActivity(),permissions,1);
        }
        else {
            //启动定位
            Log.d("current", "onCreateView: "+"启动定位");
            mapLocationClient.startLocation();
        }

        /**
         * 获取类别分类
         */
        leibie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),LeiBie.class);
                startActivityForResult(intent,1);
            }
        });

        pushButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(title.getText().toString())){
                    title.setError("标题可以简单，但是不能够没有哦");
                    return;
                }
                if (TextUtils.isEmpty(describe.getText().toString())){
                    describe.setError("服务描述不能为空哦，不然你让别人帮你干什么呢？");
                    return;
                }
                if (TextUtils.isEmpty(locationtext.getText().toString())){
                    locationtext.setError("位置有问题，是不是没有网络了");
                    return;
                }
                if (TextUtils.isEmpty(price.getText().toString())){
                    price.setError("好歹你也填一个0吧！给个面子咯");
                    return;
                }
                if (leibietext.getText().toString().equals("请选择分类")){
                    Toast.makeText(getContext(),"选一个类别吧，老大",Toast.LENGTH_SHORT).show();
                    return;
                }


                final ProgressBar progressBar = (ProgressBar)getActivity().findViewById(R.id.push_progressbar);
                progressBar.setVisibility(View.VISIBLE);

                Log.d("current", "onClick: "+ AVUser.getCurrentUser().getUsername());
                AVObject task = new AVObject("Task");
                task.put("latitude",Latitude);
                task.put("longitude",Longitude);
                task.put("pushUser",AVUser.getCurrentUser());
                task.put("taskDescribe",describe.getText().toString());
                task.put("taskLeibie",leibietext.getText().toString());
                task.put("taskPrice",Double.parseDouble(price.getText().toString()));
                task.put("taskPushLocation",locationtext.getText().toString());
                task.put("taskTitle",title.getText().toString());
                task.put("UserHeadImag",AVUser.getCurrentUser().getAVFile("image"));
                task.put("username",AVUser.getCurrentUser().getString("username"));
                task.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(AVException e) {
                        if (e==null){
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getContext(),"亲，你的服务小的已经发送了！",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getContext(),"不好意思，遇到了一些错误了",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1:
                if (resultCode==getActivity().RESULT_OK){
                    String returndata = data.getStringExtra("leibie_return");
                    leibietext.setText(returndata);
                }
                break;
            default:
                break;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:
                if (grantResults.length>0){
                    for (int result:
                         grantResults) {
                        if (result!=PackageManager.PERMISSION_GRANTED){
                            Toast.makeText(getActivity(),"小主啊，需要给我权限我才能帮你定位啊！",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    mapLocationClient.startLocation();
                }
                else {
                    Toast.makeText(getActivity(),"发生未知错误",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    public class myLocationListener implements AMapLocationListener{
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (aMapLocation != null) {
                if (aMapLocation.getErrorCode() == 0) {
                    //可在其中解析amapLocation获取相应内容。
                    Longitude = aMapLocation.getLongitude();
                    Latitude = aMapLocation.getLatitude();
                    StringBuilder currentLocation = new StringBuilder();
                    currentLocation.append(aMapLocation.getProvince()+aMapLocation.getCity()+aMapLocation.getDistrict()+aMapLocation.getStreet()+aMapLocation.getStreetNum());
                    locationtext.setText(currentLocation);
                    Log.d("current", "onLocationChanged: "+currentLocation);
                    Log.d("current", "onLocationChanged: "+Latitude);
                    Log.d("current", "onLocationChanged: "+Longitude);
                    Log.d("current", "onLocationChanged: "+aMapLocation.getLocationType());
                    Log.d("current", "onLocationChanged: "+aMapLocation.getAccuracy());
                    Log.d("current", "onLocationChanged: "+aMapLocation.getStreetNum());
                    Log.d("current", "onLocationChanged: "+aMapLocation.getBuildingId());
                    Log.d("current", "onLocationChanged: "+aMapLocation.getFloor());
                }else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    Log.d("current","location Error, ErrCode:"
                            + aMapLocation.getErrorCode() + ", errInfo:"
                            + aMapLocation.getErrorInfo());
                }
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mapLocationClient.onDestroy();
    }
}
