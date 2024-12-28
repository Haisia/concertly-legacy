//package com.concertly.concertly_legacy.domain.user.repository;
//
//import com.concertly.concertly_legacy.domain.user.entity.User;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.context.ActiveProfiles;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@ActiveProfiles("test")
//class UserRepositoryTest {
//
//  @Autowired
//  private UserRepository userRepository;
//
//  @Test
//  public void 회원가입_테스트() {
//    //given
//    User user = User.of("test@test.com", "password");
//
//    //when
//    user.chargePoints(100L);
//
//    //then
//    User savedUser = userRepository.save(user);
//
//    assertEquals("test@test.com", savedUser.getEmail());
//    assertEquals(100L, savedUser.currentPoints());
//    assertNotNull(savedUser.getId());
//  }
//}