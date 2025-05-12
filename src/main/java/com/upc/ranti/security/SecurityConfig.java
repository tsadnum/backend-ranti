package com.upc.ranti.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.GetMapping;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    private static final String[] AUTH_WHITELIST = {
            "/api/usuarios/login/**",
            "/api/usuarios/register/**",
            "/api/usuarios/existe/**",
            "/error",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/webjars/**"
    };


    // Bean para el AuthenticationManager
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // Bean para PasswordEncoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Configuración de seguridad
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(withDefaults());

        // Definición de autorización para diferentes endpoints y roles

        http.authorizeRequests()
                // Rutas públicas
                .requestMatchers("/api/usuario/grabar", "/api/usuario/login").permitAll()
                .requestMatchers(AUTH_WHITELIST).permitAll()

                // Endpoints de Artículos
                .requestMatchers(HttpMethod.POST, "/api/articulos/grabar").hasAnyAuthority("ROL_USUARIO")
                .requestMatchers(HttpMethod.GET, "/api/articulos/disponibles").hasAnyAuthority("ROL_USUARIO")
                .requestMatchers(HttpMethod.GET, "/api/articulos/usuario/{usuarioId}/publicos").hasAuthority("ROL_USUARIO")
                .requestMatchers(HttpMethod.PUT, "/api/articulos/{id}/visibilidad").hasAuthority("ROL_USUARIO")
                .requestMatchers(HttpMethod.GET, "/api/articulos/filtrar/etiquetas").hasAuthority("ROL_USUARIO")
                .requestMatchers(HttpMethod.PUT, "/api/articulos/{id}/marcar-inapropiado").hasAuthority("ROL_ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/articulos/eliminar/inapropiados").hasAuthority("ROL_ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/articulos/estadisticas/estado").hasAuthority("ROL_ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/articulos/{articuloId}/estado").hasAuthority("ROL_ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/articulos/usuario/{usuarioId}").hasAuthority("ROL_ADMIN")

                // Endpoints de Etiquetas
                .requestMatchers(HttpMethod.GET, "/api/etiquetas/filtrar/etiquetas").hasAuthority("ROL_USUARIO")
                .requestMatchers(HttpMethod.GET, "/api/etiquetas/top-etiquetas").hasAuthority("ROL_USUARIO")

                // Endpoints de Imagenes
                .requestMatchers(HttpMethod.POST, "/api/imagen/grabar").hasAuthority("ROL_USUARIO")

                // Endpoints de Posts
                .requestMatchers(HttpMethod.POST, "/api/post/grabar").hasAuthority("ROL_USUARIO")

                // Endpoints de Usuarios
                .requestMatchers(HttpMethod.GET, "/api/usuario/report").hasAuthority("ROL_USUARIO")
                .requestMatchers(HttpMethod.PUT, "/api/usuario").hasAnyAuthority("ROL_USUARIO", "ROL_ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/usuario/eliminar").hasAuthority("ROL_USUARIO")
                .requestMatchers(HttpMethod.POST, "/api/usuario/{usuarioId}/roles/{id}").hasAuthority("ROL_ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/usuario/{usuarioId}/roles/{id}").hasAuthority("ROL_ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/usuario/suspender/{id}").hasAuthority("ROL_ADMIN")

                // Endpoints de Roles
                .requestMatchers(HttpMethod.POST, "/api/roles/grabar").hasAuthority("ROL_ADMIN")

                // Endpoints de Valoraciones

                .requestMatchers(HttpMethod.POST, "/api/valoracion/grabar").hasAuthority("ROL_USUARIO")
                .requestMatchers(HttpMethod.GET, "/api/valoracion/usuario/{usuarioId}").hasAuthority("ROL_USUARIO")
                .requestMatchers(HttpMethod.GET, "/api/valoracion/usuario/{usuarioId}/desc").hasAuthority("ROL_USUARIO")
                .requestMatchers(HttpMethod.GET, "/api/valoracion/usuario/articulos/valoracionMinima").hasAuthority("ROL_USUARIO")
                .requestMatchers(HttpMethod.GET, "/api/valoracion/usuario/articulos/valoracionMaxima").hasAuthority("ROL_USUARIO")

                // Endpoints de Solicitud-Articulo
                .requestMatchers(HttpMethod.POST, "/api/solicitudes/grabar").hasAuthority("ROL_USUARIO")
                .requestMatchers(HttpMethod.GET, "/api/solicitudes/recibidas/{usuarioSolicitadoId}").hasAuthority("ROL_USUARIO")
                .requestMatchers(HttpMethod.GET, "/api/solicitudes/enviadas/{usuarioSolicitanteId}").hasAuthority("ROL_USUARIO")
                .requestMatchers(HttpMethod.GET, "/api/solicitudes/propuestas/{usuarioSolicitadoId}/{articuloOfrecidoId}").hasAuthority("ROL_USUARIO")
                .requestMatchers(HttpMethod.GET, "/api/solicitudes/propuestas-enviadas").hasAuthority("ROL_USUARIO")
                .requestMatchers(HttpMethod.GET, "/api/solicitudes/totalPorCiudad").hasAuthority("ROL_ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/solicitudes/{id}/{decision}").hasAuthority("ROL_USUARIO")
                .requestMatchers(HttpMethod.PUT, "/api/solicitudes/cancelar/{solicitudId}").hasAuthority("ROL_USUARIO")



                .requestMatchers("/error").permitAll()
                .anyRequest().authenticated();

        return http.build();
    }
}
