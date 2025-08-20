package foro.hub.domain.usuario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface IUsuarioService {
    DtoUsuarioDetalle registrarUsuario(DtoUsuario dto);
    void eliminarUsuario(Long id);
    Page<DtoUsuarioDetalle> listarUsuarios(Pageable paginacion);
    DtoUsuarioDetalle actualizarUsuario(DtoUsuario dto);
    DtoUsuarioDetalle datosUsuario(Long id);
}
