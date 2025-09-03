package com.sprint.mission.discodeit.exception;


import java.nio.file.Path;

public class FileSaveException extends AppException {
    private final Path path;
    public FileSaveException(Path path, Throwable cause) {
        super("FILE_SAVE_FAILED", "파일 저장 실패: " + path, cause);
        this.path = path;
    }
    public Path path() { return path; }
}