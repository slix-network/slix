package io.natix.slix.subject.service;

import io.natix.slix.subject.model.SubjectDetail;

import java.util.Optional;

public interface SubjectLoaderService<T extends SubjectDetail> {

    Optional<T> load();

}
