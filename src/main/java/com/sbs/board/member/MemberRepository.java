package com.sbs.board.member;

import com.sbs.board.article.Article;
import com.sbs.board.dbUtil.MysqlUtil;
import com.sbs.board.dbUtil.SecSql;

import java.util.ArrayList;
import java.util.List;

public class MemberRepository {
  private List<Member> memberList;

  public MemberRepository() {
    memberList = new ArrayList<>();
  }

  public void join(String username, String password, String name) {
    SecSql sql = new SecSql();
    sql.append("INSERT INTO `member`");
    sql.append("SET regDate = NOW()");
    sql.append(", updateDate = NOW()");
    sql.append(", username = ?", username);
    sql.append(", `password` = ?", password);
    sql.append(", `name` = ?", name);

    MysqlUtil.insert(sql);
  }
}
