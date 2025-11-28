/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import java.sql.Timestamp;
/**
 *
 * @author usuario
 */


public class Protocolo {

    private int idProtocolo;
    private Timestamp fechaHora;
    private String estado;          
    private int idTipoProtocolo;    
    private int idUsuarioCreador;  
    public Protocolo() {
    }

    public Protocolo(String estado, int idTipoProtocolo, int idUsuarioCreador) {
        this.estado = estado;
        this.idTipoProtocolo = idTipoProtocolo;
        this.idUsuarioCreador = idUsuarioCreador;
    }

    public int getIdProtocolo() {
        return idProtocolo;
    }

    public void setIdProtocolo(int idProtocolo) {
        this.idProtocolo = idProtocolo;
    }

    public Timestamp getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Timestamp fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getIdTipoProtocolo() {
        return idTipoProtocolo;
    }

    public void setIdTipoProtocolo(int idTipoProtocolo) {
        this.idTipoProtocolo = idTipoProtocolo;
    }

    public int getIdUsuarioCreador() {
        return idUsuarioCreador;
    }

    public void setIdUsuarioCreador(int idUsuarioCreador) {
        this.idUsuarioCreador = idUsuarioCreador;
    }

}