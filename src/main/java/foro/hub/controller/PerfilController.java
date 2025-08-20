package foro.hub.controller;

import foro.hub.domain.perfil.*;
import foro.hub.domain.perfil.DtoPerfil;
import foro.hub.domain.perfil.DtoPerfilDetalle;
import foro.hub.domain.perfil.IPerfilService;
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
@RequestMapping("/perfiles")
@RequiredArgsConstructor
public class PerfilController {

    @Autowired
    private IPerfilService perfilService;

    @PostMapping
    public ResponseEntity<DtoPerfilDetalle> registrarPerfil(@RequestBody @Valid DtoPerfil dto, UriComponentsBuilder uriBuilder) {
        DtoPerfilDetalle dtoDetalle = perfilService.registrarPerfil(dto);
        var uri = uriBuilder.path("/perfiles/{id}").buildAndExpand(dtoDetalle.id()).toUri();
        return ResponseEntity.created(uri).body(dtoDetalle);
    }

    @GetMapping
    public ResponseEntity<Page<DtoPerfilDetalle>> listarPerfiles(@PageableDefault(size = 2) Pageable paginacion) {
        return ResponseEntity.ok(perfilService.listarPerfiles(paginacion));
    }

    @PutMapping
    public ResponseEntity<DtoPerfilDetalle> actualizarPerfil(@RequestBody @Valid DtoPerfil dto) {
        return ResponseEntity.ok(perfilService.actualizarPerfil(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPerfil(@PathVariable Long id) {
        perfilService.eliminarPerfil(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DtoPerfilDetalle> datosPerfil(@PathVariable Long id) {
        return ResponseEntity.ok(perfilService.datosPerfil(id));
    }
}

