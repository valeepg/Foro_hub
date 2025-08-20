package foro.hub.domain.perfil;

public record DtoPerfilDetalle(
        Long id,
        String nombre,
        Boolean activo
) {
    public DtoPerfilDetalle(Perfil perfil) {
        this(perfil.getId(), perfil.getNombre(), perfil.getActivo());
    }
}
