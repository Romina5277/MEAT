package ch.axa.meatbackend.mongo.model;

import org.bson.types.ObjectId;

public class Location {

    private String id;
    private String name;
    private int locationnumber;

    public Location() {
        id = new ObjectId().toString();
    }

    public Location(String name, int locationnumber) {
        this.name = name;
        this.locationnumber = locationnumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLocationnumber() {
        return locationnumber;
    }

    public void setLocationnumber(int locationnumber) {
        this.locationnumber = locationnumber;
    }
}
