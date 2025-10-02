# 기본 요구사항
## 데이터베이스
- [x] 아래와 같이 데이터베이스 환경을 설정하세요.
    - 데이터베이스: discodeit
    - 유저: discodeit_user
    - 패스워드: discodeit1234
- [x] ERD를 참고하여 DDL을 작성하고, 테이블을 생성하세요.
  - 작성한 DDL 파일은 /src/main/resources/schema.sql 경로에 포함하세요.
  - 
  - PK: Primary Key
  - UK: Unique Key
  - NN: Not Null
  - FK: Foreign Key
  - ON DELETE CASCADE: 연관 엔티티 삭제 시 같이 삭제
  - ON DELETE SET NULL: 연관 엔티티 삭제 시 NULL로 변경
## Spring Data JPA 적용하기
- [x] Spring Data JPA와 PostgreSQL을 위한 의존성을 추가하세요.
- [x] 앞서 구성한 데이터베이스에 연결하기 위한 설정값을 application.yaml 파일에 작성하세요.
- [x] 디버깅을 위해 SQL 로그와 관련된 설정값을 application.yaml 파일에 작성하세요.
## 엔티티 정의하기
- [ ]  클래스 다이어그램을 참고해 도메인 모델의 공통 속성을 추상 클래스로 정의하고 상속 관계를 구현하세요.
    - 이때 Serializable 인터페이스는 제외합니다.
    - 패키지명: com.sprint.mission.discodeit.entity.base
    - 클래스 다이어그램
    - 
- [ ]  JPA의 어노테이션을 활용해 createdAt, updatedAt 속성이 자동으로 설정되도록 구현하세요.
    - @CreatedDate, @LastModifiedDate
- [ ]  클래스 다이어그램을 참고해 클래스 참조 관계를 수정하세요. 필요한 경우 생성자, update 메소드를 수정할 수 있습니다. 단, 아직 JPA Entity와 관련된 어노테이션은 작성하지 마세요.
    - 클래스 다이어그램
    - 화살표의 방향과 화살표 유무에 유의하세요.
- [ ]  ERD와 클래스 다이어그램을 토대로 연관관계 매핑 정보를 표로 정리해보세요.(이 내용은 PR에 첨부해주세요.)
    - 예시
    - | 엔티티 관계          | 다중성 | 방향성                  | 부모-자식 관계                | 연관관계의 주인   |
      |-----------------|-----|----------------------|-------------------------|------------|
      | User:UserStatus | 1:1 | User<---> UserStatus | 부모: User 자식: UserStatus | UserStatus |

