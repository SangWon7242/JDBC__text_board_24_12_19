package com.sbs.board.container;

import com.sbs.board.article.ArticleController;
import com.sbs.board.article.ArticleRepository;
import com.sbs.board.article.ArticleService;
import com.sbs.board.member.MemberController;
import com.sbs.board.member.MemberRepository;
import com.sbs.board.member.MemberService;

import java.util.Scanner;

public class Container {
  public static Scanner sc;

  public static MemberRepository memberRepository;
  public static ArticleRepository articleRepository;

  public static MemberService memberService;
  public static ArticleService articleService;

  public static MemberController memberController;
  public static ArticleController articleController;

  static {
    sc = new Scanner(System.in);

    memberRepository = new MemberRepository();
    articleRepository = new ArticleRepository();

    memberService = new MemberService();
    articleService = new ArticleService();

    memberController = new MemberController();
    articleController = new ArticleController();
  }
}
