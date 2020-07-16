package com.example.rtclient.ModelClasses;

public class UserData {

    private String userUid ;
    private String timeStamp ;

    public UserData() {
    }

    public UserData(String userUid, String timeStamp) {
        this.userUid = userUid;
        this.timeStamp = timeStamp;
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
