package com.sprint.mission.discodeit.storage;

import com.sprint.mission.discodeit.dto.BinaryContentDto;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class LocalBinaryContentStorage implements BinaryContentStorage {

  @Override
  public UUID put(UUID binaryContentId, byte[] data) {
    return null;
  }

  @Override
  public InputStream get(UUID binaryContentId) {
    return null;
  }

  @Override
  public ResponseEntity<?> download(BinaryContentDto binaryContentDto) {
    return null;
  }

  @Override
  public void delete(UUID binaryContentId) {

  }

  @Override
  public void deleteAllByIdIn(List<UUID> binaryContentIdList) {

  }

}
