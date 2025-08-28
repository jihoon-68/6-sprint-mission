package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.FileLoader;
import com.sprint.mission.discodeit.repository.UserRepository;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileUserRepository implements UserRepository {

    // 유저 저장 경로
    private static final Path USER_DIR = Paths.get("data","users");

    // 개별 파일 저장경로 반환
    private Path getFilePath(User user) {
        return USER_DIR.resolve(user.getId() + ".ser");
    }

    // 초기화
    public void clear() {
        try {
            if (Files.exists(USER_DIR)) {
                try (Stream<Path> paths = Files.list(USER_DIR)) {
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
            System.out.println("유저 저장소 초기화 완료");
        } catch (IOException e) {
            throw new RuntimeException("유저 저장소 초기화 실패", e);
        }
    }

    @Override
    public void save(User user) {
        Path path = getFilePath(user);
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(path))) {
            oos.writeObject(user);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 이름으로 유저 객체 불러오기
    @Override
    public User findByUserName(String name) {
        return findAll().stream()
                .filter(user -> user.getUserName().equals(name))
                .findFirst()
                .orElse(null);
    }

    // 이메일로 유저 객체 불러오기
    @Override
    public User findByEmail(String email) {
        return findAll().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    // 모든 유저 객체 불러오기 - 관리자용
    @Override
    public List<User> findAll() {
        try {
            if (!Files.exists(USER_DIR)) {
                Files.createDirectories(USER_DIR);
            }
            try (Stream<Path> files = Files.list(USER_DIR)) {
                return files
                        .map(file -> (User) FileLoader.loadOne(file))
                        .filter(user -> user != null)
                        .collect(Collectors.toList());
            }
        } catch (IOException e) {
            throw new RuntimeException("유저 목록 불러오기 실패", e);
        }
    }

    @Override
    public void delete(User user) {
        Path path = getFilePath(user);
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

