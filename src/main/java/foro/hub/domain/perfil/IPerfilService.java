package foro.hub.domain.perfil;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface IPerfilService {
    DtoPerfilDetalle registrarPerfil(DtoPerfil dto);
    void eliminarPerfil(Long id);
    Page<DtoPerfilDetalle> listarPerfiles(Pageable paginacion);
    DtoPerfilDetalle actualizarPerfil(DtoPerfil dto);
    DtoPerfilDetalle datosPerfil(Long id);
}
