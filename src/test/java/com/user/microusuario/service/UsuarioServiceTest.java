package com.user.microusuario.service;

import com.user.microusuario.model.Usuario;
import com.user.microusuario.model.entity.UsuarioEntity;
import com.user.microusuario.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;  

@ExtendWith(MockitoExtension.class) // Extiende la clase de prueba con Mockito para usar anotaciones como @Mock y @InjectMocks
public class UsuarioServiceTest {   

    @Mock   // Crea un mock del repositorio de usuarios
    private UsuarioRepository usuarioRepository;

    @InjectMocks    // Inyecta el mock del repositorio en la instancia de UsuarioService
    private UsuarioService usuarioService;


    @Test
    public void crearUsuario_CorreoNoExiste(){  // Prueba para crear un usuario con correonqu no existe

        Usuario user = new Usuario();
        user.setNombre("Carlos");
        user.setApellidos("Perez");
        user.setCorreo("carlos@correo.com");
        user.setContrasena("1234");

        Mockito.when(usuarioRepository.existsByCorreo(Mockito.any(String.class))).thenReturn(false);    // Simula que el correo no existe en la BD invoca al save
        Mockito.when(usuarioRepository.save(Mockito.any(UsuarioEntity.class))).thenAnswer(i -> { // Simula q guarda el usuario en la BD
            UsuarioEntity entity = i.getArgument(0);    // Obtiene el argumento pasado del método save   
            entity.setIdUsuario(1);
            return entity;
        });

        String respuesta = usuarioService.crearUsuario(user);

        assertThat(respuesta).isEqualTo("Usuario creado correctamente");    // Verifica que la respuesta sea la que esperamos
        assertThat(user.getIdUsuario()).isEqualTo(1);  // Verifica que el ID del usuario se le asignó correctamente
        assertThat(user.getContrasena()).isEqualTo("1234"); // conontraseña no se haya modificado
        Mockito.verify(usuarioRepository,Mockito.times(1)).existsByCorreo(Mockito.any(String.class));
        Mockito.verify(usuarioRepository,Mockito.times(1)).save(Mockito.any(UsuarioEntity.class));
    }

    @Test
    public void crearUsuario_CorreoExiste(){    

        Usuario user = new Usuario();   // Crea un nuevo usuario
        user.setNombre("Carlos");
        user.setApellidos("Perez");
        user.setCorreo("carlos@correo.com");    
        user.setContrasena("1234");

        Mockito.when(usuarioRepository.existsByCorreo(Mockito.any(String.class))).thenReturn(true); // Simula que el correo ya existe en la BD

        String respuesta = usuarioService.crearUsuario(user);

        assertThat(respuesta).isEqualTo("El correo ya existe");   // Verifica que la respuesta sea la que esperamos
        Mockito.verify(usuarioRepository,Mockito.times(1)).existsByCorreo(Mockito.any(String.class));   // Verifica que se haya llamado al método existsByCorreo una vez

    }

    @Test
    public void crearUsuario_Excepcion(){   // Prueba para crear un usuario con un correo que no existe, y que simule una excepción al guardar en la BD

        Usuario user = new Usuario();
        user.setNombre("Carlos");
        user.setApellidos("Perez");
        user.setCorreo("carlos@correo.com");
        user.setContrasena("1234");

        Mockito.when(usuarioRepository.existsByCorreo(Mockito.any(String.class))).thenThrow(new RuntimeException("DB Error"));  // Simula que ocurre una excepción al verificar si el correo existe emla BD

        String respuesta = usuarioService.crearUsuario(user);   // Llama al método crearUsuario del service

        assertThat(respuesta).isEqualTo("error al crear usuario");  // Verifica que la respuesta sea la que esperamos

    }
}
