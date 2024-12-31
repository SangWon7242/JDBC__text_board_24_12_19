package com.sbs.board;

import com.sbs.board.article.ArticleController;
import com.sbs.board.container.Container;
import com.sbs.board.controller.Controller;
import com.sbs.board.dbUtil.MysqlUtil;
import com.sbs.board.member.MemberController;

import java.util.Scanner;

public class App {

  public ArticleController articleController;
  public MemberController memberController;

  public App() {
    articleController = Container.articleController;
    memberController = Container.memberController;
  }

  private static boolean isDevMode() {
    // 이 부분을 false로 바꾸면 production 모드 이다.
    // true는 개발자 모드이다.(개발할 때 좋다.)
    return true;
  }

  // 로직의 시작점
  public void run() {
    System.out.println("== 자바 텍스트 게시판 시작 ==");

    try {
      while (true) {
        System.out.print("명령) ");
        String cmd = Container.sc.nextLine();

        Rq rq = new Rq(cmd);

        // DB 세팅
        MysqlUtil.setDBInfo("localhost", "root", "", "text_board");
        MysqlUtil.setDevMode(isDevMode());
        // DB 끝
        
        // 액션 메서드 시작
        doAction(rq);
      }
    } finally {
      System.out.println("== 자바 텍스트 게시판 종료 ==");
      Container.sc.close();
    }
  }

  private void doAction(Rq rq) {
    Controller controller = getControllerByUrl(rq.getUrlPath());

    if (controller != null) {
      controller.performAction(rq);
    } else if (rq.getUrlPath().equals("exit")) {
      System.out.println("프로그램을 종료합니다.");
      System.exit(0); // 프로그램 강제종료
    } else {
      System.out.println("잘못 된 명령어입니다.");
    }
  }

  private Controller getControllerByUrl(String urlPath) {
    if(urlPath.startsWith("/usr/article/")) {
      return articleController;
    }
    else if(urlPath.startsWith("/usr/member/")) {
      return memberController;
    }

    return null;
  }
}
