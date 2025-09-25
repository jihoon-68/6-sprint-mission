package com.sprint.mission.discodeit.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/* 설명. 파일 업로드 경로를 설정하는 클래스
 * WebMvcConfigurer 인터페이스를 구현하여 커스텀 설정을 추가할 수 있다.
 * */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${file.upload.windows.path}")
    private String windowsPath;

    @Value("${file.upload.mac.path}")
    private String macPath;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 1. 애플리케이션의 모든 API 경로에 대해 CORS 설정을 적용합니다.
                .allowedOrigins("https://editor.swagger.io", "http://localhost:3000") // 2. 요청을 허용할 출처(Origin)를 지정합니다. Swagger Editor와 일반적인 프론트엔드 개발 환경(localhost:3000)을 추가했습니다.
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PACT") // 3. 허용할 HTTP 메서드를 지정합니다.
                .allowedHeaders("*") // 4. 허용할 요청 헤더를 지정합니다.
                .allowCredentials(true) // 5. 쿠키 등 자격 증명을 포함한 요청을 허용합니다.
                .maxAge(3600); // 6. Pre-flight 요청의 결과를 캐시할 시간을 초 단위로 지정합니다.
    }

	/* 설명. 파일 업로드 경로를 설정하는 메서드 
	 *  WebMvcConfigurer 인터페이스의 addResourceHandlers 메서드를 오버라이딩하여 파일 업로드 경로를 설정한다.
	 * */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");

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