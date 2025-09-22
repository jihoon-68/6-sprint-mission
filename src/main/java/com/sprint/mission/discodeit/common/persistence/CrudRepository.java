package com.sprint.mission.discodeit.common.persistence;

public interface CrudRepository<T extends BaseEntity, ID> {

    T save(T t);

    T findById(ID id);

    Iterable<T> findAll();

    void deleteById(ID id);
}
