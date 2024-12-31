package com.sbs.board;

import com.sbs.board.container.Container;
import com.sbs.board.session.Session;
import com.sbs.board.util.Util;
import lombok.Getter;

import java.util.Map;

public class Rq {
  public String url;
  @Getter
  public Map<String, String> params;
  @Getter
  public String urlPath;

  public Session session;
  public String loginedMember = "loginedMember";

  public Rq(String url) {
    this.url = url;
    params = Util.getParamsFromUrl(this.url);
    urlPath = Util.getPathFromUrl(this.url);

    session = Container.session;
  }

  public int getIntParam(String paramName, int defaultValue) {
    if(!params.containsKey(paramName)) {
      return defaultValue;
    }

    try {
      return Integer.parseInt(params.get(paramName));
    } catch (NumberFormatException e) {
      return defaultValue;
    }
  }

  public String getParam(String paramName, String defaultValue) {
    if(!params.containsKey(paramName)) {
      return defaultValue;
    }

    return params.get(paramName);
  }
  
  // 로그인 되었는지 물어보는 메서드
  public boolean isLogined() {
    return hasSessionAttr(loginedMember);
  }

  public Object getSessionAttr(String attrName) {
    return session.getAttribute(attrName);
  }

  public void setSessionAttr(String attrName, Object value) {
    session.setAttribute(attrName, value);
  }

  public boolean hasSessionAttr(String attrName) {
    return session.hasAttribute(attrName);
  }

  public void removeSessionAttr(String attrName) {
    session.removeAttribute(attrName);
  }
}
