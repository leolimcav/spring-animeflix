package br.ufc.quixada.alu.leonardo.animeflix.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.ufc.quixada.alu.leonardo.animeflix.Dto.BaseResponseDTO;
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

  public BaseResponseDTO<UserDTO> create(CreateUserDTO createUserDTO) {
    var userAlreadyExists = userRepository.existsByEmail(createUserDTO.getEmail());
    var baseResponse = new BaseResponseDTO<UserDTO>();
    if(userAlreadyExists) {
      baseResponse.setMessage("Email already exists");
      baseResponse.setBody(UserDTO.builder().build());
      baseResponse.setError(true);

      return baseResponse;
    }

    var user = User.builder().name(createUserDTO.getName()).email(createUserDTO.getEmail())
        .password(createUserDTO.getPassword()).build();

    var createdUser = userRepository.save(user);

    var createdUserDto = UserDTO.builder().id(createdUser.getId()).name(createdUser.getName()).email(createdUser.getEmail()).build();

    baseResponse.setMessage("User created");
    baseResponse.setBody(createdUserDto);

    return baseResponse;
  }

  public BaseResponseDTO<UserDTO> update(UUID id, UpdateUserDTO updateUserDTO) {
      var baseResponse = new BaseResponseDTO<UserDTO>();
      var userExists = userRepository.existsById(id);

      if(!userExists) {
        baseResponse.setError(true);
        baseResponse.setMessage("User not found");
        baseResponse.setBody(UserDTO.builder().build());

        return baseResponse;
      }
      var user = userRepository.getById(id);

      user.setName(updateUserDTO.getName());
      user.setEmail(updateUserDTO.getEmail());
      user.setPassword(updateUserDTO.getPassword());

      var updatedUser = userRepository.save(user);

      var updatedUserDto = UserDTO.builder().id(updatedUser.getId()).name(updatedUser.getName()).email(updatedUser.getEmail()).build();

      baseResponse.setMessage("User updated");
      baseResponse.setBody(updatedUserDto);

      return baseResponse;
  }

  public BaseResponseDTO<Boolean> delete(UUID id) {
      var baseResponse = new BaseResponseDTO<Boolean>();
      var userExists = userRepository.existsById(id);

      if(!userExists) {
          baseResponse.setBody(false);
          baseResponse.setError(true);
          baseResponse.setMessage("User not found");
          return baseResponse;
      }

      userRepository.deleteById(id);

      baseResponse.setMessage("User deleted");
      baseResponse.setBody(false);

      return baseResponse;
  }
}
