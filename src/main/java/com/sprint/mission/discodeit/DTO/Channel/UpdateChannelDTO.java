package com.sprint.mission.discodeit.DTO.Channel;

<<<<<<< HEAD
public record UpdateChannelDTO(
        String newName,
        String newDescription
=======
import java.util.UUID;

public record UpdateChannelDTO(
        UUID id,
        String name,
        String description
>>>>>>> 7c7532b (박지훈 sprint3 (#2))
) {}
