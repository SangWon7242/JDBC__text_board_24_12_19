package com.sbs.board.reply;

import com.sbs.board.Rq;
import com.sbs.board.article.Article;
import com.sbs.board.article.ArticleService;
import com.sbs.board.container.Container;
import com.sbs.board.controller.Controller;
import com.sbs.board.member.Member;

public class ReplyController implements Controller {
  private ReplyService replyService;
  private ArticleService articleService;

  public ReplyController() {
    replyService = Container.replyService;
    articleService = Container.articleService;
  }

  public void performAction(Rq rq) {
    switch (rq.getUrlPath()) {
      case "/usr/reply/write" -> doWrite(rq);
      default -> System.out.println("해당 경로는 존재하지 않습니다.");
    }
  }

  private void doWrite(Rq rq) {
    if(!rq.isLogined()) {
      System.out.println("로그인 후 이용해주세요.");
      return;
    }

    int id = rq.getIntParam("id", 0);

    Member member = (Member) rq.getSessionAttr("loginedMember");
    Article article = articleService.findById(id);

    if(article == null) {
      System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
      return;
    }

    System.out.printf("== %d번 게시물 상세보기 ==\n", id);
    System.out.printf("번호 : %d\n", article.getId());
    System.out.printf("작성자 : %s\n", article.getExtra__writerName());
    System.out.printf("제목 : %s\n", article.getSubject());
    System.out.printf("내용 : %s\n", article.getContent());

    System.out.println("== 댓글 작성 ==");
    System.out.print("댓글 내용 : ");
    String content = Container.sc.nextLine();

    int memberId = member.getId();
    int articleId = article.getId();

    replyService.create(memberId, articleId, content);

    System.out.println("댓글이 작성되었습니다.");
  }
}
