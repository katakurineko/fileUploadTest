package jp.co.mediaseek.training.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import jp.co.mediaseek.training.dao.MeishiDao;
import jp.co.mediaseek.training.entity.Meishi;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Controller
public class MeishiListController {
  private final MeishiDao meishiDao;

  public MeishiListController(MeishiDao meishiDao) {
    this.meishiDao = meishiDao;
  }

  /**
   * 名刺の一覧表示とページネーション.
   * 
   * @param pageNumber ページ番号
   * @param model      データ受け渡し用
   * @return
   */
  @GetMapping("/meishiList")
  public String list(@RequestParam(name = "searchText", required = false) String searchText,
      @RequestParam(name = "pageNumber", required = false, defaultValue = "1") int pageNumber,
      MethodArgumentTypeMismatchException e, Model model) {
    model.addAttribute("pageNumber", pageNumber);

    int totalRowNumber = 0;
    int lastPageNumber = 0;
    if (searchText == null) {
      // 検索を行わない場合の分岐

      totalRowNumber = meishiDao.getTotalRowNumber();
      lastPageNumber = (totalRowNumber - 1) / 10 + 1;

      if (pageNumber >= 1 && pageNumber <= lastPageNumber) {
        // ページナンバーが1以上最後のページ番号以下の場合
        List<Meishi> meishiList = meishiDao.findAll(pageNumber, 10);
        model.addAttribute("meishiList", meishiList);
      } else {
        // ページナンバーが1以上最後のページ番号以下でない場合
        throw e;
      }
    } else {
      // 検索を行う場合の分岐
      totalRowNumber = meishiDao.getTotalRowNumberForSearch(searchText);
      lastPageNumber = (totalRowNumber - 1) / 10 + 1;

      if (pageNumber >= 1 && pageNumber <= lastPageNumber) {
        // ページナンバーが1以上最後のページ番号以下の場合
        List<Meishi> meishiList = meishiDao.search(pageNumber, searchText, 10);
        model.addAttribute("meishiList", meishiList);
        totalRowNumber = meishiDao.getTotalRowNumberForSearch(searchText);
      } else {
        // ページナンバーが1以上最後のページ番号以下でない場合
        throw e;
      }
    }


    model.addAttribute("lastPageNumber", lastPageNumber);

    int previousPageNumber = pageNumber - 1;
    model.addAttribute("previousPageNumber", previousPageNumber);
    int nextPageNumber = pageNumber + 1;
    model.addAttribute("nextPageNumber", nextPageNumber);

    model.addAttribute("searchText", searchText);

    return "meishiList";
  }

  /**
   * 検索ボタンが押された時の処理.
   * 
   * @param searchText 検索する文字列
   * @param page ページ番号
   * @param model データ受け渡し用
   * @return
   */
  @PostMapping("/meishiList")
  public String searchlist(@RequestParam(name = "searchText", required = true) String searchText,
      @RequestParam(name = "page", required = false, defaultValue = "1") int page, Model model) {

    // リダイレクトでパラメータを送る際にはエンコードしないと文字化けしてしまう
    String encodedString = null;
    try {
      encodedString = URLEncoder.encode(searchText, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }

    return "redirect:meishiList?searchText=" + encodedString;
  }
}
