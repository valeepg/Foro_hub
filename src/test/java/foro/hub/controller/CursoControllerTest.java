package foro.hub.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import foro.hub.domain.curso.DtoCurso;
import foro.hub.domain.curso.DtoCursoDetalle;
import foro.hub.domain.curso.ICursoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CursoController.class)
@AutoConfigureJsonTesters
class CursoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ICursoService cursoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JacksonTester<DtoCursoDetalle> dtoCursoDetalleJacksonTester;

    @Test
    @DisplayName("Debería retornar 201 Created cuando se registra un curso con datos válidos")
    @WithMockUser
    void registrarCurso_escenarioExitoso() throws Exception {
        // ARRANGE
        var datosEntrada = new DtoCurso(
                null,
                "Curso de Programacion",
                "Programacion"
        );
        var datosSalidaEsperados = new DtoCursoDetalle(
                101L,
                "Curso de Programacion",
                "Programacion", true
        );

        when(cursoService.registrarCurso(any(DtoCurso.class))).thenReturn(datosSalidaEsperados);

        // ACT & ASSERT
        mockMvc.perform(post("/cursos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(datosEntrada)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/cursos/" + datosSalidaEsperados.id()))
                .andExpect(jsonPath("$.id").value(datosSalidaEsperados.id()))
                .andExpect(jsonPath("$.nombre").value(datosSalidaEsperados.nombre()))
                .andExpect(jsonPath("$.categoria").value(datosSalidaEsperados.categoria()));
    }

    @Test
    @DisplayName("Debería retornar 200 OK con una lista paginada de cursos")
    @WithMockUser
    void listarCursos_debeRetornar200() throws Exception {
        // ARRANGE
        var cursoDetalle = new DtoCursoDetalle(1L, "Curso de Java", "Programacion", true);
        Page<DtoCursoDetalle> paginaDeCursos = new PageImpl<>(Collections.singletonList(cursoDetalle));

        when(cursoService.listarCursos(any(Pageable.class))).thenReturn(paginaDeCursos);

        // ACT & ASSERT
        mockMvc.perform(get("/cursos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1L))
                .andExpect(jsonPath("$.content[0].nombre").value("Curso de Java"));
    }

    @Test
    @DisplayName("Debería retornar 200 OK con los detalles de un curso específico")
    @WithMockUser
    void datosCurso_debeRetornar200() throws Exception {
        // ARRANGE
        Long idCurso = 1L;
        var cursoDetalle = new DtoCursoDetalle(idCurso, "Curso de SQL", "Bases de Datos", true);

        when(cursoService.datosCurso(idCurso)).thenReturn(cursoDetalle);

        // ACT & ASSERT
        mockMvc.perform(get("/cursos/" + idCurso)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(dtoCursoDetalleJacksonTester.write(cursoDetalle).getJson()));
    }

    @Test
    @DisplayName("Debería retornar 200 OK al actualizar un curso")
    @WithMockUser
    void actualizarCurso_debeRetornar200() throws Exception {
        // ARRANGE
        var datosActualizacion = new DtoCurso(1L, "Curso Avanzado de Spring", "Programacion");
        var cursoActualizado = new DtoCursoDetalle(1L, "Curso Avanzado de Spring", "Programacion", true);

        when(cursoService.actualizarCurso(any(DtoCurso.class))).thenReturn(cursoActualizado);

        // ACT & ASSERT
        mockMvc.perform(put("/cursos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(datosActualizacion)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(cursoActualizado.id()))
                .andExpect(jsonPath("$.nombre").value(cursoActualizado.nombre()));
    }

    @Test
    @DisplayName("Debería retornar 204 No Content al eliminar un curso")
    @WithMockUser
    void eliminarCurso_debeRetornar204() throws Exception {
        // ARRANGE
        Long idCurso = 1L;

        // ACT & ASSERT
        mockMvc.perform(delete("/cursos/" + idCurso))
                .andExpect(status().isNoContent());
    }
}