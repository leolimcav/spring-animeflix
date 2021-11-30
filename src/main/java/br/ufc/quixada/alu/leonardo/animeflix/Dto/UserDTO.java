package br.ufc.quixada.alu.leonardo.animeflix.Dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
  private UUID id;
  private String name;
  private String email;
  private String password;
}
