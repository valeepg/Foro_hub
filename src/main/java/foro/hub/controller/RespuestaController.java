package foro.hub.controller;

import foro.hub.domain.respuesta.*;
import foro.hub.domain.respuesta.DtoRespuesta;
import foro.hub.domain.respuesta.DtoRespuestaDetalle;
import foro.hub.domain.respuesta.IRespuestaService;
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
@RequestMapping("/respuestas")
@RequiredArgsConstructor
public class RespuestaController {

    @Autowired
    private IRespuestaService respuestaService;

    @PostMapping
    public ResponseEntity<DtoRespuestaDetalle> registrarRespuesta(@RequestBody @Valid DtoRespuesta dto, UriComponentsBuilder uriBuilder) {
        DtoRespuestaDetalle dtoDetalle = respuestaService.registrarRespuesta(dto);
        var uri = uriBuilder.path("/respuestas/{id}").buildAndExpand(dtoDetalle.id()).toUri();
        return ResponseEntity.created(uri).body(dtoDetalle);
    }

    @GetMapping
    public ResponseEntity<Page<DtoRespuestaDetalle>> listarRespuestas(@PageableDefault(size = 2) Pageable paginacion) {
        return ResponseEntity.ok(respuestaService.listarRespuestas(paginacion));
    }

    @PutMapping
    public ResponseEntity<DtoRespuestaDetalle> actualizarRespuesta(@RequestBody @Valid DtoRespuesta dto) {
        return ResponseEntity.ok(respuestaService.actualizarRespuesta(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRespuesta(@PathVariable Long id) {
        respuestaService.eliminarRespuesta(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DtoRespuestaDetalle> datosRespuesta(@PathVariable Long id) {
        return ResponseEntity.ok(respuestaService.datosRespuesta(id));
    }
}

