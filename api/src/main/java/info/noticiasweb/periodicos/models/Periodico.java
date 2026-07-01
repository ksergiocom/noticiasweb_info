package info.noticiasweb.periodicos.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import info.noticiasweb.periodicos.dtos.CrearPeriodicoDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "periodicos")
public class Periodico implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(unique = true, name = "nombre")
    private String nombre;

    @Column(name = "url")
    private String url;

    @Column(name = "logo")
    private String logo;

    @Column(name = "rss")
    private String rss;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    /////////////////////////////////////////////
    // Relaciones
    /////////////////////////////////////////////

    @OneToMany(mappedBy = "periodico", orphanRemoval = true)
    private List<Noticia> noticias = new ArrayList<>();

    /////////////////////////////////////////////
    // Constructores
    /////////////////////////////////////////////

    public Periodico() {
    }

    public Periodico(CrearPeriodicoDTO dto) {
        this.nombre = dto.nombre();
        this.url = dto.url();
        this.logo = dto.logo();
        this.rss = dto.rss();
    }

    /////////////////////////////////////////////
    // Getters y Setters
    /////////////////////////////////////////////

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRss() {
        return rss;
    }

    public void setRss(String rss) {
        this.rss = rss;
    }

    public List<Noticia> getNoticias() {
        return noticias;
    }

    public void setNoticias(List<Noticia> noticias) {
        this.noticias = noticias;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    /////////////////////////////////////////////
    // Hooks
    /////////////////////////////////////////////
    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.fechaCreacion = now;
        this.fechaActualizacion = now;
    }

    @PreUpdate
    protected void onUpdate() {
        this.fechaActualizacion = LocalDateTime.now();
    }

    /////////////////////////////////////////////
    // Helpers
    /////////////////////////////////////////////
    public void addNoticia(Noticia noticia) {
        noticias.add(noticia);
        noticia.setPeriodico(this);
    }

    public void removeNoticia(Noticia noticia) {
        noticias.remove(noticia);
        noticia.setPeriodico(null);
    }

}
