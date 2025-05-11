# 🏢 Smart Factory Monitoring & Simulation System

Spring Boot 기반의 프로젝트입니다. 디버이스 상태를 기본으로, 부품 생산 및 제품 생산의 결과를 API로 제공합니다.

## ✨ Features

### 해당 디버이스의 상태 갱신

* POST `/api/device-status`
* 온도/습도를 DB에 저장

### 개선 조건(온도/습도)을 적용하여 생산 시뮬레이션

* POST `/api/simulation/improve`
* 온도/습도 개선이 실제 생산에 어느 정도의 효과가 있는지 확인

### RBAC & 인증

* JWT + Spring Security
* `@ProtectedApi(role = "...")`를 적용하여, AOP 개선을 통해 사용자의 ROLE을 검사
* `RoleHierarchy` 적용 거치: ADMIN > USER > Public

### Swagger 문서

* Springdoc OpenAPI
* Request/Response DTO 설명

## 프로젝트 구조

```
Device ---1:N---> DeviceStatus
      \
       +--1:N---> Part

Product ---1:N---> ProductPart ---N:1---> Part
```

## 단위 API 예제

### 디바이스 상태 갱신

```json
POST /api/device-status
{
  "deviceId": 1,
  "temperature": 25.0,
  "humidity": 60.0
}
```

### 개선된 온도/습도를 적용하여 결과 비교

```json
POST /api/simulation/improve
{
  "deviceId": 1,
  "improvedTemperature": 22.5,
  "improvedHumidity": 45.0
}
```

결과:

```json
{
  "beforePartCount": 100,
  "afterPartCount": 120,
  "beforeProductCount": 30,
  "afterProductCount": 40,
  "beforeTemperature": 28.0,
  "afterTemperature": 22.5,
  "beforeHumidity": 60.0,
  "afterHumidity": 45.0,
  "productImprovementRate": 33.3,
  "deviceName": "Sensor-A",
  "improved": true,
  "processingTimeMs": 8
}
```

