package io.natix.slix.core.metadata;

public enum QuestionType {
    Relation(0),
    Position(1),
    Certificate(2),
    Permission(3),
    SHARING(4),
    STORAGE(5);

    private final Integer value;

    public Integer getValue() {
        return value;
    }

    QuestionType(Integer value) {
        this.value = value;
    }
}
