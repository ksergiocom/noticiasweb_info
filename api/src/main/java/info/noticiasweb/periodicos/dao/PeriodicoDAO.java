package info.noticiasweb.periodicos.dao;

import java.util.List;

import info.noticiasweb.periodicos.dtos.ActualizarPeriodicoDTO;
import info.noticiasweb.periodicos.dtos.CrearPeriodicoDTO;
import info.noticiasweb.periodicos.models.Periodico;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless
public class PeriodicoDAO {
    @PersistenceContext
    EntityManager em;

    public List<Periodico> listar() {
        return em.createQuery("SELECT p FROM Periodico p", Periodico.class).getResultList();
    }

    public Periodico crear(CrearPeriodicoDTO dto) {
        Periodico p = new Periodico(dto);
        em.persist(p);
        return p;
    }

    public void eliminar(Long id) {
        this.em.createQuery("DELETE FROM Periodico p WHERE p.id = :id").setParameter("id", id).executeUpdate();
    }

    public Periodico obtener(Long id) {
        return this.em.createQuery("SELECT p FROM Periodico p WHERE p.id = :id", Periodico.class).setParameter("id", id)
                .getSingleResult();

    }

    public Periodico actualizar(Long id, ActualizarPeriodicoDTO dto) {
        Periodico p = this.obtener(id);

        if (dto.nombre() != null && !dto.nombre().isBlank()) {
            p.setNombre(dto.nombre());
        }

        if (dto.url() != null && !dto.url().isBlank()) {
            p.setUrl(dto.url());
        }

        if (dto.rss() != null && !dto.rss().isBlank()) {
            p.setRss(dto.rss());
        }

        return p;
    }
}
