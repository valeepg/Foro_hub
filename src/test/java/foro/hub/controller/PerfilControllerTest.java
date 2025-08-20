package foro.hub.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import foro.hub.domain.perfil.DtoPerfil;
import foro.hub.domain.perfil.DtoPerfilDetalle;
import foro.hub.domain.perfil.IPerfilService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PerfilController.class)
class PerfilControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private IPerfilService perfilService;

    @Test
    @DisplayName("Debería retornar 201 Created al registrar un perfil con datos válidos")
    @WithMockUser
    void registrarPerfil_Exitoso() throws Exception {
        // Given
        DtoPerfil dtoEntrada = new DtoPerfil(null, "Estudiante");
        DtoPerfilDetalle dtoSalida = new DtoPerfilDetalle(1L, "Estudiante", true);
        when(perfilService.registrarPerfil(any(DtoPerfil.class))).thenReturn(dtoSalida);

        // When & Then
        mockMvc.perform(post("/perfiles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoEntrada)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/perfiles/1"))
                .andExpect(jsonPath("$.nombre").value("Estudiante"));
    }

    @Test
    @DisplayName("Debería retornar 403 Forbidden si el usuario no está autenticado")
    void registrarPerfil_Falla_NoAutenticado() throws Exception {
        DtoPerfil dtoEntrada = new DtoPerfil(null, "Estudiante");
        mockMvc.perform(post("/perfiles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoEntrada)))
                .andExpect(status().isForbidden());
    }
}