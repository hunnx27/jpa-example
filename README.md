# 티아카데미 JPA 프로그래밍 기본기 다지기 실습
https://www.youtube.com/watch?v=WfrSN9Z7MiA&list=PL9mhQYIlKEhfpMVndI23RwWTL9-VL-B7U&index=1

# H2 Database 설치

[H2 데이터베이스 사이트 링크](https://h2database.com/html/main.html)

각 OS에 맞게 설치

# Spring Boot 프로젝트 셋팅
## JAVA
JDK 1.8 > 11 설치

## Dependency 셋팅
[https://start.spring.io](https://start.spring.io)
- com.h2database:h2
- org.springframework.boot:spring-boot-starter-data-jpa

# JPA를 사용하는 이유
1. 계층형 아키텍처/진정한 의미의 계층 분할을 할 수 있음
2. SQL에 의존적인 개발을 탈피시켜줌
3. 패러다임의 불일치 해결해줌(객채와 관계형 데이터베이스의 차이)
   1. 상속
   2. 연관관계
   3. 데이터 타입
   4. 데이터 식별 방법

# 패키지 구성

- **common** : 공통 Core기능(필터,인터셉터, 유틸, 익셉션, 등등..)
- **config** : 스프링부트 설정 및 시큐리티 설정
- **modules** : 각 모듈 별 폴더(DDD의 계층화 아키텍처 구성)
  - **ㄴ{모듈A}** : 모듈A의 폴더
    - **ㄴweb** : Presentaion Layer(Client와 접촉지점, 사용자 인터페이스)
    - **ㄴapplication** : Application Layer(비지니스 로직 구현)
    - **ㄴdomain** : Domain Layer(도메인 계층)
    - **ㄴinfrastructure** : Infrastructure Layer(영속성 계층, 외부 접촉지점, DB커넥트, 외부API호출)

 
계층 구조는 위에 있는 계층에서는 아래 있는 계층에 접근이 가능하지만 
아래에서 위로는 불가능 한것을 기본으로 하고 있다.
ex)
> Presentaion Layer > Application Layer **(ok)**  
> Presentaion Layer > Domain Layer **(ok)**  
> Presentaion Layer > Infrastructure Layer **(ok)**  
> domain Layer > infrastructure Layer **(ok)**  
> Infrastructure Layer > Application Layer **(X)**  
> Infrastructure Layer > Domain Layer **(X)**  
> Infrastructure Layer > Presentaion Layer **(X)**  
> Application Layer Layer > Presentaion Layer **(X)**  

**링크 참조**  
아래링크의 **"계층별 설명"** 참고
> https://velog.io/@jeb1225/DDD%EC%9D%98-%EA%B3%84%EC%B8%B5%EA%B5%AC%EC%A1%B0Layered-architecture

