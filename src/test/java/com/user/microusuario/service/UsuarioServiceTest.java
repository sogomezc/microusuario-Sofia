package com.user.microusuario.service;

import com.user.microusuario.model.Usuario;
import com.user.microusuario.model.dto.UsuarioDto;
import com.user.microusuario.model.entity.UsuarioEntity;
import com.user.microusuario.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.times;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.ArrayList;
import java.util.List;  

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

     @Test
    public void getUsuarios(){
        List<UsuarioEntity> usuarios = new ArrayList<>();
        UsuarioEntity u1 = new UsuarioEntity();
        u1.setIdUsuario(1);
        u1.setNombre("Sofia");

        usuarios.add(u1);//Se agrega
        usuarios.add(u1);//Se agrega
        usuarios.add(u1);//Se agrega

        Mockito.when(usuarioRepository.findAll()).thenReturn(usuarios);

        List<UsuarioEntity> resultado = usuarioService.getUsuarios();

        assertThat(resultado).isNotNull();
        assertThat(resultado.size()).isEqualTo(3);
        Mockito.verify(usuarioRepository, times(1)).findAll();
    }

    @Test
    public void obtenerUsuarioDTO_OK(){
        UsuarioEntity u1 = new UsuarioEntity();
        u1.setIdUsuario(1);
        u1.setNombre("Sofia");
        u1.setApellidos("Lopez");
        u1.setCorreo("sofia@correo.com");

        Mockito.when(usuarioRepository.findByIdUsuario(Mockito.any(Integer.class))).thenReturn(u1);

        UsuarioDto resultado = usuarioService.obtenerUsuarioDto(1);

        assertThat(resultado).isNotNull();
        assertThat(u1.getCorreo()).isEqualTo("sofia@correo.com");
        Mockito.verify(usuarioRepository, times(1)).findByIdUsuario(Mockito.any(Integer.class));
    }

    @Test
    public void obtenerUsuarioDTO_Exception(){

        Mockito.when(usuarioRepository.findByIdUsuario(Mockito.any(Integer.class))).thenThrow(new RuntimeException("DB Error"));

        UsuarioDto resultado = usuarioService.obtenerUsuarioDto(1);

        assertThat(resultado).isNull();
    }

    @Test
    public void modificarUsuario_OK(){
        UsuarioEntity u1 = new UsuarioEntity();
        u1.setIdUsuario(1);
        u1.setNombre("Sofia");
        u1.setApellidos("Lopez");
        u1.setCorreo("sofia@correo.com");

        Usuario userMod = new Usuario();
        userMod.setIdUsuario(1);
        userMod.setNombre("Ana");
        userMod.setApellidos("Lopez");
        userMod.setCorreo("ana@correo.com");

        UsuarioEntity resultadoEsperado = new UsuarioEntity();
        resultadoEsperado.setIdUsuario(1);
        resultadoEsperado.setNombre("Ana");
        resultadoEsperado.setApellidos("Lopez");
        resultadoEsperado.setCorreo("ana@correo.com");

        Mockito.when(usuarioRepository.findByIdUsuario(Mockito.any(Integer.class))).thenReturn(u1);
        Mockito.when(usuarioRepository.save(Mockito.any(UsuarioEntity.class))).thenReturn(resultadoEsperado);

        String resultado = usuarioService.modificarUsuario(userMod);

        assertThat(resultado).isNotNull();
        assertThat(resultado).isEqualTo("Usuario modificado correctamente");
        Mockito.verify(usuarioRepository, times(1)).findByIdUsuario(Mockito.any(Integer.class));
        Mockito.verify(usuarioRepository, times(1)).save(Mockito.any(UsuarioEntity.class));

    }

    @Test
    public void modificarUsuario_Null(){

        Usuario userMod = new Usuario();
        userMod.setIdUsuario(1);
        userMod.setNombre("Ana");
        userMod.setApellidos("Lopez");
        userMod.setCorreo("ana@correo.com");

        Mockito.when(usuarioRepository.findByIdUsuario(Mockito.any(Integer.class))).thenReturn(null);

        String resultado = usuarioService.modificarUsuario(userMod);

        assertThat(resultado).isNotNull();
        assertThat(resultado).isEqualTo("Usuario no encontrado para modificar");
        Mockito.verify(usuarioRepository, times(1)).findByIdUsuario(Mockito.any(Integer.class));
    }

    @Test
    public void modificarUsuario_Exception(){

        Usuario userMod = new Usuario();
        userMod.setIdUsuario(1);
        userMod.setNombre("Ana");
        userMod.setApellidos("Lopez");
        userMod.setCorreo("ana@correo.com");

        Mockito.when(usuarioRepository.findByIdUsuario(Mockito.any(Integer.class))).thenThrow(new RuntimeException("DB Error"));

        String resultado = usuarioService.modificarUsuario(userMod);

        assertThat(resultado).isNotNull();
        assertThat(resultado).contains("Error al modificar usuario: ");
        Mockito.verify(usuarioRepository, times(1)).findByIdUsuario(Mockito.any(Integer.class));

    }

    @Test
    public void eliminarUsuarioId_OK(){

        Mockito.when(usuarioRepository.existsById(Mockito.any(Integer.class))).thenReturn(true);
        Mockito.doNothing().when(usuarioRepository).deleteById(Mockito.any(Integer.class));

        String resultado = usuarioService.eliminarUsuarioPorId(1);

        assertThat(resultado).isNotNull();
        assertThat(resultado).isEqualTo("Usuario eliminado correctamente por ID");
    }

    @Test
    public void eliminarUsuarioId_False(){

        Mockito.when(usuarioRepository.existsById(Mockito.any(Integer.class))).thenReturn(false);

        String resultado = usuarioService.eliminarUsuarioPorId(1);

        assertThat(resultado).isNotNull();
        assertThat(resultado).isEqualTo("Usuario no encontrado por ID");
    }

    @Test
    public void eliminarUsuarioId_Exception(){

        Mockito.when(usuarioRepository.existsById(Mockito.any(Integer.class))).thenThrow(new RuntimeException("DB Error"));

        String resultado = usuarioService.eliminarUsuarioPorId(1);

        assertThat(resultado).isNotNull();
        assertThat(resultado).contains("Error al eliminar usuario por ID: ");
    }
}




