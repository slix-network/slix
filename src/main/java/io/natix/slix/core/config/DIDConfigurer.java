package io.natix.slix.core.config;

import org.apache.commons.lang3.StringUtils;

public class DIDConfigurer {

    private String uri = StringUtils.EMPTY;

    public String getUri() {
        return uri;
    }

    public DIDConfigurer uri(String uri) {
        this.uri = uri;
        return this;
    }
}
