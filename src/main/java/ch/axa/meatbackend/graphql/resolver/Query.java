package ch.axa.meatbackend.graphql.resolver;

import ch.axa.meatbackend.mongo.model.*;
import ch.axa.meatbackend.service.*;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;

import java.util.List;
import java.util.Optional;

public class Query  implements GraphQLQueryResolver {

    private final ApplicationService applicationService;

    public Query(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    public List<Application> getApplications() {
        return applicationService.findAll();
    }

    public Optional<Application> getApplication(String applicationCode) {
        return applicationService.findByCode(applicationCode);
    }

    public Optional<Location> getLocation(String applicationId, String locationNumber) {
        return applicationService.findLocationById(applicationId, locationNumber);
    }

    public Optional<Ticker> getTicker(String applicationId, String tickerId) {
        return applicationService.findTickerById(applicationId, tickerId);
    }

    public Optional<Ticker> getTemplate(String applicationId, String templateId) {
        return applicationService.findTemplateById(applicationId, templateId);
    }

    public Optional<Message> getMessage(String applicationId, String tickerId, String messageIdId) {
        return applicationService.findMessageOfTickerById(applicationId, tickerId, messageIdId);
    }

}
