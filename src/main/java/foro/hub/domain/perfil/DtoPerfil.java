package foro.hub.domain.perfil;

import jakarta.validation.constraints.NotBlank;

public record DtoPerfil(
        Long id,

        @NotBlank
        String nombre
) {}
