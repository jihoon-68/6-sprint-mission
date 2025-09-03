package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.FileLoader;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileChannelRepository implements ChannelRepository {

    // 채널 저장 경로
    private static final Path CHANNEL_DIR = Paths.get("data","channels");

    // 개별 파일 저장경로 반환
    public Path getFilePath(Channel ch) {
        return CHANNEL_DIR.resolve(ch.getId() + ".ser");
    }

    // 초기화
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
            System.out.println("채널 저장소 초기화 완료");
        } catch (IOException e) {
            throw new RuntimeException("채널 저장소 초기화 실패", e);
        }
    }

    // 파일에 저장
    @Override
    public void save(Channel ch) {
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(getFilePath(ch)))) {
            oos.writeObject(ch);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 채널 이름으로 채널 객체 불러오기
    @Override
    public Channel findByChannelName(String name) {
        return findAll().stream()
                .filter(ch -> ch.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    // 전체 채널 객체 불러오기
    @Override
    public List<Channel> findAll() {
        try {
            if (!Files.exists(CHANNEL_DIR)) {
                Files.createDirectories(CHANNEL_DIR);
            }
            try (Stream<Path> files = Files.list(CHANNEL_DIR)) {
                return files
                        .map(file -> (Channel) FileLoader.loadOne(file))
                        .filter(ch -> ch != null)
                        .collect(Collectors.toList());
            }
        } catch (IOException e) {
            throw new RuntimeException("채널 목록 불러오기 실패", e);
        }
    }

    // 삭제
    public void delete(Channel ch) {
        Path path = getFilePath(ch);
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
