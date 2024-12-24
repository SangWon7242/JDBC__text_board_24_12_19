package com.sbs.board.article;

import com.sbs.board.dbUtil.MysqlUtil;
import com.sbs.board.dbUtil.SecSql;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ArticleService {
  private List<Article> articleList;

  public ArticleService() {
    articleList = new ArrayList<>();
  }

  public int write(String subject, String content) {
    SecSql sql = new SecSql();
    sql.append("INSERT INTO article");
    sql.append("SET regDate = NOW()");
    sql.append(", updateDate = NOW()");
    sql.append(", `subject` = ?", subject);
    sql.append(", content = ?", content);

    int id = MysqlUtil.insert(sql);

    return id;
  }

  public List<Article> getArticles() {
    SecSql sql = new SecSql();
    sql.append("SELECT *");
    sql.append("FROM article");
    sql.append("ORDER BY id DESC");

    List<Map<String, Object>> articleListMap = MysqlUtil.selectRows(sql);

    if(articleListMap.isEmpty()) {
      return null;
    }

    for(Map<String, Object> articleMap : articleListMap) {
      articleList.add(new Article(articleMap));
    }

    return articleList;
  }

  public Article findById(int id) {
    SecSql sql = new SecSql();
    sql.append("SELECT *");
    sql.append("FROM article");
    sql.append("WHERE id = ?", id);

    Map<String, Object> articleMap = MysqlUtil.selectRow(sql);

    if(articleMap.isEmpty()) {
      return null;
    }

    Article article = new Article(articleMap);

    return article;
  }

  public void update(int id, String subject, String content) {
    SecSql sql = new SecSql();
    sql.append("UPDATE article");
    sql.append("SET updateDate = NOW()");
    sql.append(", `subject` = ?", subject);
    sql.append(", content = ?", content);
    sql.append("WHERE id = ?", id);

    MysqlUtil.update(sql);
  }

  public void delete(int id) {
    SecSql sql = new SecSql();
    sql.append("DELETE");
    sql.append("FROM article");
    sql.append("WHERE id = ?", id);

    MysqlUtil.delete(sql);
  }
}
