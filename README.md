-en 
---

# 🌱 Fresh Guys Backend (Spring Boot)

**Project Duration:** 2025.03.07 \~ 2025.05.30 (2 months)
**Award:** Excellence Award, IoT Big Data Application Education Course Outcome Contest (Sunmoon University, May 2025)

<br>

## 📌 Project Overview

The **Fresh Guys** backend is the core API and data management service for the mobile app that recommends newly released menus based on user preferences and local availability.

### Key Responsibilities

* Provide REST APIs for menu calendar, category filters, and location-based services
* Handle user authentication and profile management
* Store and process menu, review, and alert data in Oracle DB
* Integrate POS data for real-time trend analysis
* Trigger GPS-based and subscription-based menu alerts

<br>

## 🏗 Architecture

\[System Architecture Image]

**Stack**

* **Backend:** Spring Boot (Java 17)
* **Database:** Oracle DB 19c
* **Authentication:** Email & Firebase Token
* **Others:** POS integration, GPS-based alert system

<br>

## 📡 API Overview

| Method | Endpoint                    | Description                           |
| ------ | --------------------------- | ------------------------------------- |
| GET    | `/calendar/menus`           | Retrieve menu items for calendar view |
| GET    | `/menu?category={category}` | Fetch menus by category               |
| POST   | `/auth/signup`              | Email-based user signup               |
| POST   | `/auth/login`               | User login & token generation         |
| GET    | `/menus/nearby`             | Fetch nearby menus via GPS            |
| POST   | `/reviews`                  | Submit a new review                   |
| GET    | `/trends/popular`           | (Planned) Popular menu trends         |

<br>

## 🗄 Database Structure

\[ERD Image]

**Key Tables**

* `USERS` – User accounts and profiles
* `MENUS` – Menu details with release date, category, and store location
* `REVIEWS` – User reviews and ratings
* `POS_DATA` – POS sales records for analytics
* `ALERTS` – Subscription & GPS-based alert settings

<br>

## ⚡ Implementation Highlights

* Solved **N+1 problem** via `@EntityGraph` and `JOIN FETCH`
* Applied **DTO pattern** for lightweight API responses
* **Spring Scheduler** for daily POS & menu data sync
* **CORS & Security config** for Expo app integration
* **Firebase Cloud Messaging (FCM)** integration for alerts

<br>

## 🚀 How to Run

### 1️⃣ Clone the repository

```bash
git clone https://github.com/yourusername/fresh-guys-backend.git
cd fresh-guys-backend
```

### 2️⃣ Configure environment

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

Server runs at **[http://localhost:8080](http://localhost:8080)**

<br>

## 🏆 Award

* **Excellence Award – IoT Big Data Application Contest**
  Sunmoon University, May 2025

<br><br>

-kr 
---

# 🌱 싱싱한 녀석들 백엔드 (Spring Boot)

**프로젝트 기간:** 2025.03.07 \~ 2025.05.30 (2개월)
**수상:** 선문대학교 IoT 빅데이터 응용교육 성과 경진대회 우수상 (2025.05)

<br>

## 📌 프로젝트 개요

**싱싱한 녀석들 백엔드**는 신메뉴를 지역과 취향에 맞춰 추천하고, POS 데이터를 기반으로 트렌드를 분석하는 **모바일 앱 API 서버**입니다.

### 주요 역할

* 캘린더, 카테고리, GPS 기반 신메뉴 API 제공
* 회원가입/로그인 등 사용자 인증 및 관리
* Oracle DB 기반 메뉴, 리뷰, 알림 데이터 관리
* POS 데이터 연동을 통한 메뉴 인기 분석
* 구독 및 GPS 기반 신메뉴 알림 제공

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
| GET    | `/calendar/menus`           | 캘린더용 신메뉴 조회       |
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

* `USERS` – 사용자 계정 및 프로필
* `MENUS` – 메뉴 정보(출시일, 카테고리, 매장 위치)
* `REVIEWS` – 사용자 리뷰 및 평점
* `POS_DATA` – POS 판매 데이터
* `ALERTS` – 구독 및 GPS 알림 설정

<br>

## ⚡ 구현 특징

* `@EntityGraph` + `JOIN FETCH`로 **N+1 문제 해결**
* **DTO 패턴**으로 API 응답 최적화
* **Spring Scheduler**를 이용한 일일 데이터 동기화
* **CORS & Security 설정**으로 Expo 앱 연동 지원
* **Firebase FCM** 기반 알림 기능 구현 예정

<br>

## 🚀 실행 방법

### 1️⃣ 프로젝트 클론

```bash
git clone https://github.com/yourusername/fresh-guys-backend.git
cd fresh-guys-backend
```

### 2️⃣ 환경설정

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

서버 주소: **[http://localhost:8080](http://localhost:8080)**

<br>

## 🏆 수상

* **선문대 IoT 빅데이터 응용교육 성과 경진대회 – 우수상 (2025.05)**

<br>

---

이제 프론트/백엔드 둘 다 `<br>` 기준으로 통일된 README 템플릿이 완성됐어.
원하면 내가 **프론트+백엔드 통합 버전**도 깔끔하게 만들어줄게. 할까?
****
