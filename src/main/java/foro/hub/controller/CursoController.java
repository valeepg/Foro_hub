package foro.hub.controller;

import foro.hub.domain.curso.DtoCurso;
import foro.hub.domain.curso.DtoCursoDetalle;
import foro.hub.domain.curso.ICursoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


@RestController
@RequestMapping("/cursos")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearer-key")
public class CursoController {

    @Autowired
    private ICursoService cursoService;

    @PostMapping
    @Operation(summary = "Registra un nuevo curso en la base de datos")
    public ResponseEntity<DtoCursoDetalle> registrarCurso(@RequestBody @Valid DtoCurso dto, UriComponentsBuilder uriBuilder) {
        DtoCursoDetalle dtoDetalle = cursoService.registrarCurso(dto);
        var uri = uriBuilder.path("/cursos/{id}").buildAndExpand(dtoDetalle.id()).toUri();
        return ResponseEntity.created(uri).body(dtoDetalle);
    }

    @GetMapping
    @Operation(summary = "Obtiene un listado de los cursos (paginado)")
    public ResponseEntity<Page<DtoCursoDetalle>> listarCursos(@PageableDefault(size = 2) Pageable paginacion) {
        return ResponseEntity.ok(cursoService.listarCursos(paginacion));
    }

    @PutMapping
    @Operation(summary = "Actualiza la información de un curso existente")
    public ResponseEntity<DtoCursoDetalle> actualizarCurso(@RequestBody @Valid DtoCurso dto) {
        return ResponseEntity.ok(cursoService.actualizarCurso(dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina un curso (borrado lógico)")
    public ResponseEntity<Void> eliminarCurso(@PathVariable Long id) {
        cursoService.eliminarCurso(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtiene los detalles de un curso específico por su ID")
    public ResponseEntity<DtoCursoDetalle> datosCurso(@PathVariable Long id) {
        return ResponseEntity.ok(cursoService.datosCurso(id));
    }
}
