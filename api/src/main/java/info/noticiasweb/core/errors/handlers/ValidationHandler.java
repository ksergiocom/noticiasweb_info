package info.noticiasweb.core.errors.handlers;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Provider
public class ValidationHandler implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();
        List<Map<String, String>> errors;

        // 2. Protegernos contra colecciones nulas o vacías
        if (violations == null || violations.isEmpty()) {
            errors = List.of(Map.of(
                "field", "global",
                "message", exception.getMessage() != null ? exception.getMessage() : "Error de restricción desconocido"
            ));
        } else {
            errors = violations.stream()
                    .map(this::toError)
                    .toList();
        }

        // 3. Retornar la respuesta estructurada
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(Map.of(
                        "message", "Datos inválidos",
                        "errors", errors
                ))
                .build();
    }

    private Map<String, String> toError(ConstraintViolation<?> violation) {
        return Map.of(
                "field", getFieldName(violation),
                "message", violation.getMessage()
        );
    }

    /**
     * Extrae el nombre del campo usando la API oficial de Jakarta Validation de forma robusta.
     */
    private String getFieldName(ConstraintViolation<?> violation) {
        String fieldName = null;

        // Iteramos los nodos del Path de Jakarta. El último nodo siempre es el campo/propiedad afectado.
        for (Path.Node node : violation.getPropertyPath()) {
            fieldName = node.getName();
        }

        // Si por alguna razón la API de nodos devuelve null, usamos un fallback seguro
        if (fieldName == null || fieldName.isBlank()) {
            String pathStr = violation.getPropertyPath().toString();
            int lastDot = pathStr.lastIndexOf('.');
            return lastDot >= 0 ? pathStr.substring(lastDot + 1) : pathStr;
        }

        return fieldName;
    }
}