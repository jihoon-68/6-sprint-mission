package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.request.PublicChannelCreateRequest;
import com.sprint.mission.discodeit.dto.request.PublicChannelUpdateRequest;
import com.sprint.mission.discodeit.service.ChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.NoSuchElementException;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class ChannelController {
    private final ChannelService channelService;

    @RequestMapping("/channel/create/public")
    public ResponseEntity<Object> createPublicChannel(@RequestBody PublicChannelCreateRequest publicChannelCreateRequest) {
        try{
            channelService.create(publicChannelCreateRequest);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @RequestMapping("/channel/create/private")
    public ResponseEntity<Object> createPrivateChannel(@RequestBody PublicChannelCreateRequest privateChannelCreateRequest) {
        try {
            channelService.create(privateChannelCreateRequest);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @RequestMapping("/channel/public/update/{channelId}")
    public ResponseEntity<Object> updatePublicChannel(@PathVariable String channelId, @RequestBody PublicChannelUpdateRequest publicChannelUpdateRequest){
        try {
            channelService.update(UUID.fromString(channelId), publicChannelUpdateRequest);
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body("Channel with id " + channelId + " not found: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @RequestMapping("/channel/remove/{channelId}")
    public ResponseEntity<Object> removeChannel(@PathVariable String channelId) {
        try {
            channelService.delete(UUID.fromString(channelId));
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body("Channel with id " + channelId + " not found: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @RequestMapping("/channel/list/{userId}")
    public ResponseEntity<Object> listChannels(@PathVariable String userId) {
        try {
            return ResponseEntity.ok().body(channelService.findAllByUserId(UUID.fromString(userId)));
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

}
