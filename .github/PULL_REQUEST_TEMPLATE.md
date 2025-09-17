
## 요구사항

### 기본
- [ ] 기본 항목 1
- [ ] 기본 항목 2

### 심화
- [ ] 심화 항목 1
- [ ] 심화 항목 2

## [ ] JavaApplication과 DiscodeitApplication에서 Service를 초기화하는 방식의 차이에 대해 다음의 키워드를 중심으로 정리해보세요. 

- JavaApplication
  - 개발자가 직접 객체를 생성하고 연결하기에 IoC Container 없음
  - new BasicUserService(userRepository); 처럼 의존성을 직접 생성자에 주입함
  - 단순한 POJO임, 스프링 컨테이너가 없어 Bean 개념 존재하지 않음
  
- DiscodeitApplication
  - IoC 컨테이너인 ApplicationContext를 사용함 
  - context.getBean(.class) 호출시 스프링이 관리하는 Bean반환
  - @Reposiotry 와 @Service를 사용하여 빈 등록

## 주요 변경사항
- .gitignore에 직전에 피드백 주신 보안 사항들 업데이트

## 스크린샷

기본 요구사항 결과 사진
* DiscodeitApplicaton
![DiscodeitApplication.png](../src/main/resources/images/DiscodeitApplication.png)

* JavaApplication
![JavaApplication.png](../src/main/resources/images/JavaApplication.png)


## 멘토에게
- 미션을 완료하지 못해 모범답안 코드로 PR 드립니다.
  - 기본 요구 사항 완료 및, 추가기능 요구 사항의 DTO 작업까지 직접했었으나 서비스 구현 및 고도화 부분부터 작업하지 못했습니다. 


