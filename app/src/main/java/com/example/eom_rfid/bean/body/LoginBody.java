package com.example.eom_rfid.bean.body;

/**
 * Description:
 * Author:bwang
 * Date:2020/12/4 15:10
 */
public class LoginBody {

    private String username;

    private String password;

    private String deviceType = "RFIF";

    public LoginBody(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUserName() {
        return username;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
