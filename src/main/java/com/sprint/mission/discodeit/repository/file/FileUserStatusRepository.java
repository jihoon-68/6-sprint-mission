package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.exception.NotFoundException;
import com.sprint.mission.discodeit.repository.FileLoader;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class FileUserStatusRepository implements UserStatusRepository {

    private static final Path USER_STATUS_DIR = Paths.get("data","user-statuses");

    private Path getFilePath(UserStatus userStatus) {
        return USER_STATUS_DIR.resolve(userStatus.getId() + ".ser");
    }

    @Override
    public void save(UserStatus userStatus) {
        Path path = getFilePath(userStatus);
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(path))) {
            oos.writeObject(userStatus);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<UserStatus> findById(UUID id) {
        Path filePath = USER_STATUS_DIR.resolve(id + ".ser");
        if (!Files.exists(filePath)) return null;
        UserStatus userStatus = (UserStatus) FileLoader.loadOne(filePath);
        return Optional.ofNullable(userStatus);
    }

    @Override
    public Optional<UserStatus> findByUserId(UUID userId) {
        return findAll().stream()
                .filter(user -> user.getUserId().equals(userId))
                .findFirst();
    }

    @Override
    public List<UserStatus> findAll() {
        try {
            if (!Files.exists(USER_STATUS_DIR)) {
                Files.createDirectories(USER_STATUS_DIR);
            }
            try (Stream<Path> files = Files.list(USER_STATUS_DIR)) {
                return files
                        .map(file -> (UserStatus) FileLoader.loadOne(file))
                        .filter(user -> user != null)
                        .collect(Collectors.toList());
            }
        } catch (IOException e) {
            throw new RuntimeException("유저 목록 불러오기 실패", e);
        }
    }

    @Override
    public void deleteById(UUID id) {
        UserStatus userStatus = findById(id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 UserStatus입니다."));
        Path path = getFilePath(userStatus);
        try {
            Files.delete(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clear() {
        try {
            if (Files.exists(USER_STATUS_DIR)) {
                try (Stream<Path> paths = Files.list(USER_STATUS_DIR)) {
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
            log.info("UserStatus 저장소 초기화 완료");
        } catch (IOException e) {
            throw new RuntimeException("UserStatus 저장소 초기화 실패", e);
        }
    }
}
