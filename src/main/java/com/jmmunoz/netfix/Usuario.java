/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jmmunoz.netfix;

/**
 *
 * @author juanm
 */
public class Usuario {

    private String idUsuario;
    private String nombre;
    private String rol;
    private String email;
    private String password;

    public Usuario(String idUsuario, String nombre, String rol, String email, String password) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.rol = rol;
        this.email = email;
        this.password = password;
    }

    // Getters (y setters si los necesitas)
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
}
