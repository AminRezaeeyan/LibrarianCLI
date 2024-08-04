package com.librarian.utils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Utility class for handling time-related operations
public class TimeUtil {

    //  Parses a time string in the format of "yyyy-MM-dd HH:mm" into a LocalDateTime object
    public static LocalDateTime parseTime(String timeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(timeString, formatter);
    }

    // Calculates the duration between two time strings in the format of "yyyy-MM-dd HH:mm"
    public static Duration calculateTimeDifference(String startTime, String endTime) {
        return Duration.between(parseTime(startTime), parseTime(endTime));
    }
}
