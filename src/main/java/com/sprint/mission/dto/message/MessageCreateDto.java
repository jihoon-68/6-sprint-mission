package com.sprint.mission.dto.message;

import com.sprint.mission.entity.BinaryContent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class MessageCreateDto {
    private UUID channelId;
    private UUID senderId;
    private String messageContent;
    List<BinaryContent> attachments;
}
