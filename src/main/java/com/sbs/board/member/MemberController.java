package com.sbs.board.member;

import com.sbs.board.Rq;
import com.sbs.board.container.Container;
import com.sbs.board.dbUtil.MysqlUtil;
import com.sbs.board.dbUtil.SecSql;

import java.util.Scanner;

public class MemberController {
  public Scanner sc;

  public MemberController() {
    sc = Container.sc;
  }

  public void doJoin(Rq rq) {
    String username;
    String password;
    String passwordConfirm;
    String name;
    
    // 아이디 입력
    while (true) {
      System.out.print("로그인 아이디 : ");
      username = sc.nextLine();
      
      if(username.trim().isEmpty()) {
        System.out.println("로그인 아이디를 입력해주세요.");
        continue;
      }

      break;
    }

    // 비밀번호 입력
    while (true) {
      System.out.print("로그인 비밀번호 : ");
      password = sc.nextLine();

      if(password.trim().isEmpty()) {
        System.out.println("로그인 비밀번호를 입력해주세요.");
        continue;
      }

      while (true) {
        System.out.print("로그인 비밀번호 확인 : ");
        passwordConfirm = sc.nextLine();

        if(passwordConfirm.trim().isEmpty()) {
          System.out.println("로그인 비밀번호 확인을 입력해주세요.");
          continue;
        }

        if(!passwordConfirm.equals(password)) {
          System.out.println("비밀번호가 일치하지 않습니다.");
          continue;
        }

        break;
      }

      break;
    }

    // 이름 입력
    while (true) {
      System.out.print("이름 : ");
      name = sc.nextLine();

      if(name.trim().isEmpty()) {
        System.out.println("이름을 입력해주세요.");
        continue;
      }

      break;
    }

    SecSql sql = new SecSql();
    sql.append("INSERT INTO `member`");
    sql.append("SET regDate = NOW()");
    sql.append(", updateDate = NOW()");
    sql.append(", username = ?", username);
    sql.append(", `password` = ?", password);
    sql.append(", `name` = ?", name);

    MysqlUtil.insert(sql);

    System.out.printf("\"%s\"님 회원가입이 완료되었습니다.\n", username);
  }
}
