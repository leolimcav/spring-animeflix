package br.ufc.quixada.alu.leonardo.animeflix.Services;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.ufc.quixada.alu.leonardo.animeflix.Models.User;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = { UserService.class })
@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
class UserServiceTests {

  @Autowired
  private UserService userService;

  @Test
  void ShouldCreateNewUser() {
    var user = new User("Leo");

    var createdUser = userService.create(user);

    assertTrue(createdUser.getName().equals(user.getName()));
  }
}
