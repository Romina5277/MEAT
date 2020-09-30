package ch.axa.meatbackend.graphql.resolver;

import ch.axa.meatbackend.mongo.model.*;
import ch.axa.meatbackend.service.ApplicationService;
import com.coxautodev.graphql.tools.GraphQLMutationResolver;

public class Mutation implements GraphQLMutationResolver {
    private final ApplicationService applicationService;

    public Mutation(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    public Application createApplication(Application application) {
        return applicationService.createApplication(application);
    }

    public Application editApplication(Application application) {
        return applicationService.editApplication(application);
    }

    public long deleteApplication(String applicationCode) {
        return applicationService.deleteApplication(applicationCode);
    }

    public Location createLocation(String applicationId, Location location) {
        return applicationService.createLocation(applicationId, location);
    }

    public Location editLocation(String applicationId, Location location) {
        return applicationService.editLocation(applicationId, location);
    }

    public boolean deleteLocation(String applicationId, String locationId) {
        return applicationService.deleteLocation(applicationId, locationId);
    }

    public Ticker createTicker(String applicationId, Ticker ticker) {
        return applicationService.createTicker(applicationId, ticker);
    }

    public Ticker editTicker(String applicationId, Ticker ticker) {
        return applicationService.editTicker(applicationId, ticker);
    }

    public boolean deleteTicker(String applicationId, String tickerId) {
        return applicationService.deleteTicker(applicationId, tickerId);
    }

    public Ticker createTemplate(String applicationId, Ticker template) {
        return applicationService.createTemplate(applicationId, template);
    }

    public Ticker editTemplate(String applicationId, Ticker template) {
        return applicationService.editTemplate(applicationId, template);
    }

    public boolean deleteTemplate(String applicationId, String templateId) {
        return applicationService.deleteTemplate(applicationId, templateId);
    }

}
