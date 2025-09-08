# 기본 요구사항
## Spring 프로젝트 초기화
- [ ] Spring Initializr를 통해 zip 파일을 다운로드하세요.
  - 빌드 시스템은 Gradle - Groovy를 사용합니다.
  - 언어는 Java 17를 사용합니다.
  - Spring Boot의 버전은 3.4.0입니다.
  - GroupId는 com.sprint.mission입니다.
  - ArtifactId와 Name은 discodeit입니다.
  - packaging 형식은 Jar입니다
    - Dependency를 추가합니다.
    - Lombok
    - Spring Web
  - zip 파일을 압축해제하고 원래 진행 중이던 프로젝트에 붙여넣기하세요. 일부 파일은 덮어쓰기할 수 있습니다.
  - application.properties 파일을 yaml 형식으로 변경하세요.
  - DiscodeitApplication의 main 메서드를 실행하고 로그를 확인해보세요.
## Bean 선언 및 테스트

- [ ] File*Repository 구현체를 Repository 인터페이스의 Bean으로 등록하세요.
- [ ] Basic*Service 구현체를 Service 인터페이스의 Bean으로 등록하세요.
- [ ] JavaApplication에서 테스트했던 코드를 DiscodeitApplication에서 테스트해보세요.
  - JavaApplication 의 main 메소드를 제외한 모든 메소드를 DiscodeitApplication클래스로 복사하세요.
  - JavaApplication의 main 메소드에서 Service를 초기화하는 코드를 Spring Context를 활용하여 대체하세요.

    ````
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
    ````
  - JavaApplication의 main 메소드의 셋업, 테스트 부분의 코드를 DiscodeitApplication클래스로 복사하세요.
    ````
    public static void main(String[] args) {
    // ...
    // 셋업
    User user = setupUser(userService);
    Channel channel = setupChannel(channelService);
    // 테스트
    messageCreateTest(messageService, channel, user);
    }
    ````
## Spring 핵심 개념 이해하기
- [ ] JavaApplication과 DiscodeitApplication에서 Service를 초기화하는 방식의 차이에 대해 다음의 키워드를 중심으로 정리해보세요.
  - IoC Container
  - Dependency Injection
  - Bean
  > 이 내용은 PR에 첨부해주세요.

## Lombok 적용
- [ ] 도메인 모델의 getter 메소드를 @Getter로 대체해보세요.
- [ ] Basic*Service의 생성자를 @RequiredArgsConstructor로 대체해보세요.
# 비즈니스 로직 고도화
## 시간 타입 변경하기
- [ ] 시간을 다루는 필드의 타입은 Instant로 통일합니다.
    - 기존에 사용하던 Long보다 가독성이 뛰어나며, 시간대(Time Zone) 변환과 정밀한 시간 연산이 가능해 확장성이 높습니다.
## 새로운 도메인 추가하기
  - 도메인 모델 간 참조 관계를 참고하세요.
    ```mermaid
        classDiagram
    class User {
        +UUID id
        +UUID profileId
        ...
    }

    class Channel {
        +UUID id
        ...
    }

    class UserStatus {
        +UUID id
        +UUID userId
        ...
    }

    class ReadStatus {
        +UUID id
        +UUID userId
        +UUID channelId
        ...
    }

    class Message {
        +UUID id
        +UUID channelId
        +UUID authorId
        +List<UUID> attachmentIds
        ...
    }

    class BinaryContent {
        +UUID id
        ...
    }

    User "1" --> "0..*" BinaryContent : profileId
    Message "1" --> "0..*" BinaryContent : attachmentIds
    
    UserStatus "1" --> "1" User : userId
    ReadStatus "*" --> "1" User : userId
    Message "*" --> "1" User : authorId
    
    ReadStatus "*" --> "1" Channel : channelId
    Message "*" --> "1" Channel : channelId
    
    ```
- 공통: 앞서 정의한 도메인 모델과 동일하게 공통 필드(id, createdAt, updatedAt)를 포함합니다.

- [ ] ReadStatus
    - 사용자가 채널 별 마지막으로 메시지를 읽은 시간을 표현하는 도메인 모델입니다. 사용자별 각 채널에 읽지 않은 메시지를 확인하기 위해 활용합니다.
- [ ] UserStatus
    - 사용자 별 마지막으로 확인된 접속 시간을 표현하는 도메인 모델입니다. 사용자의 온라인 상태를 확인하기 위해 활용합니다.
    - 마지막 접속 시간을 기준으로 현재 로그인한 유저로 판단할 수 있는 메소드를 정의하세요.
    - 마지막 접속 시간이 현재 시간으로부터 5분 이내이면 현재 접속 중인 유저로 간주합니다.
