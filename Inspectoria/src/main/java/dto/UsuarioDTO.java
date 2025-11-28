/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 * @author benja
 */
public class UsuarioDTO {
    private int id;
    private String usuario;
    private String pass;
    private String rol;

    public UsuarioDTO() {
    }

    public UsuarioDTO(int id, String usuario, String pass, String rol) {
        this.id = id;
        this.usuario = usuario;
        this.pass = pass;
        this.rol = rol;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    @Override
    public String toString() {
        return "UsuarioDTO{" + "id=" + id + ", usuario=" + usuario + ", pass=" + pass + ", rol=" + rol + '}';
    }
    
    
}
