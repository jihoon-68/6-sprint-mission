package com.sprint.mission.discodeit.service.basic.file;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.file.FileMessageRepository;
import com.sprint.mission.discodeit.service.MessageService;

public class BasicFileMessageService implements MessageService {

    private final BasicFileUserService userService = new BasicFileUserService(); // 의존성 주입
    private final BasicFileChannelService channelService = new BasicFileChannelService();
    private final FileMessageRepository messageRepository = new FileMessageRepository();

    // 메시지 생성
    @Override
    public Message createMessage(User creator, Channel channel, String content) {
        channelService.validateParticipant(creator, channel);

        Message message = new Message(creator, channel, content);
        messageRepository.save(message);

        creator.getCreatedMessages().add(message);
        channel.getMessages().add(message);

        System.out.print("메시지 추가 및 저장 완료  ");
        System.out.println(channel.getName() + " - " + creator.getUserName() + " : " + message.getContent());
        return message;
    }

    // 내용 수정
    @Override
    public void editMessage(User user, Message message, String content) {
        validateWriter(user, message);
        message.setContent(content);
        message.setUpdatedAt(System.currentTimeMillis());

        messageRepository.save(message);
        System.out.println("내용 수정 완료 : " + message.getContent());
    }

    // 삭제
    @Override
    public void deleteMessage(User user, Message message) {
        validateWriter(user, message);
        messageRepository.delete(message);
        System.out.println("메시지 삭제 완료");
    }

    // 검증
    public void validateWriter(User user, Message message) {
        if (!message.getUser().equals(user)) {
            throw new RuntimeException("작성자만 수정 또는 삭제할 수 있습니다.");
        }
    }
}
