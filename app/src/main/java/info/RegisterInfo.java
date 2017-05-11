package info;

import java.io.Serializable;

/**
 * Created by 袁刚 on 2017/5/10.
 */

public class RegisterInfo implements Serializable{
    public  String phonenumber;
    public String password;
    public String email;
    public String idcard;
    public String schoolname;
    public String schoolnumber;

    public RegisterInfo(String phonenumber, String password, String email, String idcard, String schoolname, String schoolnumber) {
        this.phonenumber = phonenumber;
        this.password = password;
        this.email = email;
        this.idcard = idcard;
        this.schoolname = schoolname;
        this.schoolnumber = schoolnumber;
    }

    public RegisterInfo() {
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public void setSchoolname(String schoolname) {
        this.schoolname = schoolname;
    }

    public void setSchoolnumber(String schoolnumber) {
        this.schoolnumber = schoolnumber;
    }
}
