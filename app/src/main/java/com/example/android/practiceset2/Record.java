package com.example.android.practiceset2;


public class Record {
    private String id;
    private String jaSentence;
    private String translation;
    private String hint;

    public Record() {

    }

    public Record(String id) {
        this.id = id;
    }

    public Record(String id, String jaSentence, String translation, String hint) {
        this.id = id;
        this.jaSentence = jaSentence;
        this.translation = translation;
        this.hint = hint;
    }

    public String getID() {
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

    public void setID(String id) {
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
}