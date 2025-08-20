package foro.hub.domain.topico;

public record DtoTopicoDetalle(
        Long id,
        String titulo,
        String mensaje,
        Long autorId,
        Long cursoId,
        Boolean activo
) {
    public DtoTopicoDetalle(Topico topico) {
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getAutor().getId(),
                topico.getCurso().getId(),
                topico.getActivo()
        );
    }
}
