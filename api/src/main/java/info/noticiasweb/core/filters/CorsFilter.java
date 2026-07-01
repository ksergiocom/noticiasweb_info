package info.noticiasweb.core.filters;

import java.io.IOException;

import jakarta.ws.rs.HttpMethod;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

@Provider
public class CorsFilter implements ContainerRequestFilter, ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        if (HttpMethod.OPTIONS.equals(requestContext.getMethod())) {
            requestContext.abortWith(
                    Response.ok()
                            .header("Access-Control-Allow-Origin", "http://localhost:4200")
                            .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS")
                            .header("Access-Control-Allow-Headers", "Origin, Content-Type, Accept, Authorization")
                            .header("Access-Control-Allow-Credentials", "true")
                            .build()
            );
        }
    }

    @Override
    public void filter(
            ContainerRequestContext requestContext,
            ContainerResponseContext responseContext
    ) throws IOException {

        responseContext.getHeaders().putSingle(
                "Access-Control-Allow-Origin",
                "http://localhost:4200"
        );

        responseContext.getHeaders().putSingle(
                "Access-Control-Allow-Methods",
                "GET, POST, PUT, DELETE, OPTIONS"
        );

        responseContext.getHeaders().putSingle(
                "Access-Control-Allow-Headers",
                "Origin, Content-Type, Accept, Authorization"
        );

        responseContext.getHeaders().putSingle(
                "Access-Control-Allow-Credentials",
                "true"
        );
    }
}