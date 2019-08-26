package jp.co.mediaseek.training.dao;

import java.util.List;
import jp.co.mediaseek.training.entity.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;



@Repository
public class UserDaoImpl implements UserDao {
  private final JdbcTemplate jdbcTemplate;

  // 単一のコンストラクタの場合は自動で@Autowired扱いとなる
  public UserDaoImpl(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  /**
   * userデータべースのlogin_idカラムに対し、引数であるloginIDを含むレコードを持つかを調べるメソッド.
   * 
   * @param loginId ログインID
   * @return
   */
  public User findByLoginId(String loginId) {
    User ret;
    String sql = "select * from user where login_id = '" + loginId + "'";
    RowMapper<User> mapper = new BeanPropertyRowMapper<>(User.class);

    try {
      List<User> rs = jdbcTemplate.query(sql, mapper);
      ret = rs.get(0);
    } catch (IndexOutOfBoundsException e) {
      // comment:検索結果が0件だった場合の処理
      ret = null;
    }

    return ret;

  }

}
