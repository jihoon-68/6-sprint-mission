package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.ReadStatus;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class FileInitSaveLoad {

  public static void init(Path directory) {
    // 저장할 경로의 파일 초기화
    if (!Files.exists(directory)) {
      try {
        Files.createDirectories(directory);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }

  public static <T> void save(Path filePath, T data) {
    try (
        FileOutputStream fos = new FileOutputStream(filePath.toFile());
        ObjectOutputStream oos = new ObjectOutputStream(fos);
    ) {
      oos.writeObject(data);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static <T> List<T> load(Path directory) {
    if (Files.exists(directory)) {
      try (
          Stream<Path> paths = Files.list(directory)) {
        return paths
            .map(path -> {
              try (
                  FileInputStream fis = new FileInputStream(path.toFile());
                  ObjectInputStream ois = new ObjectInputStream(fis)
              ) {
                Object data = ois.readObject();
                return (T) data;
              } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
              }
            })
            .toList();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    } else {
      return new ArrayList<>();
    }
  }
}
