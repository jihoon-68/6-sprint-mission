package com.sprint.mission.discodeit.storage;

import com.sprint.mission.discodeit.dto.binarycontent.BinaryContentResponseDto;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Component
public class LocalBinaryContentStorage implements BinaryContentStorage{

    Path root;

    @Value("${discodeit.storage.local.root-path:./uploads}")
    private String rootPath;

    public LocalBinaryContentStorage(){
    }

    // 저장위치 규칙 정의
    Path resolvePath(UUID id){
        return this.root.resolve(id.toString());
    }

    // 루트 디렉토리 초기화
    @PostConstruct
    public void init() {
        this.root = Paths.get(rootPath);
        try {
            Files.createDirectories(root);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage location", e);
        }
    }

    @Override
    public UUID put(UUID id, byte[] bytes) {
        try {
            Path filePath = resolvePath(id);
            Files.write(filePath, bytes);
            return id;
        } catch (IOException e) {
            throw new RuntimeException("Could not store the file.", e);
        }
    }

    @Override
    public InputStream get(UUID id) {
        try {
            Path file = resolvePath(id);
            return Files.newInputStream(file);
        } catch (IOException e) {
            throw new RuntimeException("파일을 읽을 수 없습니다.", e);
        }
    }

    @Override
    public ResponseEntity<Resource> download(BinaryContentResponseDto dto) {
        try {
            InputStream inputStream = get(dto.id()); // InputStream: 바이트 데이터를 읽기 위한 표준 통로
            Resource resource = new InputStreamResource(inputStream); // Resource: 추상화된 '자원'. 파일, URL 등..

            return ResponseEntity.ok()
                    // .contentType(MediaType.parseMediaType(dto.getMimeType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dto.fileName() + "\"")
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }


}
