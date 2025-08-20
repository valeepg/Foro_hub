package foro.hub.domain.topico;

import foro.hub.domain.curso.Curso;
import foro.hub.domain.respuesta.Respuesta;
import foro.hub.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "topicos")
@Entity(name = "Topico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String mensaje;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Enumerated(EnumType.STRING)
    private EstadosTopico estatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id")
    private Usuario autor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id")
    private Curso curso;

    @OneToMany(mappedBy = "topico", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Respuesta> respuestas = new ArrayList<>();

    @Column(nullable = false)
    private Boolean activo = true;

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public Usuario getAutor() {
        return autor;
    }

    public Curso getCurso() {
        return curso;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void desactivarTopico() {
        this.activo = false;
    }

    public Topico(DtoTopico dto, Usuario autor, Curso curso) {
        asignaDatosTopico(dto, autor, curso);
        this.activo = true;
        this.estatus = EstadosTopico.NO_RESPONDIDO;
        this.fechaCreacion = LocalDateTime.now();
    }

    public void actualizarTopico(DtoTopico dto, Usuario autor, Curso curso) {
        asignaDatosTopico(dto, autor, curso);
        this.estatus = dto.estatus();
    }

    private void asignaDatosTopico(DtoTopico dto, Usuario autor, Curso curso) {
        this.titulo = dto.titulo();
        this.mensaje = dto.mensaje();
        this.autor = autor;
        this.curso = curso;
    }
}
