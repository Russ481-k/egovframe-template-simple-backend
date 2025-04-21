# API 문서화 개선 내용

## 1. Swagger 설정 개선

### SwaggerConfig.java

- API 문서 제목: "User Management System API"
- API 설명: "사용자 관리 시스템 API 문서"
- JWT 인증 스키마 설정
  - Bearer 토큰 인증 방식
  - Authorization 헤더 사용
  - JWT 포맷 지정

## 2. API 응답 스키마 표준화

### ApiResponseSchema.java

- 표준화된 응답 형식 정의
  - success: 성공/실패 여부 (boolean)
  - message: 응답 메시지 (String)
  - data: 응답 데이터 (Generic Type)
- 정적 팩토리 메서드 제공
  - success(): 성공 응답 생성
  - error(): 실패 응답 생성

## 3. API 태그 체계화

### ApiTags.java

- 인증 관련 API: Authentication
  - 로그인/로그아웃
  - 토큰 검증
  - 비밀번호 변경
- 사용자 관리 API: User Management
  - 사용자 CRUD
  - 사용자 상태 관리
  - 사이트 관리자 등록
- 사용자 활동 로그 API: User Activity Log
- 비밀번호 재설정 API: Password Reset

## 4. 공통 응답 정의

### ApiResponses.java

- 기본 응답 (DEFAULT)

  - 200: 요청 성공
  - 400: 잘못된 요청
  - 401: 인증 필요
  - 403: 접근 거부
  - 404: 리소스 없음
  - 500: 서버 오류

- 인증 관련 응답 (AUTH)

  - 200: 인증 성공
  - 400: 잘못된 인증 정보
  - 401: 인증 실패

- 사용자 관련 응답 (USER)

  - 200: 사용자 정보 처리 성공
  - 400: 잘못된 사용자 정보
  - 401: 인증 필요
  - 403: 접근 권한 없음
  - 404: 사용자 없음

- 활동 로그 관련 응답 (ACTIVITY)
  - 200: 활동 로그 처리 성공
  - 401: 인증 필요
  - 403: 접근 권한 없음

## 5. API 엔드포인트 통합

### 인증 관련 API (/api/auth)

- POST /login: 일반 로그인
- POST /sns/{provider}: SNS 로그인
- POST /logout: 로그아웃
- POST /validate: 토큰 검증
- PATCH /password: 비밀번호 변경

### 사용자 관리 API (/api/v1/users)

- POST /: 사용자 생성
- PUT /{userId}: 사용자 정보 수정
- DELETE /{userId}: 사용자 삭제
- GET /{userId}: 사용자 정보 조회
- GET /: 사용자 목록 조회
- POST /{userId}/change-password: 비밀번호 변경
- PUT /{userId}/status: 사용자 상태 변경
- POST /register: 사용자 등록
- POST /site-managers: 사이트 관리자 등록
- GET /site-info: 사이트 정보 조회

## 6. 문서화 개선 효과

1. **일관성**

   - 모든 API 응답이 표준화된 형식을 따름
   - 응답 코드와 메시지가 통일됨

2. **가독성**

   - API 그룹화로 관련 API를 쉽게 찾을 수 있음
   - 명확한 응답 코드 설명 제공

3. **유지보수성**

   - 공통 응답 정의로 중복 코드 감소
   - 응답 형식 변경 시 한 곳에서 수정 가능

4. **보안**
   - JWT 인증 관련 문서화 개선
   - 권한 관련 응답 코드 명확화

## 7. 향후 개선 계획

1. **예제 추가**

   - 각 API의 요청/응답 예제 추가
   - 에러 케이스 예제 추가

2. **테스트 문서화**

   - API 테스트 방법 문서화
   - 테스트 시나리오 추가

3. **버전 관리**
   - API 버전별 문서 관리
   - 변경 이력 추적
