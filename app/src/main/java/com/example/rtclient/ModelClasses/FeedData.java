package com.example.rtclient.ModelClasses;

public class FeedData {

    //13

    private int id ;
    private long posted_timestamp ;
    private String category ;
    private String country ;
    private String pushId ;
    private String ink_url ;
    private String location ;
    private String media_type ;
    private String news_description ;
    private String shareLink ;
    private String state ;
    private String subtitle ;
    private String title ;

    public FeedData() {
    }


    public FeedData(int id, long posted_timestamp, String category, String country, String pushId, String ink_url, String location, String media_type, String news_description, String shareLink, String state, String subtitle, String title) {
        this.id = id;
        this.posted_timestamp = posted_timestamp;
        this.category = category;
        this.country = country;
        this.pushId = pushId;
        this.ink_url = ink_url;
        this.location = location;
        this.media_type = media_type;
        this.news_description = news_description;
        this.shareLink = shareLink;
        this.state = state;
        this.subtitle = subtitle;
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInk_url() {
        return ink_url;
    }

    public void setInk_url(String ink_url) {
        this.ink_url = ink_url;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMedia_type() {
        return media_type;
    }

    public void setMedia_type(String media_type) {
        this.media_type = media_type;
    }

    public String getNews_description() {
        return news_description;
    }

    public void setNews_description(String news_description) {
        this.news_description = news_description;
    }

    public long getPosted_timestamp() {
        return posted_timestamp;
    }

    public void setPosted_timestamp(long posted_timestamp) {
        this.posted_timestamp = posted_timestamp;
    }

    public String getShareLink() {
        return shareLink;
    }

    public void setShareLink(String shareLink) {
        this.shareLink = shareLink;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }
}
