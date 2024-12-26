package com.sbs.board.member;

import com.sbs.board.container.Container;

public class MemberService {
  private final MemberRepository memberRepository;

  public MemberService() {
    memberRepository = Container.memberRepository;
  }

  public void join(String username, String password, String name) {
    memberRepository.join(username, password, name);
  }

  public Member findByUsername(String username) {
    return memberRepository.findByUsername(username);
  }
}
