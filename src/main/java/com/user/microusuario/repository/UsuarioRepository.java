package com.user.microusuario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.user.microusuario.model.entity.UsuarioEntity; //Importamos la entidad UsuarioEntity

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity,Integer>{//Este repositorio va abuscar la informacion de acuerdo a la entidad modelo (UsuarioEntity))
    //Como es una interfaz voy a poder construir metodos

    UsuarioEntity findByCorreo(String correo); //consulta sql para poder buscar en la tabla de usuarios por un filtro del correo
    Boolean existsByCorreo(String correo); // primero verificamos que el ususario exista
    void deleteByCorreo(String correo); //Borrar por correo 
    UsuarioEntity findByIdUsuario(int id); //Buscar por id
}