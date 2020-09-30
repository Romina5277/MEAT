package ch.axa.meatbackend.mongo.model;

import org.bson.types.ObjectId;

public class Message {
    private String id;
    private String text;
    private String language;
    private String title;

    public Message() {
        id = new ObjectId().toString();
    }

    public Message(String text, String language, String title) {
        this.text = text;
        this.language = language;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
