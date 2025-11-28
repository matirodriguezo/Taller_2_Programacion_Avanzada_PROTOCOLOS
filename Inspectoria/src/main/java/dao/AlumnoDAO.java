/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author usuario aca
 */
import db.ConexionMySql;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import modelo.Alumno;

public class AlumnoDAO {

    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public Alumno buscarPorRut(String rut) {
        Alumno a = null;
        String sql = "SELECT id_alumno, rut, nombre, apellido, curso FROM alumno WHERE rut = ?";

        try {
            ConexionMySql cn = new ConexionMySql();
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, rut);
            rs = ps.executeQuery();

            if (rs.next()) {
                a = new Alumno();
                a.setIdAlumno(rs.getInt("id_alumno"));
                a.setRut(rs.getString("rut"));
                a.setNombre(rs.getString("nombre"));
                a.setApellido(rs.getString("apellido"));
                a.setCurso(rs.getString("curso"));
            }
        } catch (Exception e) {
            System.out.println("Error al buscar alumno por rut: " + e.getMessage());
        }

        return a;
    }

    public Alumno buscarPorId(int id) {
        Alumno a = null;
        String sql = "SELECT id_alumno, rut, nombre, apellido, curso FROM alumno WHERE id_alumno = ?";

        try {
            ConexionMySql cn = new ConexionMySql();
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                a = new Alumno();
                a.setIdAlumno(rs.getInt("id_alumno"));
                a.setRut(rs.getString("rut"));
                a.setNombre(rs.getString("nombre"));
                a.setApellido(rs.getString("apellido"));
                a.setCurso(rs.getString("curso"));
            }
        } catch (Exception e) {
            System.out.println("Error al buscar alumno por id: " + e.getMessage());
        }

        return a;
    }

    public List<Alumno> listar() {
        List<Alumno> lista = new ArrayList<>();
        String sql = "SELECT id_alumno, rut, nombre, apellido, curso FROM alumno ORDER BY curso, apellido, nombre";

        try {
            ConexionMySql cn = new ConexionMySql();
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
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
            System.out.println("Error al listar alumnos: " + e.getMessage());
        }

        return lista;
    }

    public List<Alumno> buscarPorTermino(String termino) {
        List<Alumno> lista = new ArrayList<>();
        String sql = "SELECT * FROM alumno WHERE nombre LIKE ? OR apellido LIKE ? OR rut LIKE ? LIMIT 10";
        try {
            ConexionMySql cn = new ConexionMySql();
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            String patron = "%" + termino + "%";
            ps.setString(1, patron);
            ps.setString(2, patron);
            ps.setString(3, patron);
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
            con.close(); 
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
}
