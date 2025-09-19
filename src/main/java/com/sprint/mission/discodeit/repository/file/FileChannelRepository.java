package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.exception.NotFoundException;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.FileLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class FileChannelRepository implements ChannelRepository {

    // 채널 저장 경로
    private static final Path CHANNEL_DIR = Paths.get("data","channels");

    // 개별 파일 저장경로 반환
    public Path getFilePath(Channel channel) {
        return CHANNEL_DIR.resolve(channel.getId() + ".ser");
    }

    // 파일에 저장
    @Override
    public void save(Channel channel) {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                Files.newOutputStream(getFilePath(channel)))) {
            oos.writeObject(channel);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 채널 ID로 채널 객체 불러오기
    @Override
    public Channel findById(UUID id) {
        Path filePath = CHANNEL_DIR.resolve(id + ".ser");
        if (!Files.exists(filePath)) return null;
        return (Channel) FileLoader.loadOne(filePath);
    }

    // 전체 채널 객체 불러오기
    @Override
    public List<Channel> findAll() {
        try {
            if (!Files.exists(CHANNEL_DIR)) {
                Files.createDirectories(CHANNEL_DIR);
                return null;
            }
            try (Stream<Path> files = Files.list(CHANNEL_DIR)) {
                return files
                        .map(file -> (Channel) FileLoader.loadOne(file))
                        .filter(channel -> channel != null)
                        .collect(Collectors.toList());
            }
        } catch (IOException e) {
            throw new RuntimeException("채널 목록 불러오기 실패", e);
        }
    }

    // 삭제
    @Override
    public void delete(Channel channel) {
        Path path = getFilePath(channel);
        try {
            boolean deleted = Files.deleteIfExists(path);
            if (!deleted) {
                throw new NotFoundException("존재하지 않는 채널입니다. id=" + channel.getId());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 초기화
    @Override
    public void clear() {
        try {
            if (Files.exists(CHANNEL_DIR)) {
                try (Stream<Path> paths = Files.list(CHANNEL_DIR)) {
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
            log.info("Chanel 저장소 초기화 완료");
        } catch (IOException e) {
            throw new RuntimeException("Channel 저장소 초기화 실패", e);
        }
    }
}
