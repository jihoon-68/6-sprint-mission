package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.BaseEntity;

import java.util.Optional;
import java.util.Set;

public interface CrudRepository<T extends BaseEntity, ID> {

    T save(T t);

    Optional<T> find(ID id);

    Set<T> findAll();

    void delete(ID id);
}
