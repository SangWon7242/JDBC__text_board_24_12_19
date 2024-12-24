package com.sbs.board;

import com.sbs.board.article.Article;
import com.sbs.board.dbUtil.MysqlUtil;
import com.sbs.board.dbUtil.SecSql;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class App {

  public int articleLastId;
  public List<Article> articleList;

  public App() {
    articleLastId = 0;
    articleList = new ArrayList<>();
  }

  private static boolean isDevMode() {
    // 이 부분을 false로 바꾸면 production 모드 이다.
    // true는 개발자 모드이다.(개발할 때 좋다.)
    return true;
  }

  // 로직의 시작점
  public void run() {
    Scanner sc = new Scanner(System.in);

    System.out.println("== 자바 텍스트 게시판 시작 ==");

    try {
      while (true) {
        System.out.print("명령) ");
        String cmd = sc.nextLine();

        Rq rq = new Rq(cmd);

        // DB 세팅
        MysqlUtil.setDBInfo("localhost", "root", "", "text_board");
        MysqlUtil.setDevMode(isDevMode());
        // DB 끝

        doAction(rq, sc);
      }
    } finally {
      System.out.println("== 자바 텍스트 게시판 종료 ==");
      sc.close();
    }
  }

  private void doAction(Rq rq, Scanner sc) {
    if (rq.getUrlPath().equals("/usr/article/write")) {
      System.out.println("== 게시물 작성 ==");

      System.out.print("제목 : ");
      String subject = sc.nextLine();

      System.out.print("내용 : ");

      String content = sc.nextLine();

      SecSql sql = new SecSql();
      sql.append("INSERT INTO article");
      sql.append("SET regDate = NOW()");
      sql.append(", updateDate = NOW()");
      sql.append(", `subject` = ?", subject);
      sql.append(", content = ?", content);

      int id = MysqlUtil.insert(sql);

      System.out.printf("%d번 게시물이 생성되었습니다.\n", id);

    } else if (rq.getUrlPath().equals("/usr/article/list")) {
      SecSql sql = new SecSql();
      sql.append("SELECT *");
      sql.append("FROM article");
      sql.append("ORDER BY id DESC");

      List<Map<String, Object>> articleListMap = MysqlUtil.selectRows(sql);

      if(articleListMap.isEmpty()) {
        System.out.println("게시물이 존재하지 않습니다.");
        return;
      }

      System.out.println("== 게시물 리스트 ==");
      System.out.println("번호 | 제목");

      articleListMap.forEach(articleMap ->
          System.out.printf("%d | %s\n", (int) articleMap.get("id"), articleMap.get("subject")));


    } else if (rq.getUrlPath().equals("/usr/article/detail")) {
      int id = rq.getIntParam("id", 0);

      if (id == 0) {
        System.out.println("id를 올바르게 입력해주세요.");
        return;
      }

      SecSql sql = new SecSql();
      sql.append("SELECT COUNT(*) > 0");
      sql.append("FROM article");
      sql.append("WHERE id = ?", id);

      boolean articleIsEmpty = MysqlUtil.selectRowBooleanValue(sql);

      if(!articleIsEmpty) {
        System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
        return;
      }

      sql = new SecSql();
      sql.append("SELECT *");
      sql.append("FROM article");
      sql.append("WHERE id = ?", id);

      Map<String, Object> articleMap = MysqlUtil.selectRow(sql);

      System.out.printf("== %d번 게시물 상세보기 ==\n", id);
      System.out.printf("번호 : %d\n", (int) articleMap.get("id"));
      System.out.printf("작성날짜 : %s\n", articleMap.get("regDate"));
      System.out.printf("수정날짜 : %s\n", articleMap.get("updateDate"));
      System.out.printf("제목 : %s\n", articleMap.get("subject"));
      System.out.printf("내용 : %s\n", articleMap.get("content"));

    } else if (rq.getUrlPath().equals("/usr/article/modify")) {
      int id = rq.getIntParam("id", 0);

      if (id == 0) {
        System.out.println("id를 올바르게 입력해주세요.");
        return;
      }

      SecSql sql = new SecSql();
      sql.append("SELECT COUNT(*) > 0");
      sql.append("FROM article");
      sql.append("WHERE id = ?", id);

      boolean articleIsEmpty = MysqlUtil.selectRowBooleanValue(sql);

      if(!articleIsEmpty) {
        System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
        return;
      }

      System.out.println("== 게시물 수정 ==");
      System.out.print("제목 : ");
      String subject = sc.nextLine();

      System.out.print("내용 : ");
      String content = sc.nextLine();

      sql = new SecSql();
      sql.append("UPDATE article");
      sql.append("SET updateDate = NOW()");
      sql.append(", `subject` = ?", subject);
      sql.append(", content = ?", content);
      sql.append("WHERE id = ?", id);

      MysqlUtil.update(sql);

      System.out.printf("%d번 게시물이 수정되었습니다.\n", id);

    } else if (rq.getUrlPath().equals("exit")) {
      System.out.println("프로그램을 종료합니다.");
      System.exit(0); // 프로그램 강제종료
    } else {
      System.out.println("잘못 된 명령어입니다.");
    }
  }
}
