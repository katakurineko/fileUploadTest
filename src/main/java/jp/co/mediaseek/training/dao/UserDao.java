package jp.co.mediaseek.training.dao;

import jp.co.mediaseek.training.entity.User;
import org.springframework.stereotype.Repository;


@Repository
public interface UserDao {
  public User findByLoginId(String loginId);
}