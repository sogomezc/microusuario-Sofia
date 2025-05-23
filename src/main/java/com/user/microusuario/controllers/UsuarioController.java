package com.user.microusuario.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.user.microusuario.model.Usuario;
import com.user.microusuario.model.dto.UsuarioDto;
import com.user.microusuario.model.entity.UsuarioEntity;
import com.user.microusuario.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/usuario") // Ruta base
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Operation(summary = "Entrega una lista de los usuarios")
    @GetMapping("/Listarusuarios")
    public List<UsuarioEntity> traerUsuarios() {
        return usuarioService.getUsuarios();
    }

    @Operation(summary = "Crear usuario")
    @PostMapping("/crearUsuario")
    public ResponseEntity<String> obtenerUsuario(@RequestBody Usuario usuario) {
        return ResponseEntity.ok(usuarioService.crearUsuario(usuario));
    }

    /*
    @Operation(summary = "Obtener usuario por correo")
    @GetMapping("/obtenerUsuario/{correo}")
    public ResponseEntity<Usuario> obternerUsuario(@PathVariable String correo) {
        Usuario usuario = usuarioService.obternerUsuario(correo);
        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    */

    @Operation(summary = "Obtener usuario por id")
    @GetMapping("/obtenerUsuarioId/{id}")
    public ResponseEntity<UsuarioDto> obtenerUsuarioDto(@PathVariable int id) {
        UsuarioDto dto = usuarioService.obtenerUsuarioDto(id);
        if (dto != null) {
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Modificar usuario", description = "Modificar un usuario existente")
    @PutMapping("/modificarusuario")
    public ResponseEntity<String> modificarUsuario(@RequestBody Usuario usuario) {
        return ResponseEntity.ok(usuarioService.modificarUsuario(usuario));
    }

    @Operation(summary = "Eliminar usuario por id")
    @DeleteMapping("/eliminarporid/{id}")
    public ResponseEntity<String> eliminarPorId(@PathVariable int id) {
        return ResponseEntity.ok(usuarioService.eliminarUsuarioPorId(id));
    }

    

} 