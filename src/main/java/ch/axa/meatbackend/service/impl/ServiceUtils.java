package ch.axa.meatbackend.service.impl;

import ch.axa.meatbackend.mongo.model.*;
import ch.axa.meatbackend.mongo.repository.ApplicationRepository;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class ServiceUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceUtils.class);

    private static String localDateTimeRegex = "\\d{4}\\-\\d{2}\\-\\d{2}\\s\\d{2}\\:\\d{2}\\:\\d{2}";
    private static String localDateTimeFormat = "yyyy-MM-dd HH:mm:ss";
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern(localDateTimeFormat);
    private static final String COPY_SUFFIX = " - COPY";

    public static boolean hasXMLInvalideSymbol(String s) {
        if (!StringEscapeUtils.escapeXml11(s).equals(s)) {
            LOGGER.error("Im Text '" + s + "' wurden XML invalide Zeichen gefunden.");
            return true;
        } else {
            return false;
        }
    }

    public static boolean timeValidate(String timeFrom, String timeTo) {
        // auf LocalDateTime-Format überprüfen
        if (!timeFrom.matches(localDateTimeRegex)) {
            LOGGER.error("Die Startzeit (" + timeFrom + ") entspricht nicht dem richtigen Format. (yyyy-mm-dd hh:mm:ss)");
            return false;
        }
        if (!timeTo.matches(localDateTimeRegex)) {
            LOGGER.error("Die Endzeit (" + timeTo + ") entspricht nicht dem richtigen Format. (yyyy-mm-dd hh:mm:ss)");
            return false;
        }

        // Startzeit vor Endzeit
        LocalDateTime timeFromTicker = LocalDateTime.parse(timeFrom, formatter);
        LocalDateTime timeToTicker = LocalDateTime.parse(timeTo, formatter);
        if (timeFromTicker.isAfter(timeToTicker)) {
            LOGGER.error("Die Startzeit (" + timeFrom + ") muss vor der Endzeit (" + timeTo + ") festgelegt werden.");
            return false;
        }

        // Zeitangaben sind valide
        return true;
    }

}
