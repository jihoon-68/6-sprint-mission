# 6-spring-mission
## sprint3


# 기본 요구사항
## Spring 프로젝트 초기화
- [ ] Spring Initializr를 통해 zip 파일을 다운로드하세요.
- [ ] 빌드 시스템은 Gradle - Groovy를 사용합니다.
- [ ] 언어는 Java 17를 사용합니다.
- [ ] Spring Boot의 버전은 3.4.0입니다.
- [ ] GroupId는 com.sprint.mission입니다.
- [ ] ArtifactId와 Name은 discodeit입니다.
- [ ] packaging 형식은 Jar입니다
- [ ] Dependency를 추가합니다.
- [ ] Lombok
- [ ] Spring Web
- [ ] zip 파일을 압축해제하고 원래 진행 중이던 프로젝트에 붙여넣기하세요. 일부 파일은 덮어쓰기할 수 있습니다.
- [ ] application.properties 파일을 yaml 형식으로 변경하세요.
- [ ] DiscodeitApplication의 main 메서드를 실행하고 로그를 확인해보세요.
Bean 선언 및 테스트
- [ ] File*Repository 구현체를 Repository 인터페이스의 Bean으로 등록하세요.
- [ ] Basic*Service 구현체를 Service 인터페이스의 Bean으로 등록하세요.
- [ ] JavaApplication에서 테스트했던 코드를 DiscodeitApplication에서 테스트해보세요.
- [ ]  JavaApplication 의 main 메소드를 제외한 모든 메소드를 DiscodeitApplication클래스로 복사하세요.

- [ ]  JavaApplication의 main 메소드에서 Service를 초기화하는 코드를 Spring Context를 활용하여 대체하세요.

```java
// JavaApplication
public static void main(String[] args) {
// 레포지토리 초기화
// ...
// 서비스 초기화
UserService userService = new BasicUserService(userRepository);
ChannelService channelService = new BasicChannelService(channelRepository);
MessageService messageService = new BasicMessageService(messageRepository, channelRepository, userRepository);

    // ...
}

// DiscodeitApplication
public static void main(String[] args) {
ConfigurableApplicationContext context = SpringApplication.run(DiscodeitApplication.class, args);
// 서비스 초기화
// TODO context에서 Bean을 조회하여 각 서비스 구현체 할당 코드 작성하세요.
UserService userService;
ChannelService channelService;
MessageService messageService;

    // ...
}
```
- [ ]  JavaApplication의 main 메소드의 셋업, 테스트 부분의 코드를 DiscodeitApplication클래스로 복사하세요.
```java
public static void main(String[] args) {
// ...
// 셋업
User user = setupUser(userService);
Channel channel = setupChannel(channelService);
// 테스트
messageCreateTest(messageService, channel, user);
}
```
## Spring 핵심 개념 이해하기
- [ ] JavaApplication과 DiscodeitApplication에서 Service를 초기화하는 방식의 차이에 대해 다음의 키워드를 중심으로 정리해보세요.
### IoC Container
- BeanFactor, ApplicationContext
### Dependency Injection
- 생성자 주입
### Bean
- Bean 등록
### 설명
1.
   - JavaApplication에서는 인터페이스 타입으로 구현체 객체를 생성하여 초기화였습니다.
2.
   - IoC Container에는 BeanFactory와 BeanFactory을 상속해 확장한 ApplicationContext가 있습니다
   - IOC 컨테이너에서 Bean 생성, 생명주기를 관리합니다
   - @Component 어노테이션이 있는 클래스를 Bean 등록합니다.
   - 등록된 Bean은 생성자 주입 방식으로 초기화되며 new 없이 자동으로 초기화해줍니다.
   - SpringApplication 객체인 context로 getBean 메소드를 통하여 등록된 Basic*Service Bean을 가져옵니다


## Lombok 적용
- [ ] 도메인 모델의 getter 메소드를 @Getter로 대체해보세요.
- [ ] Basic*Service의 생성자를 @RequiredArgsConstructor로 대체해보세요.
## 비즈니스 로직 고도화
- [ ]  다음의 기능 요구 사항을 구현하세요.

# Memo

@RequiredArgsConstructor는 생성자 자동 주입
