# 데이터 베이스 삭제, 생성, 선택
DROP DATABASE IF EXISTS text_board;
CREATE DATABASE text_board;
USE text_board;

# article 테이블 생성
CREATE TABLE article (
	id INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
	regDate DATETIME NOT NULL,
	updateDate DATETIME NOT NULL,
	`subject` CHAR(100) NOT NULL,
	content TEXT NOT NULL
);

# member 테이블 생성
CREATE TABLE `member` (
	id INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
	regDate DATETIME NOT NULL,
	updateDate DATETIME NOT NULL,
	username CHAR(50) UNIQUE NOT NULL,
	`password` CHAR(100) NOT NULL,
	`name` CHAR(50) NOT NULL
);

# article 테이블에 memberId 추가
ALTER TABLE article ADD COLUMN memberId INT UNSIGNED NOT NULL AFTER updateDate;

# article 테이블에 hit 칼럼 추가
ALTER TABLE article ADD COLUMN hit INT UNSIGNED NOT NULL AFTER content;

# reply테이블 생성
CREATE TABLE reply (
	id INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
	regDate DATETIME NOT NULL,
	updateDate DATETIME NOT NULL,
	memberId INT UNSIGNED NOT NULL,
	articleId INT UNSIGNED NOT NULL,
	content TEXT NOT NULL
);

# 회원 테스트 데이터
INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
username = 'user1',
`password` = '1234',
`name` = '신짱구';

INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
username = 'user2',
`password` = '1234',
`name` = '김철수';

# 게시물 테스트 데이터
INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
memberId = 1,
`subject` = '제목1',
content = '내용1',
hit = 10;

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
memberId = 1,
`subject` = '제목2',
content = '내용2',
hit = 15;

SELECT COUNT(*) FROM article;

SELECT * FROM article;

# 다량의 게시물 테스트 데이터(개수 증가)
INSERT INTO article (regDate, updateDate, memberId, `subject`, content, hit)
SELECT
NOW(),
NOW(),
CASE WHEN RAND() < 0.5 THEN 1 ELSE 2 END AS RandomValue,
CONCAT('제목-', UUID()),
CONCAT('내용-', UUID()),
CAST(10 + (30-10) * RAND() AS INT)
FROM article;

# 댓글 테스트 데이터
# 1번 회원이 1번 글에 대해 답변
INSERT INTO reply
SET regDate = NOW(),
updateDate = NOW(),
memberId = 1,
articleId = 1, # 1번 글에 대한 답변
content = '1번 게시물에 대한 댓글 1';

# 2번 회원이 1번 글에 대해 답변
INSERT INTO reply
SET regDate = NOW(),
updateDate = NOW(),
memberId = 2,
articleId = 1, # 1번 글에 대한 답변
content = '1번 게시물에 대한 댓글 2';

# 게시물 작성자의 이름을 구하기 위한 SQL
SELECT A.*,
M.`name` AS `extra__writerName`
FROM article AS A
INNER JOIN `member` AS M
ON A.memberId = M.id

# 댓글 작성자의 이름을 구하기 위한 SQL
SELECT R.*,
M.`name` AS `extra__writerName`
FROM reply AS R
INNER JOIN `member` AS M
ON R.memberId = M.id

# 1번 게시물에 대한 댓글
SELECT R.*,
M.`name` AS `extra__writerName`
FROM reply AS R
INNER JOIN `member` AS M
ON R.memberId = M.id
WHERE R.articleId = 1
ORDER BY R.id DESC;

# 해당 게시물에 댓글이 달려 있는지 여부 확인
SELECT COUNT(*) > 0
FROM reply AS R
INNER JOIN `member` AS M
ON R.memberId = M.id
WHERE R.articleId = 2