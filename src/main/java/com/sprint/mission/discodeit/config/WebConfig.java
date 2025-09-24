package com.sprint.mission.discodeit.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/* 설명. 파일 업로드 경로를 설정하는 클래스
 * WebMvcConfigurer 인터페이스를 구현하여 커스텀 설정을 추가할 수 있다.
 * */
@Configuration
public class
WebConfig implements WebMvcConfigurer {

    @Value("${file.upload.windows.path}")
    private String windowsPath;

    @Value("${file.upload.mac.path}")
    private String macPath;

	/* 설명. 파일 업로드 경로를 설정하는 메서드 
	 *  WebMvcConfigurer 인터페이스의 addResourceHandlers 메서드를 오버라이딩하여 파일 업로드 경로를 설정한다.
	 * */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        // OS별 업로드 경로 설정 (현재 OS에 따라 경로를 다르게 설정)
        String osName = System.getProperty("os.name").toLowerCase();
        String uploadPath;

        if (osName.contains("win")) {
            uploadPath = windowsPath;
        } else {
            uploadPath = macPath;
        }

		System.out.println("현재 내 OS : " + osName);
		System.out.println("파일 업로드 경로 : " + uploadPath);

        /* 설명. 실제 업로드된 파일에 접근할 URL 패턴과 실제 파일 시스템 경로 매핑
		 *  여기서 file:// 프로토콜은 OS의 파일 시스템에 접근하는 프로토콜이다.
		 * */
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:///" + uploadPath + "/");
    }
} 