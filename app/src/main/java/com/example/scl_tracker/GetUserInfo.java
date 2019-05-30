package com.example.scl_tracker;

public class GetUserInfo {
    String url;
    String phone;

    public GetUserInfo(String url, String phone) {
        this.url = url;
        this.phone = phone;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
