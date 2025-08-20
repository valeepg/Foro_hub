package foro.hub.domain.curso;

public record DtoCursoDetalle(
        Long id,
        String nombre,
        String categoria,
        Boolean activo
) {
    public DtoCursoDetalle(Curso curso) {
        this(curso.getId(), curso.getNombre(), curso.getCategoria(), curso.getActivo());
    }
}
