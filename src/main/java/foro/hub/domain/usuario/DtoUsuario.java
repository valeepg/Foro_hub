package foro.hub.domain.usuario;

import foro.hub.domain.perfil.Perfil;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DtoUsuario(
        Long id,

        @NotBlank
        String nombre,

        @NotBlank
        @Email
        String email,

        @NotBlank
        String contrasena,

        //@NotBlank
        @NotNull
        @Min(1)
        Long idPerfil
) {
    public DtoUsuario(Usuario usuario, Perfil perfil) {
        this(usuario.getId(), usuario.getNombre(), usuario.getEmail(), usuario.getContrasena(), perfil.getId());
    }
}

