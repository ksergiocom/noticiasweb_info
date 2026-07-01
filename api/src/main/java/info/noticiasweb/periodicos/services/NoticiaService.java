package info.noticiasweb.periodicos.services;

import java.util.ArrayList;
import java.util.List;

import info.noticiasweb.core.dtos.PageDTO;
import info.noticiasweb.periodicos.dao.NoticiaDAO;
import info.noticiasweb.periodicos.dtos.ActualizarNoticiaDTO;
import info.noticiasweb.periodicos.dtos.CrearNoticiaDTO;
import info.noticiasweb.periodicos.dtos.NoticiaDTO;
import info.noticiasweb.periodicos.models.Noticia;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class NoticiaService {
    @Inject
    NoticiaDAO noticiaDAO;

    public List<NoticiaDTO> listar() {
        List<Noticia> noticias = this.noticiaDAO.listar();
        List<NoticiaDTO> noticiasDTOs = new ArrayList<>();
        for (Noticia n : noticias) {
            noticiasDTOs.add(new NoticiaDTO(n));
        }
        return noticiasDTOs;
    }

    public NoticiaDTO crear(CrearNoticiaDTO dto) {
        Noticia n = this.noticiaDAO.crear(dto);
        return new NoticiaDTO(n);
    }

    // Pendiente implementar uno eficiente
    // // Mala idea; cambiarlo luego por una consulta
    // public List<NoticiaDTO> crearMultiples(List<CrearNoticiaDTO> dtos) {
    // List<NoticiaDTO> nuevos = new ArrayList<>();
    // for (CrearNoticiaDTO dto : dtos) {
    // this.crear(dto);
    // }
    // return nuevos;
    // }

    public NoticiaDTO obtener(Long id) {
        Noticia n = this.noticiaDAO.obtener(id);
        return new NoticiaDTO(n);
    }

    public void eliminar(Long id) {
        this.noticiaDAO.eliminar(id);
    }

    public NoticiaDTO actualizar(Long id, ActualizarNoticiaDTO dto) {
        Noticia n = this.noticiaDAO.actualizar(id, dto);
        return new NoticiaDTO(n);
    }

    public PageDTO<NoticiaDTO> listarPaginadoTodos(int page, int size) {
        List<Long> arrayVacio = new ArrayList<>();
        List<NoticiaDTO> noticias = noticiaDAO.listarPaginado(page, size, arrayVacio, null)
                .stream()
                .map(NoticiaDTO::new)
                .toList();

        long total = noticiaDAO.contar(arrayVacio, null);
        int totalPages = (int) Math.ceil((double) total / size);

        return new PageDTO<>(noticias, total, page, size, totalPages);
    }

    public PageDTO<NoticiaDTO> listarPaginado(int page, int size, List<Long> ids, String texto) {
        List<NoticiaDTO> noticias = noticiaDAO.listarPaginado(page, size, ids, texto)
                .stream()
                .map(NoticiaDTO::new)
                .toList();

        long total = noticiaDAO.contar(ids, texto);
        int totalPages = (int) Math.ceil((double) total / size);

        return new PageDTO<>(noticias, total, page, size, totalPages);
    }
}
