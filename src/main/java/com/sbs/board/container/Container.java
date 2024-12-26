package com.sbs.board.container;

import com.sbs.board.article.ArticleController;
import com.sbs.board.article.ArticleRepository;
import com.sbs.board.article.ArticleService;
import com.sbs.board.member.MemberController;

import java.util.Scanner;

public class Container {
  public static Scanner sc;

  public static ArticleRepository articleRepository;

  public static ArticleService articleService;

  public static MemberController memberController;
  public static ArticleController articleController;

  static {
    sc = new Scanner(System.in);

    articleRepository = new ArticleRepository();

    articleService = new ArticleService();

    memberController = new MemberController();
    articleController = new ArticleController();
  }
}
