package com.sbs.board;

import com.sbs.board.article.Article;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {

  public int articleLastId;
  public List<Article> articleList;

  // JDBC URL, 사용자명, 비밀번호를 설정합니다.
  private static final String DB_URL = "jdbc:mysql://localhost:3306/text_board?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeBehavior=convertToNull";
  private static final String DB_USER = "root"; // MariaDB 사용자 이름
  private static final String DB_PASSWORD = ""; // MariaDB 비밀번호

  public App() {
    articleLastId = 0;
    articleList = new ArrayList<>();
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

        // MariaDB에 연결 시작
        Connection conn = null;
        PreparedStatement pstat = null;

        try {
          // JDBC 드라이버 로드
          Class.forName("com.mysql.cj.jdbc.Driver");
          conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

          // 액션 메서드 시작
          doAction(conn, pstat, rq, sc);


        } catch (ClassNotFoundException e) {
          System.err.println("JDBC 드라이버를 찾지 못했습니다.");
          e.printStackTrace();
        } catch (SQLException e) {
          System.out.println("데이터 베이스 연결 실패");
          e.printStackTrace();
        } finally {
          // 자원 해제
          try {
            if (conn != null && !conn.isClosed()) {
              conn.close(); // 데이터 베이스 연결 해제
            }
          } catch (SQLException e) {
            e.printStackTrace();
          }
        }

        // MariaDB에 연결 끝
      }
    } finally {
      System.out.println("== 자바 텍스트 게시판 종료 ==");
      sc.close();
    }
  }

  private void doAction(Connection conn, PreparedStatement pstat, Rq rq, Scanner sc) {
    if (rq.getUrlPath().equals("/usr/article/write")) {
      System.out.println("== 게시물 작성 ==");

      System.out.print("제목 : ");
      String subject = sc.nextLine();

      System.out.print("내용 : ");

      String content = sc.nextLine();
      int id = ++articleLastId;

      Article article = new Article(id, subject, content);

      try {
        String sql = "INSERT INTO article";
        sql += " SET regDate = NOW()";
        sql += ", updateDate = NOW()";
        sql += ", `subject` = '%s'".formatted(subject);
        sql += ", content = '%s';".formatted(content);

        pstat = conn.prepareStatement(sql);
        pstat.executeUpdate();

        System.out.printf("%d번 게시물이 생성되었습니다.\n", article.getId());

      } catch (SQLException e) {
        System.out.println("데이터 베이스 연결 실패");
        e.printStackTrace();
      } finally {
        try {
          if (pstat != null && !pstat.isClosed()) pstat.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }

    } else if (rq.getUrlPath().equals("/usr/article/list")) {
      ResultSet rs = null;

      try {

        String sql = "SELECT *";
        sql += " FROM article";
        sql += " ORDER BY id DESC;";

        pstat = conn.prepareStatement(sql);
        rs = pstat.executeQuery(sql);

        // rs에서 데이터 읽기
        while (rs.next()) {
          int id = rs.getInt("id");
          LocalDateTime regDate = rs.getTimestamp("regDate").toLocalDateTime();
          LocalDateTime updateDate = rs.getTimestamp("updateDate").toLocalDateTime();
          ;
          String subject = rs.getString("subject");
          String content = rs.getString("content");

          Article article = new Article(id, regDate, updateDate, subject, content);
          articleList.add(article);
        }

        System.out.println("== 게시물 리스트 ==");
        System.out.println("번호 | 제목");

        articleList.forEach(article ->
            System.out.printf("%d | %s\n", article.getId(), article.getSubject()));

      } catch (SQLException e) {
        System.out.println("데이터 베이스 연결 실패");
        e.printStackTrace();
      } finally {
        try {
          if (rs != null && !rs.isClosed()) rs.close();
          if (pstat != null && !pstat.isClosed()) pstat.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    } else if (rq.getUrlPath().equals("/usr/article/modify")) {
      int id = rq.getIntParam("id", 0);

      if (id == 0) {
        System.out.println("id를 올바르게 입력해주세요.");
        return;
      }

      System.out.println("== 게시물 수정 ==");
      System.out.print("제목 : ");
      String subject = sc.nextLine();

      System.out.print("내용 : ");
      String content = sc.nextLine();

      try {
        String sql = "UPDATE article";
        sql += " SET updateDate = NOW()";
        sql += ", `subject` = '%s'".formatted(subject);
        sql += ", content = '%s'".formatted(content);
        sql += " WHERE id = %d;".formatted(id);

        pstat = conn.prepareStatement(sql);
        // 쿼리 실행

        pstat.executeUpdate();

        System.out.printf("%d번 게시물이 수정되었습니다.\n", id);

      } catch (SQLException e) {
        System.out.println("데이터 베이스 연결 실패");
        e.printStackTrace();
      } finally {
        try {
          if (pstat != null && !pstat.isClosed()) pstat.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    } else if (rq.getUrlPath().equals("exit")) {
      System.out.println("프로그램을 종료합니다.");
      System.exit(0); // 프로그램 강제종료
    } else {
      System.out.println("잘못 된 명령어입니다.");
    }
  }
}
