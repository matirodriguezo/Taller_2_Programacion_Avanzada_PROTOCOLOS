/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author benja
 */
public class Persona {
    private int id;
    private String dni;
    private String nombre;
    
    public Persona() {
    }
    
    
    public Persona(String dni, String nombre) {
        this.dni = dni;
        this.nombre = nombre;
    }

    
    public int getId() {
        return id;
    }

    
    public void setId(int id) {
        this.id = id;
    }


    public String getDni() {
        return dni;
    }

    
    public void setDni(String dni) throws ExcepcionPersonalizada {
        if (dni==null || dni.isEmpty() || dni.isBlank())
        {
            throw new ExcepcionPersonalizada("DNI no puede ser vacio");
        }
        else
        {
            this.dni = dni;
        }
    }

    
    public String getNombre() {
        return nombre;
    }

    
    public void setNombre(String nombre) throws ExcepcionPersonalizada {
        if (nombre==null || nombre.isEmpty() || nombre.isBlank())
        {
            throw new ExcepcionPersonalizada("nombre no puede ser vacio");
        }
        else
        {
            this.nombre = nombre;
        }
    }

    
    @Override
    public String toString() {
        return "Persona{" + "id=" + id + ", dni=" + dni + ", nombre=" + nombre + '}';
    }
   
}
