package info;

/**
 * Created by 袁刚 on 2017/5/26.
 */

public class MyPushedTaskItem {
    private String title;
    private String location;
    private String price;
    private String time;

    public MyPushedTaskItem(String title, String location, String price, String time) {
        this.title = title;
        this.location = location;
        this.price = price;
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public String getLocation() {
        return location;
    }

    public String getPrice() {
        return price;
    }

    public String getTime() {
        return time;
    }
}
