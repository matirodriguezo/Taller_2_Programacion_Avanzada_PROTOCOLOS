<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="modelo.Usuario"%>
<%@page import="javax.servlet.http.HttpSession"%>
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
<html lang="es">
<head>
    <meta charset="UTF-8">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">
    <title>Módulo Inspectoría</title>
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
    <div class="text-center">

        <div class="jumbotron p-5 mb-4 bg-light rounded-3 shadow-lg">
            <h1 class="display-4 text-primary">Módulo Inspectoría</h1>
            <p class="lead text-secondary">
                Desde aquí puedes ir al CRUD de Inspectoría o a la gestión de protocolos.
            </p>

            <hr class="my-4">

            <div class="d-flex justify-content-center">
                <a class="btn btn-primary btn-lg mx-2" 
                   href="ControladorProtocolos?accion=menu">
                    <i class="fas fa-cogs"></i> Gestión de Protocolos
                </a>

                <a class="btn btn-secondary btn-lg mx-2" 
                   href="index.jsp">
                    <i class="fas fa-arrow-left"></i> Volver a Sistema de Inspectoría
                </a>
            </div>

        </div>

    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
