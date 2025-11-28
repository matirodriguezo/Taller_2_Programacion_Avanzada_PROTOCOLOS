
package modelo;

import dao.UsuarioDAO;

/**
 *
 * @author benja
 */
public class Usuario {
    private int id;
    private String usuario;
    private String pass;
    private String rol;

    public Usuario() {
    }

    public Usuario(int id, String usuario, String password) {
        this.id = id;
        this.usuario = usuario;
        this.pass = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) throws ExcepcionPersonalizada {
        if(id==0){
            throw new ExcepcionPersonalizada("Id no puede ser nulo");
        }
        else{
            this.id = id;
        }
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) throws ExcepcionPersonalizada {
        if(usuario==null||usuario.isEmpty()||usuario.isBlank()){
            throw new ExcepcionPersonalizada("Usuario no puede ser nulo");
        }
        else{
            this.usuario = usuario;
        }
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) throws ExcepcionPersonalizada {
        if(pass == null||pass.isEmpty()||pass.isBlank()){
            throw new ExcepcionPersonalizada("Contraseña no puede ser nula");
        }
        else{
            this.pass = pass;
        }
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) throws ExcepcionPersonalizada {
        if(rol==null||rol.isEmpty()||rol.isBlank()){
            throw new ExcepcionPersonalizada("rol no puede ser nulo");
        }
        else{
            this.rol = rol;
        }
    }
    public Usuario obtenerUsuario(String usuario,String pass) throws IllegalAccessException, Exception{
        UsuarioDAO uDAO = new UsuarioDAO();
        return uDAO.hacerLogin(usuario, pass);
    }
    
    
    
}
