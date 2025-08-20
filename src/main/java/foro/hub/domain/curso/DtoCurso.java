package foro.hub.domain.curso;

import jakarta.validation.constraints.NotBlank;

public record DtoCurso(
        Long id,

        @NotBlank
        String nombre,

        @NotBlank
        String categoria
) {}
