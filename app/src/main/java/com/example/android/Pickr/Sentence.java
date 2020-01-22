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
    @ColumnInfo(name = "id")
    private String id;
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
        this.id = id;
    }

    public Sentence(String id, String jaSentence, String translation, String hint) {
        this.id = id;
        this.jaSentence = jaSentence;
        this.translation = translation;
        this.hint = hint;
    }

    public String getId() {
        return this.id;
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

    public void setId(String id) {
        this.id = id;
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