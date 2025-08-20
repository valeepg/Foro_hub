package foro.hub.domain.curso;

import foro.hub.domain.topico.Topico;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Table(name = "cursos")
@Entity(name = "Curso")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String categoria;

    @OneToMany(mappedBy = "curso", fetch = FetchType.LAZY)
    private List<Topico> topicos;

    @Column(nullable = false)
    private Boolean activo = true;

    public void desactivarCurso() {
        this.activo = false;
    }

    public Curso(DtoCurso dto) {
        this.nombre = dto.nombre();
        this.categoria = dto.categoria();
        this.activo = true;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void actualizarCurso(DtoCurso dto) {
        if (dto.nombre() != null) {
            this.nombre = dto.nombre();
        }
        if (dto.categoria() != null) {
            this.categoria = dto.categoria();
        }
    }

}
