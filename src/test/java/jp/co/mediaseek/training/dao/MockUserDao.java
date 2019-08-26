package jp.co.mediaseek.training.dao;

import jp.co.mediaseek.training.entity.User;

public class MockUserDao implements UserDao {

  @Override
  public User findByLoginId(String loginId) {
    User user = new User();
    user.setLoginId("kanri");
    user.setPasswordHash("$2a$10$WMzS6gWJfVeJlkUqvgxEye4XlWh0iqna8IyMI/QBSnvIcoBc6nvF.");
    user.setPasswordSalt("salt");
    user.setRoleId(1);
    user.setUserId(1);
    user.setUserName("テスト");
    if (user.getLoginId() == loginId) {
      return user;
    } else {
      return null;
    }

  }

}
