package com.example.eom_rfid.bean.response;

/**
 * Description:
 * Author:bwang
 * Date:2020/12/25 16:29
 */
public class LoginResponse {

    /**
     * token : 0878cf0bbf394de186d6dbfa52d54c8f
     * expire : 43199995
     */

    private String token;
    private String expire;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getExpire() {
        return expire;
    }

    public void setExpire(String expire) {
        this.expire = expire;
    }
}
