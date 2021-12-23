package br.ufc.quixada.alu.leonardo.animeflix.Services;

import br.ufc.quixada.alu.leonardo.animeflix.Dto.CreateUserDTO;
import br.ufc.quixada.alu.leonardo.animeflix.Dto.UpdateUserDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.ufc.quixada.alu.leonardo.animeflix.Dto.UserDTO;
import br.ufc.quixada.alu.leonardo.animeflix.Models.User;
import br.ufc.quixada.alu.leonardo.animeflix.Repositories.UserRepository;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.UUID;

@SpringBootTest(classes = { UserService.class, UserRepository.class })
@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@DisplayName("User Service Unit Tests")
class UserServiceTests {

  @Autowired
  private UserService userService;

  @MockBean
  private UserRepository userRepository;

  @Test
  @DisplayName("It should list all created users")
  void ShouldListAllUsers() {
    var userList = new ArrayList<User>();
    when(userRepository.findAll()).thenReturn(userList);

    var index = userService.index();
    assertThat(index).hasOnlyElementsOfType(UserDTO.class);
    assertThat(index).isInstanceOf(ArrayList.class);
    assertThat(index).hasSizeGreaterThanOrEqualTo(0);
  }

  @Test
  @DisplayName("It should create a new user")
  void ShouldCreateNewUser() {
    var createUserDTO = new CreateUserDTO();
    createUserDTO.setName("user name");
    createUserDTO.setEmail("useremail@email.com");
    createUserDTO.setPassword("12345");

    var user = new User();
    user.setId(UUID.randomUUID());
    user.setName(createUserDTO.getName());
    user.setEmail(createUserDTO.getEmail());
    user.setPassword(createUserDTO.getPassword());

    when(userRepository.save(any(User.class))).thenReturn(user);

    var createdUser = userService.create(createUserDTO);

    assertThat(createdUser.getBody().getId().toString()).isEqualTo(user.getId().toString());
  }

  @Test
  @DisplayName("It should not create user with repeated email")
  void ShouldNotCreateUserWithRepeatedEmail() {
    var createUserDTO = new CreateUserDTO();
    createUserDTO.setName("user name");
    createUserDTO.setEmail("alreadyexistemail@gmail.com");
    createUserDTO.setPassword("12345");

    when(userRepository.existsByEmail(createUserDTO.getEmail())).thenReturn(true);

    var createdUser = userService.create(createUserDTO);

    assertThat(createdUser.getMessage()).isEqualTo("Email already exists");
  }

  @Test
  @DisplayName("It should update user with the provided UUID")
  void ShouldUpdateUserWithProvidedUUID() throws Exception{
    var uuid = UUID.randomUUID();
    var user = User.builder().id(uuid).name("user name").email("useremail@email.com").password("12345").build();
    when(userRepository.existsById(uuid)).thenReturn(true);
    when(userRepository.getById(uuid)).thenReturn(user);

    user.setName("User Name");
    when(userRepository.save(user)).thenReturn(user);

    var updateUserDTO = new UpdateUserDTO();
    updateUserDTO.setName(user.getName());
    updateUserDTO.setEmail(user.getEmail());
    updateUserDTO.setPassword(user.getPassword());
    var updatedUser = userService.update(user.getId(), updateUserDTO);

    assertThat(updatedUser.getBody().getId()).isEqualTo(user.getId());
  }

  @Test
  @DisplayName("It should not update when the provided UUID not exists")
  void ShouldNotUpdateWithInvalidUUID() {
    var uuid = UUID.randomUUID();

    when(userRepository.existsById(uuid)).thenReturn(false);

    var updateUserDTO = new UpdateUserDTO();
    updateUserDTO.setName("user name");
    updateUserDTO.setEmail("useremail@email.com");
    updateUserDTO.setPassword("123456");
    var updatedUser = userService.update(uuid, updateUserDTO);

    assertThat(updatedUser.getMessage()).isEqualTo("User not found");
  }

  @Test
  @DisplayName("It should delete user with provided UUID")
  void ShouldDeleteUserWithProvidedUUID() {
    var uuid = UUID.randomUUID();

    when(userRepository.existsById(uuid)).thenReturn(true);
    doNothing().when(userRepository).deleteById(uuid);

    var deletedUser = userService.delete(uuid);

    assertThat(deletedUser.getMessage()).isEqualTo("User deleted");
  }

  @Test
  @DisplayName("It should not delete user with invalid UUID")
  void ShouldNotDeleteUserWithInvalidUUID() {
    var uuid = UUID.randomUUID();

    when(userRepository.existsById(uuid)).thenReturn(false);

    var deletedUser = userService.delete(uuid);
    assertThat(deletedUser.getMessage()).isEqualTo("User not found");
  }
}
