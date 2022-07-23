# 프로젝트 소개

무엇이든 간단하게 물어보고 답할 수 있는 커뮤니티 사이트
React + Springboot Web Application

site: https://blank.quest


# 개발환경

- BACK-END
    - JAVA JDK11
    - springboot 2.7.0
    - gradle
    - h2 (test)
    - mariadb (production)
    - spring data jpa
    - spring security
- FRONT-END
    - typescript
    - react
    - styled-components
- DEPLOY
    - git
    - aws ec2
    - aws rds
    - nginx
- TOOLS
    - intellij
    - VSC


# 구현 기능

- 사용자 관리
    - 소셜 로그인
    - 프로필 변경 기능
    - 프로필에서 작성한 최근 질문 및 답변 확인
- 게시판 CRUD
    - 질문 글 작성, 수정, 삭제 기능
    - 답변 글 작성, 수정, 삭제 기능
    - 질문 글 검색 기능 (키워드 별, 카테고리 별 검색)
- 질문 글 조회 수 확인 기능
- 최근 3일 이내 조회 수 상위 5개 질문글 노출 기능

# 프론트엔드 서버 접근 URL

| url | name | description |
| --- | --- | --- |
| / | 홈페이지 |  |
| /login | 로그인 페이지 | 소셜 로그인을 위한 링크 페이지 |
| /questions | 질문 검색 페이지 | 질문 검색 및 조회수 높은 질문을 확인할 수 있는 페이지 |
| /questions/{no} | 질문 상세페이지 | 질문글 상세 페이지, 답변글 작성 가능 |
| /user/{no} | 유저 프로필 |  |


# REST API 명세서

### HOST

https://blank.quest

### REQUEST

- 요청 데이터를 body에 넣어 요청해야 하는 경우 Header의 “Content-Type”을 “application/json”으로 설정하여 요청한다.
- 유저의 권한이 필요한 요청의 경우 발급 받은 “JSESSIONID”쿠키를 포함하여 요청한다.

### REQUEST OBJECT

- 유저 수정

| name | type | description | required |
| --- | --- | --- | --- |
| nickname | String | 유저 닉네임 | O |
- 질문 등록/수정

| name | type | description | required |
| --- | --- | --- | --- |
| content | String | 질문 내용 | O |
| categoryValue | String | 질문 카테고리 | O |
- 답변 등록/수정

| name | type | description | required |
| --- | --- | --- | --- |
| questionNo | Long | 답변을 등록할 질문의 번호 | O |
| content | String | 답변 내용 | O |

### RESPONSE

| name | type | description |
| --- | --- | --- |
| data | 답변 데이터 객체 별로 상이(아래 객체 설명 참조) | 요청한 데이터 |
| error | Error | 에러 발생 시 에러 응답 객체 |

### DATA OBJECT

- 유저

| name | type | description | required |
| --- | --- | --- | --- |
| no | Long | 유저 번호 | O |
| nickname | String | 유저 닉네임 | O |
| email | String | 유저 이메일 | O |
| profileImgUrl | String | 유저 프로필이미지 URL | X |
- 질문

| name | type | description | required |
| --- | --- | --- | --- |
| no | Long | 질문 번호 | O |
| categoryValue | String | 카테고리 이름 | O |
| content | String | 질문 내용 | O |
| writer | String | 질문 작성자 닉네임 | O |
| writerNo | Long | 질문 작성자 번호 | O |
| views | Integer | 조회수 | O |
- 답변

| name | type | description | required |
| --- | --- | --- | --- |
| no | Long | 답변 번호 | O |
| content | String | 답변 내용 | O |
| writer | String | 답변 작성자 닉네임 | O |
| writerNo | Long | 답변 작성자 번호 | O |
- 질문 리스트(페이징)

| name | type | description | required |
| --- | --- | --- | --- |
| questions | List<질문> | 조회된 질문 목록 | O |
| hasNext | boolean | 다음 페이지 유무 | O |
- 답변 리스트(페이징)

| name | type | description | required |
| --- | --- | --- | --- |
| answers | List<답변> | 조회된 답변 목록 | O |
| hasNext | boolean | 다음 페이지 유무 | O |

### ERROR OBJECT

| name | type | description | required |
| --- | --- | --- | --- |
| errorCode | String | 에러 코드 | O |
| message | String | 에러 메시지 | O |

### ERROR CODE

| CODE | description |
| --- | --- |
| SERVER | 서버 오류 |
| DB | DB관련 오류 |
| CLIENT | 클라이언트 오류 |

### SUMMARY
| Method | URI | Description | request | credentials | response detail |
| --- | --- | --- | --- | --- | --- |
| POST | /api/v1/question | 질문 등록 | json: 질문 등록/수정 | O | 201 CREATED / json: 질문 |
| GET | /api/v1/question/{no} | 질문 조회 |  |  | 200 OK / data: 질문 |
| PUT | /api/v1/question/{no} | 질문 수정 | json: 질문 등록/수정 | O | 202 ACCEPTED / data: 질문 |
| DELETE | /api/v1/question/{no} | 질문 삭제 |  | O | 200 OK |
| GET | /api/v1/question/category | 질문 카테고리  |  |  | 200 OK / data: 카테고리 |
| GET | /api/v1/question/top5 | 오늘 포함 최근 3일 기준 조회수 top5 |  |  | 200 OK / data: List<질문> |
| POST | /api/v1/answer | 답변 등록 | json: 답변 등록/수정 | O | 201 CREATED / data: 답변 |
| GET | /api/v1/answer | 질문에 대한 답변 | parameters: questionNo(Integer), page(Integer), size(Integer) |  | 200 OK / data: 답변 리스트(페이징) |
| PUT | /api/v1/answer/{no} | 답변 수정 | json: 답변 등록/수정 | O | 202 ACCEPTED / data: 답변 |
| DELETE | /api/v1/answer/{no} | 답변 삭제 |  | O | 200 OK |
| GET | /api/v1/user | 세션에 담긴 로그인  된 사용자 정보 |  | O | 200 OK / data: 유저 |
| GET | /api/v1/user/{no} | 유저 프로필 |  |  | 200 OK / data: 유저 |
| PUT | /api/v1/user/{no} | 유저 프로필 수정 | json: 유저 수정 | O | 202 ACCEPTED / data: 유저 |
| DELETE | /api/v1/user/{no} | 유저 정보 삭제(탈퇴) |  | O | 200 OK |
| GET | /api/v1/user/{no}/question/top3 | 유저 질문 최신 top3 |  |  | 200 OK / data: List<질문> |
| GET | /api/v1/user/{no}/answer/top3 | 유저 답변 최신 top3 |  |  | 200 OK / data: List<답변> |
| GET | /api/v1/search/question | 검색된 질문 리스트 | parameters: categoryValue(String), word(String), page( Integer), size(Integer) |  | 200 OK / data: 질문 리스트(페이징) |
