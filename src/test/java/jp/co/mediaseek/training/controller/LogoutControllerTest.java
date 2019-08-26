package jp.co.mediaseek.training.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import jp.co.mediaseek.training.session.SpyHttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class LogoutControllerTest {

  // テスト対象
  LogoutController sut;

  // テストダブル
  SpyHttpSession spyHttpSession;

  @BeforeEach
  public void initialize() {
    spyHttpSession = new SpyHttpSession();
    sut = new LogoutController(spyHttpSession);
  }

  @Test
  @DisplayName("戻り値がlogoutテンプレートを指しているか")
  public void getTest() {
    String actual = sut.get();

    assertTrue("セッションが削除されていない", spyHttpSession.invalidateCalled);
    
    assertEquals("logout", actual);

  }

}
