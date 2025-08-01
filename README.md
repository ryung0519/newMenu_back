-en
---
# 🌱 Fresh Guys Backend (Spring Boot)

**Project Duration:** 2025.03.07 ~ 2025.05.30 (2 months)  
**Award:** Excellence Award, IoT Big Data Application Education Course Outcome Contest (Sunmoon University, May 2025)
<br>

## 📌 Project Overview
The **Fresh Guys** backend is responsible for providing API services and data processing for a mobile application that introduces and recommends newly released menus based on user preferences and regional availability.

### Key Responsibilities
- Provide REST APIs for menu calendar, location-based filtering, and personalized recommendations
- Handle user account management and authentication
- Integrate with Oracle DB for storing menus, user preferences, and review data
- Connect with POS systems to collect sales and availability data
- Manage notification triggers for GPS and subscription-based alerts

<br>

## 🏗 Architecture

[시스템 아키텍처 이미지]

**Stack**
- **Backend:** Spring Boot (Java 17)
- **Database:** Oracle DB 19c
- **Authentication:** Email & Firebase Token
- **Other:** POS integration, GPS-based menu service

<br>

## 📡 API Overview

| Method | Endpoint                       | Description                                  |
|--------|--------------------------------|----------------------------------------------|
| GET    | `/calendar/menus`               | Get upcoming menu items for calendar view     |
| GET    | `/menu?category={category}`     | Get menu items by category                    |
| POST   | `/auth/signup`                  | User signup (email-based)                     |
| POST   | `/auth/login`                   | User login and token issuance                 |
| GET    | `/menus/nearby`                 | Get nearby menu items based on GPS            |
| POST   | `/reviews`                      | Submit a new review for a menu item           |
| GET    | `/trends/popular`               | (Planned) Get popular menu trends             |

<br>

## 🗄 Database Structure

[ERD 이미지]

**Key Tables**
- `USERS`: User accounts and profiles
- `MENUS`: Menu items with release date, category, location
- `REVIEWS`: User reviews and ratings
- `POS_DATA`: Store sales data for trend analysis
- `ALERTS`: Subscription and GPS-based notification settings

<br>

## ⚡ Implementation Notes
- Solved **N+1 query problem** with `@EntityGraph` and `JOIN FETCH`
- Applied **DTO pattern** to reduce payload size and decouple entities
- Used **Spring Scheduler** for daily menu data synchronization
- Designed **CORS & Security config** to allow React Native (Expo) requests
- Planned **Firebase Cloud Messaging (FCM)** integration for notifications

<br>

## 🚀 How to Run

### 1️⃣ Clone the repository
```bash
git clone https://github.com/yourusername/fresh-guys-backend.git
cd fresh-guys-backend
````

### 2️⃣ Set environment variables

* `application.properties` example:

```properties
spring.datasource.url=jdbc:oracle:thin:@localhost:1521:xe
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

### 3️⃣ Run Spring Boot

```bash
./gradlew bootRun
```

Server runs at: **[http://localhost:8080](http://localhost:8080)**

<br>

## 🏆 Award

* **Excellence Award, IoT Big Data Application Education Course Outcome Contest (Sunmoon University, May 2025)**

<br>
-kr
---
# 🌱 싱싱한 녀석들 백엔드 (Spring Boot)

**프로젝트 기간:** 2025.03.07 \~ 2025.05.30 (2개월)
**수상:** 선문대학교 IoT 빅데이터 응용교육 성과 경진대회 우수상 (2025년 5월)

## 📌 프로젝트 개요

**싱싱한 녀석들 백엔드**는 사용자의 취향과 지역 정보를 기반으로 신메뉴를 추천하고,
POS 데이터를 연동하여 메뉴 트렌드를 분석하는 **모바일 서비스 API 서버**입니다.

### 주요 역할

* 메뉴 캘린더, 카테고리, 위치 기반 메뉴 추천 API 제공
* 회원가입, 로그인 등 사용자 인증 및 관리
* Oracle DB를 통한 메뉴, 리뷰, 알림 데이터 관리
* POS 시스템과 연동하여 판매 데이터 수집
* GPS 기반 및 구독형 신메뉴 알림 제공

<br>

## 🏗 시스템 아키텍처

\[시스템 아키텍처 이미지]

**기술 스택**

* **백엔드:** Spring Boot (Java 17)
* **DB:** Oracle DB 19c
* **인증:** 이메일 & Firebase Token
* **기타:** POS 연동, GPS 기반 알림

<br>

## 📡 API 요약

| Method | Endpoint                    | 설명                |
| ------ | --------------------------- | ----------------- |
| GET    | `/calendar/menus`           | 캘린더용 신메뉴 데이터 조회   |
| GET    | `/menu?category={category}` | 카테고리별 신메뉴 조회      |
| POST   | `/auth/signup`              | 이메일 회원가입          |
| POST   | `/auth/login`               | 로그인 및 토큰 발급       |
| GET    | `/menus/nearby`             | GPS 기반 주변 신메뉴 조회  |
| POST   | `/reviews`                  | 메뉴 리뷰 작성          |
| GET    | `/trends/popular`           | (예정) 인기 메뉴 트렌드 조회 |

<br>

## 🗄 데이터베이스 구조

\[ERD 이미지]

**주요 테이블**

* `USERS`: 사용자 계정 및 프로필
* `MENUS`: 메뉴 정보(출시일, 카테고리, 판매 위치)
* `REVIEWS`: 사용자 리뷰 및 평점
* `POS_DATA`: 매장별 POS 판매 데이터
* `ALERTS`: 구독 및 GPS 기반 알림 설정

<br>

## ⚡ 구현 특징

* `@EntityGraph` 및 `JOIN FETCH`를 활용해 **N+1 문제 해결**
* **DTO 패턴** 적용으로 데이터 전송 최적화
* **Spring Scheduler**를 이용한 일별 메뉴 데이터 동기화
* **CORS & Security 설정**으로 React Native(Expo) 연동 지원
* **Firebase Cloud Messaging (FCM)** 알림 기능 연동 예정

<br>

## 🚀 실행 방법

### 1️⃣ 프로젝트 클론

```bash
git clone https://github.com/yourusername/fresh-guys-backend.git
cd fresh-guys-backend
```

### 2️⃣ 환경변수 설정

* `application.properties` 예시

```properties
spring.datasource.url=jdbc:oracle:thin:@localhost:1521:xe
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

### 3️⃣ 스프링부트 실행

```bash
./gradlew bootRun
```

서버 실행 주소: **[http://localhost:8080](http://localhost:8080)**

<br>

## 🏆 수상

* **선문대 IoT 빅데이터 응용교육 성과 경진대회 우수상 (2025.05)**

