package com.sprint.mission.discodeit.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sprint.mission.discodeit.dto.data.ChannelDto;
import com.sprint.mission.discodeit.dto.data.UserDto;
import com.sprint.mission.discodeit.dto.request.PublicChannelUpdateRequest;
import com.sprint.mission.discodeit.entity.ChannelType;
import com.sprint.mission.discodeit.exception.ErrorCode;
import com.sprint.mission.discodeit.exception.GlobalExceptionHandler;
import com.sprint.mission.discodeit.exception.UpdatePrivateChannelException;
import com.sprint.mission.discodeit.service.ChannelService;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ChannelController.class)
@Import(GlobalExceptionHandler.class)
@TestPropertySource(properties = "spring.test.context.bean.override.mode=override")
@DisplayName("ChannelController")
class ChannelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ChannelService channelService;

    @Test
    @DisplayName("GET /api/channels - 사용자가 접근 가능한 채널 목록 반환")
    void findAll_success() throws Exception {
        UUID userId = UUID.randomUUID();
        UUID channelId = UUID.randomUUID();
        ChannelDto channelDto = new ChannelDto(
            channelId,
            ChannelType.PUBLIC,
            "general",
            "desc",
            List.of(new UserDto(userId, "john", "john@example.com", null, true)),
            Instant.now()
        );
        given(channelService.findAllByUserId(userId)).willReturn(List.of(channelDto));

        mockMvc.perform(get("/api/channels").param("userId", userId.toString()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(channelId.toString()))
            .andExpect(jsonPath("$[0].name").value("general"));
    }

    @Test
    @DisplayName("PATCH /api/channels/{id} - Private 채널 수정 시 403 반환")
    void update_failure_privateChannel() throws Exception {
        UUID channelId = UUID.randomUUID();
        PublicChannelUpdateRequest request = new PublicChannelUpdateRequest("new", "desc");
        willThrow(new UpdatePrivateChannelException(Map.of("channelId", channelId)))
            .given(channelService).update(eq(channelId), any(PublicChannelUpdateRequest.class));

        mockMvc.perform(patch("/api/channels/{channelId}", channelId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isForbidden());
    }
}
