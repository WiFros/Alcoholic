package com.hanul.alcoholic;

import java.util.HashMap;
import java.util.Map;

/* 리스트 뷰에 들어갈 내용 입력받기 위해 클래스 만들어둠 */
public class Post {
    private String key;
    private String uid;
    private String title;
    private String author;
    private String body;
    private int comments;
    private String date;
    private int starCount; // 좋아요
    private Map<String, Boolean> stars = new HashMap<>();
    private String profile;

    Post() {
    }

    Post(String name, String author, String body, int comments, String date) {
        this.title = name;
        this.author = author;
        this.body = body;
        this.comments = comments;
        this.date = date;
    }

    Post(String author, String body, int comments, String date) {
        this.author = author;
        this.body = body;
        this.comments = comments;
        this.date = date;
    }

    Post(String uid, String author, String title, String body) {
        this.uid = uid;
        this.author = author;
        this.title = title;
        this.body = body;
    }

    public String getProfile() {
        return profile;
    }
    public void setProfile(String profile) {
        this.profile = profile;
    }
    public int getStarCount() {
        return starCount;
    }
    public void setStarCount(int starCount) {
        this.starCount = starCount;
    }
    public Map<String, Boolean> getStars() {
        return stars;
    }
    public void setStars(Map<String, Boolean> stars) {
        this.stars = stars;
    }
    public String getUid() {
        return uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }
    public int getStar() {
        return starCount;
    }
    public void setStar(int star) {
        this.starCount = star;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String name) {
        this.title = name;
    }
    public String getAuthor() {
        return author;
    }
    public String getBody() {
        return body;
    }
    public int getComments() {
        return comments;
    }
    public String getDate() {
        return date;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public void setBody(String body) {
        this.body = body;
    }
    public void setComments(int comments) {
        this.comments = comments;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getKey() {return key;}
    public void setKey(String key) {this.key = key;}

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("key",key);
        result.put("uid", uid);
        result.put("author", author);
        result.put("title", title);
        result.put("body", body);
        result.put("starCount", starCount);
        result.put("stars", stars);
        result.put("date", date);
        result.put("profile", profile);
        return result;
    }
}
