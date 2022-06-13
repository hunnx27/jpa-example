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