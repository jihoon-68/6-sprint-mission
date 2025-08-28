package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.FileLoader;
import com.sprint.mission.discodeit.repository.MessageRepository;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileMessageRepository implements MessageRepository {

    // 메시지 저장 경로
    private static final Path MESSAGE_DIR = Paths.get("data","messages");

    // 개별 파일 저장경로 반환
    private Path getFilePath(Message message) {
        Path dir = MESSAGE_DIR
                .resolve(message.getChannel().getId())
                .resolve("messages");
        try {
            Files.createDirectories(dir); // 경로 보장
        } catch (IOException e) {
            throw new RuntimeException("메시지 저장 경로 생성 실패", e);
        }
        return dir.resolve(message.getId() + ".ser");
    }

    // 초기화
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
            System.out.println("메시지 저장소 초기화 완료");
        } catch (IOException e) {
            throw new RuntimeException("메시지 저장소 초기화 실패", e);
        }
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
    public Message findMessageById(String id) {
        Path filePath = MESSAGE_DIR.resolve(id + ".ser");
        return (Message) FileLoader.loadOne(filePath);
    }

    // 채널별 메시지 조회
    @Override
    public List<Message> findMessagesByChannel(Channel ch) {
        Path messageDir = MESSAGE_DIR.resolve(ch.getId()).resolve("messages");
        if (!Files.exists(messageDir)) {
            return Collections.emptyList(); // 메시지 없음
        }
        try (Stream<Path> files = Files.list(messageDir)) {
            return files
                    .filter(Files::isRegularFile)
                    .map(file -> (Message) FileLoader.loadOne(file))
                    .filter(Objects::nonNull)
                    .filter(m -> m.getChannel() != null
                            && m.getChannel().getId().equals(ch.getId())) // ID로 비교!
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("채널별 메시지 목록 불러오기 실패", e);
        }
    }

    @Override
    public void delete(Message message) {
        Path path = getFilePath(message);
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
