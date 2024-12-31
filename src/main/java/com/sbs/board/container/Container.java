package com.sbs.board.container;

import com.sbs.board.article.ArticleController;
import com.sbs.board.article.ArticleRepository;
import com.sbs.board.article.ArticleService;
import com.sbs.board.member.MemberController;
import com.sbs.board.member.MemberRepository;
import com.sbs.board.member.MemberService;
import com.sbs.board.reply.Reply;
import com.sbs.board.reply.ReplyController;
import com.sbs.board.reply.ReplyRepository;
import com.sbs.board.reply.ReplyService;
import com.sbs.board.session.Session;

import java.util.Scanner;

public class Container {
  public static Scanner sc;
  public  static Session session;

  public static MemberRepository memberRepository;
  public static ReplyRepository replyRepository;
  public static ArticleRepository articleRepository;

  public static MemberService memberService;
  public static ReplyService replyService;
  public static ArticleService articleService;

  public static MemberController memberController;
  public static ReplyController replyController;
  public static ArticleController articleController;

  static {
    sc = new Scanner(System.in);
    session = new Session();

    memberRepository = new MemberRepository();
    replyRepository = new ReplyRepository();
    articleRepository = new ArticleRepository();

    memberService = new MemberService();
    replyService = new ReplyService();
    articleService = new ArticleService();

    memberController = new MemberController();
    replyController = new ReplyController();
    articleController = new ArticleController();
  }
}
