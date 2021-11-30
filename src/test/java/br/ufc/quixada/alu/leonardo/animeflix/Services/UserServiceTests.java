package br.ufc.quixada.alu.leonardo.animeflix.Services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.ufc.quixada.alu.leonardo.animeflix.Dto.UserDTO;
import br.ufc.quixada.alu.leonardo.animeflix.Models.User;
import br.ufc.quixada.alu.leonardo.animeflix.Repositories.UserRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.UUID;

@SpringBootTest(classes = { UserService.class, UserRepository.class })
@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
class UserServiceTests {

  @Autowired
  private UserService userService;

  @MockBean
  private UserRepository userRepository;

  @Test
  void ShouldCreateNewUser() {
    var userDTO = new UserDTO();
    userDTO.setName("Leo");
    userDTO.setEmail("leonardo123k@gmail.com");
    userDTO.setPassword("12345");

    var user = new User();
    user.setId(UUID.randomUUID());
    user.setName(userDTO.getName());
    user.setEmail(userDTO.getEmail());
    user.setPassword(userDTO.getPassword());

    when(userRepository.save(any(User.class))).thenReturn(user);

    var createdUser = userService.create(userDTO);

    assertThat(createdUser.getId().toString()).isEqualTo(user.getId().toString());
  }

  @Test
  void ShouldListAllUsers() {
    var userList = new ArrayList<User>();
    when(userRepository.findAll()).thenReturn(userList);

    var index = userService.index();
    assertThat(index).hasOnlyElementsOfType(User.class);
    assertThat(index).isInstanceOf(ArrayList.class);
    assertThat(index).hasSizeGreaterThanOrEqualTo(0);
  }
}
