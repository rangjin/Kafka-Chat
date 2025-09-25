# Kafka-Chat

1인 프로젝트 / 2025.08 ~ 2025.09

Discord를 모방한 채팅 서버 구현 프로젝트

## 프로젝트 개요

해당 프로젝트는 메시징 브로커를 통해 메시지 송신과 수신을 각각 REST API, WebSocket으로 분리하고 검색엔진을 적용해 메시지 내용 기반 Full-text 검색이 가능하도록 한 실시간 채팅 시스템 개발 프로젝트입니다

---

## 주요 기능

| 기능              | 설명                               |
|-----------------|----------------------------------|
| 메시지 송신 / 수신 | REST API로 메시지 전송, WebSocket으로 메세지 수신 |
| 메시지 기반 검색 | 검색 엔진을 적용해 메시지 내용 기반 Full-text 검색 |
| 채널 참여 / 탈퇴 | REST API로 채널 참여 & 탈퇴 |

---

## 시스템 아키텍쳐

<img width="512" height="452" alt="Frame 5" src="https://github.com/user-attachments/assets/b530ab10-e5a2-4a02-8baa-9185b87f059e" />

---


## 사용 기술
- **Spring Boot**: 헥사고날 아키텍쳐를 적용한 API 서버, 색인 서버 구축
- **Apache Kafka**: 메세지와 채널 참여/탈퇴 이벤트를 색인 서버와 실시간 게이트웨이에 전달
- **Node.js & Express.js & Socket.IO**: 카프카로 전달받은 이벤트를 웹소켓으로 유저에게 전달하는 실시간 게이트웨어 서버 구축
- **MySQL**: 메인 DB, DB와 Kafka 간 일관성을 지키기 위해 Outbox 패턴 적용
- **Debezium**: DB 로그를 읽어 Outbox의 변화를 Kafka로 발행
- **Elastic Search**: Full-text 검색을 위한 검색 엔진
