package info.noticiasweb.periodicos.controllers;

import java.util.List;

import info.noticiasweb.auth.security.AdminOnly;
import info.noticiasweb.periodicos.dtos.ActualizarPeriodicoDTO;
import info.noticiasweb.periodicos.dtos.CrearPeriodicoDTO;
import info.noticiasweb.periodicos.dtos.PeriodicoDTO;
import info.noticiasweb.periodicos.services.PeriodicoService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/periodicos")
public class PeriodicosController {

    /////////////////////////////////////////////
    // Dependencias
    /////////////////////////////////////////////
    @Inject
    PeriodicoService periodicoService;

    @Inject
    Validator validator;

    /////////////////////////////////////////////
    // Rutas
    /////////////////////////////////////////////
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<PeriodicoDTO> listar() {
        return periodicoService.listar();
    }

    @POST
    @AdminOnly
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public PeriodicoDTO crear(@Valid CrearPeriodicoDTO dto) {
        return this.periodicoService.crear(dto);
    }

    @DELETE
    @AdminOnly
    @Path("/{id}")
    public Response eliminar(@PathParam("id") Long id) {
        this.periodicoService.eliminar(id);
        return Response.ok().build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public PeriodicoDTO obtener(@PathParam("id") Long id) {
        return this.periodicoService.obtener(id);
    }

    @PUT
    @AdminOnly
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public PeriodicoDTO actualizar(
            @PathParam("id") Long id,
            @Valid ActualizarPeriodicoDTO dto) {
        return this.periodicoService.actualizar(id, dto);
    }

}
