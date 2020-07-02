package com.example.mynote.Data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes")
public class Note {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "timestamp")
    private String timeStamp;
    @ColumnInfo(name = "text")
    private String text;


    public Note(int id, String title, String timeStamp, String text){
        this.id = id;
        this.title = title;
        this.timeStamp = timeStamp;
        this.text = text;
    }

    @Ignore
    public Note(int x){
        this(0,"this is note " + x, null, "");
    }

    @Ignore
    public Note() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public String getTimeStampString() {
        return timeStamp.toString();
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }



}
