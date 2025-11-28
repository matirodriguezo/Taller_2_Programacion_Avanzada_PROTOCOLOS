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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import modelo.Protocolo;

public class ProtocoloDAO {

    Connection con;
    PreparedStatement ps;
    ResultSet rs;


    public int crear(Protocolo p) {
        int idGenerado = -1;
        String sql = "INSERT INTO protocolo (estado, id_tipo, id_usuario_creador) "
                   + "VALUES (?, ?, ?)";

        try {
            ConexionMySql cn = new ConexionMySql();
            con = cn.getConexion();
            ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, p.getEstado());
            ps.setInt(2, p.getIdTipoProtocolo());
            ps.setInt(3, p.getIdUsuarioCreador());

            ps.executeUpdate();

            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                idGenerado = rs.getInt(1);
            }

        } catch (Exception e) {
            System.out.println("Error al crear protocolo: " + e.getMessage());
        }

        return idGenerado;
    }


    public Protocolo buscarPorId(int idProtocolo) {
        String sql = "SELECT id_protocolo, fecha_hora, estado, id_tipo, id_usuario_creador "
                   + "FROM protocolo WHERE id_protocolo = ?";
        Protocolo p = null;

        try {
            ConexionMySql cn = new ConexionMySql();
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, idProtocolo);

            rs = ps.executeQuery();
            if (rs.next()) {
                p = new Protocolo();
                p.setIdProtocolo(rs.getInt("id_protocolo"));
                p.setFechaHora(rs.getTimestamp("fecha_hora"));
                p.setEstado(rs.getString("estado"));
                p.setIdTipoProtocolo(rs.getInt("id_tipo"));
                p.setIdUsuarioCreador(rs.getInt("id_usuario_creador"));
            }

        } catch (Exception e) {
            System.out.println("Error al buscar protocolo por ID: " + e.getMessage());
        }

        return p;
    }


    public List<Protocolo> listarTodos() {
        List<Protocolo> lista = new ArrayList<>();
        String sql = "SELECT id_protocolo, fecha_hora, estado, id_tipo, id_usuario_creador "
                   + "FROM protocolo ORDER BY fecha_hora DESC";

        try {
            ConexionMySql cn = new ConexionMySql();
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Protocolo p = new Protocolo();
                p.setIdProtocolo(rs.getInt("id_protocolo"));
                p.setFechaHora(rs.getTimestamp("fecha_hora"));
                p.setEstado(rs.getString("estado"));
                p.setIdTipoProtocolo(rs.getInt("id_tipo"));
                p.setIdUsuarioCreador(rs.getInt("id_usuario_creador"));
                lista.add(p);
            }

        } catch (Exception e) {
            System.out.println("Error al listar protocolos: " + e.getMessage());
        }

        return lista;
    }

    public boolean cambiarEstado(int idProtocolo, String nuevoEstado) {
        boolean actualizado = false;
        String sql = "UPDATE protocolo SET estado = ? WHERE id_protocolo = ?";

        try {
            ConexionMySql cn = new ConexionMySql();
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, nuevoEstado);
            ps.setInt(2, idProtocolo);

            actualizado = ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error al cambiar estado del protocolo: " + e.getMessage());
        }

        return actualizado;
    }


 
    public List<Protocolo> buscarConFiltros(String rutONombreAlumno, String fechaDesde, String fechaHasta,Integer idTipoProtocolo,String estado) {
        List<Protocolo> lista = new ArrayList<>();

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT DISTINCT p.id_protocolo, p.fecha_hora, p.estado, p.id_tipo, p.id_usuario_creador ")
           .append("FROM protocolo p ")
           .append("LEFT JOIN protocolo_alumno pa ON p.id_protocolo = pa.id_protocolo ")
           .append("LEFT JOIN alumno a ON pa.id_alumno = a.id_alumno ")
           .append("WHERE 1=1 ");


        List<Object> parametros = new ArrayList<>();

        if (rutONombreAlumno != null && !rutONombreAlumno.trim().isEmpty()) {
            sql.append(" AND (a.rut LIKE ? OR a.nombre LIKE ?) ");
            String patron = "%" + rutONombreAlumno.trim() + "%";
            parametros.add(patron);
            parametros.add(patron);
        }

        if (fechaDesde != null && !fechaDesde.trim().isEmpty()) {
            sql.append(" AND p.fecha_hora >= ? ");
            parametros.add(fechaDesde.trim() + " 00:00:00");
        }

        if (fechaHasta != null && !fechaHasta.trim().isEmpty()) {
            sql.append(" AND p.fecha_hora <= ? ");
            parametros.add(fechaHasta.trim() + " 23:59:59");
        }

        if (idTipoProtocolo != null && idTipoProtocolo > 0) {
            sql.append(" AND p.id_tipo = ? ");
            parametros.add(idTipoProtocolo);
        }

        if (estado != null && !estado.trim().isEmpty()) {
            sql.append(" AND p.estado = ? ");
            parametros.add(estado.trim());
        }

        sql.append(" ORDER BY p.fecha_hora DESC");

        try {
            ConexionMySql cn = new ConexionMySql();
            con = cn.getConexion();
            ps = con.prepareStatement(sql.toString());

            int index = 1;
            for (Object param : parametros) {
                if (param instanceof String) {
                    ps.setString(index++, (String) param);
                } else if (param instanceof Integer) {
                    ps.setInt(index++, (Integer) param);
                }
            }

            rs = ps.executeQuery();
            while (rs.next()) {
                Protocolo p = new Protocolo();
                p.setIdProtocolo(rs.getInt("id_protocolo"));
                p.setFechaHora(rs.getTimestamp("fecha_hora"));
                p.setEstado(rs.getString("estado"));
                p.setIdTipoProtocolo(rs.getInt("id_tipo"));
                p.setIdUsuarioCreador(rs.getInt("id_usuario_creador"));
                lista.add(p);
            }

        } catch (Exception e) {
            System.out.println("Error al buscar protocolos con filtros: " + e.getMessage());
        }

        return lista;
    }

}