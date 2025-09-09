package com.sprint.mission.discodeit.entity;

import lombok.Getter;

import java.io.File;
import java.util.UUID;

@Getter
public class BinaryContent {
    private UUID id;
    private File image;

    public BinaryContent(String imagePath){
        this.id = UUID.randomUUID();
        this.image = new File(imagePath);
    }
}
