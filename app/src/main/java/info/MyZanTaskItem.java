package info;

/**
 * Created by 袁刚 on 2017/5/28.
 */

public class MyZanTaskItem {
    private String title;
    private String price;
    private String location;
    private String time;

    public MyZanTaskItem(String title, String price, String location, String time) {
        this.title = title;
        this.price = price;
        this.location = location;
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public String getPrice() {
        return price;
    }

    public String getLocation() {
        return location;
    }

    public String getTime() {
        return time;
    }
}
