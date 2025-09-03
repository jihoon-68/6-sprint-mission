package com.sprint.mission.discodeit.service.basic.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.jcf.JCFChannelRepository;
import com.sprint.mission.discodeit.repository.jcf.JCFMessageRepository;
import com.sprint.mission.discodeit.repository.jcf.JCFUserRepository;
import com.sprint.mission.discodeit.service.MessageService;

import java.util.*;

public class BasicJCFMessageService implements MessageService {

    private final List<Message> data = new ArrayList<>();

    // 의존성 주입
    private final JCFUserRepository userRepository = new JCFUserRepository();
    private final JCFChannelRepository channelRepository  = new JCFChannelRepository();
    private final JCFMessageRepository messageRepository;
    private final BasicJCFUserService userService = new BasicJCFUserService(userRepository);
    private final BasicJCFChannelService channelService = new BasicJCFChannelService(channelRepository);

    public BasicJCFMessageService(JCFMessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    // 메시지 생성
    @Override
    public Message createMessage(User creator, Channel channel, String content) {
        channelService.validateParticipant(creator, channel); // 유저 검증

        Message message = new Message(creator, channel, content);
        messageRepository.save(message);

        creator.getCreatedMessages().add(message);
        channel.getMessages().add(message);

        System.out.println("메시지 추가 완료. ");
        System.out.println(channel.getName() + " - " + creator.getUserName() + " : " + message.getContent());
        return message;
    }

    // 내용 수정
    @Override
    public void editMessage(User user, Message message, String content) {
        validateWriter(user, message);
        message.setContent(content);
        message.setUpdatedAt(System.currentTimeMillis());
        System.out.println("내용 수정 완료 : " + message.getContent());
    }

    // 메시지 삭제
    @Override
    public void deleteMessage(User user, Message message) {
        validateWriter(user, message);
        messageRepository.delete(message);
    }

    // 작성자 검증
    public void validateWriter(User user, Message message) {
        if (!message.getUser().equals(user)) {
            throw new RuntimeException("작성자만 수정 또는 삭제할 수 있습니다.");
        }
    }
}