package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    public static final LocalDate MIN_DATE = LocalDate.of(0, 1,1);
    public static final LocalDate MAX_DATE = LocalDate.of(3000, 12, 31);

    public static boolean isBetween(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) <= 0;
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }

    public static LocalDate parseLocalDate(String s, LocalDate def) {
        return s.isEmpty() ? def : LocalDate.parse(s);
    }

    public static LocalTime parseLocalTime(String s, LocalTime def) {
        return s.isEmpty() ? def : LocalTime.parse(s);
    }
}
