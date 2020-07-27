package io.natix.slix.subject.model;

import io.natix.slix.core.model.Key;

public interface SubjectDetail {
    Key getEddsa();

    Key getEcdh();

    String getDid();
}
