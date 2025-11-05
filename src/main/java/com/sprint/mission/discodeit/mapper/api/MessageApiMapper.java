package com.sprint.mission.discodeit.mapper.api;

import com.sprint.mission.discodeit.dto.MessageDTO;
import com.sprint.mission.discodeit.dto.api.response.BinaryContentResponseDTO;
import com.sprint.mission.discodeit.dto.api.response.MessageResponseDTO;
import com.sprint.mission.discodeit.dto.api.response.MessageResponseDTO.FindMessageResponse;
import com.sprint.mission.discodeit.dto.api.response.UserResponseDTO;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class MessageApiMapper {

  public FindMessageResponse toFindMessageResponse(MessageDTO.Message message) {

    return MessageResponseDTO.FindMessageResponse.builder()
        .id(message.getId())
        .createdAt(message.getCreatedAt())
        .updatedAt(message.getUpdatedAt())
        .content(message.getContent())
        .channelId(message.getChannelId())
        .author(UserResponseDTO.FindUserResponse.builder()
            .id(message.getAuthor().getId())
            .username(message.getAuthor().getUsername())
            .email(message.getAuthor().getEmail())
            .profile(Optional.ofNullable(message.getAuthor().getProfileId())
                .map(profile -> BinaryContentResponseDTO.ReadBinaryContentResponse.builder()
                    .id(profile.getId())
                    .fileName(profile.getFileName())
                    .size(profile.getSize())
                    .contentType(profile.getContentType())
                    .build())
                .orElse(null))
            .isOnline(message.getAuthor().getIsOnline())
            .build())
        .attachments(message.getAttachments().stream().map(attachment ->
            BinaryContentResponseDTO.ReadBinaryContentResponse.builder()
                .id(attachment.getId())
                .fileName(attachment.getFileName())
                .size(attachment.getSize())
                .contentType(attachment.getContentType())
                .build()
        ).toList())
        .build();

  }

}
