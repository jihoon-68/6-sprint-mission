package com.sprint.mission.discodeit.storage;

import com.sprint.mission.discodeit.dto.BinaryContentDto;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;

public interface BinaryContentStorage {

  UUID put(UUID binaryContentId, byte[] data);

  InputStream get(UUID binaryContentId);

  ResponseEntity<?> download(BinaryContentDto binaryContentDto);

  void delete(UUID binaryContentId);

  void deleteAllByIdIn(List<UUID> binaryContentIdList);

}
