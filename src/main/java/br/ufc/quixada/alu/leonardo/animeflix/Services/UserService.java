package br.ufc.quixada.alu.leonardo.animeflix.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufc.quixada.alu.leonardo.animeflix.Dto.UserDTO;
import br.ufc.quixada.alu.leonardo.animeflix.Models.User;
import br.ufc.quixada.alu.leonardo.animeflix.Repositories.UserRepository;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  public List<User> index() {
    List<User> users;
    users = userRepository.findAll();
    return users;
  }

  public User create(UserDTO userDTO) {
    var user = User.builder().name(userDTO.getName()).email(userDTO.getEmail())
        .password(userDTO.getPassword()).build();

    var createdUser = userRepository.save(user);

    return createdUser;
  }

  public User update(UUID id, UserDTO updateUserDTO) throws Exception{
    try {
      var user = userRepository.getById(id);

      user.setName(updateUserDTO.getName());
      user.setEmail(updateUserDTO.getEmail());
      user.setPassword(updateUserDTO.getPassword());

      var updatedUser = userRepository.save(user);

      return updatedUser;
    } catch (Exception ex) {
      throw new Exception("User not found!");
    }
  }
}
