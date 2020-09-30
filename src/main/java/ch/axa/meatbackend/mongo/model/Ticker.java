package ch.axa.meatbackend.mongo.model;

import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class Ticker {
    private String id;
    private String title;
    private List<Integer> locationnumbers = new ArrayList<>();
    private String timeFrom;
    private String timeTo;
    private List<Message> messages = new ArrayList<>();


    public Ticker() {
        id = new ObjectId().toString();
    }

    public Ticker(String title, List<Integer> locationnumbers, String timeFrom, String timeTo, List<Message> messages) {
        this.title = title;
        this.locationnumbers = locationnumbers;
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
        this.messages = messages;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Integer> getLocationnumbers() {
        return locationnumbers;
    }

    public void setLocationnumbers(List<Integer> locationnumbers) {
        this.locationnumbers = locationnumbers;
    }

    public String getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(String timeFrom) {
        this.timeFrom = timeFrom;
    }

    public String getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(String timeTo) {
        this.timeTo = timeTo;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
