package br.ufc.quixada.alu.leonardo.animeflix.Controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufc.quixada.alu.leonardo.animeflix.Dto.UserDTO;
import br.ufc.quixada.alu.leonardo.animeflix.Models.User;
import br.ufc.quixada.alu.leonardo.animeflix.Services.UserService;

@RestController
@RequestMapping("/users")
public class UserControllers {

  @Autowired
  private UserService userService;

  @GetMapping("/")
  public ResponseEntity<ArrayList<User>> index() {
    return new ResponseEntity<ArrayList<User>>(userService.index(), HttpStatus.ACCEPTED);
  }

  @PostMapping("/")
  public ResponseEntity<User> create(@RequestBody UserDTO userDTO) {
    var createdUser = userService.create(userDTO);

    return new ResponseEntity<User>(createdUser, HttpStatus.CREATED);
  }
}
