package ru.rsreu.travelers.logic;

public class CommonLogic {
    public static String trimToLength(String string, int maxLength) {
        return string.length() > maxLength ? string.substring(0, maxLength) : string;
    }

    public static String addParameterToUrl(String url, String parameter) {
        return String.format("%s&%s", url, parameter);
    }
}
