package jp.co.mediaseek.training.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LogoutController {
  private final HttpSession session;

  // 単一のコンストラクタの場合は自動で@Autowired扱いとなる
  public LogoutController(HttpSession session) {
    this.session = session;
  }

  /**
   * This method deletes all session data and open "logout.html".
   * 
   * @return
   */
  @GetMapping("/logout")
  public String get() {
    session.invalidate();
    return "logout";
  }

}
