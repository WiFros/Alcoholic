package com.hanul.alcoholic;

import java.util.ArrayList;

public class Comment {
    private String uid; // 고유키
    private String boardId; //댓글이 속한 게시물 id
    private String body; // 내용
    private String author; //작성자
    private String date; // 작성일
    private String parantId; //댓글의 답글일 경우 댓글의 id

    private String root; // 댓글의 답글이 지속될경우 댓글 맨 꼭대기 댓글의 id depth가 1이면 parant

    private boolean mode; // 댓글읠 답글 : true 일반 댓글 : false
    private ArrayList<String> ToReplys; // 답글 리스트

    Comment(){}

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

    public boolean isMode() {return mode;}

    public void setMode(boolean mode) {this.mode = mode;}

    public ArrayList<String> getToReplys() {return ToReplys;}

    public void setToReplys(ArrayList<String> toReplys) {ToReplys = toReplys;}
}
