package foro.hub.domain.topico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ITopicoService {
    DtoTopicoDetalle registrarTopico(DtoTopico dto);
    void eliminarTopico(Long id);
    Page<DtoTopicoDetalle> listarTopicos(Pageable paginacion);
    DtoTopicoDetalle actualizarTopico(DtoTopico dto);
    DtoTopicoDetalle datosTopico(Long id);
}
