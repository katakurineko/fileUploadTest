package jp.co.mediaseek.training.controller;

import static org.junit.Assert.assertEquals;

import jp.co.mediaseek.training.dao.MockUserDao;
import jp.co.mediaseek.training.dao.UserDao;
import jp.co.mediaseek.training.session.SpyHttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;



@ExtendWith(SpringExtension.class)
@SpringBootTest
public class LoginControllerTest {

  LoginController sut;
  SpyHttpSession spyHttpSession;
  UserDao userDao;
  BCryptPasswordEncoder bpe;

  /**
   * 初期設定.
   */
  @BeforeEach
  public void initialize() {
    spyHttpSession = new SpyHttpSession();
    userDao = new MockUserDao();
    bpe = new BCryptPasswordEncoder();
    sut = new LoginController(userDao, spyHttpSession, bpe);
  }

  @Test
  @DisplayName("getメソッドの戻り値がloginテンプレートを指しているか")
  public void getTest() {
    String actual = sut.get();
    assertEquals("login", actual);
  }

  @Test
  @DisplayName("ログインIDが入力されていなかった場合の戻り値がloginテンプレートを指しているか")
  public void emptyLoginIdTest() {

    Model model = new ConcurrentModel();

    String actual = sut.post("", "pass", model);
    assertEquals("login", actual);
  }

  @Test
  @DisplayName("ログインIDが入力されていなかった場合errに正しいメッセージが入っているか")
  public void emptyLoginIdErrorTest() {

    Model model = new ConcurrentModel();

    sut.post("", "pass", model);

    assertEquals("ログインIDを入力してください", model.asMap().get("err"));
  }

  @Test
  @DisplayName("パスワードが入力されていなかった場合の戻り値がloginテンプレートを指しているか")
  public void emptyPasswordTest() {

    Model model = new ConcurrentModel();

    String actual = sut.post("kanri", "", model);
    assertEquals("login", actual);
  }

  @Test
  @DisplayName("パスワードが入力されていなかった場合errに正しいメッセージが入っているか")
  public void emptyPasswordErrorTest() {

    Model model = new ConcurrentModel();

    sut.post("kanri", "", model);

    assertEquals("パスワードを入力してください", model.asMap().get("err"));
  }

  @Test
  @DisplayName("存在しないログインIDを入力した場合の戻り値がloginテンプレートを指しているか")
  public void incorrectLoginIdTest() {

    Model model = new ConcurrentModel();

    String actual = sut.post("xxx", "pass", model);
    assertEquals("login", actual);
  }

  @Test
  @DisplayName("存在しないログインIDを入力した場合errに正しいメッセージが入っているか")
  public void incorrectLoginIdErrorTest() {

    Model model = new ConcurrentModel();

    sut.post("xxx", "pass", model);

    assertEquals("ログインIDかパスワードが間違っています", model.asMap().get("err"));
  }

  @Test
  @DisplayName("間違ったパスワードを入力した場合の戻り値がloginテンプレートを指しているか")
  public void incorrectPasswordTest() {

    Model model = new ConcurrentModel();

    String actual = sut.post("kanri", "xxx", model);
    assertEquals("login", actual);
  }

  @Test
  @DisplayName("間違ったパスワードを入力した場合errに正しいメッセージが入っているか")
  public void incorrectPasswordErrorTest() {

    Model model = new ConcurrentModel();

    sut.post("kanri", "xxx", model);

    assertEquals("ログインIDかパスワードが間違っています", model.asMap().get("err"));
  }

  @Test
  @DisplayName("正しいIDとパスワードを入力した場合、セッション情報を保持し、戻り値がmeishiListテンプレートを指しているか")
  public void signInTest() {

    Model model = new ConcurrentModel();

    String actual = sut.post("kanri", "pass", model);

    assertEquals("meishiList", actual);

    assertEquals(1, spyHttpSession.getAttribute("roleId"));
    assertEquals(1, spyHttpSession.getAttribute("userId"));
    assertEquals("テスト", spyHttpSession.getAttribute("userName"));

  }
}
