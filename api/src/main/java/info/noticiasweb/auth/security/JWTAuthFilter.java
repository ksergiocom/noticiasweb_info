package info.noticiasweb.auth.security;

import com.nimbusds.jwt.JWTClaimsSet;
import info.noticiasweb.auth.services.JWTService;
import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

@Provider
@AdminOnly
@Priority(Priorities.AUTHENTICATION)
public class JWTAuthFilter implements ContainerRequestFilter {

    @Inject
    JWTService jwtService;

    @Override
    public void filter(ContainerRequestContext requestContext) {
        String authHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            requestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED)
                            .build()
            );
            return;
        }

        String token = authHeader.substring("Bearer ".length());

        try {
            JWTClaimsSet claims = jwtService.validateToken(token);

            var roles = claims.getStringListClaim("roles");

            if (roles == null || !roles.contains("ADMIN")) {
                requestContext.abortWith(
                        Response.status(Response.Status.FORBIDDEN)
                                .build()
                );
            }

        } catch (Exception e) {
            requestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED)
                            .build()
            );
        }
    }
}