package info.noticiasweb.periodicos.dtos;

import info.noticiasweb.periodicos.models.Periodico;

public record PeriodicoDTO(Long id, String nombre, String url, String logo, String rss) {

    public PeriodicoDTO(Periodico p) {
        this(
                p.getId(),
                p.getNombre(),
                p.getUrl(),
                p.getLogo(),
                p.getRss()
            );
    }
}
