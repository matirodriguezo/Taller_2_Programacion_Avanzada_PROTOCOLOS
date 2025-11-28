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
import modelo.ObservacionProtocolo;

public class ObservacionProtocoloDAO {

    Connection con;
    PreparedStatement ps;

    public boolean crear(ObservacionProtocolo o) {
        boolean creada = false;
        String sql = "INSERT INTO observacion_protocolo "
                   + "(id_protocolo, id_usuario, texto, tipo) "
                   + "VALUES (?, ?, ?, ?)";

        try {
            ConexionMySql cn = new ConexionMySql();
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, o.getIdProtocolo());
            ps.setInt(2, o.getIdUsuario());
            ps.setString(3, o.getTexto());
            ps.setString(4, o.getTipo());

            creada = ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error al crear observación de protocolo: " + e.getMessage());
        }

        return creada;
    }


    public List<ObservacionProtocolo> listarPorProtocolo(int idProtocolo) {
        List<ObservacionProtocolo> lista = new ArrayList<>();
        String sql = "SELECT id_observacion, id_protocolo, id_usuario, fecha_hora, texto, tipo "
                   + "FROM observacion_protocolo "
                   + "WHERE id_protocolo = ? "
                   + "ORDER BY fecha_hora ASC";

        try {
            ConexionMySql cn = new ConexionMySql();
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, idProtocolo);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ObservacionProtocolo o = new ObservacionProtocolo();
                o.setIdObservacion(rs.getInt("id_observacion"));
                o.setIdProtocolo(rs.getInt("id_protocolo"));
                o.setIdUsuario(rs.getInt("id_usuario"));
                o.setFechaHora(rs.getTimestamp("fecha_hora"));
                o.setTexto(rs.getString("texto"));
                o.setTipo(rs.getString("tipo"));
                lista.add(o);
            }

        } catch (Exception e) {
            System.out.println("Error al listar observaciones de protocolo: " + e.getMessage());
        }

        return lista;
    }

}