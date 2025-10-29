package com.sprint.mission.discodeit.dto.request;

import com.sprint.mission.discodeit.dto.message.BinaryContentDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public record MessageCreateRequest(
        String content,
        UUID channelId,
        UUID authorId,
        MultipartFile attachment
) {

    public CreateMessageWithContent toContentDto() throws IOException {
        BinaryContentDto binaryContent = null;

        if (attachment != null && !attachment.isEmpty()) {
            UUID tempId = UUID.randomUUID();
            binaryContent = BinaryContentDto.from(attachment, tempId);
        }

        return new CreateMessageWithContent(
                this.content,
                this.channelId,
                this.authorId,
                binaryContent
        );
    }

    public record CreateMessageWithContent(
            String content,
            UUID channelId,
            UUID authorId,
            BinaryContentDto binaryContent
    ) {}
}