- [ ] BinaryContent
  - 이미지, 파일 등 바이너리 데이터를 표현하는 도메인 모델입니다. 사용자의 프로필 이미지, 메시지에 첨부된 파일을 저장하기 위해 활용합니다.
  - 수정 불가능한 도메인 모델로 간주합니다. 따라서 updatedAt 필드는 정의하지 않습니다.
  - User, Message 도메인 모델과의 의존 관계 방향성을 잘 고려하여 id 참조 필드를 추가하세요.
- [ ]  각 도메인 모델 별 레포지토리 인터페이스를 선언하세요.
  - 레포지토리 구현체(File, JCF)는 아직 구현하지 마세요. 이어지는 서비스 고도화 요구사항에 따라 레포지토리 인터페이스에 메소드가 추가될 수 있어요.
## DTO 활용하기

## UserService 고도화
- 고도화
    - [ ] create
      - 선택적으로 프로필 이미지를 같이 등록할 수 있습니다.
      - DTO를 활용해 파라미터를 그룹화합니다.
      - 유저를 등록하기 위해 필요한 파라미터, 프로필 이미지를 등록하기 위해 필요한 파라미터 등
        - username과 email은 다른 유저와 같으면 안됩니다.
      - UserStatus를 같이 생성합니다.
    - [ ] find, findAll
      - DTO를 활용하여:
        - 사용자의 온라인 상태 정보를 같이 포함하세요.
        - 패스워드 정보는 제외하세요.
   - [ ] update
     - 선택적으로 프로필 이미지를 대체할 수 있습니다.
     - DTO를 활용해 파라미터를 그룹화합니다.
       - 수정 대상 객체의 id 파라미터, 수정할 값 파라미터
   - [ ] delete
     - 관련된 도메인도 같이 삭제합니다.
       - BinaryContent(프로필), UserStatus
## 의존성
- 같은 레이어 간 의존성 주입은 순환 참조 방지를 위해 지양합니다. 다른 Service 대신 필요한 Repository 의존성을 주입해보세요.
    ```mermaid
    classDiagram
        UserService --> UserRepository
        UserService --> BinaryContentRepository
        UserService --> UserStatusRepository
    ```

## AuthService 구현
- [ ] login
  - username, password과 일치하는 유저가 있는지 확인합니다.
      - 일치하는 유저가 있는 경우: 유저 정보 반환
      - 일치하는 유저가 없는 경우: 예외 발생
  - DTO를 활용해 파라미터를 그룹화합니다.
- 의존성
    - 같은 레이어 간 의존성 주입은 순환 참조 방지를 위해 지양합니다. 다른 Service 대신 필요한 Repository 의존성을 주입해보세요.
      ```mermaid
      classDiagram
          AuthService --> UserRepository
      ```

## ChannelService 고도화
- 고도화
    - [ ] create
        - PRIVATE 채널과 PUBLIC 채널을 생성하는 메소드를 분리합니다.
        - 분리된 각각의 메소드를 DTO를 활용해 파라미터를 그룹화합니다.
        - PRIVATE 채널을 생성할 때:
            - 채널에 참여하는 User의 정보를 받아 User 별 ReadStatus 정보를 생성합니다.
            - name과 description 속성은 생략합니다.
        - PUBLIC 채널을 생성할 때에는 기존 로직을 유지합니다.
    - [ ] find
        - DTO를 활용하여:
            - 해당 채널의 가장 최근 메시지의 시간 정보를 포함합니다.
            - PRIVATE 채널인 경우 참여한 User의 id 정보를 포함합니다.
    - [ ] findAll
        - DTO를 활용하여:
            - 해당 채널의 가장 최근 메시지의 시간 정보를 포함합니다.
        - PRIVATE 채널인 경우 참여한 User의 id 정보를 포함합니다.
        - 특정 User가 볼 수 있는 Channel 목록을 조회하도록 조회 조건을 추가하고, 메소드 명을 변경합니다. findAllByUserId
        - PUBLIC 채널 목록은 전체 조회합니다.
        - PRIVATE 채널은 조회한 User가 참여한 채널만 조회합니다.
    - [ ] update
        - DTO를 활용해 파라미터를 그룹화합니다.
            - 수정 대상 객체의 id 파라미터, 수정할 값 파라미터
            - PRIVATE 채널은 수정할 수 없습니다.
    - [ ] delete
    [ ] 관련된 도메인도 같이 삭제합니다.
      - Message, ReadStatus
