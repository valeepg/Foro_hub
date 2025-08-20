package foro.hub.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import foro.hub.domain.perfil.DtoPerfil;
import foro.hub.domain.perfil.Perfil;
import foro.hub.domain.usuario.DtoAutenticacionUsuario;
import foro.hub.domain.usuario.DtoUsuario;
import foro.hub.domain.usuario.Usuario;
import foro.hub.domain.usuario.UsuarioRepository;
import foro.hub.infra.security.TokenService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AutenticacionController.class)
class AutenticacionControllerTest {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AuthenticationManager authenticationManager;

    @MockitoBean
    private TokenService tokenService;

    @MockitoBean
    private UsuarioRepository usuarioRepository;

    @Test
    @DisplayName("Debería retornar 200 OK con un token JWT para credenciales válidas")
    void autenticarUsuario_Exitoso() throws Exception {
        // Given
        DtoAutenticacionUsuario dto = new DtoAutenticacionUsuario("test@example.com", "123456");
        Usuario usuario = crearUsuarioDePrueba();
        Authentication auth = new UsernamePasswordAuthenticationToken(usuario, null);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(auth);
        when(tokenService.generarToken(any(Usuario.class))).thenReturn("mock.jwt.token");

        // When & Then
        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.jwtToken").value("mock.jwt.token"));
    }

    @Test
    @DisplayName("Debería retornar 400 Bad Request para una solicitud con datos de validación incorrectos (ej. contraseña en blanco)")
    void autenticarUsuario_Falla_BadRequest() throws Exception {
        // Given
        DtoAutenticacionUsuario dtoConError = new DtoAutenticacionUsuario("test@example.com", "");

        // When & Then
        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoConError)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Debería retornar 403 Forbidden para credenciales inválidas")
    void autenticarUsuario_Falla_CredencialesInvalidas() throws Exception {
        // Given
        DtoAutenticacionUsuario dto = new DtoAutenticacionUsuario("test@example.com", "wrongpassword");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Credenciales inválidas"));

        // When & Then
        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isForbidden());
    }

    private Usuario crearUsuarioDePrueba() {
        return new Usuario(
                new DtoUsuario(1L, "juan", "test@example.com", "123456", 1L),
                new Perfil(new DtoPerfil(1L, "Estudiante")),
                this.passwordEncoder
        );
    }
}