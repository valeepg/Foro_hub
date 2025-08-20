package foro.hub.domain.usuario;


import foro.hub.domain.perfil.Perfil;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.List;

@Table(name = "usuarios")
@Entity(name = "Usuario")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario  implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Column(name = "email", unique = true)
    private String email;

    private String contrasena;

    /*@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "usuario_perfil",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "perfil_id")
    )
    private Set<Perfil> perfiles = new HashSet<>();
    */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "perfil_id")
    private Perfil perfil;

    @Column(nullable = false)
    private Boolean activo;

    public void desactivarUsuario() {
        this.activo = false;
    }

    public Usuario(DtoUsuario dto, Perfil elPerfil, PasswordEncoder passwordEncoder) {
        asignaDatosUsuario(dto, elPerfil, passwordEncoder);
        this.activo = true;
    }

    private String codifica(String clave, PasswordEncoder pwdEncoder) {
        // Encriptar el password antes de guardar
        return pwdEncoder.encode(clave);
    }

    public void actualizarUsuario(DtoUsuario dto, Perfil elPerfil, PasswordEncoder passwordEncoder) {
        asignaDatosUsuario(dto, elPerfil, passwordEncoder);
    }

    private void asignaDatosUsuario(DtoUsuario dto, Perfil elPerfil, PasswordEncoder passwordEncoder) {
        this.nombre = dto.nombre();
        this.email = dto.email();
        this.contrasena = this.codifica(dto.contrasena(), passwordEncoder);
        this.perfil = elPerfil;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    public Long getId() {
        return id;
    }

    @Override
    public String getPassword() {
        return contrasena;
    }

    @Override
    public String getUsername() {
        return email;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public String getContrasena() {
        return contrasena;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}