/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jmmunoz.netfix.modelo;

/**
 *
 * @author Juanma Muñoz
 */
/**
 * Representa a un usuario del sistema (agente, técnico o administrador).
 * <p>
 * Almacena la información de sesión y perfil del usuario autenticado.
 * </p>
 * 
 * @author Juanma Muñoz
 */
public class Usuario {

    private String idUsuario;
    private String nombre;
    private String rol;
    private String email;
    private String password;

    /**
     * Crea un nuevo objeto Usuario con los datos proporcionados.
     * 
     * @param idUsuario Identificador único del usuario.
     * @param nombre    Nombre completo del usuario.
     * @param rol       Rol o perfil de permisos (ej. "admin", "tecnico").
     * @param email     Correo electrónico (o nombre de usuario).
     * @param password  Contraseña (hash o texto plano según contexto).
     */
    public Usuario(String idUsuario, String nombre, String rol, String email, String password) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.rol = rol;
        this.email = email;
        this.password = password;
    }

    // Getters (y setters si los necesitas)

    /**
     * @return El identificador único del usuario.
     */
    public String getIdUsuario() {
        return idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public String getRol() {
        return rol;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
