package io.natix.slix.core.payload;

import io.natix.slix.core.metadata.FieldKind;

public class Field implements Payload {
    private String id;

    private String name;

    private String title;

    private FieldKind kind;

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

    public FieldKind getKind() {
        return kind;
    }

    public void setKind(FieldKind kind) {
        this.kind = kind;
    }

    public Field() {
    }

    public Field(String id, String name, String title, FieldKind kind) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.kind = kind;
    }

    @Override
    public String toString() {
        return "Field{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", kind=" + kind +
                '}';
    }
}
