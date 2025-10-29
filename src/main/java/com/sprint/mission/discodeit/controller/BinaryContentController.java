package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.response.BinaryContentResponse;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.service.BinaryContentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;
import org.springframework.web.bind.annotation.RestController;

@Tag(name="BinaryContent")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/binaryContents")
public class BinaryContentController {

  private final BinaryContentService binaryContentService;

  @Operation(summary = "BinaryContentId 로 조회", operationId = "findBinaryContent")
  @GetMapping(path = "/{id}")
  public ResponseEntity<BinaryContentResponse> find(@PathVariable("id") UUID binaryContentId) {
    BinaryContent binaryContent = binaryContentService.find(binaryContentId);
    BinaryContentResponse response = BinaryContentResponse.from(binaryContent);
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(response);
  }

  @Operation(summary = "BinaryContentId 의 모든 Content 조회", operationId = "findAllBinaryContentByIdIn")
  @GetMapping
  public ResponseEntity<List<BinaryContentResponse>> findAllByIdIn(
      @RequestParam("binaryContentIds") List<UUID> binaryContentIds) {
    List<BinaryContent> binaryContents = binaryContentService.findAllByIdIn(binaryContentIds);
    List<BinaryContentResponse> responseList = BinaryContentResponse.fromList(binaryContents);
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(responseList);
  }

    @Operation(summary = "파일 다운로드", operationId = "downloadBinaryContent")
    @GetMapping(path = "/{pathBinaryContentId}/download")
    public ResponseEntity<?> download(
            @PathVariable("pathBinaryContentId") UUID pathBinaryContentId,
            @RequestParam("binaryContentId") UUID paramBinaryContentId) {
        return binaryContentService.download(paramBinaryContentId);
    }
}

