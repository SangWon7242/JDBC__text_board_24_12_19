package com.sbs.board.reply;

import com.sbs.board.container.Container;

public class ReplyService {
  private ReplyRepository replyRepository;

  public ReplyService() {
    replyRepository = Container.replyRepository;
  }

  public void create(int memberId, int articleId, String content) {
    replyRepository.create(memberId, articleId, content);
  }
}
