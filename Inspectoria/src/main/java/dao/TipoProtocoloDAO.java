/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author usuario
 */
import db.ConexionMySql;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import modelo.TipoProtocolo;


public class TipoProtocoloDAO {

    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public List<TipoProtocolo> listar() {
        List<TipoProtocolo> lista = new ArrayList<>();
        String sql = "SELECT id_tipo_protocolo, nombre_protocolo, descripcion FROM tipo_protocolo ORDER BY nombre_protocolo";
        try {
            ConexionMySql cn = new ConexionMySql();
            con = cn.getConexion();

            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                TipoProtocolo tp = new TipoProtocolo();
                tp.setIdTipoProtocolo(rs.getInt("id_tipo_protocolo"));
                tp.setNombreProtocolo(rs.getString("nombre_protocolo"));
                tp.setDescripcion(rs.getString("descripcion"));
                lista.add(tp);
            }
        } catch (Exception e) {
            System.out.println("Error al listar tipos de protocolo: " + e.getMessage());
        }
        return lista;
    }

    public TipoProtocolo buscarPorId(int id) {
        TipoProtocolo tp = null;
        String sql = "SELECT id_tipo_protocolo, nombre_protocolo, descripcion FROM tipo_protocolo WHERE id_tipo_protocolo = ?";
        try {
            ConexionMySql cn = new ConexionMySql();
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                tp = new TipoProtocolo();
                tp.setIdTipoProtocolo(rs.getInt("id_tipo_protocolo"));
                tp.setNombreProtocolo(rs.getString("nombre_protocolo"));
                tp.setDescripcion(rs.getString("descripcion"));
            }
        } catch (Exception e) {
            System.out.println("Error al buscar tipo de protocolo: " + e.getMessage());
        }
        return tp;
    }
}
