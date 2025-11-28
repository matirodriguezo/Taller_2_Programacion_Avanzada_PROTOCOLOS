<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="modelo.Protocolo"%>
<%@page import="modelo.TipoProtocolo"%>
<%@page import="modelo.ObservacionProtocolo"%>
<%@page import="modelo.Alumno"%>
<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="modelo.Usuario"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>

<%
    
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); 
    response.setHeader("Pragma", "no-cache"); 
    response.setDateHeader("Expires", 0);     


    HttpSession sesion = request.getSession(false);
    if (sesion == null || sesion.getAttribute("usuarioLogeado") == null) {
        response.sendRedirect("login.jsp");
        return;
    }

   
    Usuario usuario = (Usuario) sesion.getAttribute("usuarioLogeado");
    Date horaLogin = (Date) sesion.getAttribute("horaLogin");
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    Protocolo p = (Protocolo) request.getAttribute("protocolo");
    TipoProtocolo tipo = (TipoProtocolo) request.getAttribute("tipoProtocolo");
    List<ObservacionProtocolo> listaObs =
            (List<ObservacionProtocolo>) request.getAttribute("listaObservaciones");
    List<Alumno> listaAlumnos =
            (List<Alumno>) request.getAttribute("listaAlumnosProtocolo");

    String error = (String) request.getAttribute("error");
    String mensaje = (String) request.getAttribute("mensaje");
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Detalle de protocolo</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet">
</head>
<body class="bg-light">


<div style="position: fixed; top: 10px; right: 20px; text-align: right;
            z-index: 999; font-size: 0.85rem; background: rgba(255,255,255,0.9);
            padding: 6px 10px; border-radius: 6px; box-shadow: 0 0 5px rgba(0,0,0,0.15);">
    Usuario:
    <strong><%= (usuario != null ? usuario.getUsuario() : "N/D") %></strong><br>
    Inicio sesión:
    <%= (horaLogin != null ? sdf.format(horaLogin) : "N/D") %>
</div>

<div class="container mt-5">

   
    <h2 class="text-center text-primary mb-4">Detalle del protocolo</h2>

    
    <% if (error != null) { %>
        <div class="alert alert-danger"><%= error %></div>
    <% } %>
    <% if (mensaje != null) { %>
        <div class="alert alert-success"><%= mensaje %></div>
    <% } %>

   
    <div class="card mb-4 shadow-sm">
        <div class="card-body">
            <p><strong>ID Protocolo:</strong> <%= p.getIdProtocolo() %></p>
            <p><strong>Fecha/Hora:</strong> <%= p.getFechaHora() %></p>
            <p><strong>Estado:</strong> <%= p.getEstado() %></p>
            <p><strong>Tipo de protocolo:</strong>
                <%= (tipo != null ? tipo.getNombreProtocolo() : "N/D") %>
            </p>
        </div>
    </div>

    
    <div class="card mb-4 shadow-sm">
        <div class="card-header">
            <h4 class="mb-0">Alumnos involucrados</h4>
        </div>
        <div class="card-body">
            <% if (listaAlumnos == null || listaAlumnos.isEmpty()) { %>
                <div class="alert alert-info mb-0">
                    Este protocolo aún no tiene alumnos asociados.
                </div>
            <% } else { %>
                <table class="table table-sm table-striped mb-0">
                    <thead>
                    <tr>
                        <th>RUT</th>
                        <th>Nombre</th>
                        <th>Curso</th>
                    </tr>
                    </thead>
                    <tbody>
                    <% for (Alumno a : listaAlumnos) { %>
                        <tr>
                            <td><%= a.getRut() %></td>
                            <td><%= a.getNombreCompleto() %></td>
                            <td><%= a.getCurso() %></td>
                        </tr>
                    <% } %>
                    </tbody>
                </table>
            <% } %>
        </div>
    </div>

    
    <div class="card mb-4 shadow-sm">
        <div class="card-header">
            <h4 class="mb-0">Historial de observaciones</h4>
        </div>
        <div class="card-body">
            <% if (listaObs == null || listaObs.isEmpty()) { %>
                <div class="alert alert-info mb-0">
                    No hay observaciones registradas para este protocolo.
                </div>
            <% } else { %>
                <table class="table table-sm table-bordered mb-0">
                    <thead>
                    <tr>
                        <th>Fecha/Hora</th>
                        <th>Tipo</th>
                        <th>ID Usuario</th>
                        <th>Observación</th>
                    </tr>
                    </thead>
                    <tbody>
                    <% for (ObservacionProtocolo o : listaObs) { %>
                        <tr>
                            <td><%= o.getFechaHora() %></td>
                            <td><%= o.getTipo() %></td>
                            <td><%= o.getIdUsuario() %></td>
                            <td><%= o.getTexto() %></td>
                        </tr>
                    <% } %>
                    </tbody>
                </table>
            <% } %>
        </div>
    </div>

    
    <div class="mt-4 d-flex justify-content-between">
        <a href="ControladorProtocolos?accion=listar" class="btn btn-secondary">
            Volver al historial de Protocolos
        </a>

        <div class="d-flex gap-2">
            <%
                String estado = p.getEstado();
                if (!"ANULADO".equals(estado) && !"CERRADO".equals(estado)) {
            %>
            <a href="ControladorProtocolos?accion=actualizar&idProtocolo=<%= p.getIdProtocolo() %>"
               class="btn btn-primary">
                Registrar actualización
            </a>
            <% } %>

            <% if ("NUEVO".equals(estado)) { %>
                <a href="ControladorProtocolos?accion=cambiarEstado&idProtocolo=<%= p.getIdProtocolo() %>&nuevoEstado=EN_INVESTIGACION"
                   class="btn btn-info">
                    Marcar como "En Investigación"
                </a>
                <a href="ControladorProtocolos?accion=cambiarEstado&idProtocolo=<%= p.getIdProtocolo() %>&nuevoEstado=CERRADO"
                   class="btn btn-success">
                    Cerrar protocolo
                </a>
            <% } else if ("EN_INVESTIGACION".equals(estado)) { %>
                <a href="ControladorProtocolos?accion=cambiarEstado&idProtocolo=<%= p.getIdProtocolo() %>&nuevoEstado=CERRADO"
                   class="btn btn-success">
                    Cerrar protocolo
                </a>
            <% } %>

            <% if (!"ANULADO".equals(estado) && !"CERRADO".equals(estado)) { %>
                <a href="ControladorProtocolos?accion=anular&idProtocolo=<%= p.getIdProtocolo() %>"
                   class="btn btn-danger">
                    Anular protocolo
                </a>
            <% } %>
        </div>
    </div>

</div>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
