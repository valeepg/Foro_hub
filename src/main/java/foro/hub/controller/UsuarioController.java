package foro.hub.controller;

import foro.hub.domain.usuario.*;
import foro.hub.domain.usuario.DtoUsuario;
import foro.hub.domain.usuario.DtoUsuarioDetalle;
import foro.hub.domain.usuario.IUsuarioService;
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
@RequestMapping("/usuarios")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearer-key")
public class UsuarioController {

    @Autowired
    private IUsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<DtoUsuarioDetalle> registrarUsuario(@RequestBody @Valid DtoUsuario dto, UriComponentsBuilder uriBuilder) {
        DtoUsuarioDetalle dtoDetalle = usuarioService.registrarUsuario(dto);
        var uri = uriBuilder.path("/usuarios/{id}").buildAndExpand(dtoDetalle.id()).toUri();
        return ResponseEntity.created(uri).body(dtoDetalle);
    }

    @GetMapping
    public ResponseEntity<Page<DtoUsuarioDetalle>> listarUsuarios(@PageableDefault(size = 2) Pageable paginacion) {
        return ResponseEntity.ok(usuarioService.listarUsuarios(paginacion));
    }

    @PutMapping
    public ResponseEntity<DtoUsuarioDetalle> actualizarUsuario(@RequestBody @Valid DtoUsuario dto) {
        return ResponseEntity.ok(usuarioService.actualizarUsuario(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DtoUsuarioDetalle> datosUsuario(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.datosUsuario(id));
    }
}