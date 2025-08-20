package foro.hub.controller;

import foro.hub.domain.topico.*;
import foro.hub.domain.topico.DtoTopico;
import foro.hub.domain.topico.DtoTopicoDetalle;
import foro.hub.domain.topico.ITopicoService;
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
@RequestMapping("/topicos")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearer-key")
class TopicoController {

    @Autowired
    private ITopicoService topicoService;

    @PostMapping
    public ResponseEntity<DtoTopicoDetalle> registrarTopico(@RequestBody @Valid DtoTopico dto, UriComponentsBuilder uriBuilder) {
        DtoTopicoDetalle dtoDetalle = topicoService.registrarTopico(dto);
        var uri = uriBuilder.path("/topicos/{id}").buildAndExpand(dtoDetalle.id()).toUri();
        return ResponseEntity.created(uri).body(dtoDetalle);
    }

    @GetMapping
    public ResponseEntity<Page<DtoTopicoDetalle>> listarTopicos(@PageableDefault(size = 2) Pageable paginacion) {
        return ResponseEntity.ok(topicoService.listarTopicos(paginacion));
    }

    @PutMapping
    public ResponseEntity<DtoTopicoDetalle> actualizarTopico(@RequestBody @Valid DtoTopico dto) {
        return ResponseEntity.ok(topicoService.actualizarTopico(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTopico(@PathVariable Long id) {
        topicoService.eliminarTopico(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DtoTopicoDetalle> datosTopico(@PathVariable Long id) {
        return ResponseEntity.ok(topicoService.datosTopico(id));
    }
}
