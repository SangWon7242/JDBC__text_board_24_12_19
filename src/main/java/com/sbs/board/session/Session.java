package com.sbs.board.session;

import java.util.LinkedHashMap;
import java.util.Map;

public class Session {
  private Map<String, Object> store;

  public Session() {
    store = new LinkedHashMap<>();
  }

  // CREATE, READ, HAS(존재여부), REMOVE
  public Object getAttribute(String key) {
    return store.get(key);
  }

  public void setAttribute(String key, Object value) {
    store.put(key, value);
  }

  public boolean hasAttribute(String key) {
    return store.containsKey(key);
  }

  public void removeAttribute(String key) {
    store.remove(key);
  }
}
