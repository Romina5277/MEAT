package ch.axa.meatbackend.mongo.model;

import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

public class Application {
    @Id
    private String id;
    private String code;
    private String name;
    private List<Location> locations = new ArrayList<>();
    private List<Ticker> tickers = new ArrayList<>();
    private List<Ticker> templates = new ArrayList<>();

    public Application(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public Application() {
    }

    @Override
    public String toString() {
        return String.format("Application[code='%s', name='%s']", code, name);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String id_code) {
        this.code = id_code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public List<Ticker> getTickers() {
        return tickers;
    }

    public void setTickers(List<Ticker> tickers) {
        this.tickers = tickers;
    }

    public List<Ticker> getTemplates() {
        return templates;
    }

    public void setTemplates(List<Ticker> templates) {
        this.templates = templates;
    }
}
