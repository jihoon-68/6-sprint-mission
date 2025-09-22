package com.sprint.mission.discodeit;

import com.sprint.mission.discodeit.common.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackageClasses = AppConfig.class)
public class DiscodeitApplication {

    public static void main(String... args) {
        SpringApplication.run(DiscodeitApplication.class, args);
    }
}
