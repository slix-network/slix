package io.natix.slix.core.message;

import io.natix.slix.core.I18n;
import io.natix.slix.core.metadata.Language;
import org.apache.commons.lang3.StringUtils;

public interface MessageHandler {
    String name();

    default String getMessage() {
        return getMessage(I18n.getLanguage());
    }

    default Integer getCode() {
        return MessageHelper.getCode(name());
    }

    default String getMessage(Object... var1) {
        String result = MessageHelper.getMessage(I18n.getLanguage(), name(), var1);
        return StringUtils.isEmpty(result) ? this.name().replace('_', ' ').toLowerCase() : result;
    }

    default String getMessage(Language language) {
        String result = MessageHelper.getMessage(language, name());
        return StringUtils.isEmpty(result) ? this.name().replace('_', ' ').toLowerCase() : result;
    }

    default String getMessage(Language language, Object... var1) {
        String result = MessageHelper.getMessage(language, name(), var1);
        return StringUtils.isEmpty(result) ? this.name().replace('_', ' ').toLowerCase() : result;
    }

}
