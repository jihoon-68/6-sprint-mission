package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.BinaryContentDto;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.BinaryContent.OwnerType;
import com.sprint.mission.discodeit.mapper.BinaryContentMapper;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.service.BinaryContentService;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.sprint.mission.discodeit.service.basic.BasicServiceMessageConstants.BINARY_CONTENT_NOT_FOUND_BY_ID;

@Service
public class BasicBinaryContentService implements BinaryContentService {

    private final BinaryContentRepository binaryContentRepository;
    private final BinaryContentMapper binaryContentMapper;

    public BasicBinaryContentService(
            BinaryContentRepository binaryContentRepository,
            BinaryContentMapper binaryContentMapper
    ) {
        this.binaryContentRepository = binaryContentRepository;
        this.binaryContentMapper = binaryContentMapper;
    }

    @Override
    public BinaryContentDto.Response create(BinaryContentDto.Request request) {
        BinaryContent binaryContent = switch (request.ownerType()) {
            case USER_PROFILE -> binaryContentMapper.fromUserProfile(request);
            case MESSAGE_ATTACHMENT -> binaryContentMapper.fromMessageAttachment(request);
        };
        binaryContent = binaryContentRepository.save(binaryContent);
        return binaryContentMapper.toResponse(binaryContent);
    }

    @Override
    public BinaryContentDto.Response read(UUID id) {
        BinaryContent binaryContent = binaryContentRepository.find(id).orElseThrow(() ->
                new IllegalArgumentException(BINARY_CONTENT_NOT_FOUND_BY_ID.formatted(id)));
        return binaryContentMapper.toResponse(binaryContent);
    }

    @Override
    public Set<BinaryContentDto.Response> readAll(OwnerType ownerType) {
        return binaryContentRepository.findAll(ownerType)
                .stream()
                .map(binaryContentMapper::toResponse)
                .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public void delete(UUID id) {
        BinaryContent binaryContent = binaryContentRepository.find(id).orElseThrow(() ->
                new IllegalArgumentException(BINARY_CONTENT_NOT_FOUND_BY_ID.formatted(id)));
        binaryContentRepository.delete(binaryContent.getId());
    }
}
