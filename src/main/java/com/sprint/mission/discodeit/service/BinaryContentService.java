package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.binarycontent.BinaryContentCreateRequestDto;
import com.sprint.mission.discodeit.dto.binarycontent.BinaryContentResponseDto;
import com.sprint.mission.discodeit.dto.message.MessageResponseDto;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.exception.NotFoundException;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class BinaryContentService {

    private final BinaryContentRepository binaryContentRepository;

    public BinaryContentResponseDto create(BinaryContentCreateRequestDto dto){
        BinaryContent binaryContent = new BinaryContent(dto.userId(), dto.messageId(), dto.type(), dto.byteBuffer());
        binaryContentRepository.save(binaryContent);

        return new BinaryContentResponseDto(
                binaryContent.getId(),
                binaryContent.getUserId(),
                binaryContent.getMessageId(),
                binaryContent.getType(),
                binaryContent.getCreatedAt()
        );
    }

    public BinaryContentResponseDto findById(UUID id){
        BinaryContent binaryContent = binaryContentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 BinaryContent입니다."));

        return new BinaryContentResponseDto(
                binaryContent.getId(),
                binaryContent.getUserId(),
                binaryContent.getMessageId(),
                binaryContent.getType(),
                binaryContent.getCreatedAt()
        );
    }

    public List<BinaryContentResponseDto> findAllByIdIn(List<UUID> ids){
        List<BinaryContent> contents = binaryContentRepository.findAllByIdIn(ids);
        if (contents.isEmpty()) {
            throw new NotFoundException("해당하는 BinaryContent가 존재하지 않습니다.");
        }
        return contents.stream()
                .map(content -> new BinaryContentResponseDto(
                        content.getId(),
                        content.getUserId(),
                        content.getMessageId(),
                        content.getType(),
                        content.getCreatedAt()
                ))
                .toList();
    }

    public void deleteById(UUID id){
        binaryContentRepository.deleteById(id);
        log.info("BinaryContent 삭제 완료: " + id);
    }

    public void clear(){
        binaryContentRepository.clear();
    }
}
