package info.noticiasweb.auth.controllers;

import info.noticiasweb.auth.dtos.JWTDTO;
import info.noticiasweb.auth.dtos.LoginDTO;
import info.noticiasweb.auth.services.AuthService;

import jakarta.ws.rs.Produces;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;

@Path("/auth")
public class AuthController {
    @Inject
    AuthService authService;
    
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JWTDTO login(@Valid LoginDTO dto){
        return this.authService.login(dto);
    }

}
