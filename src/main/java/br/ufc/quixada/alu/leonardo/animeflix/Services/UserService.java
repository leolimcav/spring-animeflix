package br.ufc.quixada.alu.leonardo.animeflix.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.ufc.quixada.alu.leonardo.animeflix.Dto.CreateUserDTO;
import br.ufc.quixada.alu.leonardo.animeflix.Dto.UpdateUserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufc.quixada.alu.leonardo.animeflix.Dto.UserDTO;
import br.ufc.quixada.alu.leonardo.animeflix.Models.User;
import br.ufc.quixada.alu.leonardo.animeflix.Repositories.UserRepository;

@Service
public class UserService {
  private static final Logger logger = LoggerFactory.getLogger(UserService.class);

  @Autowired
  private UserRepository userRepository;

  public List<UserDTO> index() {
    var users = userRepository.findAll();

    List<UserDTO> userDtoList = new ArrayList<>();
    for (var user : users) {
      userDtoList.add(UserDTO.builder().id(user.getId()).name(user.getName()).email(user.getEmail()).build());
    }
    return userDtoList;
  }

  public UserDTO create(CreateUserDTO createUserDTO) {
    var user = User.builder().name(createUserDTO.getName()).email(createUserDTO.getEmail())
        .password(createUserDTO.getPassword()).build();

    var createdUser = userRepository.save(user);

    var createdUserDto = UserDTO.builder().id(createdUser.getId()).name(createdUser.getName()).email(createdUser.getEmail()).build();

    return createdUserDto;
  }

  public UserDTO update(UUID id, UpdateUserDTO updateUserDTO) throws Exception{
    try {
      var user = userRepository.getById(id);

      user.setName(updateUserDTO.getName());
      user.setEmail(updateUserDTO.getEmail());
      user.setPassword(updateUserDTO.getPassword());

      var updatedUser = userRepository.save(user);

      var updatedUserDto = UserDTO.builder().id(updatedUser.getId()).name(updatedUser.getName()).email(updatedUser.getEmail()).build();

      return updatedUserDto;
    } catch (Exception ex) {
      logger.error(ex.getMessage());
      throw new Exception("User not found!");
    }
  }
}
