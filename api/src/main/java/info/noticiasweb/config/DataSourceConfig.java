package info.noticiasweb.config;

import jakarta.annotation.sql.DataSourceDefinition;
import jakarta.enterprise.context.ApplicationScoped;

@DataSourceDefinition(
        name = "java:app/PeriodicosDS",
        className = "org.mariadb.jdbc.MariaDbDataSource",
        url = "${env.DB_URL}",
        user = "${env.DB_USER}",
        password = "${env.DB_PASSWORD}",
        minPoolSize = 1,
        maxPoolSize = 10
)
@ApplicationScoped
public class DataSourceConfig {
}
