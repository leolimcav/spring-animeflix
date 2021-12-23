package br.ufc.quixada.alu.leonardo.animeflix.Controllers;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import br.ufc.quixada.alu.leonardo.animeflix.Dto.BaseResponseDTO;
import br.ufc.quixada.alu.leonardo.animeflix.Dto.CreateUserDTO;
import br.ufc.quixada.alu.leonardo.animeflix.Dto.UpdateUserDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import br.ufc.quixada.alu.leonardo.animeflix.Dto.UserDTO;
import br.ufc.quixada.alu.leonardo.animeflix.Services.UserService;

@RestController
@RequestMapping("/users")
@Api("User Management")
public class UserControllers {

  @Autowired
  private UserService userService;

  @GetMapping("/")
  @ApiOperation("List all users")
  @ApiResponses(value = {
          @ApiResponse(message = "Returns the updated user", code = 200, response = List.class),
          @ApiResponse(message = "Not authorized to perform this action", code = 401, response = String.class),
          @ApiResponse(message = "Server with issues to respond", code = 500, response = String.class)})
  public ResponseEntity<List<UserDTO>> index() {
    return ResponseEntity.ok(userService.index());
  }

  @PostMapping("/")
  @ApiOperation("Create User")
  @ApiResponses(value = {
          @ApiResponse(message = "Returns the created user", code = 201, response = UserDTO.class),
          @ApiResponse(message = "Already exists an user with informed email", code = 400, response = String.class),
          @ApiResponse(message = "Not authorized to perform this action", code = 401, response = String.class),
          @ApiResponse(message = "Server with issues to respond", code = 500, response = String.class)})
  public ResponseEntity<BaseResponseDTO<UserDTO>> create(@RequestBody @Validated CreateUserDTO createUserDTO) {
    var createdUser = userService.create(createUserDTO);

    if(createdUser.isError()) {
      return ResponseEntity.badRequest().body(createdUser);
    }

    return ResponseEntity.created(URI.create("/users/"+((UserDTO)createdUser.getBody()).getId())).body(createdUser);
  }

  @PutMapping("/{uuid}")
  @ApiOperation("Update user info")
  @ApiResponses(value = {
          @ApiResponse(message = "Returns the updated user", code = 200, response = UserDTO.class),
          @ApiResponse(message = "The info sent by the client not correspond to the expected type", code = 400, response = String.class),
          @ApiResponse(message = "Not authorized to perform this action", code = 401, response = String.class),
          @ApiResponse(message = "User not found with this UUID", code = 404, response = String.class),
          @ApiResponse(message = "Server with issues to respond", code = 500, response = String.class)})
  public ResponseEntity<BaseResponseDTO<UserDTO>> update(@PathVariable("uuid") UUID id, @RequestBody @Validated UpdateUserDTO updateUserDTO) {
    var updatedUser = userService.update(id, updateUserDTO);
    if(updatedUser.isError()) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(updatedUser);
  }

  @DeleteMapping("/{uuid}")
  @ApiOperation("Delete User")
  @ApiResponses(value = {
          @ApiResponse(message = "Delete the user", code = 200, response = UserDTO.class),
          @ApiResponse(message = "Not authorized to perform this action", code = 401, response = String.class),
          @ApiResponse(message = "User not found with this UUID", code = 404, response = String.class),
          @ApiResponse(message = "Server with issues to respond", code = 500, response = String.class)})
  public ResponseEntity<BaseResponseDTO<UserDTO>> delete(@PathVariable("uuid") UUID id) {
    var response = userService.delete(id);
    if(response.isError()) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(response);
  }
}
