package info.noticiasweb.auth.services;

import info.noticiasweb.auth.dtos.JWTDTO;
import info.noticiasweb.auth.dtos.LoginDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotAuthorizedException;

@ApplicationScoped
public class AuthService {

    @Inject
    JWTService jwtService;

    private final String adminUsername =
            System.getenv().getOrDefault("NOTICIAS_ADMIN_USERNAME", "admin");

    private final String adminPassword =
            System.getenv().getOrDefault("NOTICIAS_ADMIN_PASSWORD", "admin");

    public JWTDTO login(LoginDTO dto) {
        if (!dto.username().equals(adminUsername) || !dto.password().equals(adminPassword)) {
            throw new NotAuthorizedException("Credenciales no válidas");
        }

        String token = jwtService.generateToken(dto.username());

        return new JWTDTO(token);
    }
}