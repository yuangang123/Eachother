package info;

import java.io.Serializable;

/**
 * Created by 袁刚 on 2017/5/10.
 */

public class LoginInfo implements Serializable{
    public String acount;
    public String password;

    public LoginInfo() {
    }

    public LoginInfo(String acount, String password) {
        this.acount = acount;
        this.password = password;
    }

    public void setAcount(String acount) {
        this.acount = acount;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
