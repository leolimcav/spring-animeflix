package br.ufc.quixada.alu.leonardo.animeflix.Dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponseDTO<T> {
    private String message;
    private T body;
    private boolean error = false;
}
