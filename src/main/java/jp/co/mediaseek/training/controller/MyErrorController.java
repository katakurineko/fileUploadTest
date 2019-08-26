package jp.co.mediaseek.training.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyErrorController implements ErrorController {

  /**
   * エラーが発生した際にエラーページを表示するメソッド.
   * 
   * @param request エラーコード取得用
   * @param model   エラーページへのパラメータ送信用
   */
  @RequestMapping("/error")
  public String handleError(HttpServletRequest request, Model model) {
    Object errorCode = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

    if (errorCode != null) {
      String errorMessage = null;

      //後でおこなう分岐処理のために、Object型のerrorCodeを、Enum型に変換
      Integer statusCode = Integer.valueOf(errorCode.toString());
      HttpStatus httpStatus = HttpStatus.resolve(statusCode);

      switch (httpStatus) {
        case NOT_FOUND :
          errorMessage = "お探しのページが見つかりませんでした";
          break;
        case BAD_REQUEST :
          errorMessage = "要求の形式が正しくありません";
          break;
        default:
          errorMessage = "予期せぬエラーが発生しました";
          break;
      }

      model.addAttribute("errorCode", errorCode + "エラー");
      model.addAttribute("errorMessage", errorMessage);
    }
    return "error";
  }

  @Override
  public String getErrorPath() {
    return "/error";
  }
}
