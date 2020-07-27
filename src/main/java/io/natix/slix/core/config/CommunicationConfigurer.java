package io.natix.slix.core.config;

import io.natix.slix.I18n;
import org.apache.commons.lang3.StringUtils;

public class CommunicationConfigurer {
    private String privateKey = StringUtils.EMPTY;

    private int publishPort = I18n.DEFAULT_PUBLISH_PORT;

    private int replyPort = I18n.DEFAULT_REPLY_PORT;

    public String getPrivateKey() {
        return privateKey;
    }

    public CommunicationConfigurer privateKey(String privateKey) {
        this.privateKey = privateKey;
        return this;
    }

    public int getPublishPort() {
        return publishPort;
    }

    public CommunicationConfigurer publishPort(int publishPort) {
        this.publishPort = publishPort;
        return this;
    }

    public int getReplyPort() {
        return replyPort;
    }

    public CommunicationConfigurer replyPort(int replyPort) {
        this.replyPort = replyPort;
        return this;
    }
}
