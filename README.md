# 일정관리 프로젝트

### Lv 0. API 명세 및 ERD 작성   `필수`

- [ ]  **API 명세서 작성하기**
    - [ ]  API명세서는 프로젝트 root(최상위) 경로의 `README.md` 에 작성
    - 참고) API 명세서 작성 가이드
        - API 명세서란 API명, 요청 값(파라미터), 반환 값, 인증/인가 방식, 데이터 및 전달 형식 등 API를 정확하게 호출하고 그 결과를 명확하게 해석하는데 필요한 정보들을 일관된 형식으로 기술한 것을 의미합니다.
        - request 및 response는 [json(링크)](https://namu.wiki/w/JSON) 형태로 작성합니다.
        
        [예) [서점] 책 API 설계하기](https://www.notion.so/1832dc3ef5148117b04eff90ef7264e7?pvs=21)
        
        > API 명세서 추천 무료 Tool
        > 
        > 
        > [Document your APIs in Postman | Postman Learning Center](https://learning.postman.com/docs/publishing-your-api/api-documentation-overview/)
        >
|  기능                |Method |URL        |request |response|상태코드|
|------                 |-------|-----      |------- |------- |-------  |
| 일정등록              |POST   |schedule   |요청body|등록정보|200:정상등록|
| 전체일정 조회         |GET    |schedule   |요청param|다건 응답정보|200:정상조회|
| 선택일정조회          |GET    |schedule/1 |요청param|단건 응답 정보|200:정상조회|
| 선택한 일정 수정하기  |PUT    |schedule/1 |요청body|수정정보|200:정상수정|
| 선택한 일정 삭제하기  |DELETE |schedule/1 |요청param|-|200:정상삭제|


        
        
  - ERD 작성간에 다음과 같은 항목들을 학습합니다.
    - E(Entity. 개체)
        - 구현 할 서비스의 영역에서 필요로 하는 데이터를 담을 개체를 의미합니다.
            - ex) 할일,작성자명,비밀번호,작성/수정일
            - 작성 수정일은 날짜와 시간을 모두 포함한 형태.
    - A(Attribute. 속성)
        - 각 개체가 가지는 속성을 의미합니다.
            - ex) 일정은 `할일`, `작성자명`, `비밀번호`, `작성/수정일`등의 속성을 가질 수 있습니다.
    - R(Relationship. 관계)
        - 개체들 사이의 관계를 정의합니다.
            - ex) `작성자`는 여러 개의 `일정`을 등록할 수 있습니다. 이때, 작성자와 일정의 관계는 일대다(1:N) 관계입니다.      
