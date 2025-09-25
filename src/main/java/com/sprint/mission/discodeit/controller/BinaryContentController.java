package com.sprint.mission.discodeit.controller;

import com.fasterxml.jackson.databind.introspect.TypeResolutionContext;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.service.BinaryContentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Tag(name = "BinaryContent", description = "첨부 파일 API")
@RequestMapping("api/binaryContents")
@Controller
@RequiredArgsConstructor
public class BinaryContentController {
    private final BinaryContentService binaryContentService;


    @Operation(summary = "첨부 파일 조회")
    @ApiResponse(responseCode = "200", description = "첨부 파일 조회 성공")
    @ApiResponse(responseCode = "404", description = "첨부 파일을 찾을 수 없음")
    @GetMapping(value = "/{binaryContentId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BinaryContent> getBinaryContent(
            @Schema(description = "조회할 첨부 파일 ID")
            @PathVariable UUID binaryContentId) {
        return ResponseEntity.status(HttpStatus.OK).body(binaryContentService.findById(binaryContentId));
    }

    @Operation(summary = "조회할 첨부 파일 ID 목록")
    @ApiResponse(responseCode = "200", description = "첨부 파일 목록 조회 성공")
    @GetMapping(consumes = MediaType.ALL_VALUE)
    public ResponseEntity<List<BinaryContent>> getAllBinaryContent(
            @Schema(description = "조회할 첨부 파일 ID 목록")
            @RequestParam List<UUID> binaryContentIds
    ) {
        List<BinaryContent> BinaryContentList = binaryContentIds.stream().
                map(binaryContentService::findById)
                .toList();
        return ResponseEntity.status(HttpStatus.OK).body(BinaryContentList);
    }

}
