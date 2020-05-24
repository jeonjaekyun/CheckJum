# CheckJum

## 주제

알라딘 API를 활용한 베스트셀러 제공 및 사용자 위치 기반 공공 도서관 정보 제공 App

## 주제 선정 이유

당시 책을 읽는 것에 흥미를 가지기 시작하여 팀원들과의 회의를 통해 도서 추천 App을 생각 하였다. 
기존 도서 추천 App들이 있었기 때문에 차별성을 주기 위해 인터넷을 검색 하던 중 우리 나라의 독서량 증진을 위해 나라에서
공공 도서관에 대한 지원을 높인다는 기사를 보고 공공 도서관 정보를 제공하면 좋겠다고 생각하였다.

## 목적

도서 추천을 통한 책에 대한 흥미 진진과 공공 도서관 정보를 제공하여 책을 구매하는 것에 대한 부담을 줄여 결과적으로 사용자의
독서량과 도서관 이용률을 증가시킨다

## 사용 언어 및 IT Skill

- HTML, JAVA, Android, PHP
- Apache Tomcat
- MySQL
- Open API

## 구조도

![checkjum_structurechart](https://user-images.githubusercontent.com/39545165/59562248-411c9680-9065-11e9-9557-6178e8962d04.jpg)

## DataBase

- 사용자(ID, PW, 이름, 이메일)
- 도서관(이름, 주소, 휴일, 운영 시간, 전화 번호, 홈페이지, 위도, 경도)
- 관심 도서(사용자 ID, 제목, 표지 , 저자, 출판사, 평점)
- 한 줄 평(사용자 ID, 제목, 표지 , 저자, 출판사, 평점, 한 줄 평)
- 개인 별점(사용자 ID, 제목, 표지, 저자, 출판사, 평점, 별점)

## 주요 기능

- 알라딘 베스트셀러 상위 20개 제공
- 도서 상세 정보 제공
- 관심 도서, 한 줄 평, 개인 별점 등록 및 수정 기능
- 사용자 위치 기반 공공 도서관 정보 제공(수도권 내)

## 스크린샷

#### 로그인 / 도서추천 / 도서정보

<div>
    <img width="200" src="https://user-images.githubusercontent.com/39545165/59562272-ab353b80-9065-11e9-8ae2-8ff8018d5125.jpg">
    <img width="200" src="https://user-images.githubusercontent.com/39545165/59562274-ab353b80-9065-11e9-8948-b4e828c2352d.jpg">
    <img width="200" src="https://user-images.githubusercontent.com/39545165/59562268-aa040e80-9065-11e9-8d42-e5da737597ea.jpg">
</div>

#### 도서관 검색 / 도서관 정보 / 마이페이지

<div>
  <img width="200" src="https://user-images.githubusercontent.com/39545165/59562270-aa9ca500-9065-11e9-8aa6-d30567bc84f0.jpg">
  <img width="200" src="https://user-images.githubusercontent.com/39545165/59562271-aa9ca500-9065-11e9-8e60-65c66fda88e3.jpg">
  <img width="200" src="https://user-images.githubusercontent.com/39545165/59562273-ab353b80-9065-11e9-94e3-c61cd83a1e3b.jpg">
</div>

#### 관심 도서

<img width="200" src="https://user-images.githubusercontent.com/39545165/59562269-aa9ca500-9065-11e9-9608-bb39eedf2b47.jpg">

## 시연 영상

https://www.youtube.com/watch?v=v2GHZ68flIY&feature=youtu.be

