package foro.hub.domain.perfil;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PerfilServiceImpl implements IPerfilService {

    @Autowired
    private PerfilRepository perfilRepository;

    @Override
    public DtoPerfilDetalle registrarPerfil(DtoPerfil dto) {
        return new DtoPerfilDetalle( perfilRepository.save(new Perfil(dto)));
    }

    @Transactional
    @Override
    public void eliminarPerfil(Long id) {
        //perfilRepository.deleteById(id);
        perfilRepository.findByIdAndActivoTrue(id)
                .ifPresent(Perfil::desactivarPerfil);
    }

    @Override
    public Page<DtoPerfilDetalle> listarPerfiles(Pageable paginacion) {
        /*
        return perfilRepository.findAllByActivoTrue()
                .stream()
                .map(DtoPerfilDetalle::new)
                .toList();
         */
        return perfilRepository.findByActivoTrue(paginacion)
                .map(DtoPerfilDetalle::new);
    }

    @Override
    public DtoPerfilDetalle datosPerfil(Long id) {
        return perfilRepository.findByIdAndActivoTrue(id)
                .map(DtoPerfilDetalle::new)
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado"));
    }

    @Transactional
    @Override
    public DtoPerfilDetalle actualizarPerfil(DtoPerfil dto) {
        Perfil usuario = perfilRepository.findByIdAndActivoTrue(dto.id())
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado"));

        Optional<Perfil> duplicado = perfilRepository.findByNombreAndActivoTrue(dto.nombre());
        if (duplicado.isPresent() && !duplicado.get().getId().equals(usuario.getId())) {
            throw new RuntimeException("Ya existe un usuario activo con ese email");
        }
        usuario.actualizarPerfil(dto);
        return new DtoPerfilDetalle(usuario);
    }
}
