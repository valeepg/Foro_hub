package foro.hub.domain.curso;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CursoServiceImpl implements ICursoService {

    @Autowired
    private CursoRepository cursoRepository;

    @Override
    public DtoCursoDetalle registrarCurso(DtoCurso dto) {
        return new DtoCursoDetalle( cursoRepository.save(new Curso(dto)));
    }

    @Transactional
    @Override
    public void eliminarCurso(Long id) {
        //cursoRepository.deleteById(id);
        cursoRepository.findByIdAndActivoTrue(id)
                .ifPresent(Curso::desactivarCurso);
    }

    @Override
    public Page<DtoCursoDetalle> listarCursos(Pageable paginacion) {
        /*
        return cursoRepository.findAllByActivoTrue()
                .stream()
                .map(DtoCursoDetalle::new)
                .toList();
         */
//        return medicoRepository.findAll(paginacion).map(DatosListadoMedico::new);
        return cursoRepository.findByActivoTrue(paginacion)
                .map(DtoCursoDetalle::new);
    }

    @Override
    public DtoCursoDetalle datosCurso(Long id) {
        return cursoRepository.findByIdAndActivoTrue(id)
                .map(DtoCursoDetalle::new)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));
    }

    @Transactional
    @Override
    public DtoCursoDetalle actualizarCurso(DtoCurso dto) {
        Curso usuario = cursoRepository.findByIdAndActivoTrue(dto.id())
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        Optional<Curso> duplicado = cursoRepository.findByNombreAndActivoTrue(dto.nombre());
        if (duplicado.isPresent() && !duplicado.get().getId().equals(usuario.getId())) {
            throw new RuntimeException("Ya existe un usuario activo con ese email");
        }
        usuario.actualizarCurso(dto);
        return new DtoCursoDetalle(usuario);
    }
}
