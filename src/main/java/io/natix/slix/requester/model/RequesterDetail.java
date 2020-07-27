package io.natix.slix.requester.model;

import io.natix.slix.core.model.Key;

public interface RequesterDetail {
    Key getEddsa();

    Key getEcdh();

    String getDid();
}
