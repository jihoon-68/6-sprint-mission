package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.DTOs.Channel.ChannelUpdate;
import com.sprint.mission.discodeit.DTOs.Channel.ChannelView;
import com.sprint.mission.discodeit.DTOs.Channel.PrivateChannelCreate;
import com.sprint.mission.discodeit.DTOs.Channel.PublicChannel;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ChannelType;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.exception.ChannelModificationNotAllowedException;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BasicChannelService implements ChannelService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    //
    private final ChannelRepository channelRepository;
    private final ReadStatusRepository readStatusRepository;
    private final MessageRepository messageRepository;

    public BasicChannelService(ChannelRepository channelRepository,
                               ReadStatusRepository readStatusRepository,
                               MessageRepository messageRepository) {
        this.channelRepository = channelRepository;
        this.readStatusRepository = readStatusRepository;
        this.messageRepository = messageRepository;
    }

    @Override
    public Channel create(PrivateChannelCreate channel) {
        Channel newChannel = Channel.newPrivate(channel.participantIds());
        channelRepository.save(newChannel);

        for (UUID userId : channel.participantIds()) {
            var dto = new ReadStatus(userId, newChannel.getId());
            readStatusRepository.save(dto);
        }

        return newChannel;
    }

    @Override
    public Channel create(PublicChannel channel) {
        Channel newchannel = Channel.newPublic(channel.name(), channel.description());
        return channelRepository.save(newchannel);
    }

    @Override
    public ChannelView find(UUID channelId) {

        var channel = getChannelById(channelId);

        var readStatus = readStatusRepository.findByChannelId(channelId).orElseThrow();

        Set<UUID> memberIds = null;
        if (channel.getType() == ChannelType.PRIVATE) {
//            memberIds =
                    var asd = readStatusRepository.findByChannelId(channelId).stream()
                    .map(ReadStatus::getUserID)
//                    .flatMap(r -> r.)
//                    .map(s -> s.forEach(r-> r.getUserID()))
//                    .map(ReadStatus::getUserID)
                    .collect(Collectors.toCollection(HashSet::new));
        }

        var view = new ChannelView(channelId,
                channel.getType(),
                channel.getName(),
                channel.getDescription(),
                Optional.ofNullable(readStatus.()),
                memberIds
        );

        return view;
    }

//    @Override
//    public List<ChannelView> findAllByUserId(UUID id) {
//        var allChannels = channelRepository.findAll();
//
//        var privateChannels = allChannels.stream().filter(c -> c.getType().equals(ChannelType.PRIVATE)).toList();
//
//        var publicChannels = allChannels.stream().filter(c -> c.getType().equals(ChannelType.PUBLIC)).toList();
//
//        privateChannels.addAll(publicChannels);
//
//        var allchancelsID = privateChannels.stream().map(c -> c.getId()).collect(Collectors.toSet());
//
//        var recentMessageTimeMap = messageRepository.findLatestTimestampByChannelIds(allchancelsID);
//        var membersmap = readStatusRepository.findMemberIdsByChannelIds(allchancelsID);
//        return privateChannels.stream().map(c -> {
//            return new ChannelView(c.getId(),
//                    c.getType(),
//                    c.getName(),
//                    c.getDescription(),
//                    recentMessageTimeMap.get(c.getId()),
//                    membersmap.get(c.getId())
//            );
//        }).toList();
//    }

    @Override
    public List<BinaryContent> findAllByIdIn(List<UUID> ids) {
        List<BinaryContent> contents = new ArrayList<>();
        for (UUID id : ids) {
            BinaryContent binaryContent = binaryContentRepository.find(id).orElse(null);
            if (binaryContent == null)
                continue;

            contents.add(binaryContent);
        }

        return contents;
    }

    @Override
    public List<ChannelView> findAllByUserId(UUID userId) {
        List<Channel> all = channelRepository.findAll();

        // 모든 채널 ID로 멤버 맵 생성
        Set<UUID> allIds = all.stream().map(Channel::getId).collect(Collectors.toSet());
        Map<UUID, Set<UUID>> membersMap = readStatusRepository.f(allIds);

        // 볼 수 있는 채널만 필터
        List<Channel> accessible = all.stream()
                .filter(ch ->
                        ch.getType() == ChannelType.PUBLIC ||
                                membersMap.getOrDefault(ch.getId(), Set.of()).contains(userId)
                )
                .toList();

        // 배치로 최신 메시지 시간
        Set<UUID> ids = accessible.stream().map(Channel::getId).collect(Collectors.toSet());
        Map<UUID, Optional<Instant>> latestMap =
                messageRepository.findLatestTimestampByChannelIds(ids);

        return accessible.stream()
                .map(ch -> new ChannelView(
                        ch.getId(),
                        ch.getType(),
                        ch.getName(),
                        ch.getDescription(),
                        latestMap.getOrDefault(ch.getId(), Optional.empty()),
                        ch.getType() == ChannelType.PRIVATE
                                ? membersMap.getOrDefault(ch.getId(), Set.of())
                                : null
                ))
                .toList();
    }



    @Override
    public Channel update(ChannelUpdate update) {
        Channel channel = getChannelById(update.id());
        if (channel.getType().equals(ChannelType.PUBLIC)) {
            throw ChannelModificationNotAllowedException.forPrivate(update.id());
        }

        channel.update(update);
        return channelRepository.save(channel);
    }

    @Override
    public void delete(UUID channelId) {
        if (!channelRepository.existsById(channelId)) {
            throw new NoSuchElementException("Channel with id " + channelId + " not found");
        }

        if (readStatusRepository.deleteByChannelId(channelId)) {
            log.warn("ReadStatus missing while deleting Channel: {}", channelId);
        }

        messageRepository.deleteById(channelId);
        channelRepository.deleteById(channelId);
    }

    private Channel getChannelById(UUID channelId) {
        return channelRepository.findById(channelId)
                .orElseThrow(() -> new NoSuchElementException("Channel with id " + channelId + " not found"));
    }
}
