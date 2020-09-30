package ch.axa.meatbackend.service;

import ch.axa.meatbackend.mongo.model.*;

import java.util.List;
import java.util.Optional;

public interface ApplicationService {
    Application save(Application application);
    List<Application> findAll();
    Optional<Application> findByCode(String code);

    Application createApplication(Application application);
    Application editApplication(Application newApplication);
    long deleteApplication(String applicationId);

    Location createLocation(String applicationId, Location location);
    Location editLocation(String applicationId, Location location);
    boolean deleteLocation(String applicationId, String locationId);
    Optional<Location> findLocationById(String applicationId, String locationId);

    Ticker createTicker(String applicationId, Ticker ticker);
    Ticker editTicker(String applicationId, Ticker ticker);
    boolean deleteTicker(String applicationId, String tickerId);
    Optional<Ticker> findTickerById(String applicationId, String tickerId);

    Ticker createTemplate(String applicationId, Ticker template);
    Ticker editTemplate(String applicationId, Ticker template);
    boolean deleteTemplate(String applicationId, String templateId);
    Optional<Ticker> findTemplateById(String applicationId, String templateId);

    Optional<Message> findMessageOfTickerById(String applicationId, String tickerId, String messageId);
    Optional<Message> findMessageOfTemplateById(String applicationId, String templateId, String messageId);
}
