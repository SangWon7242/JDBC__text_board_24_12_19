package com.sbs.board.reply;

import com.sbs.board.article.Article;
import com.sbs.board.dbUtil.MysqlUtil;
import com.sbs.board.dbUtil.SecSql;

import java.util.ArrayList;
import java.util.List;

public class ReplyRepository {
  private List<Reply> replyList;

  public ReplyRepository() {
    replyList = new ArrayList<>();
  }

  public void create(int memberId, int articleId, String content) {
    SecSql sql = new SecSql();
    sql.append("INSERT INTO reply");
    sql.append("SET regDate = NOW()");
    sql.append(", updateDate = NOW()");
    sql.append(", memberId = ?", memberId);
    sql.append(", articleId = ?", articleId);
    sql.append(", content = ?", content);

    MysqlUtil.insert(sql);
  }
}
