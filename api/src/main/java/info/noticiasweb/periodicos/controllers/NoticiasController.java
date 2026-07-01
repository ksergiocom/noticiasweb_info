package info.noticiasweb.periodicos.controllers;

import java.util.List;

import info.noticiasweb.auth.security.AdminOnly;
import info.noticiasweb.core.dtos.PageDTO;
import info.noticiasweb.periodicos.dtos.ActualizarNoticiaDTO;
import info.noticiasweb.periodicos.dtos.BuscarNoticiasDTO;
import info.noticiasweb.periodicos.dtos.CrearNoticiaDTO;
import info.noticiasweb.periodicos.dtos.NoticiaDTO;
import info.noticiasweb.periodicos.services.NoticiaService;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;

@Path("/noticias")
public class NoticiasController {
    /////////////////////////////////////////////
    // Dependencias
    /////////////////////////////////////////////
    @Inject
    NoticiaService noticiaService;

    /////////////////////////////////////////////
    // Rutas
    /////////////////////////////////////////////
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public PageDTO<NoticiaDTO> listar(
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("size") @DefaultValue("10") int size) {
        return this.noticiaService.listarPaginadoTodos(page, size);
    }

    @POST
    @AdminOnly
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public NoticiaDTO crear(@Valid CrearNoticiaDTO dto) {
        return this.noticiaService.crear(dto);
    }

    @POST
    @Path("/buscar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public PageDTO<NoticiaDTO> buscar(BuscarNoticiasDTO dto) {
        return noticiaService.listarPaginado(
                dto.page(),
                dto.size(),
                dto.periodicoIds(),
                dto.texto());
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public NoticiaDTO obtener(@PathParam("id") Long id) {
        return this.noticiaService.obtener(id);
    }

    @DELETE
    @AdminOnly
    @Path("/{id}")
    public Response eliminar(@PathParam("id") Long id) {
        this.noticiaService.eliminar(id);
        return Response.ok().build();
    }

    @PUT
    @AdminOnly
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public NoticiaDTO actualizar(@Valid ActualizarNoticiaDTO dto, @PathParam("id") Long id) {
        return this.noticiaService.actualizar(id, dto);
    }
}
