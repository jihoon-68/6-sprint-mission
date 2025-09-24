package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Repository
@ConditionalOnProperty(
    prefix = "discodeit.repository",
    name = "type",
    havingValue = "file"
)
public class FileReadStatusRepository implements ReadStatusRepository {

  public final Path directory = Paths.get(System.getProperty("user.dir"), "file-data",
      "ReadStatusData");

  private Path resolvePath(UUID id) {
    return directory.resolve(id + ".ser");
  }

  @Override
  public ReadStatus save(ReadStatus readStatus) {
    FileInitSaveLoad.init(directory);

    Path filePath = resolvePath(readStatus.getId());
    FileInitSaveLoad.<ReadStatus>save(filePath, readStatus);

    return readStatus;
  }

  @Override
  public Optional<ReadStatus> findById(UUID Id) {
    return FileInitSaveLoad.<ReadStatus>load(directory).stream()
        .filter(readStatus -> readStatus.getId().equals(Id)).findAny();
  }


  @Override
  public List<ReadStatus> findAllByUserId(UUID userId) {
    return FileInitSaveLoad.<ReadStatus>load(directory).stream()
        .filter(readStatus -> readStatus.getUserId().equals(userId)).toList();
  }

  @Override
  public List<ReadStatus> findAllByChannelId(UUID channelId) {
    return FileInitSaveLoad.<ReadStatus>load(directory).stream()
        .filter(readStatus -> readStatus.getChannelId().equals(channelId)).toList();
  }

  @Override
  public boolean existsById(UUID id) {
    Path path = resolvePath(id);
    return Files.exists(path);
  }

  @Override
  public void deleteById(UUID id) {
    Path path = resolvePath(id);
    try {
      Files.delete(path);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void deleteAllByChannelId(UUID channelId) {
    this.findAllByChannelId(channelId)
        .forEach(readStatus -> this.deleteById(readStatus.getId()));
  }
}
