package foro.hub.domain.respuesta;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DtoRespuesta(
        Long id,

        @NotBlank
        String mensaje,

        @NotNull
        Long topicoId,

        @NotNull
        Long autorId,

        LocalDateTime fechaCreacion,

        Boolean solucion
) {}
