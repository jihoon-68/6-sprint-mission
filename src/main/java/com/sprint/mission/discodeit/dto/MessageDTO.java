package com.sprint.mission.discodeit.dto;

import com.sprint.mission.discodeit.dto.BinaryContentDTO.BinaryContentCreateCommand;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MessageDTO {

  @Builder
  public record CreateMessageCommand(String content, UUID channelId, UUID userId,
                                     List<BinaryContentCreateCommand> binaryContentList) {

  }

  @Getter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Message {

    private UUID id;
    private Instant createdAt;
    private Instant updatedAt;
    private UUID channelId;
    private UserDTO.User author;
    private String content;
    private List<BinaryContentDTO.BinaryContent> attachments = new ArrayList<>();

  }

  //message update를 위한 Request DTO
  @Builder
  public record UpdateMessageCommand(UUID id, String content) {

  }

}
