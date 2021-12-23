package br.ufc.quixada.alu.leonardo.animeflix.Dto;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
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
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private UUID id;
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String name;
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String email;
}
