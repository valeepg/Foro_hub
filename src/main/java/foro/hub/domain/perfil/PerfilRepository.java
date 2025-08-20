package foro.hub.domain.perfil;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PerfilRepository extends JpaRepository<Perfil, Long> {
    Optional<Perfil> findByIdAndActivoTrue(Long id);
    Optional<Perfil> findByNombreAndActivoTrue(@NotBlank String nombre);
    Page<Perfil> findByActivoTrue(Pageable paginacion);
}
