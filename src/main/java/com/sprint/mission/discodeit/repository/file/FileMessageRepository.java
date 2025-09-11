package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.FileLoader;
import com.sprint.mission.discodeit.repository.MessageRepository;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileMessageRepository implements MessageRepository {

    // 메시지 저장 경로
    private static final Path MESSAGE_DIR = Paths.get("data","messages");

    // 개별 파일 저장경로 반환
    private Path getFilePath(Message message) {
        Path dir = MESSAGE_DIR
                .resolve(String.valueOf(message.getChannelId()))
                .resolve("messages");
        try {
            Files.createDirectories(dir); // 경로 보장
        } catch (IOException e) {
            throw new RuntimeException("메시지 저장 경로 생성 실패", e);
        }
        return dir.resolve(message.getId() + ".ser");
    }

    @Override
    public void save(Message message) {
        Path path = getFilePath(message);
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(path))) {
            oos.writeObject(message);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 메시지 단건 조회
    @Override
    public Message findById(UUID messageId) {
        try {
            return Files.walk(MESSAGE_DIR)
                    .filter(path -> path.getFileName().toString().equals(messageId + ".ser"))
                    .findFirst()
                    .map(FileLoader::loadOne)
                    .map(obj -> (Message) obj)
                    .orElse(null);
        } catch (IOException e) {
            throw new RuntimeException("메시지 탐색 실패", e);
        }
    }

    // 채널별 메시지 조회
    @Override
    public List<Message> findByChannelId(UUID channelId) {
        Path messageDir = MESSAGE_DIR.resolve(channelId.toString()).resolve("messages");
        if (!Files.exists(messageDir)) {
            return Collections.emptyList(); // 메시지 없음
        }
        try (Stream<Path> files = Files.list(messageDir)) {
            return files
                    .filter(Files::isRegularFile)
                    .map(file -> (Message) FileLoader.loadOne(file))
                    .filter(Objects::nonNull)
                    .filter(message -> message.getChannelId() != null
                            && message.getChannelId().equals(channelId)) // ID로 비교
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("채널별 메시지 목록 불러오기 실패", e);
        }
    }

    @Override
    public List<Message> findAllByIdIn(List<UUID> ids) {
        try {
            if (!Files.exists(MESSAGE_DIR)) {
                Files.createDirectories(MESSAGE_DIR);
                return null; // 디렉토리 없으면 만들고 null 반환
            }

            try (Stream<Path> files = Files.walk(MESSAGE_DIR)) {
                return files
                        .map(FileLoader::loadOne)
                        .filter(Objects::nonNull)
                        .filter(obj -> obj instanceof Message)
                        .map(obj -> (Message) obj)
                        .filter(message -> ids.contains(message.getId()))
                        .collect(Collectors.toList());
            }
        } catch (IOException e) {
            throw new RuntimeException("ID로 ReadStatus 조회 실패", e);
        }
    }

    @Override
    public void delete(Message message) {
        Path path = getFilePath(message);
        try {
            boolean deleted = Files.deleteIfExists(path);
            if (!deleted) {
                throw new NoSuchElementException("존재하지 않는 UserStatus입니다. id=" + message.getId());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 초기화
    @Override
    public void clear() {
        try {
            if (Files.exists(MESSAGE_DIR)) {
                Files.walk(MESSAGE_DIR)
                        .sorted((p1, p2) -> p2.compareTo(p1)) // 하위 → 상위 순으로 정렬 (파일 먼저, 디렉토리 나중)
                        .forEach(path -> {
                            try {
                                Files.deleteIfExists(path);
                            } catch (IOException e) {
                                throw new RuntimeException("삭제 실패: " + path, e);
                            }
                        });
            }
            System.out.println("Message 저장소 초기화 완료");
        } catch (IOException e) {
            throw new RuntimeException("Message 저장소 초기화 실패", e);
        }
    }
}
