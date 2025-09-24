package com.sprint.mission.discodeit.binarycontent.service;

import com.sprint.mission.discodeit.binarycontent.BinaryContentDto.Request;
import com.sprint.mission.discodeit.binarycontent.BinaryContentDto.Response;
import com.sprint.mission.discodeit.binarycontent.BinaryContentMapper;
import com.sprint.mission.discodeit.binarycontent.domain.BinaryContent;
import com.sprint.mission.discodeit.binarycontent.domain.BinaryContent.OwnerType;
import com.sprint.mission.discodeit.binarycontent.repository.BinaryContentRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class BasicBinaryContentService implements BinaryContentService {

    private final BinaryContentRepository binaryContentRepository;

    public BasicBinaryContentService(BinaryContentRepository binaryContentRepository) {
        this.binaryContentRepository = binaryContentRepository;
    }

    @Override
    public Response createBinaryContent(Request request) {
        BinaryContent binaryContent = BinaryContentMapper.from(request);
        binaryContent = binaryContentRepository.save(binaryContent);
        return BinaryContentMapper.toResponse(binaryContent);
    }

    @Override
    public Response getBinaryContentById(UUID id) {
        BinaryContent binaryContent = binaryContentRepository.findById(id);
        return BinaryContentMapper.toResponse(binaryContent);
    }

    @Override
    public Optional<Response> getUserProfileByUserId(UUID userId) {
        return binaryContentRepository.findByOwnerTypeAndOwnerId(OwnerType.USER_PROFILE, userId)
                .map(BinaryContentMapper::toResponse);
    }

    @Override
    public Set<Response> getMessageAttachmentsByMessageId(UUID messageId) {
        Iterable<BinaryContent> binaryContents =
                binaryContentRepository.findAllByOwnerTypeAndOwnerId(OwnerType.MESSAGE_ATTACHMENT, messageId);
        return StreamSupport.stream(binaryContents.spliterator(), false)
                .map(BinaryContentMapper::toResponse)
                .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public void deleteBinaryContentById(UUID id) {
        BinaryContent binaryContent = binaryContentRepository.findById(id);
        binaryContentRepository.deleteById(binaryContent.getId());
    }
}
