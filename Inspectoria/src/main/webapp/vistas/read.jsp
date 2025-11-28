<%@page import="java.util.ArrayList"%>
<%@page import="modelo.Persona"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/bootstrap.css" rel="stylesheet" type="text/css"/>
        <title>JSP Page</title>
    </head>
    <body>
        <div class="container">
        <h1>CRUD Personas</h1>
        
        <script type="text/javascript">
            <% 
                String mensaje = (String) request.getAttribute("alertaMensaje");
                if (mensaje != null && !mensaje.isEmpty())
                {
            %>
                    alert('<%= mensaje %>');
            <% 
                } 
            %>
        </script>
            
            
            <a class="btn btn-success" href="Controlador?accion=add">Agregar Nuevo</a>
            <br>
            <br>
            
            
            <table  class="table table-bordered">
                <thead>
                    <tr>
                        <th class="text-center">ID</th>
                        <th class="text-center">DNI</th>
                        <th class="text-center">NOMBRES</th>
                        <th class="text-center">ACCIONES</th>
                    </tr>
                </thead>
                <%
                    ArrayList registros = (ArrayList) request.getAttribute("aRPersonas");
                    if (registros != null && registros.size()!=0)
                    {
                        for (Object registro : registros)
                        {
                            Persona p = (Persona)registro;
                %>
                            <tr>
                                <td><%= p.getId() %></td>
                                <td><%= p.getDni() %></td>
                                <td><%= p.getNombre() %></td>
                                <td class="text-center">
                                    <a class="btn btn-warning" href="Controlador?accion=Editar&id=<%= p.getId()%>">Editar</a>
                                    <a class="btn btn-danger" href="Controlador?accion=Eliminar&id=<%= p.getId()%>">Eliminar</a>
                                </td>
                            </tr>
                <%
                        }
                    }
                    else
                    {
                %>
                        <tr>
                        <td>No hay registros disponibles.</td> 
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        </tr>
                <%
                    }
                %>
            </table>
        </div>
    </body>
</html>
