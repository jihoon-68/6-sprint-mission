package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.FileLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class FileBinaryContentRepository implements BinaryContentRepository {

    private static final Path BINARY_CONTENT_DIR = Paths.get("data","user-statuses");

    private Path getFilePath(BinaryContent binaryContent) {
        return BINARY_CONTENT_DIR.resolve(binaryContent.getId() + ".ser");
    }

    @Override
    public void save(BinaryContent binaryContent) {
        Path path = getFilePath(binaryContent);
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(path))) {
            oos.writeObject(binaryContent);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public BinaryContent findById(UUID id) {
        Path filePath = BINARY_CONTENT_DIR.resolve(id + ".ser");
        if (!Files.exists(filePath)) return null;
        return (BinaryContent) FileLoader.loadOne(filePath);
    }

    @Override
    public List<BinaryContent> findAllByIdIn(List<UUID> ids) {
        try {
            if (!Files.exists(BINARY_CONTENT_DIR)) {
                Files.createDirectories(BINARY_CONTENT_DIR);
                return null; // 디렉토리 없으면 null 반환
            }

            try (Stream<Path> files = Files.list(BINARY_CONTENT_DIR)) {
                return files
                        .map(FileLoader::loadOne)
                        .filter(Objects::nonNull)
                        .filter(obj -> obj instanceof BinaryContent)
                        .map(obj -> (BinaryContent) obj)
                        .filter(binaryContent -> ids.contains(binaryContent.getId()))
                        .collect(Collectors.toList());
            }
        } catch (IOException e) {
            throw new RuntimeException("ID로 ReadStatus 조회 실패", e);
        }
    }

    @Override
    public void deleteById(UUID id) {
        BinaryContent binaryContent = findById(id);
        if (binaryContent == null) {
            throw new NoSuchElementException("존재하지 않는 BinaryContent입니다. id=" + id);
        }
        Path path = getFilePath(binaryContent);
        try {
            boolean deleted = Files.deleteIfExists(path);
            if (!deleted) {

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clear() {
        try {
            if (Files.exists(BINARY_CONTENT_DIR)) {
                try (Stream<Path> paths = Files.list(BINARY_CONTENT_DIR)) {
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
            log.info("BinaryContent 저장소 초기화 완료");
        } catch (IOException e) {
            throw new RuntimeException("BinaryContent 저장소 초기화 실패", e);
        }
    }
}
