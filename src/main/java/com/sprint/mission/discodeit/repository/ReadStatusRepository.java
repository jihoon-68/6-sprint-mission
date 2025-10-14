package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.ReadStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ReadStatusRepository extends JpaRepository<ReadStatus, UUID> {

    @Query("SELECT rs FROM ReadStatus " +
            "rs LEFT JOIN FETCH rs.channel " +
            "LEFT JOIN FETCH rs.user " +
            "a LEFT JOIN FETCH a.profile " +
            "LEFT JOIN FETCH a.status "
    )
    List<ReadStatus> findAll();

}
