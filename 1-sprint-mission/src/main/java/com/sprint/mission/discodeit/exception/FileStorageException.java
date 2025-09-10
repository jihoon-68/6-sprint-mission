package com.sprint.mission.discodeit.exception;

import java.nio.file.Path;

public class FileStorageException extends RuntimeException {
    private final String path;
    public FileStorageException(String message, String path, Throwable cause) {
        super(message, cause);
        this.path = path;
    }
    public String getPath() { return path; }

    public static FileStorageException saveFailed(Throwable c) {
        return new FileStorageException("파일 저장 실패", null, c);
    }

    public static FileStorageException deleteFailed(Throwable c) {
        return new FileStorageException("파일 삭제 실패", null, c);
    }
}