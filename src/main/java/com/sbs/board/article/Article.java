package com.sbs.board.article;

import com.sbs.board.reply.Reply;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Data
public class Article {
  private final int id;
  private final LocalDateTime regDate;
  private LocalDateTime updateDate;
  private final int memberId;
  private String subject;
  private String content;
  private int hit;
  private List<Reply> replyList;

  private String extra__writerName; // 작성자 이름

  public Article(Map<String, Object> articleMap) {
    this.id = (int) articleMap.get("id");
    this.regDate = (LocalDateTime) articleMap.get("regDate");
    this.updateDate = (LocalDateTime) articleMap.get("updateDate");
    this.memberId = (int) articleMap.get("memberId");
    this.subject = (String) articleMap.get("subject");
    this.content = (String) articleMap.get("content");
    this.hit = (int) articleMap.get("hit");

    if(articleMap.get("extra__writerName") != null) {
      this.extra__writerName = (String) articleMap.get("extra__writerName");
    }
  }
}
