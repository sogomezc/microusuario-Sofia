package com.user.microusuario.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.user.microusuario.model.Usuario;
import com.user.microusuario.model.dto.UsuarioDto;
import com.user.microusuario.model.entity.UsuarioEntity;
import com.user.microusuario.repository.UsuarioRepository;

@Service
public class UsuarioService {
    @Autowired // interactua con el usuarioRepository repository
    private UsuarioRepository ususariorepository;


    public List<UsuarioEntity> getUsuarios() {
        return ususariorepository.findAll();
    }

    public String crearUsuario(Usuario user) {

        try {
            Boolean estado = ususariorepository.existsByCorreo(user.getCorreo());// Verificamos si el correo ya existe
            if (!estado) {// Si el correo no existe
                // Creamos un nuevo usuario
                // y lo guardamos en la base de datos
                // UsuarioEntity representa la base de datos
                UsuarioEntity usuarioNuevo = new UsuarioEntity();
                usuarioNuevo.setNombre(user.getNombre());
                usuarioNuevo.setApellidos(user.getApellidos());
                usuarioNuevo.setCorreo(user.getCorreo());
                usuarioNuevo.setContrasena(user.getContrasena());
                
                // Guardamos el usuario en la base de datos
                // y le asignamos un id
                UsuarioEntity UsuarioSave = ususariorepository.save(usuarioNuevo);
                user.setIdUsuario(UsuarioSave.getIdUsuario());// Asignamos el id del usuario guardado al usuario que estamos creando
                
                //ususariorepository.save(usuarioNuevo); esto era lo que habia antes
                return "Usuario creado correctamente";

            }
            return "El correo ya existe";
        } catch (Exception e) {

            return "error al crear usuario";
        }

    }

   // public Usuario obternerUsuario(String correo) {
       // try {
           // UsuarioEntity usuario = ususariorepository.findByCorreo(correo);// Buscamos el usuario por correo
            // Si es que el usuario existe
            // Creamos un nuevo usuario
            // y lo guardamos en la base de datos

            //if (usuario != null) {

              //  Usuario user = new Usuario(
                //        usuario.getIdUsuario(),
                  //      usuario.getNombre(),
                    //    usuario.getApellidos(),
                      //  usuario.getCorreo(),
                        //usuario.getContrasena()

               // );
                //return user;

            //}
            //return null;
        //} catch (Exception e) {
          //  return null;
        //}

    

    public UsuarioDto obtenerUsuarioDto(int id) {// Este metodo es para obtener el usuarioDto
        try {
            UsuarioEntity usuario = ususariorepository.findByIdUsuario(id);// Buscamos el usuario por id
            UsuarioDto nuevoUsuario = new UsuarioDto(// Creamos un nuevo usuarioDto
                    usuario.getNombre(), // Le pasamos el nombre del usuario
                    usuario.getApellidos(), // Le pasamos el apellido del usuario
                    usuario.getCorreo()// Le pasamos el correo del usuario
            );
            return nuevoUsuario;// Retornamos el nuevo usuarioDto
        } catch (Exception e) {// Si no se encuentra el usuario retornamos null
            return null;
        }

    }

    public String modificarUsuario(Usuario user) {
        try {
            UsuarioEntity e = ususariorepository.findByIdUsuario(user.getIdUsuario());// Buscamos usuarpor id
            if (e != null) {// Si el usuario existe
                e.setNombre(user.getNombre());// Modificamos el nombre
                e.setApellidos(user.getApellidos());
                e.setCorreo(user.getCorreo());
                e.setContrasena(user.getContrasena());
                ususariorepository.save(e);// Guardamos el usuario modificado
                return "Usuario modificado correctamente";
            }
            return "Usuario no encontrado para modificar";
        } catch (Exception ex) {
            return "Error al modificar usuario: " + ex.getMessage();
        }
    }

    public String eliminarUsuarioPorId(int id) {// Este metodo es para eliminar el usuario por id
        // Buscamos el usuario por su id
        try {
            if (ususariorepository.existsById(id)) {// Si el usuairio existe,
                ususariorepository.deleteById(id); // lo eliminamos
                return "Usuario eliminado correctamente por ID";
            }
            return "Usuario no encontrado por ID";
        } catch (Exception ex) {// Si no se encuentra el usuario retornamos un error
            return "Error al eliminar usuario por ID: " + ex.getMessage();// mensaje de eerro
        }

    }

    

}