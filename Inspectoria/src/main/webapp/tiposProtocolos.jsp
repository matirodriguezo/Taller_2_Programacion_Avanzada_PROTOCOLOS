<%@page contentType="text/html; charset=UTF-8" %>
<%@page import="java.util.List" %>
<%@page import="modelo.TipoProtocolo" %>
<%@page import="javax.servlet.http.HttpSession" %>
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

    List<TipoProtocolo> lista = (List<TipoProtocolo>) request.getAttribute("listaTipos");

    Usuario usuario = (Usuario) sesion.getAttribute("usuarioLogeado");
    Date horaLogin = (Date) sesion.getAttribute("horaLogin");
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Tipos de Protocolo</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        .panel-blanco {
            background: #ffffff;
            padding: 30px;
            border-radius: 12px;
            box-shadow: 0 0 15px rgba(0,0,0,0.15);
        }
    </style>
</head>

<body class="bg-light">

    
    <div style="
        position: fixed;
        top: 10px;
        right: 20px;
        text-align: right;
        z-index: 999;
        font-size: 0.85rem;
        background: rgba(255,255,255,0.9);
        padding: 6px 12px;
        border-radius: 6px;
        box-shadow: 0 0 5px rgba(0,0,0,0.20);
    ">
        Usuario: <strong><%= usuario.getUsuario() %></strong><br>
        Inicio sesión: <%= (horaLogin != null ? sdf.format(horaLogin) : "N/D") %>
    </div>

    
    <div class="container mt-5">

        <div class="panel-blanco">

            <h2 class="text-center text-primary mb-4">Tipos de Protocolo</h2>

            <table class="table table-bordered table-striped text-center">
                <thead class="table-dark">
                    <tr>
                        <th>ID</th>
                        <th>Nombre</th>
                        <th>Descripción</th>
                    </tr>
                </thead>

                <tbody>
                <%
                    if (lista != null && !lista.isEmpty()) {
                        for (TipoProtocolo t : lista) {
                %>
                    <tr>
                        <td><%= t.getIdTipoProtocolo() %></td>
                        <td><%= t.getNombreProtocolo() %></td>
                        <td><%= t.getDescripcion() %></td>
                    </tr>
                <%
                        }
                    } else {
                %>
                    <tr>
                        <td colspan="3" class="text-muted">No se encontraron tipos de protocolo.</td>
                    </tr>
                <% } %>
                </tbody>
            </table>

            <div class="text-center mt-3">
                <a href="ControladorProtocolos?accion=menu" class="btn btn-secondary">
                    ← Volver a Gestión de Protocolos
                </a>
            </div>

        </div>
    </div>

</body>
</html>
