package br.ufc.quixada.alu.leonardo.animeflix.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufc.quixada.alu.leonardo.animeflix.Dto.UserDTO;
import br.ufc.quixada.alu.leonardo.animeflix.Models.User;
import br.ufc.quixada.alu.leonardo.animeflix.Repositories.UserRepository;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  public User create(UserDTO userDTO) {
    var user = User.builder().name(userDTO.getName()).email(userDTO.getEmail())
        .password(userDTO.getPassword()).build();

    var createdUser = userRepository.save(user);

    return createdUser;
  }

  public List<User> index() {
    List<User> users = new ArrayList<User>();
    users = userRepository.findAll();
    return users;
  }
}
