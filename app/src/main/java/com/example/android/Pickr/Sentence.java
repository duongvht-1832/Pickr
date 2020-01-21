package com.example.android.Pickr;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "Sentence")
public class Sentence {
    @PrimaryKey
    @NonNull
    private String sid;
    @ColumnInfo(name = "jaSentence")
    private String jaSentence;
    @ColumnInfo(name = "translation")
    private String translation;
    @ColumnInfo(name = "hint")
    private String hint;
    @ColumnInfo(name = "favourite")
    private boolean favourite;

    @Ignore
    public Sentence() {

    }

    @Ignore
    public Sentence(String id) {
        this.sid = id;
    }

    public Sentence(String sid, String jaSentence, String translation, String hint) {
        this.sid = sid;
        this.jaSentence = jaSentence;
        this.translation = translation;
        this.hint = hint;
    }

    public String getSid() {
        return this.sid;
    }

    public String getJaSentence() {
        return this.jaSentence;
    }

    public String getTranslation() {
        return this.translation;
    }

    public String getHint() {
        return this.hint;
    }

    public boolean isFavourite() {
        return this.favourite;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public void setJaSentence(String jaSentence) {
        this.jaSentence = jaSentence;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }
}