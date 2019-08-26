package jp.co.mediaseek.training.controller;

import javax.servlet.http.HttpSession;

import jp.co.mediaseek.training.dao.UserDao;
import jp.co.mediaseek.training.entity.User;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
  private final UserDao userDao;
  private final HttpSession session;
  private final PasswordEncoder passwordEncoder;

  /**
   * コンストラクタインジェクションでDIを行うコンストラクタ.
   * 
   * @param userDao データベースとのやり取りを行うクラス
   * @param session セッションの操作を行うための、HttpSessionインターフェースを実装したクラス
   * @param passwordEncoder     パスワードのハッシュ化を行うための、PasswordEncoderインターフェースを実装したクラス
   */
  // 単一のコンストラクタの場合は自動で@Autowired扱いとなる.
  public LoginController(UserDao userDao, HttpSession session,
      PasswordEncoder passwordEncoder) {
    this.userDao = userDao;
    this.session = session;
    this.passwordEncoder = passwordEncoder;
  }

  @GetMapping("/login")
  public String get() {
    return "login";
  }

  /**
   * ログインで入力された値を受け取って判定しセッションに保持する.
   * 
   * @param loginId  ログインID
   * @param password パスワード
   * @param model    エラーメッセージ格納用
   * @return
   */
  @PostMapping("/login")
  public String post(@RequestParam(name = "loginId", required = false) String loginId,
      @RequestParam(name = "password", required = false) String password, Model model) {

    if (loginId.isEmpty()) {
      model.addAttribute("err", "ログインIDを入力してください");
      return "login";
    }
    if (password.isEmpty()) {
      model.addAttribute("err", "パスワードを入力してください");
      return "login";
    }

    User user = userDao.findByLoginId(loginId);
    if (user == null) {
      /*
       * comment:ログインIDが存在していなかった時の処理
       */
      model.addAttribute("err", "ログインIDかパスワードが間違っています");
      return "login";
    }

    String rawPassword = password + user.getPasswordSalt();
    String encodedPassword = user.getPasswordHash();

    if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
      // comment:パスワードが正しくなかった時の処理
      model.addAttribute("err", "ログインIDかパスワードが間違っています");
      return "login";
    }

    session.setAttribute("userId", user.getUserId());
    session.setAttribute("userName", user.getUserName());
    session.setAttribute("roleId", user.getRoleId());

    return "redirect:meishiList";
  }

}
