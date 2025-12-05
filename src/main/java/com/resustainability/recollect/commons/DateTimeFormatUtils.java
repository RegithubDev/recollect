package com.resustainability.recollect.commons;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class DateTimeFormatUtils {
    private DateTimeFormatUtils() {}

    // COMMON DATE/TIME FORMATTERS
    public static final DateTimeFormatter ISO_DATE = DateTimeFormatter.ISO_DATE;
    public static final DateTimeFormatter ISO_DATE_TIME = DateTimeFormatter.ISO_DATE_TIME;
    public static final DateTimeFormatter ISO_TIME = DateTimeFormatter.ISO_TIME;

    public static final DateTimeFormatter HUMAN_DATE_SLASH = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public static final DateTimeFormatter HUMAN_DATE_DASH = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    public static final DateTimeFormatter HUMAN_DATE_TEXT = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.ENGLISH);
    public static final DateTimeFormatter HUMAN_DATE_SHORT_TEXT = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH);
    public static final DateTimeFormatter HUMAN_DATE_DAY_NAME = DateTimeFormatter.ofPattern("EEEE, dd MMM yyyy", Locale.ENGLISH);

    public static final DateTimeFormatter TIME_12_HOUR = DateTimeFormatter.ofPattern("hh:mm a", Locale.ENGLISH);
    public static final DateTimeFormatter TIME_24_HOUR = DateTimeFormatter.ofPattern("HH:mm");

    public static final DateTimeFormatter HUMAN_DATE_TIME = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm");
    public static final DateTimeFormatter DB_TIMESTAMP = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter FILE_TIMESTAMP = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

    // FORMAT METHODS
    public static String format(LocalDate date, DateTimeFormatter formatter) {
        return date.format(formatter);
    }

    public static String format(LocalTime time, DateTimeFormatter formatter) {
        return time.format(formatter);
    }

    public static String format(LocalDateTime dateTime, DateTimeFormatter formatter) {
        return dateTime.format(formatter);
    }

    public static String format(Instant instant, DateTimeFormatter formatter, ZoneId zone) {
        return formatter.format(instant.atZone(zone));
    }

    // PARSE METHODS
    /**
     * Parses a date string into a {@link LocalDate} using the provided {@link DateTimeFormatter}.
     *
     * @param input     the date string to parse (e.g., "2024-12-31")
     * @param formatter the formatter that defines the expected input format
     * @return a {@link LocalDate} representing the parsed date
     * @throws DateTimeParseException if the text cannot be parsed
     */
    public static LocalDate parseDate(String input, DateTimeFormatter formatter) {
        return LocalDate.parse(input, formatter);
    }

    /**
     * Parses a time string into a {@link LocalTime} using the provided {@link DateTimeFormatter}.
     *
     * @param input     the time string to parse (e.g., "23:45" or "11:45 PM")
     * @param formatter the formatter that defines the expected input format
     * @return a {@link LocalTime} representing the parsed time
     * @throws DateTimeParseException if the text cannot be parsed
     */
    public static LocalTime parseTime(String input, DateTimeFormatter formatter) {
        return LocalTime.parse(input, formatter);
    }

    /**
     * Parses a date-time string into a {@link LocalDateTime} using the provided {@link DateTimeFormatter}.
     *
     * @param input     the date-time string to parse (e.g., "2024-12-31T23:45")
     * @param formatter the formatter that defines the expected input format
     * @return a {@link LocalDateTime} representing the parsed date and time
     * @throws DateTimeParseException if the text cannot be parsed
     */
    public static LocalDateTime parseDateTime(String input, DateTimeFormatter formatter) {
        return LocalDateTime.parse(input, formatter);
    }

    /**
     * Parses a date-time string into an {@link Instant}, using the given {@link DateTimeFormatter} and {@link ZoneId}.
     *
     * This method first parses the input string into a {@link LocalDateTime}, and then applies the provided zone
     * to convert it to an {@link Instant}.
     *
     * @param input     the date-time string to parse (e.g., "2024-12-31T23:45")
     * @param formatter the formatter that defines the expected input format
     * @param zone      the time zone to apply when converting to {@link Instant}
     * @return an {@link Instant} representing the parsed moment in time
     * @throws DateTimeParseException if the text cannot be parsed
     */
    public static Instant parseInstant(String input, DateTimeFormatter formatter, ZoneId zone) {
        return LocalDateTime.parse(input, formatter).atZone(zone).toInstant();
    }

    // SHORTCUTS FOR NOW (date/time)
    /**
     * Returns current date in ISO format: yyyy-MM-dd (e.g., 2025-07-25)
     */
    public static String nowIsoDate() {
        return format(LocalDate.now(), ISO_DATE);
    }

    /**
     * Returns current date and time in ISO format: yyyy-MM-dd'T'HH:mm:ss (e.g., 2025-07-25T14:05:00)
     */
    public static String nowIsoDateTime() {
        return format(LocalDateTime.now(), ISO_DATE_TIME);
    }

    /**
     * Returns current time in ISO format: HH:mm:ss (e.g., 14:05:00)
     */
    public static String nowIsoTime() {
        return format(LocalTime.now(), ISO_TIME);
    }

    /**
     * Returns current date in human-readable format using slashes: dd/MM/yyyy (e.g., 25/07/2025)
     */
    public static String nowDateSlash() {
        return format(LocalDate.now(), HUMAN_DATE_SLASH);
    }

    /**
     * Returns current date in human-readable format using dashes: dd-MM-yyyy (e.g., 25-07-2025)
     */
    public static String nowDateDash() {
        return format(LocalDate.now(), HUMAN_DATE_DASH);
    }

    /**
     * Returns current date with full month name: dd MMMM yyyy (e.g., 25 July 2025)
     */
    public static String nowDateText() {
        return format(LocalDate.now(), HUMAN_DATE_TEXT);
    }

    /**
     * Returns current date with short month name: dd MMM yyyy (e.g., 25 Jul 2025)
     */
    public static String nowDateShortText() {
        return format(LocalDate.now(), HUMAN_DATE_SHORT_TEXT);
    }

    /**
     * Returns current date with weekday name: EEEE, dd MMM yyyy (e.g., Friday, 25 Jul 2025)
     */
    public static String nowDateWithDayName() {
        return format(LocalDate.now(), HUMAN_DATE_DAY_NAME);
    }

    /**
     * Returns current time in 12-hour format with AM/PM: hh:mm a (e.g., 02:05 PM)
     */
    public static String nowTime12h() {
        return format(LocalTime.now(), TIME_12_HOUR);
    }

    /**
     * Returns current time in 24-hour format: HH:mm (e.g., 14:05)
     */
    public static String nowTime24h() {
        return format(LocalTime.now(), TIME_24_HOUR);
    }

    /**
     * Returns current date and time in a readable format: dd MMM yyyy HH:mm (e.g., 25 Jul 2025 14:05)
     */
    public static String nowHumanDateTime() {
        return format(LocalDateTime.now(), HUMAN_DATE_TIME);
    }

    /**
     * Returns current timestamp formatted for databases: yyyy-MM-dd HH:mm:ss (e.g., 2025-07-25 14:05:00)
     */
    public static String nowDbTimestamp() {
        return format(LocalDateTime.now(), DB_TIMESTAMP);
    }

    /**
     * Returns current timestamp suitable for filenames: yyyyMMdd_HHmmss (e.g., 20250725_140500)
     */
    public static String nowFileTimestamp() {
        return format(LocalDateTime.now(), FILE_TIMESTAMP);
    }

    /**
     * Returns current UTC instant in ISO format: yyyy-MM-dd'T'HH:mm:ssZ (e.g., 2025-07-25T08:35:00Z)
     */
    public static String nowUtcIsoInstant() {
        return format(Instant.now(), ISO_DATE_TIME, ZoneId.of("UTC"));
    }
    /**
     * Returns current system time (local zone) as ISO-formatted instant: yyyy-MM-dd'T'HH:mm:ssZ (e.g., 2025-07-25T14:30:00+0530)
     */
    public static String nowLocalIsoInstant() {
        return format(Instant.now(), ISO_DATE_TIME, ZoneId.systemDefault());
    }

    // LocalDate

    /**
     * Formats the given LocalDate in ISO format: yyyy-MM-dd (e.g., 2025-07-25)
     */
    public static String toIsoDate(LocalDate date) {
        return format(date, ISO_DATE);
    }

    /**
     * Formats the given LocalDate using slashes: dd/MM/yyyy (e.g., 25/07/2025)
     */
    public static String toDateSlash(LocalDate date) {
        return format(date, HUMAN_DATE_SLASH);
    }

    /**
     * Formats the given LocalDate using dashes: dd-MM-yyyy (e.g., 25-07-2025)
     */
    public static String toDateDash(LocalDate date) {
        return format(date, HUMAN_DATE_DASH);
    }

    /**
     * Formats the given LocalDate as full text: dd MMMM yyyy (e.g., 25 July 2025)
     */
    public static String toDateText(LocalDate date) {
        return format(date, HUMAN_DATE_TEXT);
    }

    /**
     * Formats the given LocalDate as short text: dd MMM yyyy (e.g., 25 Jul 2025)
     */
    public static String toDateShortText(LocalDate date) {
        return format(date, HUMAN_DATE_SHORT_TEXT);
    }

    /**
     * Formats the given LocalDate with day name: EEEE, dd MMM yyyy (e.g., Friday, 25 Jul 2025)
     */
    public static String toDateWithDayName(LocalDate date) {
        return format(date, HUMAN_DATE_DAY_NAME);
    }

    // LocalTime

    /**
     * Formats the given LocalTime in 12-hour format: hh:mm a (e.g., 02:45 PM)
     */
    public static String toTime12h(LocalTime time) {
        return format(time, TIME_12_HOUR);
    }

    /**
     * Formats the given LocalTime in 24-hour format: HH:mm (e.g., 14:45)
     */
    public static String toTime24h(LocalTime time) {
        return format(time, TIME_24_HOUR);
    }

    // LocalDateTime

    /**
     * Formats the given LocalDateTime in ISO format: yyyy-MM-dd'T'HH:mm:ss (e.g., 2025-07-25T14:30:00)
     */
    public static String toIsoDateTime(LocalDateTime dateTime) {
        return format(dateTime, ISO_DATE_TIME);
    }

    /**
     * Formats the given LocalDateTime as human-readable: dd MMM yyyy HH:mm (e.g., 25 Jul 2025 14:30)
     */
    public static String toHumanDateTime(LocalDateTime dateTime) {
        return format(dateTime, HUMAN_DATE_TIME);
    }

    /**
     * Formats the given LocalDateTime for DB: yyyy-MM-dd HH:mm:ss (e.g., 2025-07-25 14:30:45)
     */
    public static String toDbTimestamp(LocalDateTime dateTime) {
        return format(dateTime, DB_TIMESTAMP);
    }

    /**
     * Formats the given LocalDateTime for file name: yyyyMMdd_HHmmss (e.g., 20250725_143045)
     */
    public static String toFileTimestamp(LocalDateTime dateTime) {
        return format(dateTime, FILE_TIMESTAMP);
    }

    // Instant

    /**
     * Formats the given Instant in UTC zone as ISO timestamp (e.g., 2025-07-25T09:00:00Z)
     */
    public static String toUtcIsoInstant(Instant instant) {
        return format(instant, ISO_DATE_TIME, ZoneId.of("UTC"));
    }

    /**
     * Formats the given Instant in system default zone as ISO timestamp (e.g., 2025-07-25T14:30:00+0530)
     */
    public static String toLocalIsoInstant(Instant instant) {
        return format(instant, ISO_DATE_TIME, ZoneId.systemDefault());
    }
}
