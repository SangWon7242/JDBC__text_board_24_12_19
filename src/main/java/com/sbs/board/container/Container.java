package com.sbs.board.container;

import com.sbs.board.article.ArticleController;
import com.sbs.board.article.ArticleService;

import java.util.Scanner;

public class Container {
  public static Scanner sc;

  public static ArticleService articleService;

  public static ArticleController articleController;

  static {
    sc = new Scanner(System.in);

    articleService = new ArticleService();

    articleController = new ArticleController();
  }
}
