package modelo;

public class ProtocoloAlumno {

    private int idProtocolo;
    private int idAlumno;

    public ProtocoloAlumno() {
    }

    public ProtocoloAlumno(int idProtocolo, int idAlumno) {
        this.idProtocolo = idProtocolo;
        this.idAlumno = idAlumno;
    }

    public int getIdProtocolo() {
        return idProtocolo;
    }

    public void setIdProtocolo(int idProtocolo) {
        this.idProtocolo = idProtocolo;
    }

    public int getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(int idAlumno) {
        this.idAlumno = idAlumno;
    }
}
