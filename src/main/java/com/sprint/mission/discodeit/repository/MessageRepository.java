package com.sprint.mission.discodeit.repository;


import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.Message;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MessageRepository extends JpaRepository<Message, UUID> {
    Optional<Message> findTopByChannelIdOrderByCreatedAtDesc(UUID channelId);
    Slice<Message> findByChannelIdOrderByCreatedAtDesc(UUID channelId, Pageable pageable, Limit limit);

}
