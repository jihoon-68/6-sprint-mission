package com.sprint.mission.discodeit.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

// application.yml에 정의된 설정값(파일 디렉토리)을 자동으로 자바 객체로 매핑
@Component
@Getter @Setter
@ConfigurationProperties(prefix = "discodeit.repository")
public class RepositoryProperties {
    private String type;
    private String fileDirectory;
}
