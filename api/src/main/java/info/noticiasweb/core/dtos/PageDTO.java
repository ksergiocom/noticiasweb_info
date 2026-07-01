package info.noticiasweb.core.dtos;

import java.util.List;

public record PageDTO<T>(
        List<T> data,
        long total,
        int page,
        int size,
        int totalPages
) {
}