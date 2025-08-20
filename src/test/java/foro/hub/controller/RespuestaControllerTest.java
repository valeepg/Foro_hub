package foro.hub.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import foro.hub.domain.respuesta.DtoRespuesta;
import foro.hub.domain.respuesta.DtoRespuestaDetalle;
import foro.hub.domain.respuesta.IRespuestaService;
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

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RespuestaController.class)
class RespuestaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private IRespuestaService respuestaService;

    private DtoRespuesta dtoRespuesta;
    private DtoRespuestaDetalle dtoRespuestaDetalle;

    @BeforeEach
    void setUp() {
        dtoRespuesta = new DtoRespuesta(1L, "Mi respuesta al tópico", 1L, 1L, LocalDateTime.now(), false);
        dtoRespuestaDetalle = new DtoRespuestaDetalle(1L, "Mi respuesta al tópico", 1L, 1L, LocalDateTime.now(), false, true);
    }

    @Test
    @DisplayName("Debería retornar 201 Created al registrar una respuesta con datos válidos")
    @WithMockUser
    void registrarRespuesta_Exitoso() throws Exception {
        // Given
        when(respuestaService.registrarRespuesta(any(DtoRespuesta.class))).thenReturn(dtoRespuestaDetalle);

        // When & Then
        mockMvc.perform(post("/respuestas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoRespuesta)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/respuestas/1"))
                .andExpect(jsonPath("$.mensaje").value("Mi respuesta al tópico"));
    }

    @Test
    @DisplayName("Debería retornar 403 Forbidden si el usuario no está autenticado")
    void registrarRespuesta_Falla_NoAutenticado() throws Exception {
        mockMvc.perform(post("/respuestas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoRespuesta)))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Debería retornar 200 OK con una página de respuestas")
    @WithMockUser
    void listarRespuestas_Exitoso() throws Exception {
        // Given
        Page<DtoRespuestaDetalle> page = new PageImpl<>(List.of(dtoRespuestaDetalle));
        when(respuestaService.listarRespuestas(any(Pageable.class))).thenReturn(page);

        // When & Then
        mockMvc.perform(get("/respuestas")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].mensaje").value("Mi respuesta al tópico"));
    }

    @Test
    @DisplayName("Debería retornar 200 OK al actualizar una respuesta")
    @WithMockUser
    void actualizarRespuesta_Exitoso() throws Exception {
        // Given
        when(respuestaService.actualizarRespuesta(any(DtoRespuesta.class))).thenReturn(dtoRespuestaDetalle);

        // When & Then
        mockMvc.perform(put("/respuestas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoRespuesta)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensaje").value("Mi respuesta al tópico"));
    }

    @Test
    @DisplayName("Debería retornar 204 No Content al eliminar una respuesta")
    @WithMockUser
    void eliminarRespuesta_Exitoso() throws Exception {
        // Given
        doNothing().when(respuestaService).eliminarRespuesta(1L);

        // When & Then
        mockMvc.perform(delete("/respuestas/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Debería retornar 200 OK con los datos de una respuesta")
    @WithMockUser
    void datosRespuesta_Exitoso() throws Exception {
        // Given
        when(respuestaService.datosRespuesta(1L)).thenReturn(dtoRespuestaDetalle);

        // When & Then
        mockMvc.perform(get("/respuestas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }
}