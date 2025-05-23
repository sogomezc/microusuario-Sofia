package com.user.microusuario.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UsuarioDto {
    
  private String nombre;
  private String apellidos;
  private String correo;

}
