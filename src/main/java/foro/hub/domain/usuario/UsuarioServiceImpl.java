package foro.hub.domain.usuario;

import foro.hub.domain.perfil.Perfil;
import foro.hub.domain.perfil.PerfilRepository;
import foro.hub.infra.errores.ValidacionDeIntegridad;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements IUsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PerfilRepository perfilRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public DtoUsuarioDetalle registrarUsuario(DtoUsuario dto) {
        var perfil = this.validaPerfil(dto.idPerfil());

        return new DtoUsuarioDetalle( usuarioRepository.save(new Usuario(dto, perfil, passwordEncoder)));
    }

    @Transactional
    @Override
    public void eliminarUsuario(Long id) {
        //usuarioRepository.deleteById(id);
        usuarioRepository.findByIdAndActivoTrue(id)
                .ifPresent(Usuario::desactivarUsuario);
    }

    @Override
    public Page<DtoUsuarioDetalle> listarUsuarios(Pageable paginacion) {
        /*
        return usuarioRepository.findAllByActivoTrue()
                .stream()
                .map(DtoUsuarioDetalle::new)
                .toList();
         */
        return usuarioRepository.findByActivoTrue(paginacion)
                .map(DtoUsuarioDetalle::new);
    }

    @Override
    public DtoUsuarioDetalle datosUsuario(Long id) {
        return usuarioRepository.findByIdAndActivoTrue(id)
                .map(DtoUsuarioDetalle::new)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    @Transactional
    @Override
    public DtoUsuarioDetalle actualizarUsuario(DtoUsuario dto) {
        Usuario usuario = usuarioRepository.findByIdAndActivoTrue(dto.id())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Optional<Usuario> duplicado = usuarioRepository.findByEmailAndActivoTrue(dto.email());
        if (duplicado.isPresent() && !duplicado.get().getId().equals(usuario.getId())) {
            throw new RuntimeException("Ya existe un usuario activo con ese email");
        }

        var perfil = this.validaPerfil(dto.idPerfil());

        usuario.actualizarUsuario(dto, perfil, passwordEncoder);
        return new DtoUsuarioDetalle(usuario);
    }

    private Perfil validaPerfil(Long idPerfil) {
        if(perfilRepository.findByIdAndActivoTrue(idPerfil).isEmpty()){
            throw new ValidacionDeIntegridad("El id para el Perfil no fue encontrado");
        }
        var perfil = perfilRepository.findByIdAndActivoTrue(idPerfil).get();

        return perfil;
    }
}
