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
<html lang="es">
<head>
    <meta charset="UTF-8">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">
    <title>Gestión de Protocolos</title>
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

        
        <h1 class="mb-4 text-center text-primary">Gestión de Protocolos</h1>

        
        <a class="btn btn-primary btn-lg mb-3 d-block mx-auto" 
           href="${pageContext.request.contextPath}/ControladorProtocolos?accion=registrarNuevo">
            <i class="fas fa-plus-circle"></i> Registrar nuevo protocolo
        </a>

        
        <a class="btn btn-info btn-lg mb-3 d-block mx-auto" 
           href="ControladorProtocolos?accion=listar">
            <i class="fas fa-list"></i> Detalle de protocolos registrados (Buscar, Actualizar, Anular y Cerrar)
        </a>

        
        <a class="btn btn-secondary btn-lg d-block mx-auto" 
           href="irAlCRUDinspectoria.jsp">
            <i class="fas fa-arrow-left"></i> Volver al Módulo de Inspectoría
        </a>

    </div>

    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
