package io.natix.slix.core.payload;

import java.util.Arrays;

public class Form implements Payload {
    private String id;

    private String name;

    private String title;

    private Field[] fields;

    private Boolean forwardingPermission;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Field[] getFields() {
        return fields;
    }

    public void setFields(Field[] fields) {
        this.fields = fields;
    }

    public Boolean getForwardingPermission() {
        return forwardingPermission;
    }

    public void setForwardingPermission(Boolean forwardingPermission) {
        this.forwardingPermission = forwardingPermission;
    }

    public Form() {
    }

    public Form(String id, String name, String title, Field[] fields, Boolean forwardingPermission) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.fields = fields;
        this.forwardingPermission = forwardingPermission;
    }

    @Override
    public String toString() {
        return "Form{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", fields=" + Arrays.toString(fields) +
                ", forwardingPermission=" + forwardingPermission +
                '}';
    }
}
