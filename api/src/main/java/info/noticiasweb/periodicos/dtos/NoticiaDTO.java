package info.noticiasweb.periodicos.dtos;

import java.time.LocalDateTime;

import info.noticiasweb.periodicos.models.Noticia;

public record NoticiaDTO(Long id, String titulo, String url, String descripcion, LocalDateTime fechaPublicacion, PeriodicoDTO periodico) {
    public NoticiaDTO(Noticia noticia){
        this(noticia.getId(), noticia.getTitulo(), noticia.getUrl(), noticia.getDescripcion(), noticia.getFechaPublicacion(), new PeriodicoDTO(noticia.getPeriodico()));
    }
}
