package com.sprint.mission.discodeit.service.basic.file;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.file.FileChannelRepository;
import com.sprint.mission.discodeit.service.ChannelService;

public class BasicFileChannelService implements ChannelService {

    private final FileChannelRepository channelRepository = new FileChannelRepository();

    // 채널 생성 및 저장
    @Override
    public Channel createChannel(String name, User creator) {
        if (channelRepository.findByChannelName(name) != null) {
            throw new IllegalStateException("이미 존재하는 채널명입니다.");
        }
        Channel ch = new Channel(name, creator);
        channelRepository.save(ch);
        System.out.println("채널 추가 및 저장 완료: " + ch.getName());
        return ch;
    }

    // 채널 이름 변경
    @Override
    public void renameChannel(User user, Channel ch, String newName) {
        validateCreator(user, ch);
        if (channelRepository.findByChannelName(newName) != null) {
            throw new IllegalStateException("이미 사용중인 닉네임입니다.");
        }
        ch.setName(newName);
        ch.setUpdatedAt(System.currentTimeMillis());

        channelRepository.save(ch);
        System.out.println("채널명 수정 및 저장 완료 : " + ch.getName());
    }

    // 채널 삭제
    @Override
    public void deleteChannel(User user, Channel ch) {
        validateCreator(user, ch); // 만든사람만 삭제 가능
        channelRepository.delete(ch);
        System.out.println("채널 삭제 완료");
    }

    // 수정/삭제 시 유저 검증
    @Override
    public void validateCreator(User user, Channel ch){
        if (!ch.getCreator().getId().equals(user.getId())) {
            throw new IllegalStateException("채널 수정 또는 삭제는 생성한 사람만 가능합니다.");
        }
    }

    // 채널 참여중인 사람인지 검증
    @Override
    public void validateParticipant(User user, Channel channel) {
        if (!channel.getParticipants().contains(user)) {
            throw new IllegalStateException("채널에 참여하지 않은 유저입니다.");
        }
    }
}
