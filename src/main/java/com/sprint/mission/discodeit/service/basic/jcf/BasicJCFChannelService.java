package com.sprint.mission.discodeit.service.basic.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.jcf.JCFChannelRepository;
import com.sprint.mission.discodeit.service.ChannelService;

public class BasicJCFChannelService implements ChannelService {

    private final JCFChannelRepository channelRepository;

    public BasicJCFChannelService(JCFChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
    }

    // 채널 생성
    @Override
    public Channel createChannel(String name, User creator) {
        Channel ch = new Channel(name, creator);
        channelRepository.save(ch);
        System.out.println("채널 추가 완료: " + ch.getName());
        return ch;
    }

    // 채널명 변경
    @Override
    public void renameChannel(User user, Channel ch, String newName) {
        validateCreator(user, ch);
        if (channelRepository.findByChannelName(newName) != null) {
            throw new IllegalStateException("이미 사용중인 채널명입니다.");
        }
        ch.setName(newName);
        ch.setUpdatedAt(System.currentTimeMillis());
        System.out.println("채널명 수정 완료 : " + ch.getName());
    }

    // 채널 삭제
    @Override
    public void deleteChannel(User user, Channel ch) {
        validateCreator(user, ch); // 만든사람만 삭제 가능
        channelRepository.delete(ch);
        System.out.println("채널 삭제 완료");
    }

    // 채널 만든 사람인지 검증
    @Override
    public void validateCreator(User user, Channel ch){
        if (ch.getCreator() != user) {
            throw new IllegalStateException("채널 수정 또는 삭제는 생성인만 가능합니다.");
        }
    }

    // 채널 참여중인 사람인지
    @Override
    public void validateParticipant(User user, Channel channel) {
        if (!channel.getParticipants().contains(user)) {
            throw new IllegalStateException("채널에 참여하지 않은 유저입니다.");
        }
    }
}
