package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.MessageService;

import java.util.*;

public class BasicMessageService implements MessageService {
    private final MessageRepository repo;
    private final UserRepository userRepo;
    private final ChannelRepository channelRepo;

    public BasicMessageService(MessageRepository repo, UserRepository userRepo, ChannelRepository channelRepo) {
        this.repo = repo;
        this.userRepo = userRepo;
        this.channelRepo = channelRepo;
    }

    @Override
    public Message create(String content, UUID channelId, UUID authorId) {
        channelRepo.findById(channelId).orElseThrow(() -> new IllegalArgumentException("Channel not found"));
        userRepo.findById(authorId).orElseThrow(() -> new IllegalArgumentException("Author not found"));
        return repo.save(new Message(content, channelId, authorId));
    }

    @Override public Optional<Message> findById(UUID id) { return repo.findById(id); }
    @Override public List<Message> findAll() { return repo.findAll(); }

    @Override
    public Optional<Message> update(UUID id, String content, UUID channelId, UUID authorId) {
        Optional<Message> opt = repo.findById(id);
        if (opt.isEmpty()) return Optional.empty();
        Message m = opt.get();
        UUID newChannel = channelId != null ? channelId : m.getChannelId();
        UUID newAuthor = authorId != null ? authorId : m.getAuthorId();
        channelRepo.findById(newChannel).orElseThrow(() -> new IllegalArgumentException("Channel not found"));
        userRepo.findById(newAuthor).orElseThrow(() -> new IllegalArgumentException("Author not found"));
        m.update(content != null ? content : m.getContent(), newChannel, newAuthor);
        repo.save(m);
        return Optional.of(m);
    }

    @Override public boolean delete(UUID id) { return repo.deleteById(id); }
}
