package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.data.BinaryContentDto;
import com.sprint.mission.discodeit.dto.request.BinaryContentCreateRequest;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.mapper.BinaryContentMapper;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.service.BinaryContentService;
import com.sprint.mission.discodeit.storage.BinaryContentStorage;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Slf4j
public class BasicBinaryContentService implements BinaryContentService {

  private final BinaryContentRepository binaryContentRepository;
  private final BinaryContentMapper binaryContentMapper;
  private final BinaryContentStorage binaryContentStorage;

  @Transactional
  @Override
  public BinaryContentDto create(BinaryContentCreateRequest request) {
    log.info(String.format("Create binary content request: %s", request));

    String fileName = request.fileName();
    byte[] bytes = request.bytes();
    String contentType = request.contentType();

    if (bytes == null || bytes.length == 0) {
      log.warn("Empty payload for fileName='{}'", fileName);
    }
    if (contentType == null || !contentType.contains("/")) {
      log.warn("Suspicious contentType for fileName='{}': {}", fileName, contentType);
    }

    BinaryContent entity = new BinaryContent(
        fileName,
        (long) bytes.length,
        contentType
    );

    try {
      // 1) 메타 저장
      binaryContentRepository.save(entity);
      log.debug("Meta persisted: id={}, fileName='{}'", entity.getId(), entity.getFileName());

      // 2) 실제 바이트 저장
      binaryContentStorage.put(entity.getId(), bytes);
      log.debug("Payload stored: id={}, storedSize={}", entity.getId(),
          bytes != null ? bytes.length : 0);

//      long took = System.currentTimeMillis() - start;
      log.info("BinaryContent created successfully: id={}", entity.getId());

      return binaryContentMapper.toDto(entity);

    } catch (Exception e) {
      // 실패 시 상황 로그 (메타 저장 완료 후 스토리지 실패 가능성 등)
      log.error("Failed to create BinaryContent: fileName='{}', reason={}",
          fileName, e.toString(), e);
      // 필요하면 보상 동작도 시도(예: 메타 삭제) + 해당 결과를 WARN/ERROR로 남김
      // try { binaryContentRepository.deleteById(entity.getId()); } catch (Exception ex) { log.warn("Compensation failed for id={}", entity.getId(), ex); }
      throw e; // 혹은 도메인 예외로 래핑
    } finally {
      // MDC.clear(); // 넣었으면 정리
    }
  }

  @Override
  public BinaryContentDto find(UUID binaryContentId) {
//    return binaryContentRepository.findById(binaryContentId)
//        .map(binaryContentMapper::toDto)
//        .orElseThrow(() -> new NoSuchElementException(
//            "BinaryContent with id " + binaryContentId + " not found"));

    long start = System.currentTimeMillis();
    log.info("Find binaryContent: id={}", binaryContentId);

    return binaryContentRepository.findById(binaryContentId)
        .map(entity -> {
          if (log.isDebugEnabled()) {
            log.debug("Find hit: id={}, size={}, contentType={}",
                entity.getId(), entity.getSize(), entity.getContentType());
          }
          long took = System.currentTimeMillis() - start;
          log.info("Find binaryContent success: id={}, tookMs={}", binaryContentId, took);
          return binaryContentMapper.toDto(entity);
        })
        .orElseThrow(() -> {
          long took = System.currentTimeMillis() - start;
          log.warn("Find binaryContent not found: id={}, tookMs={}", binaryContentId, took);
          return new NoSuchElementException("BinaryContent not found: " + binaryContentId);
        });
  }

  @Override
  public List<BinaryContentDto> findAllByIdIn(List<UUID> binaryContentIds) {
//    return binaryContentRepository.findAllById(binaryContentIds).stream()
//        .map(binaryContentMapper::toDto)
//        .toList();

    long start = System.currentTimeMillis();
    int total = binaryContentIds != null ? binaryContentIds.size() : 0;
    // 첫 몇 개만 샘플링해서 로그 (너무 길어지지 않게)
    List<UUID> sample = binaryContentIds.stream().limit(5).toList();
    log.info("FindAll binaryContent: count={}, sampleIds={}", total, sample);

    var entities = binaryContentRepository.findAllById(binaryContentIds);
    // 누락된 ID 파악(디버그에서만)
    if (log.isDebugEnabled()) {
      var foundIds = entities.stream().map(BinaryContent::getId).collect(java.util.stream.Collectors.toSet());
      var missing = binaryContentIds.stream().filter(id -> !foundIds.contains(id)).limit(5).toList();
      log.debug("FindAll result: found={}, missingSample={}", entities.size(), missing);
    }

    long took = System.currentTimeMillis() - start;
    log.info("FindAll binaryContent success: requested={}, returned={}, tookMs={}",
        total, entities.size(), took);

    return entities.stream().map(binaryContentMapper::toDto).toList();
  }

  @Transactional
  @Override
  public void delete(UUID binaryContentId) {
//    if (!binaryContentRepository.existsById(binaryContentId)) {
//      throw new NoSuchElementException("BinaryContent with id " + binaryContentId + " not found");
//    }
//    binaryContentRepository.deleteById(binaryContentId);

    long start = System.currentTimeMillis();
    log.info("Delete binaryContent requested: id={}", binaryContentId);

    if (!binaryContentRepository.existsById(binaryContentId)) {
      long took = System.currentTimeMillis() - start;
      log.warn("Delete skipped (not found): id={}, tookMs={}", binaryContentId, took);
      throw new NoSuchElementException("BinaryContent not found: " + binaryContentId);
    }

    try {
      // 메타/스토리지 둘 다 삭제한다면 스토리지 삭제 결과도 DEBUG로
      binaryContentRepository.deleteById(binaryContentId);
      log.debug("Meta deleted: id={}", binaryContentId);
      // 예: binaryContentStorage.delete(binaryContentId); log.debug("Payload deleted: id={}", binaryContentId);

      long took = System.currentTimeMillis() - start;
      log.info("Delete binaryContent success: id={}, tookMs={}", binaryContentId, took);
    } catch (Exception e) {
      long took = System.currentTimeMillis() - start;
      log.error("Delete binaryContent failed: id={}, tookMs={}, reason={}",
          binaryContentId, took, e.toString(), e);
      throw e; // 필요하면 도메인 예외로 래핑
    }
  }
}
