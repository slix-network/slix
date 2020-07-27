package io.natix.slix.prover.model;

import io.natix.slix.core.model.Key;

public interface ProverDetail {
    Key getEddsa();

    Key getEcdh();

    String getDid();
}
