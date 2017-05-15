package info;

import android.net.Uri;

/**
 * Created by 袁刚 on 2017/5/15.
 */

public class TaskItem {
    private String title;
    private String price;
    private String pushLoation;
    private String time;
    private String circleImageViewURL;

    public TaskItem(String title, String price, String pushLoation, String time,String circleImageViewURL) {
        this.title = title;
        this.price = price;
        this.pushLoation = pushLoation;
        this.time = time;
        this.circleImageViewURL=circleImageViewURL;
    }

    public String getTitle() {
        return title;
    }

    public String getPrice() {
        return price;
    }

    public String getPushLoation() {
        return pushLoation;
    }

    public String getTime() {
        return time;
    }

    public String getCircleImageView() {
        return circleImageViewURL;
    }

}
