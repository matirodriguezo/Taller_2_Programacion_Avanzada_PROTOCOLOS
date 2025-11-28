<%-- 
    Document   : buscarProtocoloActualizar
    Created on : 18-11-2025, 15:33:22
    Author     : usuario
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Buscar protocolo para actualizar</title>
    <link href="./css/bootstrap.css" rel="stylesheet" type="text/css"/>
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
    <h2 class="mb-4">Buscar protocolo para actualizar</h2>

    <p class="mb-3">
        Aquí podrás buscar un protocolo existente para luego registrarle una actualización.
    </p>

    <form action="ControladorProtocolos" method="get" class="mb-4">
        <input type="hidden" name="accion" value="menu"/>

        <div class="mb-3">
            <label for="idProtocolo" class="form-label">ID del protocolo</label>
            <input type="text" class="form-control" id="idProtocolo" name="idProtocolo"
                   placeholder="Ej: 1001">
        </div>

        <button type="submit" class="btn btn-primary">
            Buscar protocolo
        </button>
    </form>

    <a href="menuProtocolos.jsp" class="btn btn-secondary">
        ← Volver al menú de protocolos
    </a>
</div>

</body>
</html>
