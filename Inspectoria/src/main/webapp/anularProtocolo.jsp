<%-- 
    Document   : anularProtocolo
    Created on : 20-11-2025, 21:17:45
    Author     : usuario
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="modelo.Protocolo"%>
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

    String error = (String) request.getAttribute("error");
    Protocolo p = (Protocolo) request.getAttribute("protocolo");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Anular Protocolo</title>
    <link href="./css/bootstrap.css" rel="stylesheet"/>
</head>
<body class="bg-light">

    <div style="position: fixed; top: 15px; right: 20px; z-index: 9999;">
        <div style="
            background: #ffffff;
            padding: 8px 14px;
            border-radius: 8px;
            box-shadow: 0 4px 10px rgba(0,0,0,0.15);
            font-size: 0.85rem;
            min-width: 220px;
        ">
            Usuario:
            <strong><%= (usuario != null ? usuario.getUsuario() : "N/D") %></strong><br>
            Inicio sesión:
            <%= (horaLogin != null ? sdf.format(horaLogin) : "N/D") %>
        </div>
    </div>


    <div class="container mt-5">

        <h2>Anular Protocolo</h2>

        <% if (error != null) { %>
            <div class="alert alert-danger"><%= error %></div>
        <% } %>

        <% if (p == null) { %>
            <div class="alert alert-warning">No se encontró el protocolo.</div>
            <a class="btn btn-secondary" href="ControladorProtocolos?accion=listar">Volver</a>
        <% } else { %>

            <p><strong>ID Protocolo:</strong> <%= p.getIdProtocolo() %></p>
            <p><strong>Estado actual:</strong> <%= p.getEstado() %></p>

            <form method="POST" action="ControladorProtocolos">
                <input type="hidden" name="accion" value="guardarAnulacion">
                <input type="hidden" name="idProtocolo" value="<%= p.getIdProtocolo() %>">

                <div class="mb-3">
                    <label class="form-label">Motivo de anulación:</label>
                    <textarea class="form-control" name="motivo" rows="4" required></textarea>
                </div>

                <button class="btn btn-danger">Anular</button>
                <a class="btn btn-secondary" 
                   href="ControladorProtocolos?accion=verDetalle&idProtocolo=<%= p.getIdProtocolo() %>">
                    Cancelar
                </a>
            </form>

        <% } %>

    </div>
</body>
</html>
