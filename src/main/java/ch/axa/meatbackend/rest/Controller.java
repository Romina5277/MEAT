package ch.axa.meatbackend.rest;

import ch.axa.meatbackend.mongo.model.Application;
import ch.axa.meatbackend.mongo.model.Ticker;
import ch.axa.meatbackend.service.ApplicationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("rest")
public class Controller {

    private static final Logger LOGGER = LoggerFactory.getLogger(Controller.class);

    private ApplicationService applicationService;
    private String localDateTimeFormat = "yyyy-MM-dd HH:mm:ss";
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern(localDateTimeFormat);

    @Autowired
    public Controller(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @GetMapping("/tickers/{applicationCode}/{locationnumber}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<List<Ticker>> messages(@PathVariable String applicationCode, @PathVariable int locationnumber) {
        Optional<Application> application = applicationService.findByCode(applicationCode);

        if (application.isPresent()) {
            List<Ticker> tickers = findCurrentTickersByLocation(application.get().getTickers(), locationnumber);

            return CollectionUtils.isEmpty(tickers) ? ResponseEntity.noContent().build() : ResponseEntity.ok(tickers);
        } else {
            LOGGER.error("Die Applikation " + applicationCode + " wurde nicht gefunden.");
            return ResponseEntity.notFound().build();
        }
    }

    public List<Ticker> findCurrentTickersByLocation(List<Ticker> allTickers, int locationNumber) {
        LocalDateTime timeNow = getCurrentDateTime();

        List<Ticker> foundTickers = new ArrayList<>();
        for (Ticker ticker : allTickers) {
            LocalDateTime timeFromTicker = LocalDateTime.parse(ticker.getTimeFrom(), formatter);
            LocalDateTime timeToTicker = LocalDateTime.parse(ticker.getTimeTo(), formatter);
            for (Integer locNr : ticker.getLocationnumbers()) {
                if (locNr.equals(locationNumber) && !timeFromTicker.isAfter(timeNow) && !timeToTicker.isBefore(timeNow)) {
                    foundTickers.add(ticker);
                }
            }
        }
        return foundTickers;
    }

    public LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now();
    }
}
