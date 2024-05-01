package com.ishans.dev.URLShortenerApplication.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimestampUtil {
	public static String getCurrentTimestampAsString() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        return currentDateTime.format(formatter);
    }
}
