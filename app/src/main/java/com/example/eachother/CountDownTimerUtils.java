package com.example.eachother;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

/**
 * Created by 袁刚 on 2017/5/12.
 */

public class CountDownTimerUtils extends CountDownTimer {
    private TextView mTextView;

    public CountDownTimerUtils(TextView textView,long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.mTextView  =  textView;
    }

    @Override
    public void onTick(long l) {
        mTextView.setClickable(false);
        mTextView.setText(l/1000+"秒");
        mTextView.setBackgroundResource(R.drawable.shape_vertify_bth_press);
        SpannableString spannableString = new SpannableString(mTextView.getText().toString());
        ForegroundColorSpan span = new ForegroundColorSpan(Color.RED);

        spannableString.setSpan(span,0,2, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        mTextView.setText(spannableString);

    }

    @Override
    public void onFinish() {
        mTextView.setText("重新获取");
        mTextView.setClickable(true);
        mTextView.setBackgroundResource(R.drawable.shape_vertify_bth_normal);
    }
}
