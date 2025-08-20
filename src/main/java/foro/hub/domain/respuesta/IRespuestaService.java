package foro.hub.domain.respuesta;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface IRespuestaService {
    DtoRespuestaDetalle registrarRespuesta(DtoRespuesta dto);
    void eliminarRespuesta(Long id);
    Page<DtoRespuestaDetalle> listarRespuestas(Pageable paginacion);
    DtoRespuestaDetalle actualizarRespuesta(DtoRespuesta dto);
    DtoRespuestaDetalle datosRespuesta(Long id);

}
