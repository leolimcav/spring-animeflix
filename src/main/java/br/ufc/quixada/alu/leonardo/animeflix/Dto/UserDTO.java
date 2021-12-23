package br.ufc.quixada.alu.leonardo.animeflix.Dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
  private UUID id;
  private String name;
  private String email;
}
