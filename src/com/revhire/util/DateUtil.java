package com.revhire.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    private static final SimpleDateFormat formatter =
            new SimpleDateFormat("dd-MM-yyyy");

    private DateUtil() {
        // prevent object creation
    }

    public static String formatDate(Date date) {
        if (date == null) {
            return "";
        }
        return formatter.format(date);
    }
}
