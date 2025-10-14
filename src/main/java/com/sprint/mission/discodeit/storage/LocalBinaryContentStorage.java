package com.sprint.mission.discodeit.storage;

import com.sprint.mission.discodeit.dto.BinaryContent.BinaryContentDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.UUID;

@Service
@ConditionalOnExpression("'${discodeit.repository.type}'=='local'")
public class LocalBinaryContentStorage implements BinaryContentStorage {
    private final Path root;

    LocalBinaryContentStorage(@Value("${discodeit.repository.local.root-path}") Path root) {
        this.root = root;
        init();
    }

    private Path resolvePath(UUID id) {
        return root.resolve(id.toString());
    }

    void init() {
        System.out.println(root.toString());
        File directory = new File(root.toString());
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
        try (FileOutputStream fos = new FileOutputStream(path.toFile());) {
            fos.write(data);
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
            try {
                stream = new FileInputStream(path.toFile());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return stream;
    }

    @Override
    public ResponseEntity<?> download(BinaryContentDto binaryContentDto) {
        try (InputStream stream = get(binaryContentDto.id())){

            byte[] bytes = stream.readAllBytes();
            String encodedFileName = URLEncoder.encode(
                    binaryContentDto.fileName(),
                    StandardCharsets.UTF_8
            ).replace("\\+", "%20");

            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.parseMediaType(binaryContentDto.contentType()))
                    .contentLength(binaryContentDto.size())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encodedFileName + "\"")
                    .body(bytes);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
