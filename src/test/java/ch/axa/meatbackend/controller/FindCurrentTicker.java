package ch.axa.meatbackend.controller;

import ch.axa.meatbackend.mongo.model.Ticker;
import ch.axa.meatbackend.rest.Controller;
import ch.axa.meatbackend.service.ApplicationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class FindCurrentTicker {
    private Controller controller = new Controller(Mockito.mock(ApplicationService.class));
    private List<Ticker> allTickers;
    private List<Ticker> currentTickers;

    private static String localDateTimeFormat = "yyyy-MM-dd HH:mm:ss";
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern(localDateTimeFormat);

    private static final int LOCATIONNUMBER = 1;
    private static final LocalDateTime DATE_TIME_NOW = LocalDateTime.parse("2020-07-30 13:00:00", formatter);

    @Before
    public void before() {
        // Happy Flow (mit 2 Locations)
        Ticker ticker1 = new Ticker("Schulung", new ArrayList<>(Arrays.asList(1, 2)),
                                    "2020-07-29 08:00:00", "2020-07-31 17:00:00", new ArrayList<>());
        // Happy Flow (mit einer Location)
        Ticker ticker2 = new Ticker("Schulung", new ArrayList<>(Arrays.asList(1)),
                                    "2020-07-29 08:00:00", "2020-07-31 17:00:00", new ArrayList<>());

        // Grenzwerte
        // startet jetzt
        Ticker ticker3 = new Ticker("Schulung", new ArrayList<>(Arrays.asList(1, 2)),
                                    "2020-07-30 13:00:00", "2020-08-29 17:00:00", new ArrayList<>());
        // endet jetzt
        Ticker ticker4 = new Ticker("Schulung", new ArrayList<>(Arrays.asList(1, 2)),
                                    "2020-07-29 08:00:00", "2020-07-30 13:00:00", new ArrayList<>());
        // startet in einer Sekunde
        Ticker ticker5 = new Ticker("Schulung", new ArrayList<>(Arrays.asList(1, 2)),
                                    "2020-07-30 13:00:01", "2020-08-29 17:00:00", new ArrayList<>());
        // endete vor einer Sekunde
        Ticker ticker6 = new Ticker("Schulung", new ArrayList<>(Arrays.asList(1, 2)),
                                    "2020-07-29 08:00:00", "2020-07-30 12:59:59", new ArrayList<>());

        // Falsche Location
        Ticker ticker7 = new Ticker("Schulung", new ArrayList<>(Arrays.asList(2)),
                                    "2020-07-29 08:00:00", "2020-07-31 17:00:00", new ArrayList<>());
        // Ticker bereits beendet
        Ticker ticker8 = new Ticker("Schulung", new ArrayList<>(Arrays.asList(1, 2)),
                                    "2020-06-29 08:00:00", "2020-06-30 17:00:00", new ArrayList<>());
        // Ticker noch nicht gestarted
        Ticker ticker9 = new Ticker("Schulung", new ArrayList<>(Arrays.asList(1, 2)),
                                    "2020-08-29 08:00:00", "2020-08-29 17:00:00", new ArrayList<>());

        allTickers = new ArrayList<>(Arrays.asList(ticker1, ticker2, ticker3, ticker4, ticker5, ticker6, ticker7, ticker8, ticker9));
        currentTickers = new ArrayList<>(Arrays.asList(ticker1, ticker2, ticker3, ticker4));
    }

    @Test
    public void testFindCurrentTickersByLocation() {
        Controller spyController = Mockito.spy(controller);
        when(spyController.getCurrentDateTime()).thenReturn(DATE_TIME_NOW);

        assertEquals(currentTickers, spyController.findCurrentTickersByLocation(allTickers, LOCATIONNUMBER));
    }

}
