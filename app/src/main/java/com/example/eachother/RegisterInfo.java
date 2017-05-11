package com.example.eachother;

import java.io.Serializable;

/**
 * Created by 袁刚 on 2017/5/9.
 */

public class RegisterInfo implements Serializable {
    public String phoneNumber;
    public String passWord;
    public String email;
    public String idCard;
    public String school;
    public String schoolNumber;

    public RegisterInfo(String phoneNumber, String passWord, String email, String idCard, String school, String schoolNumber) {
        this.phoneNumber = phoneNumber;
        this.passWord = passWord;
        this.email = email;
        this.idCard = idCard;
        this.school = school;
        this.schoolNumber = schoolNumber;
    }

}
