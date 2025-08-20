package foro.hub.domain.respuesta;

import foro.hub.domain.topico.Topico;
import foro.hub.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "respuestas")
@Entity(name = "Respuesta")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Respuesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mensaje;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topico_id")
    private Topico topico;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id")
    private Usuario autor;

    private Boolean solucion = false;

    @Column(nullable = false)
    private Boolean activo = true;

    public Respuesta(DtoRespuesta dto, Usuario autor, Topico topico) {
        asignaRespuesta(dto, autor, topico);
        this.activo = true;
        this.solucion = false;
        this.fechaCreacion = dto.fechaCreacion();
    }

    public Long getId() {
        return id;
    }

    public String getMensaje() {
        return mensaje;
    }

    public Topico getTopico() {
        return topico;
    }

    public Usuario getAutor() {
        return autor;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public Boolean getSolucion() {
        return solucion;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void desactivarRespuesta() {
        this.activo = false;
    }

    public void actualizarRespuesta(DtoRespuesta dto, Usuario autor, Topico topico) {
        asignaRespuesta(dto, autor, topico);
        this.solucion = dto.solucion();
    }

    private void asignaRespuesta(DtoRespuesta dto, Usuario autor, Topico topico) {
        this.mensaje = dto.mensaje();
        this.autor = autor;
        this.topico = topico;
    }
}
