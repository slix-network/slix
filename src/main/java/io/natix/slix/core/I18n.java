package io.natix.slix.core;


import io.natix.slix.core.metadata.Calendar;
import io.natix.slix.core.metadata.Language;

public class I18n {
    public static Language DEFAULT_LANGUAGE = Language.FA;

    public static Calendar DEFAULT_CALENDAR = Calendar.JALALI;

    public static String DEFAULT_TIME_ZONE = "Asia/Tehran";

    public static Language getLanguage() {
        return DEFAULT_LANGUAGE;
    }
}
