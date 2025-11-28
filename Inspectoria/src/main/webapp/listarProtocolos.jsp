<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="modelo.Protocolo"%>
<%@page import="modelo.TipoProtocolo"%>
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

    
    List<Protocolo> listaProtocolos = (List<Protocolo>) request.getAttribute("listaProtocolos");
    List<TipoProtocolo> listaTipos = (List<TipoProtocolo>) request.getAttribute("listaTipos");
    Map<Integer, String> mapaTipos = (Map<Integer, String>) request.getAttribute("mapaTipos");

    String mensaje = (String) request.getAttribute("mensaje");
    String error = (String) request.getAttribute("error");

    String textoAlumno = request.getParameter("textoAlumno") != null ? request.getParameter("textoAlumno") : "";
    String fechaDesde = request.getParameter("fechaDesde") != null ? request.getParameter("fechaDesde") : "";
    String fechaHasta = request.getParameter("fechaHasta") != null ? request.getParameter("fechaHasta") : "";
    String estadoSel = request.getParameter("estado");
    if (estadoSel == null || estadoSel.isEmpty()) {
        estadoSel = "TODOS";
    }

    String idTipoFiltroStr = request.getParameter("idTipoFiltro");
    int idTipoFiltro = -1;
    try {
        if (idTipoFiltroStr != null && !idTipoFiltroStr.isEmpty()) {
            idTipoFiltro = Integer.parseInt(idTipoFiltroStr);
        }
    } catch (NumberFormatException e) {
        idTipoFiltro = -1;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Historial de Protocolos</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }
        h1 {
            margin-bottom: 10px;
        }
        .filtros {
            border: 1px solid #ccc;
            padding: 15px;
            margin-bottom: 15px;
            background-color: #f9f9f9;
            border-radius: 8px;
        }
        .filtros div {
            margin-bottom: 8px;
        }
        label {
            display: inline-block;
            width: 160px;
        }
        input[type="text"], input[type="date"], select {
            padding: 4px;
            width: 220px;
        }
        table {
            border-collapse: collapse;
            width: 100%;
            background-color: #fff;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 6px;
            text-align: left;
        }
        th {
            background-color: #eee;
        }
        .acciones a {
            margin-right: 6px;
        }
        .mensaje-ok {
            color: green;
            margin-bottom: 10px;
        }
        .mensaje-error {
            color: red;
            margin-bottom: 10px;
        }
    </style>
</head>
<body>

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

    

    <h1>Historial de Protocolos</h1>

    <div class="mb-3">
        <a href="ControladorProtocolos?accion=menu" class="btn btn-primary">
            Volver al Menú de Protocolos
        </a>
    </div>

    <% if (mensaje != null) { %>
        <div class="mensaje-ok"><%= mensaje %></div>
    <% } %>

    <% if (error != null) { %>
        <div class="mensaje-error"><%= error %></div>
    <% } %>

    <div class="filtros">
        <form method="get" action="ControladorProtocolos">
            <input type="hidden" name="accion" value="listar"/>

            <div>
                <label>Alumno (RUT o nombre):</label>
                <input type="text" name="textoAlumno" value="<%= textoAlumno %>"/>
            </div>

            <div>
                <label>Fecha desde:</label>
                <input type="date" name="fechaDesde" value="<%= fechaDesde %>"/>
            </div>

            <div>
                <label>Fecha hasta:</label>
                <input type="date" name="fechaHasta" value="<%= fechaHasta %>"/>
            </div>

            <div>
                <label>Tipo de protocolo:</label>
                <select name="idTipoFiltro">
                    <option value="">Todos</option>
                    <% if (listaTipos != null) {
                           for (TipoProtocolo tp : listaTipos) { %>
                        <option value="<%= tp.getIdTipoProtocolo() %>"
                                <%= (tp.getIdTipoProtocolo() == idTipoFiltro ? "selected" : "") %>>
                            <%= tp.getNombreProtocolo() %>
                        </option>
                    <%   }
                       } %>
                </select>
            </div>

            <div>
                <label>Estado:</label>
                <select name="estado">
                    <option value="TODOS" <%= "TODOS".equals(estadoSel) ? "selected" : "" %>>Todos</option>
                    <option value="NUEVO" <%= "NUEVO".equals(estadoSel) ? "selected" : "" %>>Nuevo</option>
                    <option value="EN_INVESTIGACION" <%= "EN_INVESTIGACION".equals(estadoSel) ? "selected" : "" %>>En investigación</option>
                    <option value="CERRADO" <%= "CERRADO".equals(estadoSel) ? "selected" : "" %>>Cerrado</option>
                    <option value="ANULADO" <%= "ANULADO".equals(estadoSel) ? "selected" : "" %>>Anulado</option>
                </select>
            </div>

            <div class="mt-2">
                <button type="submit" class="btn btn-primary">Buscar</button>
                <a href="ControladorProtocolos?accion=listar"
                   class="btn btn-danger">
                    Limpiar filtros
                </a>
            </div>
        </form>
    </div>

    <table class="table table-striped table-bordered">
        <thead>
            <tr>
                <th>ID Protocolo</th>
                <th>Fecha / Hora</th>
                <th>Tipo</th>
                <th>Estado</th>
                <th>Detalle</th>
            </tr>
        </thead>
        <tbody>
            <% if (listaProtocolos != null && !listaProtocolos.isEmpty()) {
                   for (Protocolo p : listaProtocolos) { %>
                <tr>
                    <td><%= p.getIdProtocolo() %></td>
                    <td><%= p.getFechaHora() %></td>
                    <td>
                        <%
                            String nombreTipo = null;
                            if (mapaTipos != null) {
                                nombreTipo = mapaTipos.get(p.getIdTipoProtocolo());
                            }
                            if (nombreTipo == null) {
                                nombreTipo = "ID tipo: " + p.getIdTipoProtocolo();
                            }
                        %>
                        <%= nombreTipo %>
                    </td>
                    <td><%= p.getEstado() %></td>
                    <td class="acciones">
                        <a href="ControladorProtocolos?accion=verDetalle&idProtocolo=<%= p.getIdProtocolo() %>"
                           class="btn btn-sm btn-outline-primary">
                            Ver detalle
                        </a>
                    </td>
                </tr>
            <%   }
               } else { %>
                <tr>
                    <td colspan="5">No hay protocolos para mostrar.</td>
                </tr>
            <% } %>
        </tbody>
    </table>

</body>
</html>
