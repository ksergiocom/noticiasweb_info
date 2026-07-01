package info.noticiasweb.periodicos.dtos;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;

public record ActualizarNoticiaDTO(
                String titulo,
                String url,
                String descripcion,
                @PastOrPresent(message = "La fecha no puede ser futura") LocalDateTime fechaPublicacion,
                @Digits(integer = 6, fraction = 0) @Positive Long periodicoId) {
}
