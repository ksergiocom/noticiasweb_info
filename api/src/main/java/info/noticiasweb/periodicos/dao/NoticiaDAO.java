package info.noticiasweb.periodicos.dao;

import java.util.List;

import info.noticiasweb.periodicos.dtos.ActualizarNoticiaDTO;
import info.noticiasweb.periodicos.dtos.CrearNoticiaDTO;
import info.noticiasweb.periodicos.models.Noticia;
import info.noticiasweb.periodicos.models.Periodico;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless
public class NoticiaDAO {

    @PersistenceContext
    EntityManager em;

    @Inject
    PeriodicoDAO periodicoDAO;

    public List<Noticia> listar() {
        return em.createQuery("SELECT n FROM Noticia n", Noticia.class)
                .getResultList();
    }

    public Noticia crear(CrearNoticiaDTO dto) {
        Periodico periodico = periodicoDAO.obtener(dto.periodicoId());

        Noticia noticia = new Noticia();
        noticia.setTitulo(dto.titulo());
        noticia.setUrl(dto.url());
        noticia.setDescripcion(dto.descripcion());
        noticia.setFechaPublicacion(dto.fechaPublicacion());
        noticia.setPeriodico(periodico);

        em.persist(noticia);

        return noticia;
    }

    public void eliminar(Long id) {
        Noticia noticia = obtener(id);
        em.remove(noticia);
    }

    public Noticia obtener(Long id) {
        return em.createQuery(
                "SELECT n FROM Noticia n WHERE n.id = :id",
                Noticia.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    public Noticia actualizar(Long id, ActualizarNoticiaDTO dto) {
        Noticia n = obtener(id);

        if (dto.titulo() != null && !dto.titulo().isBlank()) {
            n.setTitulo(dto.titulo());
        }

        if (dto.url() != null && !dto.url().isBlank()) {
            n.setUrl(dto.url());
        }

        if (dto.descripcion() != null && !dto.descripcion().isBlank()) {
            n.setDescripcion(dto.descripcion());
        }

        if (dto.fechaPublicacion() != null) {
            n.setFechaPublicacion(dto.fechaPublicacion());
        }

        if (dto.periodicoId() != null) {
            Periodico p = periodicoDAO.obtener(dto.periodicoId());
            n.setPeriodico(p);
        }

        return n;
    }

    public List<Noticia> listarPaginado(int page, int size, List<Long> periodicoIds, String texto) {
        boolean tieneFiltroPeriodico = periodicoIds != null && !periodicoIds.isEmpty();
        boolean tieneFiltroTexto = texto != null && !texto.isBlank();

        StringBuilder sql = new StringBuilder("SELECT * FROM noticias n WHERE 1=1");

        if (tieneFiltroPeriodico) {
            sql.append(" AND n.periodico_id IN :periodicoIds");
        }

        if (tieneFiltroTexto) {
            sql.append(" AND MATCH(n.titulo, n.descripcion) AGAINST(:texto)");
        }

        if (tieneFiltroTexto) {
            sql.append(" ORDER BY MATCH(n.titulo, n.descripcion) AGAINST(:texto) DESC, n.fecha_publicacion DESC, n.id DESC");
        } else {
            sql.append(" ORDER BY n.fecha_publicacion DESC, n.id DESC");
        }

        var query = em.createNativeQuery(sql.toString(), Noticia.class)
                .setFirstResult(page * size)
                .setMaxResults(size);

        if (tieneFiltroPeriodico) {
            query.setParameter("periodicoIds", periodicoIds);
        }

        if (tieneFiltroTexto) {
            query.setParameter("texto", texto);
        }

        return query.getResultList();
    }

    public long contar(List<Long> periodicoIds, String texto) {
        boolean tieneFiltroPeriodico = periodicoIds != null && !periodicoIds.isEmpty();
        boolean tieneFiltroTexto = texto != null && !texto.isBlank();

        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM noticias n WHERE 1=1");

        if (tieneFiltroPeriodico) {
            sql.append(" AND n.periodico_id IN :periodicoIds");
        }

        if (tieneFiltroTexto) {
            sql.append(" AND MATCH(n.titulo, n.descripcion) AGAINST(:texto)");
        }

        var query = em.createNativeQuery(sql.toString());

        if (tieneFiltroPeriodico) {
            query.setParameter("periodicoIds", periodicoIds);
        }

        if (tieneFiltroTexto) {
            query.setParameter("texto", texto);
        }

        return ((Number) query.getSingleResult()).longValue();
    }
}