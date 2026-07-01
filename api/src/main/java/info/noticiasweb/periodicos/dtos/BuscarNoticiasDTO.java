package info.noticiasweb.periodicos.dtos;

import java.util.List;

public record BuscarNoticiasDTO(
        int page,
        int size,
        List<Long> periodicoIds,
        String texto
) {}