package com.sprint.mission.discodeit.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
  // 🧍 USER DOMAIN
  USER_NOT_FOUND(HttpStatus.NOT_FOUND, "U001", "유저를 찾을 수 없습니다."),
  DUPLICATE_USER(HttpStatus.CONFLICT, "U002", "유저가 이미 존재합니다."),
  INVALID_USER_CREDENTIAL(HttpStatus.UNAUTHORIZED, "U003", "잘못된 로그인 정보입니다."),

  // 💬 CHANNEL DOMAIN
  CHANNEL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C000", "채널관련 오류가 발생했습니다."),
  CHANNEL_NOT_FOUND(HttpStatus.NOT_FOUND, "C001", "채널을 찾을 수 없습니다."),
  PRIVATE_CHANNEL_UPDATE(HttpStatus.FORBIDDEN, "C002", "개인 채널은 수정할 수 없습니다."),

  // 📁 FILE / BINARY DOMAIN
  FILE_READ_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "F001", "파일을 읽는 중 오류가 발생했습니다."),
  FILE_EMPTY(HttpStatus.BAD_REQUEST, "F002", "업로드된 파일이 비어 있습니다."),
  FILE_UNSUPPORTED_MEDIA_TYPE(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "F003", "지원되지 않는 파일 형식입니다."),

  // ⚙️ COMMON / GLOBAL
  BAD_REQUEST(HttpStatus.BAD_REQUEST, "G001", "잘못된 요청입니다."),
  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "G002", "서버 내부 오류입니다.");

  private final HttpStatus status;
  private final String code;
  private final String message;

  ErrorCode(final HttpStatus status, final String code, final String message) {
    this.status = status;
    this.code = code;
    this.message = message;
  }
}