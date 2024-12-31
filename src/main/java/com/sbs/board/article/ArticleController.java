package com.sbs.board.article;

import com.sbs.board.Rq;
import com.sbs.board.container.Container;
import com.sbs.board.controller.Controller;
import com.sbs.board.member.Member;

import java.util.List;
import java.util.Scanner;

public class ArticleController implements Controller {
  private final ArticleService articleService;
  public Scanner sc;

  public ArticleController() {
    articleService = Container.articleService;
    sc = Container.sc;
  }

  public void performAction(Rq rq) {
    switch (rq.getUrlPath()) {
      case "/usr/article/write" -> doWrite(rq);
      case "/usr/article/list" -> showList();
      case "/usr/article/detail" -> showDetail(rq);
      case "/usr/article/modify" -> doModify(rq);
      case "/usr/article/delete" -> doDelete(rq);
      default -> System.out.println("해당 경로는 존재하지 않습니다.");
    }
  }

  public void doWrite(Rq rq) {
    if(!rq.isLogined()) {
      System.out.println("로그인 후 이용해주세요.");
      return;
    }

    System.out.println("== 게시물 작성 ==");

    System.out.print("제목 : ");
    String subject = sc.nextLine();

    if(subject.trim().isEmpty()) {
      System.out.println("제목을 입력해주세요.");
      return;
    }

    System.out.print("내용 : ");
    String content = sc.nextLine();

    if(content.trim().isEmpty()) {
      System.out.println("내용을 입력해주세요.");
      return;
    }

    Member member = (Member) rq.getSessionAttr("loginedMember");
    int memberId = member.getId();

    int id = articleService.write(memberId, subject, content);

    System.out.printf("%d번 게시물이 생성되었습니다.\n", id);
  }

  public void showList() {
    List<Article> articleList = articleService.getArticles();

    if(articleList == null) {
      System.out.println("게시물이 존재하지 않습니다.");
      return;
    }

    System.out.println("== 게시물 리스트 ==");
    System.out.println("번호 | 제목 | 작성자");

    articleList.forEach(article ->
        System.out.printf("%d | %s | %s\n",
            article.getId(), article.getSubject(), article.getExtra__writerName()));
  }

  public void showDetail(Rq rq) {
    int id = rq.getIntParam("id", 0);

    if (id == 0) {
      System.out.println("id를 올바르게 입력해주세요.");
      return;
    }

    Article article = articleService.findById(id);

    if(article == null) {
      System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
      return;
    }

    System.out.printf("== %d번 게시물 상세보기 ==\n", id);
    System.out.printf("번호 : %d\n", article.getId());
    System.out.printf("작성날짜 : %s\n", article.getRegDate());
    System.out.printf("수정날짜 : %s\n", article.getUpdateDate());
    System.out.printf("작성자 : %s\n", article.getExtra__writerName());
    System.out.printf("제목 : %s\n", article.getSubject());
    System.out.printf("내용 : %s\n", article.getContent());
  }

  public void doModify(Rq rq) {
    int id = rq.getIntParam("id", 0);

    if (id == 0) {
      System.out.println("id를 올바르게 입력해주세요.");
      return;
    }

    if(!rq.isLogined()) {
      System.out.println("로그인 후 이용해주세요.");
      return;
    }

    Member member = (Member) rq.getSessionAttr("loginedMember");
    Article article = articleService.findById(id);

    if(article == null) {
      System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
      return;
    }

    if(article.getMemberId() != member.getId()) {
      System.out.println("글 수정에 대한 권한이 없습니다.");
      return;
    }

    System.out.println("== 게시물 수정 ==");

    System.out.print("제목 : ");
    String subject = sc.nextLine();

    if(subject.trim().isEmpty()) {
      System.out.println("제목을 입력해주세요.");
      return;
    }

    System.out.print("내용 : ");
    String content = sc.nextLine();

    if(content.trim().isEmpty()) {
      System.out.println("내용을 입력해주세요.");
      return;
    }

    articleService.update(id, subject, content);

    System.out.printf("%d번 게시물이 수정되었습니다.\n", id);
  }

  public void doDelete(Rq rq) {
    int id = rq.getIntParam("id", 0);

    if (id == 0) {
      System.out.println("id를 올바르게 입력해주세요.");
      return;
    }

    if(!rq.isLogined()) {
      System.out.println("로그인 후 이용해주세요.");
      return;
    }

    Member member = (Member) rq.getSessionAttr("loginedMember");
    Article article = articleService.findById(id);

    if(article == null) {
      System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
      return;
    }

    if(article.getMemberId() != member.getId()) {
      System.out.println("글 수정에 대한 권한이 없습니다.");
      return;
    }

    articleService.delete(id);

    System.out.printf("%d번 게시물이 삭제되었습니다.\n", id);
  }
}
