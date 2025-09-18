package com.sprint.mission.discodeit.dto.data;

import com.sprint.mission.discodeit.dto.request.BinaryContentCreateRequest;
import com.sprint.mission.discodeit.dto.request.MessageCreateRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class MessageSendDto {
    private MessageCreateRequest messageCreateRequest;
    private List<BinaryContentCreateRequest> binaryContentCreateRequests;

    public MessageSendDto(MessageCreateRequest messageCreateRequest){
        this.messageCreateRequest = messageCreateRequest;
        this.binaryContentCreateRequests = new ArrayList<>();
    }

    public MessageSendDto(MessageCreateRequest messageCreateRequest, List<BinaryContentCreateRequest> binaryContentCreateRequests){
        this.messageCreateRequest = messageCreateRequest;
        this.binaryContentCreateRequests = binaryContentCreateRequests;
    }
}
