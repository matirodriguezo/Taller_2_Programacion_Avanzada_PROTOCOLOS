<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="modelo.Usuario"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">


    <%
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache"); 
        response.setDateHeader("Expires", 0);   
    %>

    <link href="./css/bootstrap.css" rel="stylesheet" type="text/css"/>
    <title>Sistema de Inspectoría</title>
</head>
<body class="bg-light">

    <%
        // ✔ Validar sesión
        HttpSession sesion = request.getSession(false);
        if (sesion == null || sesion.getAttribute("usuarioLogeado") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        Usuario usuario = (Usuario) sesion.getAttribute("usuarioLogeado");
        Date horaLogin = (Date) sesion.getAttribute("horaLogin");
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    %>


    <div style="position: fixed; top: 10px; right: 20px; text-align: right;
                z-index: 999; font-size: 0.85rem; background: rgba(255,255,255,0.9);
                padding: 6px 10px; border-radius: 6px; box-shadow: 0 0 5px rgba(0,0,0,0.15);">
        Usuario:
        <strong><%= (usuario != null ? usuario.getUsuario() : "N/D") %></strong><br>
        Inicio sesión:
        <%= (horaLogin != null ? sdf.format(horaLogin) : "N/D") %>
    </div>

    <div class="container mt-5">
        <div class="jumbotron p-5 mb-4 bg-light rounded-3 shadow-lg">
            <h1 class="display-6">Sistema de Inspectoría</h1>
            <p class="lead">
                Bienvenido al sistema de Inspectoria del Colegio Marcela Paz.
            </p>
            <hr class="my-4">

            <a class="btn btn-primary btn-lg" href="irAlCRUDinspectoria.jsp">
                Ir al CRUD Inspectoria
            </a>

            <a class="btn btn-danger btn-lg"
               href="ControladorLogin?accion=CerrarSesion">
                Cerrar Sesión
            </a>
        </div>
    </div>

</body>
</html>
