# 1차 기본 요구사항
## 프로젝트 초기화
- [x] IntelliJ를 통해 다음의 조건으로 Java 프로젝트를 생성합니다.
    - [x]  IntelliJ에서 제공하는 프로젝트 템플릿 중 Java를 선택합니다.
    - [x]  프로젝트의 경로는 스프린트 미션 리포지토리의 경로와 같게 설정합니다.
        - 예를 들어 스프린트 미션 리포지토리의 경로가 /some/path/1-sprint-mission 이라면:
          - Name은 1-sprint-mission
          - Location은 /some/path
          - 으로 설정합니다.

- [x]  Create Git Repository 옵션은 체크하지 않습니다.

- [x]  Build system은 Gradle을 사용합니다. Gradle DSL은 Groovy를 사용합니다.

- [x]  JDK 17을 선택합니다.

- [x]  GroupId는 com.sprint.mission로 설정합니다.

- [x]  ArtifactId는 수정하지 않습니다.

- [x]  .gitignore에 IntelliJ와 관련된 파일이 형상관리 되지 않도록 .idea디렉토리를 추가합니다.


## 도메인 모델링
- [x] 디스코드 서비스를 활용해보면서 각 도메인 모델에 필요한 정보를 도출하고, Java Class로 구현하세요.
    - 패키지명: com.sprint.mission.discodeit.entity
    - [x] 도메인 모델 정의
      - 공통
        - id: 객체를 식별하기 위한 id로 UUID 타입으로 선언합니다.
        - createdAt, updatedAt: 각각 객체의 생성, 수정 시간을 유닉스 타임스탬프로 나타내기 위한 필드로 Long 타입으로 선언합니다.
        - [x] User
            - id(UUID)
            - 이름(String)
            - 나이(int)
            - 이메일
            - 채널 목록(List<Channel>)
            - 친구 목록(List<User>)
            - 생성 일자(Long)
            - 수정 일자(Long)
        - [x] Channel
            - id (UUID)
            - 채널이름 (String)
            - 방장 (User)
            - User 목록 (List<User>)
            - 메세지 목록 (List<Message>)
            - 생성 일자 (Long)
            - 수정 일자 (Long)
        - [x] Message
            - id (UUID)
            - 보낸 User (User)
            - 메세지 본문( String)
            - 생성 일자 (Long)
            - 수정 일자 (Long)
    - 생성자
      - [x] id는 생성자에서 초기화하세요.
      - [x] createdAt는 생성자에서 초기화하세요.
      - [x] id, createdAt, updatedAt을 제외한 필드는 생성자의 파라미터를 통해 초기화하세요.

    - 메소드
      - [x] 각 필드를 반환하는 Getter 함수를 정의하세요.
      - [x] 필드를 수정하는 update 함수를 정의하세요.


## 서비스 설계 및 구현
- [x] 도메인 모델 별 CRUD(생성, 읽기, 모두 읽기, 수정, 삭제) 기능을 인터페이스로 선언하세요.
  - 인터페이스 패키지명: com.sprint.mission.discodeit.service
  - 인터페이스 네이밍 규칙: [도메인 모델 이름]Service
- [x] 다음의 조건을 만족하는 서비스 인터페이스의 구현체를 작성하세요.
  - 클래스 패키지명: com.sprint.mission.discodeit.service.jcf
  - 클래스 네이밍 규칙: JCF[인터페이스 이름]
  - Java Collections Framework를 활용하여 데이터를 저장할 수 있는 필드(data)를 final로 선언하고 생성자에서 초기화하세요.
  - data 필드를 활용해 생성, 조회, 수정, 삭제하는 메소드를 구현하세요.

## 메인 클래스 구현
- [x] 메인 메소드가 선언된 JavaApplication 클래스를 선언하고, 도메인 별 서비스 구현체를 테스트해보세요.
  - 등록
  - 조회(단건, 다건)
  - 수정
  - 수정된 데이터 조회
  - 삭제
  - 조회를 통해 삭제되었는지 확인

## 심화 요구 사항
### 서비스 간 의존성 주입
- [X] 도메인 모델 간 관계를 고려해서 검증하는 로직을 추가하고, 테스트해보세요.
    - 힌트: Message를 생성할 때 연관된 도메인 모델 데이터 확인하기


# 2차 요구사항
## 기본 요구사항
### File IO를 통한 데이터 영속화
- [x] 다음의 조건을 만족하는 서비스 인터페이스의 구현체를 작성하세요.

    - 클래스 패키지명: com.sprint.mission.discodeit.service.file

    - 클래스 네이밍 규칙: File[인터페이스 이름]

    - JCF 대신 FileIO와 객체 직렬화를 활용해 메소드를 구현하세요.

    - Application에서 서비스 구현체를 File*Service로 바꾸어 테스트해보세요.

