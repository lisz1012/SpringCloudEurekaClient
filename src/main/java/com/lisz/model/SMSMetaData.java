package com.lisz.model;

import java.util.Date;

public class SMSMetaData {
    private int id;
    private String type;
    private String content;
    private Date date;

    public SMSMetaData() {
    }

    public SMSMetaData(int id) {
        this.id = id;
    }

    public SMSMetaData(int id, String type, String content, Date date) {
        this.id = id;
        this.type = type;
        this.content = content;
        this.date = date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }



    public void setContent(String content) {
        this.content = content;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

    public Date getDate() {
        return date;
    }
}
