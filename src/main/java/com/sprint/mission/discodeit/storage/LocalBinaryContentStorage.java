package com.sprint.mission.discodeit.storage;

import com.sprint.mission.discodeit.dto.BinaryContent.BinaryContentDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Service
@ConditionalOnExpression("'${discodeit.repository.type}'='local'")
public class LocalBinaryContentStorage implements BinaryContentStorage {
    private final Path root;

    LocalBinaryContentStorage(@Value("${discodeit.repository.local.root-path}") Path root) {
        this.root = root;
        init();
    }

    private Path resolvePath(UUID id) {
        return root.resolve(id + ".ser");
    }

    void init() {
        File directory = new File(root.toUri());
        if (!directory.exists()) {
            // 경로가 존재하지 않을 때
            boolean ok = directory.mkdirs();
            if (!ok) {
                System.out.println("디랙토리 생성 안됨");
            }
        }
    }

    @Override
    public UUID put(UUID id, byte[] data) {
        Path path = resolvePath(id);
        try (
                FileOutputStream fos = new FileOutputStream(path.toFile());
                ObjectOutputStream oos = new ObjectOutputStream(fos)
        ) {
            oos.writeObject(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return id;
    }

    @Override
    public InputStream get(UUID id) {
        InputStream stream = null;
        Path path = resolvePath(id);
        if (Files.exists(path)) {
            try (FileInputStream fis = new FileInputStream(path.toFile())) {
                stream = fis;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return stream;
    }

    @Override
    public ResponseEntity<?> download(BinaryContentDto binaryContentDto) {
        Path path = resolvePath(binaryContentDto.id());
        byte[] data = null;
        try {
            data = get(binaryContentDto.id()).readAllBytes();
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(new BinaryContentDto(binaryContentDto, data));
    }
}