### 서비스 구현체 분석
- [x] JCF*Service 구현체와 File*Service 구현체를 비교하여 공통점과 차이점을 발견해보세요.
    - "비즈니스 로직"과 관련된 코드를 식별해보세요.
    - "저장 로직"과 관련된 코드를 식별해보세요.

| 구분             | JCF 서비스 구현체                                            | 파일 서비스 구현체                             |
| -------------- | ------------------------------------------------------ | -------------------------------------- |
| **데이터 저장 위치**  | 메모리 (In-Memory)                                        | 디스크 (File System)                      |
| **데이터 영속성**    | 애플리케이션 종료 시 데이터 소멸(휘발성)                                | 애플리케이션 종료 후에도<br>데이터 유지(비 휘발성)         |
| **처리 속도**      | 매우 빠름 (디스크 I/O 없음)                                     | 상대적으로 느림 (디스크 I/O 발생)                  |
| **주요 용도**      | 임시 데이터 캐싱, 세션 관리, <br>빠른 조회가 필요한 데이터 처리                | 데이터 영구 저장, 로그 기록,<br>설정 정보 관리          |
| **데이터 동시성 제어** | 동기화(synchronized) 컬렉션 또는 <br/>`java.util.concurrent` 패키지 필요 | 파일 잠금(File Locking) 등 운영체제 수준의 메커니즘 필요 |
| **확장성**        | 단일 애플리케이션 메모리에 종속적                                     | 분산 파일 시스템 등을 통해 확장 가능                  |
---
### 레포지토리 설계 및 구현
- [x] "저장 로직"과 관련된 기능을 도메인 모델 별 인터페이스로 선언하세요.
  - 인터페이스 패키지명: com.sprint.mission.discodeit.repository
  - 인터페이스 네이밍 규칙: [도메인 모델 이름]Repository
- [x] 다음의 조건을 만족하는 레포지토리 인터페이스의 구현체를 작성하세요.
  - 클래스 패키지명: com.sprint.mission.discodeit.repository.jcf
  - 클래스 네이밍 규칙: JCF[인터페이스 이름]
  - 기존에 구현한 JCF*Service 구현체의 "저장 로직"과 관련된 코드를 참고하여 구현하세요.
- [x] 다음의 조건을 만족하는 레포지토리 인터페이스의 구현체를 작성하세요.
  - 클래스 패키지명: com.sprint.mission.discodeit.repository.file
  - 클래스 네이밍 규칙: File[인터페이스 이름]
  - 기존에 구현한 File*Service 구현체의 "저장 로직"과 관련된 코드를 참고하여 구현하세요.
## 심화 요구 사항
### 관심사 분리를 통한 레이어 간 의존성 주입
- [x] 다음의 조건을 만족하는 서비스 인터페이스의 구현체를 작성하세요.
  - 클래스 패키지명: com.sprint.mission.discodeit.service.basic
  - 클래스 네이밍 규칙: Basic[인터페이스 이름]
  - 기존에 구현한 서비스 구현체의 "비즈니스 로직"과 관련된 코드를 참고하여 구현하세요.
  - 필요한 Repository 인터페이스를 필드로 선언하고 생성자를 통해 초기화하세요.
  - "저장 로직"은 Repository 인터페이스 필드를 활용하세요. (직접 구현하지 마세요.)
- [x] Basic*Service 구현체를 활용하여 테스트해보세요.
  - 코드 템플릿
    ````
    public class JavaApplication {
        static User setupUser(UserService userService) {
            User user = userService.create("woody", "woody@codeit.com", "woody1234");
            return user;
        }
    
        static Channel setupChannel(ChannelService channelService) {
            Channel channel = channelService.create(ChannelType.PUBLIC, "공지", "공지 채널입니다.");
            return channel;
        }
    
        static void messageCreateTest(MessageService messageService, Channel channel, User author) {
            Message message = messageService.create("안녕하세요.", channel.getId(), author.getId());
            System.out.println("메시지 생성: " + message.getId());
        }
    
        public static void main(String[] args) {
            // 서비스 초기화
            // TODO Basic*Service 구현체를 초기화하세요.
            UserService userService;
            ChannelService channelService;
            MessageService messageService;
    
            // 셋업
            User user = setupUser(userService);
            Channel channel = setupChannel(channelService);
            // 테스트
            messageCreateTest(messageService, channel, user);
        }
    }
    ````
  - [x] JCF*Repository  구현체를 활용하여 테스트해보세요.
  - [x] File*Repository 구현체를 활용하여 테스트해보세요.
- [ ] 이전에 작성했던 코드(JCF*Service 또는 File*Service)와 비교해 어떤 차이가 있는지 정리해보세요.
  - 불어오는 방식 서비스 코드느 발로 들고 오지만 래초지스토리 코드는 비지니스클래스를 통해 불러와진다?


