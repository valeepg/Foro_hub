package foro.hub.domain.curso;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ICursoService {
    DtoCursoDetalle registrarCurso(DtoCurso dto);
    void eliminarCurso(Long id);
    Page<DtoCursoDetalle> listarCursos(Pageable paginacion);
    DtoCursoDetalle actualizarCurso(DtoCurso dto);
    DtoCursoDetalle datosCurso(Long id);
}
