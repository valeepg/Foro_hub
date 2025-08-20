package foro.hub.domain.topico;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
    Optional<Topico> findByIdAndActivoTrue(Long id);
    Optional<Topico> findByTituloAndActivoTrue(@NotBlank String titulo);
    Page<Topico> findByActivoTrue(Pageable paginacion);
}
