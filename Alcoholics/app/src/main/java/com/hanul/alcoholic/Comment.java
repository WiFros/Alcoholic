package com.hanul.alcoholic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Comment {
    private String uid; // 고유키
    private String boardId; //댓글이 속한 게시물 id
    private String body; // 내용
    private String author; //작성자
    private String date; // 작성일
    private String authorId;
    private String parantId; //댓글의 답글일 경우 댓글의 id
    private String root; // 댓글의 답글이 지속될경우 댓글 맨 꼭대기 댓글의 id depth가 1이면 parant
    private ArrayList<String> ToReplys; // 답글 리스트

    Comment(){} // 제일 중요

    Comment(
            String uid,
            String boardId,
            String body,
            String author,
            String date,
            String parantId,
            String root,
            String authorId){
        this.uid = uid;
        this.boardId = boardId;
        this.body = body;
        this.author = author;
        this.date = date;
        this.parantId = parantId;
        this.root = root;
        this.authorId = authorId;
    }

    public String getUid() {return uid;}
    public void setUid(String uid) {this.uid = uid;}

    public String getBoardId() {return boardId;}

    public void setBoardId(String boardId) {this.boardId = boardId;}

    public String getBody() {return body;}

    public void setBody(String body) {this.body = body;}

    public String getAuthor() {return author;}

    public void setAuthor(String author) {this.author = author;}

    public String getDate() {return date;}

    public void setDate(String date) {this.date = date;}

    public String getParantId() {return parantId;}

    public void setParantId(String parantId) {this.parantId = parantId;}

    public String getRoot() {return root;}

    public void setRoot(String root) {this.root = root;}

    public ArrayList<String> getToReplys() {return ToReplys;}

    public void setToReplys(ArrayList<String> toReplys) {ToReplys = toReplys;}

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("boardId",boardId);
        result.put("body", body);
        result.put("author", author);
        result.put("authorId",authorId);
        result.put("date", date);
        result.put("parantId", parantId);
        result.put("root",root);
        return result;
    }
}
