package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.abstractservice.AbstractMessageService;

public class FileMessageService extends AbstractMessageService {
    public FileMessageService(MessageRepository messageRepository) {
        super(messageRepository);
    }
}
