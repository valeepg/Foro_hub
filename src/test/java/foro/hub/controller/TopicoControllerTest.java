package foro.hub.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import foro.hub.domain.topico.DtoTopico;
import foro.hub.domain.topico.DtoTopicoDetalle;
import foro.hub.domain.topico.EstadosTopico;
import foro.hub.domain.topico.ITopicoService;
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

@WebMvcTest(TopicoController.class)
class TopicoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private ITopicoService topicoService;

    @Test
    @DisplayName("Debería retornar 201 Created al registrar un tópico con datos válidos")
    @WithMockUser // Simula un usuario autenticado para pasar el filtro de seguridad
    void registrarTopico_Exitoso() throws Exception {
        // Given
        DtoTopico dtoEntrada = new DtoTopico(null, "Título de prueba", "Mensaje de prueba", 1L, 1L, EstadosTopico.NO_RESPONDIDO);
        DtoTopicoDetalle dtoSalida = new DtoTopicoDetalle(1L, "Título de prueba", "Mensaje de prueba", 1L, 1L, true);

        when(topicoService.registrarTopico(any(DtoTopico.class))).thenReturn(dtoSalida);

        // When & Then
        mockMvc.perform(post("/topicos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoEntrada)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", "http://localhost/topicos/1"))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.titulo").value("Título de prueba"));
    }

    @Test
    @DisplayName("Debería retornar 403 Forbidden si el usuario no está autenticado")
    void registrarTopico_Falla_NoAutenticado() throws Exception {
        // Given
        DtoTopico dtoEntrada = new DtoTopico(null, "Título", "Mensaje", 1L, 1L, null);

        // When & Then
        // No usamos @WithMockUser, por lo que la petición será anónima y rechazada
        mockMvc.perform(post("/topicos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoEntrada)))
                .andExpect(status().isForbidden());
    }
}