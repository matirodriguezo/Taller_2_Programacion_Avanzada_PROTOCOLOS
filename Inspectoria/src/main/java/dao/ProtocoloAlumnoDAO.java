package dao;

import db.ConexionMySql;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import modelo.Alumno;
import modelo.ProtocoloAlumno;

public class ProtocoloAlumnoDAO {

    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public boolean agregar(int idProtocolo, int idAlumno) {
        boolean ok = false;
        String sql = "INSERT INTO protocolo_alumno (id_protocolo, id_alumno) VALUES (?, ?)";

        try {
            ConexionMySql cn = new ConexionMySql();
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, idProtocolo);
            ps.setInt(2, idAlumno);
            ok = ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Error al asociar alumno a protocolo: " + e.getMessage());
        }

        return ok;
    }


    public List<ProtocoloAlumno> listarPorProtocolo(int idProtocolo) {
        List<ProtocoloAlumno> lista = new ArrayList<>();
        String sql = "SELECT id_protocolo, id_alumno "
                   + "FROM protocolo_alumno WHERE id_protocolo = ?";

        try {
            ConexionMySql cn = new ConexionMySql();
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, idProtocolo);
            rs = ps.executeQuery();

            while (rs.next()) {
                ProtocoloAlumno pa = new ProtocoloAlumno();
                pa.setIdProtocolo(rs.getInt("id_protocolo"));
                pa.setIdAlumno(rs.getInt("id_alumno"));
                lista.add(pa);
            }

        } catch (Exception e) {
            System.out.println("Error al listar protocolo_alumno: " + e.getMessage());
        }

        return lista;
    }

    public List<Alumno> listarAlumnosPorProtocolo(int idProtocolo) {
        List<Alumno> lista = new ArrayList<>();
        String sql = "SELECT a.id_alumno, a.rut, a.nombre, a.apellido, a.curso "
                   + "FROM protocolo_alumno pa "
                   + "JOIN alumno a ON pa.id_alumno = a.id_alumno "
                   + "WHERE pa.id_protocolo = ? "
                   + "ORDER BY a.curso, a.apellido, a.nombre";

        try {
            ConexionMySql cn = new ConexionMySql();
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, idProtocolo);
            rs = ps.executeQuery();

            while (rs.next()) {
                Alumno a = new Alumno();
                a.setIdAlumno(rs.getInt("id_alumno"));
                a.setRut(rs.getString("rut"));
                a.setNombre(rs.getString("nombre"));
                a.setApellido(rs.getString("apellido"));
                a.setCurso(rs.getString("curso"));
                lista.add(a);
            }

        } catch (Exception e) {
            System.out.println("Error al listar alumnos por protocolo: " + e.getMessage());
        }

        return lista;
    }
}