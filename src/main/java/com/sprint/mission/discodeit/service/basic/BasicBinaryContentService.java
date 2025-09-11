package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.binaryContent.model.BinaryContentDto;
import com.sprint.mission.discodeit.dto.binaryContent.request.BinaryContentCreateRequest;
import com.sprint.mission.discodeit.dto.binaryContent.response.BinaryContentFindAllResponse;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.service.BinaryContentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class BasicBinaryContentService implements BinaryContentService {
    private final BinaryContentRepository  binaryContentRepository;

    @Override
    public void createBinaryContent(BinaryContentCreateRequest request) {
        if (request.getUserId() == null && request.getMessageId() == null) {
            log.info("입력되지 않은 userId와 messageId");
            throw new IllegalArgumentException("userId와 messageId는 필수 입력입니다.");
        }

        BinaryContent binaryContent = new BinaryContent(request.getUserId(), request.getMessageId(), request.getPath());
        binaryContentRepository.save(binaryContent);
    }

    @Override
    public BinaryContentFindAllResponse findAllByMessageId(UUID id) {
        List<BinaryContent> contents = binaryContentRepository.findByMessageId(id);

        if (contents == null || contents.isEmpty()) {
            log.info("binaryContent를 찾을 수 없습니다. - id: {}", id);
            throw new IllegalArgumentException("binaryContent를 찾을 수 없습니다. id: " + id);
        }

        List<BinaryContentDto> binaryContentDtoList = new ArrayList<>();

        for (BinaryContent content : contents) {
            BinaryContentDto binaryContentDto = new BinaryContentDto();
            binaryContentDto.setId(content.getId());
            binaryContentDto.setPath(content.getPath());

            binaryContentDtoList.add(binaryContentDto);
        }
        BinaryContentFindAllResponse response = new BinaryContentFindAllResponse();
        response.setBinaryContents(binaryContentDtoList);

        return  response;
    }
}
