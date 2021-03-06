package br.ufc.quixada.alu.leonardo.animeflix.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserDTO {
    private String name;
    private String email;
    private String password;
}
