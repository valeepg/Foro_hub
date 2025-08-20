package foro.hub.domain.curso;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CursoRepository extends JpaRepository<Curso, Long> {
    Optional<Curso> findByIdAndActivoTrue(Long id);
    Optional<Curso> findByNombreAndActivoTrue(@NotBlank String nombre);
    Page<Curso> findByActivoTrue(Pageable paginacion);
}
