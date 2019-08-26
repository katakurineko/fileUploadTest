package jp.co.mediaseek.training.dao;

import java.util.List;
import jp.co.mediaseek.training.entity.Meishi;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class MeishiDaoImpl implements MeishiDao {
  private final JdbcTemplate jdbcTemplate;

  private final int hatena = 7;// 検索対象の数

  public MeishiDaoImpl(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public List<Meishi> findAll(int pageNumber, int count) {
    List<Meishi> ret;
    int startRow = (pageNumber - 1) * count + 1;

    String spl = "select * from meishi_info limit ?,?";
    RowMapper<Meishi> mapper = new BeanPropertyRowMapper<>(Meishi.class);

    try {
      List<Meishi> rs = jdbcTemplate.query(spl, mapper, startRow - 1, count);
      ret = rs;
    } catch (IndexOutOfBoundsException e) {
      ret = null;
    }
    return ret;
  }

  @Override
  public int getTotalRowNumber() {
    int rowCount =
        this.jdbcTemplate.queryForObject("select count(*) from meishi_info", Integer.class);

    return rowCount;
  }

  @Override
  public List<Meishi> search(int pageNumber, String searchText, int count) {
    List<Meishi> ret;

    String sql = "SELECT b.meishi_id,b.name,b.company,"
        + "b.picture_path,GROUP_CONCAT(u.user_name SEPARATOR ',')  "
        + "FROM (user AS u INNER JOIN contact AS c ON u.user_id = c.user_id)  "
        + "RIGHT JOIN meishi_info AS b ON c.meishi_id = b.meishi_id WHERE "
        + " u.user_name LIKE ? OR b.name LIKE ? " + " OR b.yomigana LIKE ? OR b.company LIKE ? "
        + " OR b.position LIKE ? OR b.department LIKE ? "
        + " OR b.address LIKE ? GROUP BY b.meishi_id limit ?,?";
    int startRow = (pageNumber - 1) * 10 + 1;

    Object[] argumentsToBind = new Object[hatena + 2];
    for (int i = 0; i < hatena; i++) {
      argumentsToBind[i] = "%" + searchText + "%";
    }
    argumentsToBind[argumentsToBind.length - 2] = startRow - 1;
    argumentsToBind[argumentsToBind.length - 1] = count;

    RowMapper<Meishi> mapper = new BeanPropertyRowMapper<>(Meishi.class);
    try {
      List<Meishi> rs = jdbcTemplate.query(sql, mapper, argumentsToBind);
      ret = rs;
    } catch (IndexOutOfBoundsException e) {
      ret = null;
    }
    return ret;
  }

  @Override
  public int getTotalRowNumberForSearch(String searchText) {
    String sql = "SELECT count(distinct b.meishi_id) "
        + "FROM (user AS u INNER JOIN contact AS c ON u.user_id = c.user_id)  "
        + "RIGHT JOIN meishi_info AS b ON c.meishi_id = b.meishi_id WHERE "
        + " u.user_name LIKE ? OR b.name LIKE ? " + " OR b.yomigana LIKE ? OR b.company LIKE ? "
        + " OR b.position LIKE ? OR b.department LIKE ? " + " OR b.address LIKE ?";

    Object[] argumentsToBind = new Object[hatena];
    for (int i = 0; i < hatena; i++) {
      argumentsToBind[i] = "%" + searchText + "%";
    }

    int rowCount = this.jdbcTemplate.queryForObject(sql, Integer.class, argumentsToBind);

    return rowCount;
  }

}
