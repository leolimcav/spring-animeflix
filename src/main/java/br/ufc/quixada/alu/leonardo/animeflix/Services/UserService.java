package br.ufc.quixada.alu.leonardo.animeflix.Services;

import br.ufc.quixada.alu.leonardo.animeflix.Interfaces.Services.IUserService;
import br.ufc.quixada.alu.leonardo.animeflix.Models.User;

public class UserService implements IUserService {

  @Override
  public User create(User user) {
    return new User(user.getName());
  }
}
