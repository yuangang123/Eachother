package com.example.eachother;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;


/**
 * Created by 袁刚 on 2017/5/1.
 */

public class BottomTool extends LinearLayout{
    public BottomTool(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.bottomtool,this);
    }
}
