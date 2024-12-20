package com.sbs.board.article;

public class Article {
  private final int id;
  private String subject;
  private String content;
  public Article(int id, String subject, String content) {
    this.id = id;
    this.subject = subject;
    this.content = content;
  }
  public int getId() {
    return id;
  }
  public String getSubject() {
    return subject;
  }
  public String getContent() {
    return content;
  }
  public void setSubject(String subject) {
    this.subject = subject;
  }
  public void setContent(String content) {
    this.content = content;
  }
  @Override
  public String toString() {
    return "Article{" +
        "id=" + id +
        ", subject='" + subject + '\'' +
        ", content='" + content + '\'' +
        '}';
  }
}
