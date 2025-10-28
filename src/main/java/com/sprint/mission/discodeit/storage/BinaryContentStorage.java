package com.sprint.mission.discodeit.storage;

import com.sprint.mission.discodeit.dto.BinaryContent.BinaryContentDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.UUID;


public interface BinaryContentStorage {
    UUID put(UUID id, byte[] data);
    InputStream get(UUID id);
    ResponseEntity<?> download(BinaryContentDto binaryContentDto);
}
