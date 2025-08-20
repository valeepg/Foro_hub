package foro.hub.domain.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DtoAutenticacionUsuario(
        @NotBlank @Email String email,
        @NotBlank String contrasena) {}