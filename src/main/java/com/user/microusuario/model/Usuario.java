package com.user.microusuario.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data // Getter and Stters 
@AllArgsConstructor //Paramatros con constructor
@NoArgsConstructor //Parametros sin constructor

public class Usuario {

    private int idUsuario;
    private String nombre;
    private String apellidos;
    private String correo;
    private String contrasena;



}
//Este es el modelo de la entidad usuario, aqui se declaran los atributos que va a tener el usuario
