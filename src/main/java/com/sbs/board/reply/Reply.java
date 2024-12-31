package com.sbs.board.reply;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@AllArgsConstructor
@Data
public class Reply {
  private final int id;
  private final LocalDateTime regDate;
  private LocalDateTime updateDate;
  private final int memberId;
  private final int articleId;
  private String content;

  public Reply(Map<String, Object> articleMap) {
    this.id = (int) articleMap.get("id");
    this.regDate = (LocalDateTime) articleMap.get("regDate");
    this.updateDate = (LocalDateTime) articleMap.get("updateDate");
    this.memberId = (int) articleMap.get("memberId");
    this.articleId = (int) articleMap.get("articleId");
    this.content = (String) articleMap.get("content");
  }
}
