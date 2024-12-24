package com.sbs.board.article;

import com.sbs.board.container.Container;

import java.util.List;

public class ArticleService {
  private final ArticleRepository articleRepository;

  public ArticleService() {
    articleRepository = Container.articleRepository;
  }

  public int write(String subject, String content) {
    return articleRepository.write(subject, content);
  }

  public List<Article> getArticles() {
    return articleRepository.getArticles();
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
}
