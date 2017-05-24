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
    private String taskid;

    public TaskItem(String title, String price, String pushLoation, String time,String circleImageViewURL,String taskId) {
        this.title = title;
        this.price = price;
        this.pushLoation = pushLoation;
        this.time = time;
        this.circleImageViewURL=circleImageViewURL;
        this.taskid = taskId;
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

    public String getTaskid() {
        return taskid;
    }
}
