package com.sbs.board.article;

import com.sbs.board.container.Container;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArticleService {
  private final ArticleRepository articleRepository;

  public ArticleService() {
    articleRepository = Container.articleRepository;
  }

  public int write(int memberId, String subject, String content) {
    return articleRepository.write(memberId, subject, content);
  }

  public List<Article> getArticles(int page, int pageItemCount, String searchKeyword) {
    int limitFrom = (page - 1) * pageItemCount;
    int limitTake = pageItemCount;

    Map<String, Object> args = new HashMap<>();
    args.put("searchKeyword", searchKeyword);
    args.put("limitFrom", limitFrom);
    args.put("limitTake", limitTake);

    return articleRepository.getArticles(args);
  }

  public Article findById(int id) {
    return articleRepository.findById(id);
  }

  public void update(int id, String subject, String content) {
    articleRepository.update(id, subject, content);
  }

  public void delete(int id) {
    articleRepository.delete(id);
  }

  public void increaseHit(int id) {
    articleRepository.increaseHit(id);
  }

  public Article findByIdWithReplies(int id) {
    return articleRepository.findByIdWithReplies(id);
  }
}
