package foro.hub.domain.topico;

import foro.hub.domain.curso.Curso;
import foro.hub.domain.curso.CursoRepository;
import foro.hub.domain.usuario.Usuario;
import foro.hub.domain.usuario.UsuarioRepository;
import foro.hub.infra.errores.ValidacionDeIntegridad;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TopicoServiceImpl implements ITopicoService {

    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private CursoRepository cursoRepository;

    @Override
    public DtoTopicoDetalle registrarTopico(DtoTopico dto) {
        var autor = this.validaAutor(dto.autorId());
        var curso = this.validaCurso(dto.cursoId());

        return new DtoTopicoDetalle( topicoRepository.save(new Topico(dto, autor, curso)));
    }

    @Transactional
    @Override
    public void eliminarTopico(Long id) {
        //topicoRepository.deleteById(id);
        topicoRepository.findByIdAndActivoTrue(id)
                .ifPresent(Topico::desactivarTopico);
    }

    @Override
    public Page<DtoTopicoDetalle> listarTopicos(Pageable paginacion) {
        /*
        return topicoRepository.findAllByActivoTrue()
                .stream()
                .map(DtoTopicoDetalle::new)
                .toList();
         */
        return topicoRepository.findByActivoTrue(paginacion)
                .map(DtoTopicoDetalle::new);
    }

    @Override
    public DtoTopicoDetalle datosTopico(Long id) {
        return topicoRepository.findByIdAndActivoTrue(id)
                .map(DtoTopicoDetalle::new)
                .orElseThrow(() -> new EntityNotFoundException("Topico no encontrado con id: " + id));
    }

    @Transactional
    @Override
    public DtoTopicoDetalle actualizarTopico(DtoTopico dto) {
        Topico topico = topicoRepository.findByIdAndActivoTrue(dto.id())
                .orElseThrow(() -> new EntityNotFoundException("Topico no encontrado con id: " + dto.id()));

        Optional<Topico> duplicado = topicoRepository.findByTituloAndActivoTrue(dto.titulo());
        if (duplicado.isPresent() && !duplicado.get().getId().equals(topico.getId())) {
            throw new ValidacionDeIntegridad("Ya existe un topico activo con ese titulo");
        }

        var autor = this.validaAutor(dto.autorId());
        var curso = this.validaCurso(dto.cursoId());

        topico.actualizarTopico(dto, autor, curso);
        return new DtoTopicoDetalle(topico);
    }

    private Usuario validaAutor(Long idAutor) {
        if(usuarioRepository.findByIdAndActivoTrue(idAutor).isEmpty()){
            throw new ValidacionDeIntegridad("El id para el Autor no fue encontrado");
        }
        var autor = usuarioRepository.findByIdAndActivoTrue(idAutor).get();

        return autor;
    }

    private Curso validaCurso(Long idCurso) {
        if(cursoRepository.findByIdAndActivoTrue(idCurso).isEmpty()){
            throw new ValidacionDeIntegridad("El id para el Curso no fue encontrado");
        }
        var curso = cursoRepository.findByIdAndActivoTrue(idCurso).get();

        return curso;
    }
}