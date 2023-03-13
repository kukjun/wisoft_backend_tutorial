# Toy Project (강의 정보 나눔 게시판)

ToyProject를 통해서 Frontend와 Backend를 보다 잘 이해하기 위해 기획하였습니다.

<br>
<br>
<br>

## Version

사용한 라이브러리 버전입니다.

Spring Boot - 3.0.2
<br>
Java - 17
<br>
jjwt - 0.9.1
<br>
crypto - 5.6.0

<br>
<br>
<br>

## 개요

해당 서비스는 강의에 대한 사용자들 간의 의견을 나누기 위한 목적으로 만들게 되었습니다.

<br>

먼저 **사용자(MEMBER)** 들은 ADMIN, USER로 나눌 수 있습니다.
<br>
ADMIN은 **강의(LECTURE)** 의 게시판을 개설할 수 있습니다.
<br>
**MEMBER(USER, ADMIN)** 는 해당 강의 게시판이 열리면 해당 강의에 대한 **게시글(POST)** 을 작성할 수 있습니다.
<br>
**MEMBER(USER, ADMIN)** 는 게시글에 대해 **덧글(COMMENT)** 을 작성할 수 있습니다.

<br>
<br>

**자세한 API 문서, 동작 과정, DB 구조 등 자세한 정보는 민기형이 알려줄거예요. 😃** 