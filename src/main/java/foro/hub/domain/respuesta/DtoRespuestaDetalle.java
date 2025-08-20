package foro.hub.domain.respuesta;

import java.time.LocalDateTime;

public record DtoRespuestaDetalle(
        Long id,
        String mensaje,
        Long autorId,
        Long topicoId,
        LocalDateTime fechaCreacion,
        Boolean solucion,
        Boolean activo
) {
    public DtoRespuestaDetalle(Respuesta respuesta) {
        this(
                respuesta.getId(),
                respuesta.getMensaje(),
                respuesta.getAutor().getId(),
                respuesta.getTopico().getId(),
                respuesta.getFechaCreacion(),
                respuesta.getSolucion(),
                respuesta.getActivo()
        );
    }
}
