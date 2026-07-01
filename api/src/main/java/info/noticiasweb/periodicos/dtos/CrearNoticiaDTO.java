package info.noticiasweb.periodicos.dtos;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;

public record CrearNoticiaDTO(
        @NotBlank(message = "No puede estar vacío") 
        String titulo,
        @NotBlank(message = "No puede estar vacío") 
        String url,
        @NotBlank(message = "No puede estar vacío") 
        String descripcion,
        @NotNull(message = "La fecha de publicación es obligatoria")
        @PastOrPresent(message = "La fecha no puede ser futura") 
        LocalDateTime fechaPublicacion,
        @Digits(integer = 6, fraction = 0)
        @Positive
        @NotNull(message = "Debe pertenecer a un periodico")
        Long periodicoId
) {
}
