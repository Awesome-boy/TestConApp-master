package com.it.testconapp.javabean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by zt on 2019/5/6.
 */
@Entity
public class Note {

    private Long _id;
    /** Not-null value. */
    private String text;
    private String comment;
    private java.util.Date date;
    @Generated(hash = 118437544)
    public Note(Long _id, String text, String comment, java.util.Date date) {
        this._id = _id;
        this.text = text;
        this.comment = comment;
        this.date = date;
    }
    @Generated(hash = 1272611929)
    public Note() {
    }
    public Long get_id() {
        return this._id;
    }
    public void set_id(Long _id) {
        this._id = _id;
    }
    public String getText() {
        return this.text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public String getComment() {
        return this.comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public java.util.Date getDate() {
        return this.date;
    }
    public void setDate(java.util.Date date) {
        this.date = date;
    }

}
