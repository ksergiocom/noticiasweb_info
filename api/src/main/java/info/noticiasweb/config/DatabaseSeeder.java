package info.noticiasweb.config;

import info.noticiasweb.periodicos.models.Periodico;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Singleton
@Startup
public class DatabaseSeeder {

    @PersistenceContext(unitName = "PeriodicosDS")
    private EntityManager em;

    @PostConstruct
    public void seed() {
        seedPeriodicos();
    }

    private void seedPeriodicos() {
        Long count = em.createQuery(
                "SELECT COUNT(p) FROM Periodico p",
                Long.class).getSingleResult();

        if (count > 0) {
            return;
        }

        Periodico elPais = new Periodico();
        elPais.setNombre("El País");
        elPais.setUrl("https://elpais.com");
        elPais.setLogo("https://upload.wikimedia.org/wikipedia/commons/a/a5/El_Pa%C3%ADs_logo.svg");
        elPais.setRss("https://feeds.elpais.com/mrss-s/pages/ep/site/elpais.com/portada");

        Periodico elMundo = new Periodico();
        elMundo.setNombre("El Mundo");
        elMundo.setUrl("https://www.elmundo.es");
        elMundo.setLogo("https://upload.wikimedia.org/wikipedia/commons/7/7b/El_Mundo_logo.svg");
        elMundo.setRss("https://e00-elmundo.uecdn.es/elmundo/rss/portada.xml");

        Periodico abc = new Periodico();
        abc.setNombre("ABC");
        abc.setUrl("https://www.abc.es");
        abc.setLogo("https://upload.wikimedia.org/wikipedia/commons/c/c3/Diario_ABC_logo.svg");
        abc.setRss("https://www.abc.es/rss/feeds/abcPortada.xml");

        em.persist(elPais);
        em.persist(elMundo);
        em.persist(abc);
    }
}