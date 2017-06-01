package info;

/**
 * Created by 袁刚 on 2017/5/31.
 */

public class MeseageUserReport {
//    public String imageUrl;
    public String Username;
    public String time;


    public MeseageUserReport(String username, String time) {
//        this.imageUrl = imageUrl;
        Username = username;
        this.time = time;
    }

//    public MeseageUserReport() {
//    }

//    public String getImageUrl() {
//        return imageUrl;
//    }

    public String getUsername() {
        return Username;
    }

    public String getTime() {
        return time;
    }

//    public void setImageUrl(String imageUrl) {
//        this.imageUrl = imageUrl;
//    }
//
//    public void setUsername(String username) {
//        Username = username;
//    }
//
//    public void setTime(String time) {
//        this.time = time;
//    }
}
