package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MessageRepository extends JpaRepository<Message, UUID> {
    Message save(Message message);
    Optional<Message> findById(UUID id);
    List<Message> findByChannelId(UUID channelId);
    List<Message> findAllByIdIn(List<UUID> ids);

    @Query("SELECT m FROM Message m WHERE m.channel.id = :channelId " +
            "AND (:cursor IS NULL OR m.createdAt < :cursor) " +
            "ORDER BY m.createdAt DESC")
    List<Message> findByChannelIdAndCursor(
            @Param("channelId") UUID channelId,
            @Param("cursor") Instant cursor,
            Pageable pageable);

    void delete(Message message);
    // void clear();
}
