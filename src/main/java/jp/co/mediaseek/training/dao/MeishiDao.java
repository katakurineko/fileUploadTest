package jp.co.mediaseek.training.dao;

import java.util.List;
import jp.co.mediaseek.training.entity.Meishi;
import org.springframework.stereotype.Repository;


@Repository
public interface MeishiDao {

  /**
   * データベースから一覧に表示する情報を取得する.
   * 
   * @param startRow ページの最初の行
   * @return
   */
  public List<Meishi> findAll(int startRow, int count);

  /**
   * 全データの数を取得する.
   * 
   * @return
   */
  public int getTotalRowNumber();

  /**
   * 入力された文字列を含むデータを検索する.
   * 
   * @param pageNumber ページ番号
   * @param searchText 検索する文字列
   * @return
   */
  public List<Meishi> search(int pageNumber, String searchText, int count);

  /**
   * 検索した時のデータの数を取得する.
   * 
   * @param searchText 検索する文字列
   * @return
   */
  public int getTotalRowNumberForSearch(String searchText);

}
