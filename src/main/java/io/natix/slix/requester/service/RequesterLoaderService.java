package io.natix.slix.requester.service;

import io.natix.slix.requester.model.RequesterDetail;

import java.util.Optional;

public interface RequesterLoaderService<T extends RequesterDetail> {

    Optional<T> load();

}

