package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.exception.NotFoundException;
import com.sprint.mission.discodeit.repository.FileLoader;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import lombok.Locked;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class FileReadStatusRepository implements ReadStatusRepository {

    private static final Path READ_STATUS_DIR = Paths.get("data","read-statuses");

    private Path getFilePath(ReadStatus readStatus) {
        return READ_STATUS_DIR.resolve(readStatus.getId() + ".ser");
    }

    @Override
    public void save(ReadStatus readStatus) {
        Path path = getFilePath(readStatus);
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(path))) {
            oos.writeObject(readStatus);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<ReadStatus> findById(UUID id) {
        Path filePath = READ_STATUS_DIR.resolve(id + ".ser");
        if (!Files.exists(filePath)) return null;
        ReadStatus readStatus = (ReadStatus) FileLoader.loadOne(filePath);
        return Optional.ofNullable(readStatus);
    }

    @Override
    public List<ReadStatus> findAllByUserId(UUID userId) {
        try {
            if (!Files.exists(READ_STATUS_DIR)) {
                Files.createDirectories(READ_STATUS_DIR);
            }
            try (Stream<Path> files = Files.list(READ_STATUS_DIR)) {
                return files
                        .map(file -> (ReadStatus) FileLoader.loadOne(file))
                        .filter(Objects::nonNull)
                        .filter(readStatus -> readStatus.getUserId().equals(userId))
                        .collect(Collectors.toList());
            }
        } catch (IOException e) {
            throw new RuntimeException("유저 목록 불러오기 실패", e);
        }
    }

    @Override
    public List<ReadStatus> findAllByChannelId(UUID channelId) {
        try {
            if (!Files.exists(READ_STATUS_DIR)) {
                Files.createDirectories(READ_STATUS_DIR);
            }
            try (Stream<Path> files = Files.list(READ_STATUS_DIR)) {
                return files
                        .map(file -> (ReadStatus) FileLoader.loadOne(file))
                        .filter(Objects::nonNull)
                        .filter(readStatus -> readStatus.getChannelId().equals(channelId))
                        .collect(Collectors.toList());
            }
        } catch (IOException e) {
            throw new RuntimeException("유저 목록 불러오기 실패", e);
        }
    }

    @Override
    public void deleteById(UUID id) {
        ReadStatus readStatus = findById(id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 ReadStatus입니다."));
        Path path = getFilePath(readStatus);
        try {
            Files.delete(path);
        } catch (IOException e) {
          e.printStackTrace();
        }
    }

    @Override
    public void clear() {
        try {
            if (Files.exists(READ_STATUS_DIR)) {
                try (Stream<Path> paths = Files.walk(READ_STATUS_DIR)) {
                    paths
                            .sorted((p1, p2) -> p2.compareTo(p1)) // 하위 → 상위 순으로 정렬 (파일 먼저, 디렉토리 나중)
                            .forEach(path -> {
                                try {
                                    Files.deleteIfExists(path);
                                } catch (IOException e) {
                                    throw new RuntimeException("삭제 실패: " + path, e);
                                }
                            });
                }
            }
            log.info("ReadStatus 저장소 초기화 완료");
        } catch (IOException e) {
            throw new RuntimeException("ReadStatus 저장소 초기화 실패", e);
        }
    }
}
