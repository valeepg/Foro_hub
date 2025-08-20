package foro.hub.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import foro.hub.domain.usuario.DtoUsuario;
import foro.hub.domain.usuario.DtoUsuarioDetalle;
import foro.hub.domain.usuario.IUsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioController.class)
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private IUsuarioService usuarioService;

    private DtoUsuario dtoUsuario;
    private DtoUsuarioDetalle dtoUsuarioDetalle;

    @BeforeEach
    void setUp() {
        dtoUsuario = new DtoUsuario(1L, "Ana Torres", "ana.torres@example.com", "password123", 1L);
        dtoUsuarioDetalle = new DtoUsuarioDetalle(1L, "Ana Torres", "ana.torres@example.com", true);
    }

    @Test
    @DisplayName("Debería retornar 201 Created al registrar un usuario con datos válidos")
    @WithMockUser
    void registrarUsuario_Exitoso() throws Exception {
        // Given
        when(usuarioService.registrarUsuario(any(DtoUsuario.class))).thenReturn(dtoUsuarioDetalle);

        // When & Then
        mockMvc.perform(post("/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoUsuario)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/usuarios/1"))
                .andExpect(jsonPath("$.nombre").value("Ana Torres"));
    }

    @Test
    @DisplayName("Debería retornar 403 Forbidden si el usuario no está autenticado")
    void registrarUsuario_Falla_NoAutenticado() throws Exception {
        mockMvc.perform(post("/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoUsuario)))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Debería retornar 200 OK con una página de usuarios")
    @WithMockUser
    void listarUsuarios_Exitoso() throws Exception {
        // Given
        Page<DtoUsuarioDetalle> page = new PageImpl<>(List.of(dtoUsuarioDetalle));
        when(usuarioService.listarUsuarios(any(Pageable.class))).thenReturn(page);

        // When & Then
        mockMvc.perform(get("/usuarios")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].nombre").value("Ana Torres"));
    }

    @Test
    @DisplayName("Debería retornar 200 OK al actualizar un usuario")
    @WithMockUser
    void actualizarUsuario_Exitoso() throws Exception {
        // Given
        when(usuarioService.actualizarUsuario(any(DtoUsuario.class))).thenReturn(dtoUsuarioDetalle);

        // When & Then
        mockMvc.perform(put("/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoUsuario)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Ana Torres"));
    }

    @Test
    @DisplayName("Debería retornar 204 No Content al eliminar un usuario")
    @WithMockUser
    void eliminarUsuario_Exitoso() throws Exception {
        // Given
        doNothing().when(usuarioService).eliminarUsuario(1L);

        // When & Then
        mockMvc.perform(delete("/usuarios/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Debería retornar 200 OK con los datos de un usuario")
    @WithMockUser
    void datosUsuario_Exitoso() throws Exception {
        // Given
        when(usuarioService.datosUsuario(1L)).thenReturn(dtoUsuarioDetalle);

        // When & Then
        mockMvc.perform(get("/usuarios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }
}