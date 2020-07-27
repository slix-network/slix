package io.natix.slix.prover.service;

import io.natix.slix.prover.model.ProverDetail;

import java.util.Optional;

public interface ProverLoaderService <T extends ProverDetail> {

    Optional<T> load();

}

