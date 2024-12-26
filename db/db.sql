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