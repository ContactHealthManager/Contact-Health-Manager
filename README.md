
## 캡스톤 디자인 프로젝트  : Contact Health manager (컨택트 헬스 매니저)
- 전문 헬스 트레이너 매칭 웹 서비스
- 사용자와 헬스 트레이너간 채팅 
- 특정 헬스 트레이너를 주변에서 찾기
- 반응형 웹 구현으로 스마트폰으로도 간편

---
[링크]

-<a href="https://www.youtube.com/watch?v=dygkSRegXVY&t=28s">웹 구동 시뮬레이션 동영상</a>
<br>
-<a href="https://github.com/ContactHealthManager/spring_practice/tree/master/document">컨택트 헬스 매니저 최종 보고서</a>

---

## 개발 환경 도구 및 기술 스택
![tools](https://user-images.githubusercontent.com/43032589/102872643-573dbb00-4483-11eb-8fa4-3b1aef6f4dc9.png)

---

## 전체 구성도
![flowchart](https://user-images.githubusercontent.com/43032589/102872665-6290e680-4483-11eb-864b-c23213dd4de5.png)

---

## DB 구조
![DB](https://user-images.githubusercontent.com/43032589/102874193-7d645a80-4485-11eb-8df2-c1ab5c0d3320.png)


---

## Front 화면 



### 메인페이지
![main](https://user-images.githubusercontent.com/43032589/102874509-e21fb500-4485-11eb-88c2-d0bec7ee1af2.png)



### 게시물 등록
![register](https://user-images.githubusercontent.com/43032589/102874513-e2b84b80-4485-11eb-8927-0a56217b9c32.png)



### 게시물 상세정보
![detail](https://user-images.githubusercontent.com/43032589/102874517-e350e200-4485-11eb-95e0-a6036b86bc86.png)



### 주변 매니저
![map](https://user-images.githubusercontent.com/43032589/102874520-e350e200-4485-11eb-9a1d-cd03b32d5de6.png)



### 내 정보
![my](https://user-images.githubusercontent.com/43032589/102874521-e3e97880-4485-11eb-9778-254a0b2d4651.png)


---

## Back-end 핵심 기능
- 로그인 ,회원가입 ,로그아웃 , 유저 권한 부여 (트레이너/일반유저)
- 전체 매니저 게시물 CRUD(작성,조회,수정,삭제) 
- 전체 매니저 게시물 날짜,조회순 정렬 필터 
- 전체 매니저 게시물 페이징
- 메인 페이지 인기 게시물 TOP 5 조회
- 전체 매니저 게시물 검색
- 주변 매니저 주소로 게시물 찾기 (kakaomap API)
- 주변 매니저 등록된 게시물 마커로 표시
- 주변 매니저 게시물 마커 클릭시 미리보기
- 주변 매니저 미리보기 상세정보 클릭시 게시물 상세정보
- 사용자와 헬스 매니저(게시물 작성자) 간의 1 대 1 채팅방
- MYTALK 나의 채팅방 목록
- 내가 좋아하는 게시물 찜저장 및 찜삭제
- 내 정보에서 내가 찜한 게시물 조회하기 및 상세정보 이동

---

## author
정재빈(jaebin1234)<br>
-<a href="//https://github.com/jaebin1234"정재빈(jaebin1234)</a><br>
이성수(LeeSeongSu)<br>
진형준(jhj960918)<br>

### Helper
김승래(Seungrae)<br>
박현주()<br>

