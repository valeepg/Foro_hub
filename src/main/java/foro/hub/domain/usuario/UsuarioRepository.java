package foro.hub.domain.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    UserDetails findByEmailAndActivo(@NotBlank @Email String email, boolean activo);
    Optional<Usuario> findByIdAndActivoTrue(Long id);
    Optional<Usuario> findByEmailAndActivoTrue(@NotBlank @Email String email);
    Page<Usuario> findByActivoTrue(Pageable paginacion);
}
