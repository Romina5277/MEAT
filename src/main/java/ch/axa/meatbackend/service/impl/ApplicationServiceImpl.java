package ch.axa.meatbackend.service.impl;

import ch.axa.meatbackend.mongo.model.*;
import ch.axa.meatbackend.mongo.repository.ApplicationRepository;
import ch.axa.meatbackend.service.ApplicationService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class ApplicationServiceImpl implements ApplicationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationServiceImpl.class);
    private static final int APPLICATION_CODE_MAX_LENGTH = 15;

    private final ApplicationRepository applicationRepository;

    public ApplicationServiceImpl(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    @Override
    public Location createLocation(String applicationId, Location location) {
        if (isLocationInputValid(location, applicationId, false)) {
            Optional<Application> application = applicationRepository.findById(applicationId);
            return application.map(app -> {
                app.getLocations().add(location);
                save(app);
                return location;
            }).orElseThrow(() -> new IllegalArgumentException("application with id: '" + applicationId + "' not found"));
        } else {
            throw new IllegalArgumentException("Aufgrund unerlaubten Eingaben kann die Location nicht erstellt werden.");
        }
    }

    @Override
    public Location editLocation(String applicationId, Location newLocation) {
        if (isLocationInputValid(newLocation, applicationId, true)) {
            Optional<Application> application = applicationRepository.findById(applicationId);

            if (application.isPresent()) {
                Application foundApplication = application.get();
                foundApplication.getLocations()
                        .stream()
                        .filter(location -> location.getId().equals(newLocation.getId()))
                        .findFirst()
                        .ifPresent(loc -> {
                            foundApplication.getLocations().remove(loc);
                            foundApplication.getLocations().add(newLocation);
                            save(foundApplication);
                        });
            } else {
                throw new IllegalArgumentException("application with id: '" + applicationId + "' not found");
            }
        } else {
            throw new IllegalArgumentException("Aufgrund unerlaubten Eingaben kann die Location nicht bearbeitet werden.");
        }
        return newLocation;
    }

    @Override
    public boolean deleteLocation(String applicationId, String locationId) {
        Optional<Application> application = applicationRepository.findById(applicationId);
        if (application.isPresent()) {
            boolean deleted = false;
            Application foundApplication = application.get();
            Optional<Location> foundLocation = foundApplication.getLocations().stream().filter(location -> location.getId().equals(locationId)).findAny();
            if (foundLocation.isPresent()) {
                deleted = foundApplication.getLocations().remove(foundLocation.get());
                if (deleted) {
                    //Remove Location number from tickers and templates
                    foundApplication.getTickers().forEach(ticker -> ticker.getLocationnumbers().removeIf(locNr -> locNr.equals(foundLocation.get().getLocationnumber())));
                    foundApplication.getTemplates().forEach(template -> template.getLocationnumbers().removeIf(locNr -> locNr.equals(foundLocation.get().getLocationnumber())));

                    save(application.get());
                }
            }
            return deleted;
        }
        throw new IllegalArgumentException("application with id: '" + applicationId + "' not found");
    }

    @Override
    public Ticker createTicker(String applicationId, Ticker ticker) {
        if (isTickerInputValid(ticker, applicationId, false)) {
            Optional<Application> application = applicationRepository.findById(applicationId);
            return application.map(app -> {
                app.getTickers().add(ticker);
                save(app);
                return ticker;
            }).orElseThrow(() -> new IllegalArgumentException("application with id: '" + applicationId + "' not found"));
        }
        throw new IllegalArgumentException("Aufgrund unerlaubten Eingaben kann der Ticker nicht erstellt werden.");
    }

    @Override
    public Ticker editTicker(String applicationId, Ticker ticker) {
        if (isTickerInputValid(ticker, applicationId, true)) {
            applicationRepository.findById(applicationId).ifPresent(application -> application.getTickers()
                    .stream()
                    .filter(t -> t.getId().equals(ticker.getId()))
                    .findFirst()
                    .map(original -> {
                        application.getTickers().remove(original);
                        application.getTickers().add(ticker);
                        save(application);
                        return original;
                    })
                    .orElseThrow(() -> new IllegalArgumentException("application with id: " + applicationId + " not found")));
            return ticker;
        }
        throw new IllegalArgumentException("Aufgrund unerlaubten Eingaben kann der Ticker nicht bearbeitet werden.");
    }

    @Override
    public boolean deleteTicker(String applicationId, String tickerId) {
        Optional<Application> application = applicationRepository.findById(applicationId);

        if (application.isPresent()) {
            Application foundApplication = application.get();
            boolean deleted = foundApplication.getTickers().removeIf(msg -> msg.getId().equals(tickerId));
            if (deleted) {
                save(application.get());
            }
            return deleted;
        } else {
            throw new IllegalArgumentException("application with id: '" + applicationId + "' not found");
        }
    }

    @Override
    public Ticker createTemplate(String applicationId, Ticker template) {
        if (isTickerInputValid(template, applicationId, false)) {
            Optional<Application> application = applicationRepository.findById(applicationId);
            return application.map(app -> {
                app.getTemplates().add(template);
                save(app);
                return template;
            }).orElseThrow(() -> new IllegalArgumentException("application with id: '" + applicationId + "' not found"));
        }
        throw new IllegalArgumentException("Aufgrund unerlaubten Eingaben kann die Vorlage nicht erstellt werden.");
    }

    @Override
    public Ticker editTemplate(String applicationId, Ticker template) {
        if (isTickerInputValid(template, applicationId, true)) {
            Optional<Application> application = applicationRepository.findById(applicationId);
            application.ifPresent(app -> app.getTemplates()
                    .stream()
                    .filter(s -> s.getId().equals(template.getId()))
                    .findAny()
                    .map(matchingTemplate -> {
                        app.getTemplates().remove(matchingTemplate);
                        app.getTemplates().add(template);
                        save(app);
                        return matchingTemplate;
                    })
                    .orElseThrow(() -> new IllegalArgumentException("application with id: '" + applicationId + "' not found")));
            return template;
        }
        throw new IllegalArgumentException("Aufgrund unerlaubten Eingaben kann die Vorlage nicht bearbeitet werden.");
    }

    @Override
    public boolean deleteTemplate(String applicationId, String templateId) {
        Optional<Application> application = applicationRepository.findById(applicationId);
        if (application.isPresent()) {
            boolean deleted = application.get().getTemplates().removeIf(template -> template.getId().equals(templateId));
            if (deleted) {
                save(application.get());
            }
            return deleted;
        } else {
            throw new IllegalArgumentException("application with id: '" + applicationId + "' not found");
        }
    }

    @Override
    public Application createApplication(Application application) {
        if (isApplicationInputValid(application)) {
            return save(application);
        } else {
            throw new IllegalArgumentException("Aufgrund unerlaubten Eingaben kann die Applikation nicht erstellt werden.");
        }
    }

    @Override
    public Application editApplication(Application newApplication) {
        if (isApplicationInputValid(newApplication)) {
            applicationRepository.findById(newApplication.getId()).ifPresent(oldApplication -> {
                oldApplication.setCode(newApplication.getCode());
                oldApplication.setName(newApplication.getName());
                save(oldApplication);
            });
            return newApplication;
        } else {
            throw new IllegalArgumentException("Aufgrund unerlaubten Eingaben kann die Applikation nicht bearbeitet werden.");
        }
    }

    @Override
    public long deleteApplication(String applicationId) {
        return applicationRepository.deleteApplicationById(applicationId);
    }

    @Override
    public Application save(Application application) {
        return applicationRepository.save(application);
    }

    @Override
    public List<Application> findAll() {
        return applicationRepository.findAll();
    }

    @Override
    public Optional<Application> findByCode(String code) {
        return applicationRepository.findByCode(code);
    }

    @Override
    public Optional<Ticker> findTickerById(String applicationId, String tickerId) {
        Optional<Application> foundApplication = applicationRepository.findById(applicationId);
        return foundApplication.flatMap(application -> application.getTickers().stream().filter(msg -> msg.getId().equals(tickerId)).findAny());
    }

    @Override
    public Optional<Ticker> findTemplateById(String applicationId, String templateId) {
        Optional<Application> foundApplication = applicationRepository.findById(applicationId);
        return foundApplication.flatMap(application -> application.getTemplates().stream().filter(template -> template.getId().equals(templateId)).findAny());
    }

    @Override
    public Optional<Location> findLocationById(String applicationId, String locationId) {
        Optional<Application> foundApplication = applicationRepository.findById(applicationId);
        return foundApplication.flatMap(application -> application.getLocations().stream().filter(location -> location.getId().equals(locationId)).findAny());
    }

    @Override
    public Optional<Message> findMessageOfTickerById(String applicationId, String tickerId, String messageId) {
        Optional<Application> foundApplication = applicationRepository.findById(applicationId);
        Optional<Ticker> foundTicker = foundApplication.flatMap(application -> application.getTickers().stream().filter(t -> t.getId().equals(tickerId)).findAny());
        return foundTicker.flatMap(message -> message.getMessages().stream().filter(mt -> mt.getId().equals(messageId)).findAny());
    }

    @Override
    public Optional<Message> findMessageOfTemplateById(String applicationId, String templateId, String messageId) {
        Optional<Application> foundApplication = applicationRepository.findById(applicationId);
        Optional<Ticker> foundTemplate = foundApplication.flatMap(application -> application.getTemplates().stream().filter(template -> template.getId().equals(templateId)).findAny());
        return foundTemplate.flatMap(template -> template.getMessages().stream().filter(mt -> mt.getId().equals(messageId)).findAny());
    }

    public boolean checkApplicationExistence(String applicationId) {
        if (!applicationId.isEmpty() && !applicationRepository.findById(applicationId).isPresent()) {
            LOGGER.error("Es wurde keine Applikation mit der id '" + applicationId + "' gefunden");
            throw new IllegalArgumentException("Es wurde keine Applikation mit der id '" + applicationId + "' gefunden");
        }
        return true;
    }

    /**
     * isApplicationInputValid --> Check the input before manipulating the Database
     *
     * @param application (Application)
     *                    The new Application that should be saved
     * @return TRUE:   if the data are correct and may be processed
     * FALSE:  if the data are incorrect and may not be processed
     */

    public boolean isApplicationInputValid(Application application) {
        // sind alle Daten abgefüllt
        if (StringUtils.isEmpty(application.getCode())) {
            LOGGER.error("Der Code der Applikation wurde nicht abgefüllt.");
            return false;
        }
        if (StringUtils.isEmpty(application.getName())) {
            LOGGER.error("Der Name der Applikation wurde nicht abgefüllt.");
            return false;
        }

        // Daten auf XML invalide Zeichen prüfen
        if (ServiceUtils.hasXMLInvalideSymbol(application.getCode())) {
            return false;
        }
        if (ServiceUtils.hasXMLInvalideSymbol(application.getName())) {
            return false;
        }

        // der Code für die Applikation prüfen
        if (application.getCode().length() > APPLICATION_CODE_MAX_LENGTH) {
            LOGGER.error("Der Code '" + application.getCode() + "' der Applikation ist zu lang. (max. " + APPLICATION_CODE_MAX_LENGTH + " Zeichen)");
            return false;
        }

        // gibt es den Code bereits
        findByCode(application.getCode())
                .filter(existingApp -> !existingApp.getId().equals(application.getId()))
                .ifPresent(existingApp -> {
                    LOGGER.error("Es wurde bereits eine bestehende Applikation mit dem Code '" + existingApp.getCode() + "' gefunden");
                    throw new IllegalArgumentException("Es wure bereits eine bestehende Applikation mit dem Code '" + existingApp.getCode() + "' gefunden");
                });

        // Inputdaten sind valide
        return true;
    }

    public boolean isLocationInputValid(Location location, String applicationId, boolean existingLocation) {
        if (existingLocation) {
            // gewünschte Location vorhanden
            findLocationById(applicationId, location.getId()).orElseThrow(() -> {
                LOGGER.error("Es wurde keine Location mit der angegebenen ID '" + location.getId() + "' gefunden");
                return new IllegalArgumentException("Es wurde keine Location mit der angegebenen ID '" + location.getId() + "' gefunden");
            });
        }

        // sind alle Daten abgefüllt
        if (StringUtils.isEmpty(location.getName())) {
            LOGGER.error("Der Name der Location wurde nicht abgefüllt.");
            return false;
        }

        // ist Location-Nr. korrekt abgefüllt
        if (location.getLocationnumber() < 1) {
            LOGGER.error("Die Location-Nr. '" + location.getLocationnumber() + "' wurde nicht korrekt abgefüllt. (diese muss zwischen 1 und n liegen)");
            return false;
        }

        // Inputfelder auf XML invalide Zeichen prüfen
        if (ServiceUtils.hasXMLInvalideSymbol(location.getName())) {
            return false;
        }

        // Inputdaten sind valide
        return true;
    }

    public boolean isTickerInputValid(Ticker ticker, String applicationId, boolean existingTicker) {
        if (existingTicker && checkApplicationExistence(applicationId)) {
            // gewünschter Ticker vorhanden
            findTickerById(applicationId, ticker.getId()).orElseThrow(() -> {
                LOGGER.error("Es wurde kein Ticker mit der id '" + ticker.getId() + "' gefunden");
                return new IllegalArgumentException("Es wurde keinen Ticker mit der id '" + ticker.getId() + "' gefunden");
            });
        }

        // sind alle Daten abgefüllt
        if (StringUtils.isEmpty(ticker.getTitle())) {
            LOGGER.error("Der Titel des Tickers wurde nicht abgefüllt.");
            return false;
        }
        if (StringUtils.isEmpty(ticker.getTimeFrom())) {
            LOGGER.error("Die Startzeit des Tickers wurde nicht abgefüllt.");
            return false;
        }
        if (StringUtils.isEmpty(ticker.getTimeTo())) {
            LOGGER.error("Die Endzeit des Tickers wurde nicht abgefüllt.");
            return false;
        }
        if (ticker.getLocationnumbers().isEmpty()) {
            LOGGER.error("Die Locationnumber wurde nicht abgefüllt.");
            return false;
        }

        // Inputfelder auf XML invalide Zeichen prüfen
        if (ServiceUtils.hasXMLInvalideSymbol(ticker.getTitle())) {
            return false;
        }

        // Validate Messages
        if (!ticker.getMessages().stream().allMatch(mt -> isMessageInputOfTickerValid(mt, applicationId, ticker.getId(), existingTicker))) {
            return false;
        }

        // Zeitangaben überprüfen
        if (!ServiceUtils.timeValidate(ticker.getTimeFrom(), ticker.getTimeTo())) {
            return false;
        }

        // Inputdaten sind valide
        return true;
    }

    public boolean isMessageInputValid(Message message, String applicationId, String tickerId, String templateId, boolean existingMessage) {
        if (existingMessage) {
            // gewünschte Message vorhanden
            if (tickerId != null) {
                findMessageOfTickerById(applicationId, tickerId, message.getId()).orElseThrow(() -> {
                    LOGGER.error("Es wurde keine Message mit der angegebenen ID '" + message.getId() + "' gefunden");
                    return new IllegalArgumentException("Es wurde keine Message mit der angegebenen ID '" + message.getId() + "' gefunden");
                });
            } else if (templateId != null) {
                findMessageOfTemplateById(applicationId, templateId, message.getId()).orElseThrow(() -> {
                    LOGGER.error("Es wurde keine Message mit der angegebenen ID '" + message.getId() + "' gefunden");
                    return new IllegalArgumentException("Es wurde keine Message mit der angegebenen ID '" + message.getId() + "' gefunden");
                });
            }
        }

        // sind alle Daten abgefüllt
        if (StringUtils.isEmpty(message.getTitle())) {
            LOGGER.error("Der Titel der Meldung wurde nicht abgefüllt.");
            return false;
        }
        if (StringUtils.isEmpty(message.getText())) {
            LOGGER.error("Der Text der Meldung wurde nicht abgefüllt.");
            return false;
        }
        if (StringUtils.isEmpty(message.getLanguage())) {
            LOGGER.error("Die Sprache der Meldung wurde nicht abgefüllt.");
            return false;
        }

        // Inputfelder auf XML invalide Zeichen prüfen
        if (ServiceUtils.hasXMLInvalideSymbol(message.getText())) {
            return false;
        }
        if (ServiceUtils.hasXMLInvalideSymbol(message.getTitle())) {
            return false;
        }

        // Sprache auf korrektes ISO-Format prüfen
        List<String> languages = Arrays.asList(Locale.getISOLanguages());
        if (!languages.contains(message.getLanguage())) {
            LOGGER.error("Die Sprache '" + message.getLanguage() + "' der Meldung entspricht nicht dem gewünschten ISO-Standard.");
            return false;
        }

        // Inputdaten sind valide
        return true;
    }

    public boolean isMessageInputOfTemplateValid(Message message, String applicationId, String templateId, boolean existingMessage) {
        return isMessageInputValid(message, applicationId, null, templateId, existingMessage);
    }

    public boolean isMessageInputOfTickerValid(Message message, String applicationId, String tickerId, boolean existingMessage) {
        return isMessageInputValid(message, applicationId, tickerId, null, existingMessage);
    }
}
