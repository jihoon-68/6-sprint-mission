package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.abstractservice.AbstractMessageService;

import java.util.*;
import java.util.stream.Collectors;



public class JCFMessageService extends AbstractMessageService {

    public JCFMessageService(MessageRepository messageRepository) {
        super(messageRepository);
    }
}
