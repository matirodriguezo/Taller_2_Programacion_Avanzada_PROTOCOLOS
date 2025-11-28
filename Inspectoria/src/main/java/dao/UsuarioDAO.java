
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import modelo.Usuario;
import servicios.FactoriaServiciosImpl;

/**
 *
 * @author benja
 */
public class UsuarioDAO{
    private Connection conn;
    private PreparedStatement stmt;
    private ResultSet rs;
    
    public Usuario hacerLogin(String usuario,String password) throws InstantiationException, IllegalAccessException, Exception{
        Usuario u = null;
        conn = FactoriaServiciosImpl.getFactoria().getConexionDB().getConexion();
        String sql = "select * from user where usuario='"+usuario+"' and pass ='"+password+"'";
        stmt = conn.prepareStatement(sql);
        rs = stmt.executeQuery();
        if(rs.next()){
           u = new Usuario();
           u.setId(rs.getInt("id"));
           u.setUsuario(rs.getString("usuario"));
           u.setPass(rs.getString("pass"));
           u.setRol(rs.getString("rol"));
           return u;
        }
        else{
            return null;
        }
        
        
        
        
    }
    
    
}
