package com.sbs.board.article;

import com.sbs.board.dbUtil.MysqlUtil;
import com.sbs.board.dbUtil.SecSql;
import com.sbs.board.reply.Reply;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ArticleRepository {
  private List<Article> articleList;

  public ArticleRepository() {
    articleList = new ArrayList<>();
  }

  public int write(int memberId, String subject, String content) {
    SecSql sql = new SecSql();
    sql.append("INSERT INTO article");
    sql.append("SET regDate = NOW()");
    sql.append(", updateDate = NOW()");
    sql.append(", memberId = ?", memberId);
    sql.append(", `subject` = ?", subject);
    sql.append(", content = ?", content);

    int id = MysqlUtil.insert(sql);

    return id;
  }

  public List<Article> getArticles(Map<String, Object> args) {
    SecSql sql = new SecSql();

    String searchKeyword = "";

    if(args.containsKey("searchKeyword")) {
      searchKeyword = (String) args.get("searchKeyword");
    }

    int limitFrom = -1;
    int limitTake = -1;

    if(args.containsKey("limitFrom")) {
      limitFrom = (int) args.get("limitFrom");
    }

    if(args.containsKey("limitTake")) {
      limitTake = (int) args.get("limitTake");
    }

    sql.append("SELECT A.*");
    sql.append(", M.name AS `extra__writerName`");
    sql.append("FROM article AS A");
    sql.append("INNER JOIN `member` AS M");
    sql.append("ON A.memberId = M.id");
    if(!searchKeyword.isEmpty()) {
      sql.append("WHERE A.`subject` LIKE CONCAT('%', ?, '%')", searchKeyword);
    }

    sql.append("ORDER BY id DESC");

    if(limitFrom != -1) {
      sql.append("LIMIT ?, ?", limitFrom, limitTake);
    }

    System.out.println("limitFrom : " + limitFrom);
    System.out.println("limitTake : " + limitTake);

    List<Map<String, Object>> articleListMap = MysqlUtil.selectRows(sql);

    if(articleListMap.isEmpty()) {
      return null;
    }

    List<Article> articleList = new ArrayList<>();

    for(Map<String, Object> articleMap : articleListMap) {
      articleList.add(new Article(articleMap));
    }

    return articleList;
  }

  public Article findById(int id) {
    SecSql sql = new SecSql();
    sql.append("SELECT A.*");
    sql.append(", M.name AS `extra__writerName`");
    sql.append("FROM article AS A");
    sql.append("INNER JOIN `member` AS M");
    sql.append("ON A.memberId = M.id");
    sql.append("WHERE A.id = ?", id);

    Map<String, Object> articleMap = MysqlUtil.selectRow(sql);

    if(articleMap.isEmpty()) {
      return null;
    }

    Article article = new Article(articleMap);

    return article;
  }

  public Article findByIdWithReplies(int id) {
    // 게시물 작성자명 가져오기
    SecSql sql = new SecSql();
    sql.append("SELECT A.*");
    sql.append(", M.name AS `extra__writerName`");
    sql.append("FROM article AS A");
    sql.append("INNER JOIN `member` AS M");
    sql.append("ON A.memberId = M.id");
    sql.append("WHERE A.id = ?", id);

    Map<String, Object> articleMap = MysqlUtil.selectRow(sql);

    if(articleMap.isEmpty()) {
      return null;
    }

    Article article = new Article(articleMap);

    boolean isReplyListNotEmpty = repliesCount(id);

    List<Reply> replyList = new ArrayList<>();

    if(isReplyListNotEmpty) {
      sql = new SecSql();
      sql.append("SELECT R.*");
      sql.append(", M.name AS `extra__writerName`");
      sql.append("FROM reply AS R");
      sql.append("INNER JOIN `member` AS M");
      sql.append("ON R.memberId = M.id");
      sql.append("WHERE R.articleId = ?", id);
      sql.append("ORDER BY R.id DESC");

      List<Map<String, Object>> replyListMap = MysqlUtil.selectRows(sql);

      if(replyListMap.isEmpty()) {
        return null;
      }

      for(Map<String, Object> replyMap : replyListMap) {
        replyList.add(new Reply(replyMap));
      }
    }

    article.setReplyList(replyList);

    return article;
  }

  private boolean repliesCount(int id) {
    SecSql sql = new SecSql();
    sql.append("SELECT COUNT(*) > 0");
    sql.append("FROM reply AS R");
    sql.append("INNER JOIN `member` AS M");
    sql.append("ON R.memberId = M.id");
    sql.append("WHERE R.articleId = ?", id);

    return MysqlUtil.selectRowBooleanValue(sql);
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

  public void increaseHit(int id) {
    SecSql sql = new SecSql();
    sql.append("UPDATE article");
    sql.append("SET hit = hit + 1");
    sql.append("WHERE id = ?", id);

    MysqlUtil.update(sql);
  }
}
