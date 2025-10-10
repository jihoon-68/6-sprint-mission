package com.sprint.mission.discodeit.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter(AccessLevel.PACKAGE)
@NoArgsConstructor
@Table(name = "binary_contents")
public class BinaryContent extends BaseEntity{

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private Long size;

    @Column(nullable = false, length = 100)
    private String contentType;


    //채널에 파일 업로드
    public BinaryContent(String fileName, Long size, String contentType) {
        super();
        this.fileName = fileName;
        this.size = size;
        this.contentType= contentType;
    }


}
