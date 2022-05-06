package com.hanul.alcoholic;

import java.util.Date;

public class List_item {
    private String nickname;
    private String title;
    private Date write_date;
    private String content;
    private int comment_count;

    public List_item(String nickname, String title, Date write_date, String content, int comment_count) {
        this.nickname = nickname;
        this.title = title;
        this.write_date = write_date;
        this.content = content;
        this.comment_count = comment_count;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getWrite_date() {
        return write_date;
    }

    public void setWrite_date(Date write_date) {
        this.write_date = write_date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getComment_count() {
        return comment_count;
    }

    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
    }
}
