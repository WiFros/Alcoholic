package com.hanul.alcoholic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Favorite_view {
    private String uid; // 고유키
    private String body; // 내용
    private ArrayList<String> ToReplys; // 답글 리스트

    Favorite_view(){}

    Favorite_view(
            String uid,
            String body
            ){
        this.uid = uid;
        this.body = body;
    }

    public String getUid() {return uid;}
    public void setUid(String uid) {this.uid = uid;}


    public String getBody() {return body;}

    public void setBody(String body) {this.body = body;}


    public ArrayList<String> getToReplys() {return ToReplys;}

    public void setToReplys(ArrayList<String> toReplys) {ToReplys = toReplys;}
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("body", body);
        return result;
    }
}
