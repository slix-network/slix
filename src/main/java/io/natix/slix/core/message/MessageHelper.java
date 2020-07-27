package io.natix.slix.core.message;

import io.natix.slix.core.I18n;
import io.natix.slix.core.metadata.Language;
import org.apache.commons.lang3.StringUtils;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class MessageHelper {
    public static String getMessage(String message) {
        return getMessage(I18n.getLanguage(), message);
    }

    public static Integer getCode(String message) {
        try {
            Locale locale = new Locale("code");
            ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);
            return Integer.valueOf(bundle.getString(message));
        } catch (Exception e) {
            return null;
        }
    }

    public static String getMessage(Language language, String message) {
        try {
            language = language == null ? I18n.getLanguage() : language;
            Locale locale = new Locale(language.name());
            ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);
            return bundle.getString(message);
        } catch (Exception e) {
            return StringUtils.EMPTY;
        }
    }

    public static String getMessage(String message, Object... var1) {
        return getMessage(I18n.getLanguage(), message, var1);
    }

    public static String getMessage(Language language, String message, Object... var1) {
        try {
            language = language == null ? I18n.getLanguage() : language;
            Locale locale = new Locale(language.name());
            ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);
            return MessageFormat.format(bundle.getString(message), var1);
        } catch (Exception e) {
            return StringUtils.EMPTY;
        }
    }
}
