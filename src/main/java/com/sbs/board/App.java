package com.sbs.board;

import com.sbs.board.article.Article;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {

  public int articleLastId;
  public List<Article> articleList;
  
  public App() {
    articleLastId = 0;
    articleList = new ArrayList<>();
  }

  // 로직의 시작점
  public void run() {
    Scanner sc = new Scanner(System.in);

    System.out.println("== 자바 텍스트 게시판 시작 ==");

    while (true) {
      System.out.print("명령) ");
      String cmd = sc.nextLine();

      if(cmd.equals("/usr/article/write")) {
        System.out.println("== 게시물 작성 ==");

        System.out.print("제목 : ");
        String subject = sc.nextLine();

        System.out.print("내용 : ");
        String content = sc.nextLine();
        int id = ++articleLastId;

        Article article = new Article(id, subject, content);
        articleList.add(article);
        System.out.println(article);

        System.out.printf("%d번 게시물이 생성되었습니다.\n", article.getId());
      }
      else if(cmd.equals("/usr/article/list")) {
        System.out.println("게시물 리스트");
      }
      else if(cmd.equals("break")) {
        System.out.println("프로그램을 종료합니다.");
        break;
      }
      else {
        System.out.println("잘못 된 명령어입니다.");
      }
    }

    System.out.println("== 자바 텍스트 게시판 종료 ==");
    sc.close();
  }
}
