package com.easylearnz.status_page.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    public static String convertDateToString(LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy hh:mm a");
        return date.format(formatter);
    }

    public static LocalDateTime convertUTCTimeStringToDate(String dateTimeString) {
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(dateTimeString);
        return zonedDateTime.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
    }
}
