package jp.co.mediaseek.training.session;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

@SuppressWarnings("deprecation")
public class SpyHttpSession implements HttpSession {

  Map<String, Object> map = new HashMap<>();

  public Boolean invalidateCalled;

  @Override
  public long getCreationTime() {
    // TODO 自動生成されたメソッド・スタブ
    return 0;
  }

  @Override
  public String getId() {
    // TODO 自動生成されたメソッド・スタブ
    return null;
  }

  @Override
  public long getLastAccessedTime() {
    // TODO 自動生成されたメソッド・スタブ
    return 0;
  }

  @Override
  public ServletContext getServletContext() {
    // TODO 自動生成されたメソッド・スタブ
    return null;
  }

  @Override
  public void setMaxInactiveInterval(int interval) {
    // TODO 自動生成されたメソッド・スタブ

  }

  @Override
  public int getMaxInactiveInterval() {
    // TODO 自動生成されたメソッド・スタブ
    return 0;
  }

  @Override
  public HttpSessionContext getSessionContext() {
    // TODO 自動生成されたメソッド・スタブ
    return null;
  }

  @Override
  public Object getAttribute(String name) {
    return map.get(name);
  }

  @Override
  public Object getValue(String name) {
    // TODO 自動生成されたメソッド・スタブ
    return null;
  }

  @Override
  public Enumeration<String> getAttributeNames() {
    // TODO 自動生成されたメソッド・スタブ
    return null;
  }

  @Override
  public String[] getValueNames() {
    // TODO 自動生成されたメソッド・スタブ
    return null;
  }

  @Override
  public void setAttribute(String name, Object value) {
    map.put(name, value);
  }

  @Override
  public void putValue(String name, Object value) {
    // TODO 自動生成されたメソッド・スタブ

  }

  @Override
  public void removeAttribute(String name) {
    // TODO 自動生成されたメソッド・スタブ

  }

  @Override
  public void removeValue(String name) {
    // TODO 自動生成されたメソッド・スタブ

  }

  @Override
  public void invalidate() {
    // このメソッドが呼び出されたかどうかを記録
    invalidateCalled  = true;

    map.clear();
  }

  @Override
  public boolean isNew() {
    // TODO 自動生成されたメソッド・スタブ
    return false;
  }

}
