package info.noticiasweb.periodicos.services;

import java.util.ArrayList;
import java.util.List;

import info.noticiasweb.periodicos.dao.PeriodicoDAO;
import info.noticiasweb.periodicos.dtos.ActualizarPeriodicoDTO;
import info.noticiasweb.periodicos.dtos.CrearPeriodicoDTO;
import info.noticiasweb.periodicos.dtos.PeriodicoDTO;
import info.noticiasweb.periodicos.models.Periodico;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class PeriodicoService {
    @Inject
    PeriodicoDAO periodicoDAO;

    public List<PeriodicoDTO> listar() {
        List<PeriodicoDTO> periodicoDTOs = new ArrayList<>();
        for (Periodico p : this.periodicoDAO.listar()) {
            periodicoDTOs.add(new PeriodicoDTO(p));
        }
        return periodicoDTOs;
    }

    public PeriodicoDTO crear(CrearPeriodicoDTO dto) {
        return new PeriodicoDTO(this.periodicoDAO.crear(dto));
    }

    public void eliminar(Long id) {
        this.periodicoDAO.eliminar(id);
    }

    public PeriodicoDTO obtener(Long id) {
        return new PeriodicoDTO(this.periodicoDAO.obtener(id));
    }

    public PeriodicoDTO actualizar(Long id, ActualizarPeriodicoDTO dto) {
        return new PeriodicoDTO(this.periodicoDAO.actualizar(id, dto));
    }

}
