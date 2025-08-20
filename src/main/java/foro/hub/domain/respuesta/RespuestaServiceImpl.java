package foro.hub.domain.respuesta;

import foro.hub.domain.topico.Topico;
import foro.hub.domain.topico.TopicoRepository;
import foro.hub.domain.usuario.Usuario;
import foro.hub.domain.usuario.UsuarioRepository;
import foro.hub.infra.errores.ValidacionDeIntegridad;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RespuestaServiceImpl implements IRespuestaService {

    @Autowired
    private RespuestaRepository respuestaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private TopicoRepository topicoRepository;

    @Override
    public DtoRespuestaDetalle registrarRespuesta(DtoRespuesta dto) {
        var autor = this.validaAutor(dto.autorId());
        var topico = this.validaTopico(dto.topicoId());

        return new DtoRespuestaDetalle( respuestaRepository.save(new Respuesta(dto, autor, topico)));
    }

    @Transactional
    @Override
    public void eliminarRespuesta(Long id) {
        //respuestaRepository.deleteById(id);
        respuestaRepository.findByIdAndActivoTrue(id)
                .ifPresent(Respuesta::desactivarRespuesta);
    }

    @Override
    public Page<DtoRespuestaDetalle> listarRespuestas(Pageable paginacion) {
        /*
        return respuestaRepository.findAllByActivoTrue()
                .stream()
                .map(DtoRespuestaDetalle::new)
                .toList();
         */
        return respuestaRepository.findByActivoTrue(paginacion)
                .map(DtoRespuestaDetalle::new);
    }

    @Override
    public DtoRespuestaDetalle datosRespuesta(Long id) {
        return respuestaRepository.findByIdAndActivoTrue(id)
                .map(DtoRespuestaDetalle::new)
                .orElseThrow(() -> new RuntimeException("Respuesta no encontrado"));
    }

    @Transactional
    @Override
    public DtoRespuestaDetalle actualizarRespuesta(DtoRespuesta dto) {
        Respuesta respuesta = respuestaRepository.findByIdAndActivoTrue(dto.id())
                .orElseThrow(() -> new RuntimeException("Respuesta no encontrado"));

        Optional<Respuesta> duplicado = respuestaRepository.findByMensajeAndTopicoIdAndActivoTrue(dto.mensaje(), dto.topicoId());
        if (duplicado.isPresent() && !duplicado.get().getId().equals(respuesta.getId())) {
            throw new RuntimeException("Ya existe un respuesta activo con ese email");
        }

        var autor = this.validaAutor(dto.autorId());
        var topico = this.validaTopico(dto.topicoId());

        respuesta.actualizarRespuesta(dto, autor, topico);
        return new DtoRespuestaDetalle(respuesta);
    }

    private Usuario validaAutor(Long idAutor) {
        if(usuarioRepository.findByIdAndActivoTrue(idAutor).isEmpty()){
            throw new ValidacionDeIntegridad("El id para el Autor no fue encontrado");
        }
        var autor = usuarioRepository.findByIdAndActivoTrue(idAutor).get();

        return autor;
    }

    private Topico validaTopico(Long idTopico) {
        if(topicoRepository.findByIdAndActivoTrue(idTopico).isEmpty()){
            throw new ValidacionDeIntegridad("El id para el Topico no fue encontrado");
        }
        var topico = topicoRepository.findByIdAndActivoTrue(idTopico).get();

        return topico;
    }

}
