package info.noticiasweb.config;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;

@Startup
@Singleton
public class FulltextIndexInitializer {

    @Resource(lookup = "java:app/PeriodicosDS")
    DataSource dataSource;

    @PostConstruct
    public void init() {
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("ALTER TABLE noticias ADD FULLTEXT INDEX ft_noticias_busqueda (titulo, descripcion)");
        } catch (SQLException e) {
            if (e.getErrorCode() != 1061) {
                e.printStackTrace();
            }
        }
    }
}
