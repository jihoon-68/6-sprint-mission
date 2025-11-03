package com.sprint.mission.discodeit.storage;

import com.sprint.mission.discodeit.dto.binarycontent.BinaryContentResponseDto;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Component
public class LocalBinaryContentStorage implements BinaryContentStorage{

    Path root;

    public LocalBinaryContentStorage(
            @Value("${discodeit.storage.local.root-path:./uploads}") String rootPath
    ){
        this.root = Paths.get(rootPath);
    }

    // 루트 디렉토리 초기화
    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(root);
        } catch (IOException e) {
            throw new RuntimeException("초기화에 실패했습니다.", e);
        }
    }

    // 로컬에 저장
    @Override
    public UUID put(UUID id, byte[] bytes) {
        try {
            Path filePath = resolvePath(id);
            Files.write(filePath, bytes);
            return id;
        } catch (IOException e) {
            throw new RuntimeException("업로드에 실패했습니다.", e);
        }
    }

    // 저장된 파일 읽기
    @Override
    public InputStream get(UUID id) {
        try {
            Path filePath = resolvePath(id);
            return Files.newInputStream(filePath);
        } catch (IOException e) {
            throw new RuntimeException("파일을 읽을 수 없습니다.", e);
        }
    }

    // 파일 다운로드
    @Override
    public ResponseEntity<Resource> download(BinaryContentResponseDto dto) {
        try {
            log.info("다운로드를 시작합니다.");
            InputStream inputStream = get(dto.id()); // InputStream: 바이트 데이터를 읽기 위한 표준 통로
            Resource resource = new InputStreamResource(inputStream); // Resource: 추상화된 '자원'. 파일, URL 등..

            return ResponseEntity.ok()
                    // .contentType(MediaType.parseMediaType(dto.getMimeType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dto.fileName() + "\"")
                    .body(resource);
        } catch (Exception e) {
            log.error("다운로드 실패 - id: {}, 원인: {}", dto.id(), e.getMessage(), e);
            throw new RuntimeException("다운로드에 실패했습니다.");
        }
    }

    // 저장위치 규칙 정의
    Path resolvePath(UUID id){
        return this.root.resolve(id.toString());
    }

}
