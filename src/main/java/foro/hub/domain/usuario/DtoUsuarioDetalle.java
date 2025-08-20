package foro.hub.domain.usuario;

public record DtoUsuarioDetalle(
        Long id,
        String nombre,
        String email,
        Boolean activo
) {
    public DtoUsuarioDetalle(Usuario usuario) {
        this(usuario.getId(), usuario.getNombre(), usuario.getEmail(), usuario.isEnabled());
    }
}
