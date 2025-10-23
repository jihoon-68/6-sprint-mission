package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.ReadStatus;
import lombok.Locked;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReadStatusRepository extends JpaRepository<ReadStatus, UUID> {
    ReadStatus save(ReadStatus readStatus);
    Optional<ReadStatus> findById(UUID id);
    List<ReadStatus> findAllByUserId(UUID id);
    List<ReadStatus> findAllByChannelId(UUID id);

    @Query("""
    SELECT DISTINCT rs FROM ReadStatus rs
    LEFT JOIN FETCH rs.user u
    LEFT JOIN FETCH u.userStatus
    LEFT JOIN FETCH u.profile
    WHERE rs.channel.id = :channelId
""")
    List<ReadStatus> findAllByChannelIdWithUser(@Param("channelId") UUID channelId);

    void deleteById(UUID id);
    // void clear();
}
