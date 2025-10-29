package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.ReadStatus;

import java.util.List;
import java.util.UUID;

import com.sprint.mission.discodeit.entity.ReadStatusId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReadStatusRepository extends JpaRepository<ReadStatus, ReadStatusId> {

  List<ReadStatus> findAllByPkUser_Id(UUID userId);

  List<ReadStatus> findAllByPkChannel_Id(UUID channelId);

  void deleteAllByPkChannel_Id(UUID channelId);
}
