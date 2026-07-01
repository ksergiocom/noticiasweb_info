package info.noticiasweb.periodicos.dtos;

import jakarta.validation.constraints.NotBlank;

public record CrearPeriodicoDTO(
        @NotBlank(message = "No puede estar vacío") 
        String nombre,
        @NotBlank(message = "No puede estar vacío") String url,
        @NotBlank(message = "No puede estar vacío") String logo,
        @NotBlank(message = "No puede estar vacío") String rss
) {
}