- 의존성
   - 같은 레이어 간 의존성 주입은 순환 참조 방지를 위해 지양합니다. 다른 Service 대신 필요한 Repository 의존성을 주입해보세요.
   ```mermaid
    classDiagram
        ChannelService --> ChannelRepository
        ChannelService --> ReadStatusRepository
        ChannelService --> MessageRepository
   ```
## MessageService 고도화
- 고도화
    - [ ] create
        - 선택적으로 여러 개의 첨부파일을 같이 등록할 수 있습니다.
        - DTO를 활용해 파라미터를 그룹화합니다.
    - [ ] findAll
        - 특정 Channel의 Message 목록을 조회하도록 조회 조건을 추가하고, 메소드 명을 변경합니다. findallByChannelId
    - [ ] update
        - DTO를 활용해 파라미터를 그룹화합니다.
            - 수정 대상 객체의 id 파라미터, 수정할 값 파라미터
    - [ ] delete
        - 관련된 도메인도 같이 삭제합니다.
            - 첨부파일(BinaryContent)
- 의존성
    - 같은 레이어 간 의존성 주입은 순환 참조 방지를 위해 지양합니다. 다른 Service 대신 필요한 Repository 의존성을 주입해보세요.
  ```mermaid
  classDiagram
      MessageService --> MessageRepository
      MessageService --> UserRepository
      MessageService --> ChannelRepository
      MessageService --> BinaryContentRepository
   ```

## ReadStatusService 구현
- [ ] create
    - DTO를 활용해 파라미터를 그룹화합니다.
    - 관련된 Channel이나 User가 존재하지 않으면 예외를 발생시킵니다.
    - 같은 Channel과 User와 관련된 객체가 이미 존재하면 예외를 발생시킵니다.
- [ ] find
    - id로 조회합니다.
- [ ] findAllByUserId
    - userId를 조건으로 조회합니다.
- [ ] update
    - DTO를 활용해 파라미터를 그룹화합니다.
    - 수정 대상 객체의 id 파라미터, 수정할 값 파라미터
- [ ] delete
    - id로 삭제합니다.
- 의존성
    - 같은 레이어 간 의존성 주입은 순환 참조 방지를 위해 지양합니다. 다른 Service 대신 필요한 Repository 의존성을 주입해보세요.
    ```mermaid
  classDiagram
  ReadStatusService --> ReadStatusRepository
  ReadStatusService --> UserRepository
  ReadStatusService --> ChannelRepository
   ```

## UserStatusService 
- 고도화
    - [ ] create
        - DTO를 활용해 파라미터를 그룹화합니다.
        - 관련된 User가 존재하지 않으면 예외를 발생시킵니다.
        - 같은 User와 관련된 객체가 이미 존재하면 예외를 발생시킵니다.
    - [ ] find
        - id로 조회합니다.
    - [ ] findAll
        - 모든 객체를 조회합니다.
    - [ ] update
        - DTO를 활용해 파라미터를 그룹화합니다.
        - 수정 대상 객체의 id 파라미터, 수정할 값 파라미터
    - [ ] updateByUserId
        - userId 로 특정 User의 객체를 업데이트합니다.
    - [ ] delete
        - id로 삭제합니다.
- 의존성
    - 같은 레이어 간 의존성 주입은 순환 참조 방지를 위해 지양합니다. 다른 Service 대신 필요한 Repository 의존성을 주입해보세요.
    ```mermaid
    classDiagram
        UserStatusService --> UserStatusRepository
        UserStatusService --> UserRepository
   ```

BinaryContentService 구현
- [ ] create
    - DTO를 활용해 파라미터를 그룹화합니다.
- [ ] find
    - id로 조회합니다.
- [ ] findAllByIdIn
    - id 목록으로 조회합니다.
- [ ] delete
    - id로 삭제합니다.
- 의존성
    - 같은 레이어 간 의존성 주입은 순환 참조 방지를 위해 지양합니다. 다른 Service 대신 필요한 Repository 의존성을 주입해보세요.
    ```mermaid
    classDiagram
        BinaryContentService --> BinaryContentRepository
   ```    

## 새로운 도메인 Repository 구현체 구현
- [ ] 지금까지 인터페이스로 설계한 각각의 Repository를 JCF, File로 각각 구현하세요.
    ```mermaid
    classDiagram
        class FileRepository
        class JCFRepository
        class Repository{ <<interface>> }     
        FileRepository --|> Repository
        JCFRepository --|> Repository
   ```   
