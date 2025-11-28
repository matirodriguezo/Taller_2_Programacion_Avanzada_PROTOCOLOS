/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author usuario
 */

public class TipoProtocolo {
    private int idTipoProtocolo;
    private String nombreProtocolo;
    private String descripcion;

    public TipoProtocolo() {}

    public TipoProtocolo(int idTipoProtocolo, String nombreProtocolo, String descripcion) {
        this.idTipoProtocolo = idTipoProtocolo;
        this.nombreProtocolo = nombreProtocolo;
        this.descripcion = descripcion;
    }

    public int getIdTipoProtocolo() {
        return idTipoProtocolo;
    }

    public void setIdTipoProtocolo(int idTipoProtocolo) {
        this.idTipoProtocolo = idTipoProtocolo;
    }

    public String getNombreProtocolo() {
        return nombreProtocolo;
    }

    public void setNombreProtocolo(String nombreProtocolo) {
        this.nombreProtocolo = nombreProtocolo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
