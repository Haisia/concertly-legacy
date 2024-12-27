package com.concertly.concertly_legacy.domain.user.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UserTest {

  @Test
  public void 회원은_패스워드_및_개인정보를_변경할_수_있어야_한다() {
    //given
    User user = User.of("test@test.com", "password");

    //when
    user.updatePassword("newPassword");

    //then
    Assertions.assertEquals("newPassword", user.getPassword());
  }

  @Test
  public void 회원은_포인트를_충전할_수_있어야_한다() {
    //given
    User user = User.of("test@test.com", "password");

    //when
    user.chargePoints(100L);

    //then
    Assertions.assertEquals(100L, user.currentPoints());
  }

}