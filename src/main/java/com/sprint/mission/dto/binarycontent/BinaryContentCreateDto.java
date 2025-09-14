package com.sprint.mission.dto.binarycontent;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class BinaryContentCreateDto {

    private UUID userId;
}
