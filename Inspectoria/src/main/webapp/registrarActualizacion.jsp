<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="modelo.Protocolo"%>
<%@page import="modelo.Usuario"%>
<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Registrar actualización del Protocolo</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

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


<div style="position: fixed; top: 10px; right: 20px; text-align: right;
            z-index: 999; font-size: 0.85rem; background: rgba(255,255,255,0.9);
            padding: 6px 10px; border-radius: 6px; box-shadow: 0 0 5px rgba(0,0,0,0.15);">
    Usuario:
    <strong><%= (usuario != null ? usuario.getUsuario() : "N/D") %></strong><br>
    Inicio sesión:
    <%= (horaLogin != null ? sdf.format(horaLogin) : "N/D") %>
</div>

<div class="container mt-5">

    <h2 class="text-center text-primary mb-4">Registrar actualización del protocolo</h2>

    <%
        String error = (String) request.getAttribute("error");
        String mensaje = (String) request.getAttribute("mensaje");
        Protocolo p = (Protocolo) request.getAttribute("protocolo");

        if (error != null) {
    %>
    <div class="alert alert-danger"><%= error %></div>
    <% } %>
    <% if (mensaje != null) { %>
    <div class="alert alert-success"><%= mensaje %></div>
    <% } %>

    <% if (p == null) { %>
        <div class="alert alert-warning">No se encontró el protocolo.</div>
        <a href="ControladorProtocolos?accion=listar" class="btn btn-secondary">Volver</a>
    <% } else { %>

    <div class="card mb-4">
        <div class="card-body">
            <p><strong>ID Protocolo:</strong> <%= p.getIdProtocolo() %></p>
            <p><strong>Fecha/Hora:</strong> <%= p.getFechaHora() %></p>
            <p><strong>Estado actual:</strong> <%= p.getEstado() %></p>
        </div>
    </div>

    <form action="ControladorProtocolos" method="post" class="shadow p-4 bg-white rounded">

        <input type="hidden" name="accion" value="guardarActualizacion">
        <input type="hidden" name="idProtocolo" value="<%= p.getIdProtocolo() %>">

        <div class="mb-3">
            <label class="form-label">Nueva observación:</label>
            <textarea class="form-control" name="texto" rows="4"
                      placeholder="Agregue una nueva observación" required></textarea>
        </div>

        <hr>

        <h5>Agregar nuevos alumnos involucrados (opcional)</h5>

        <div class="mb-3 position-relative">
            <label class="form-label">Buscar Alumno</label>
            <input type="text" class="form-control" id="buscadorAlumnos"
                   placeholder="Escriba nombre, apellido o RUT para buscar..." autocomplete="off">

            <div id="listaSugerencias"
                 class="list-group position-absolute w-100"
                 style="z-index: 1000; max-height: 200px; overflow-y: auto;">
            </div>
        </div>

        <div class="mb-3">
            <label class="form-label">RUT(s) a agregar</label>
            <input type="text" class="form-control" name="rutsAlumnos" id="inputRuts"
                   placeholder="Los RUTs seleccionados aparecerán aquí...">
            <small class="form-text text-muted">
                Separe cada RUT con coma. El sistema solo aceptará alumnos que ya estén registrados
                y que aún no estén asociados a este protocolo.
            </small>
        </div>

        <div class="d-flex justify-content-between mt-4">
            <button class="btn btn-primary" type="submit">
                <i class="fas fa-save"></i> Guardar actualización
            </button>
            <a href="ControladorProtocolos?accion=verDetalle&idProtocolo=<%= p.getIdProtocolo() %>"
               class="btn btn-secondary">
                <i class="fas fa-arrow-left"></i> Cancelar
            </a>
        </div>

    </form>

    <% } %>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://kit.fontawesome.com/a076d05399.js"></script>

<script>
    const inputBuscador = document.getElementById('buscadorAlumnos');
    const listaSugerencias = document.getElementById('listaSugerencias');
    const inputRuts = document.getElementById('inputRuts');

    if (inputBuscador) {
        inputBuscador.addEventListener('keyup', function () {
            const texto = this.value.trim();

            if (texto.length < 2) {
                listaSugerencias.innerHTML = '';
                return;
            }

            fetch('ControladorProtocolos?accion=buscarAlumnosJSON&q=' + encodeURIComponent(texto))
                .then(response => {
                    const contentType = response.headers.get("content-type");
                    if (!contentType || !contentType.includes("application/json")) {
                        throw new Error("El servidor no devolvió JSON.");
                    }
                    return response.json();
                })
                .then(data => {
                    let html = '';
                    if (data.length === 0) {
                        html = '<div class="list-group-item text-muted">No se encontraron resultados para "' + texto + '"</div>';
                    } else {
                        data.forEach(function (alumno) {
                            html += '<a href="#" class="list-group-item list-group-item-action" ';
                            html += 'onclick="agregarAlumno(\'' + alumno.rut + '\', \'' + alumno.nombre + '\'); return false;">';
                            html += '<strong>' + alumno.nombre + '</strong> - ' + alumno.curso + '<br>';
                            html += '<small class="text-muted">RUT: ' + alumno.rut + '</small>';
                            html += '</a>';
                        });
                    }
                    listaSugerencias.innerHTML = html;
                    listaSugerencias.style.display = 'block';
                })
                .catch(err => {
                    console.error('Error:', err);
                });
        });

        document.addEventListener('click', function (e) {
            if (e.target !== inputBuscador) {
                listaSugerencias.innerHTML = '';
            }
        });
    }

    function agregarAlumno(rut, nombre) {
        let valorActual = inputRuts.value.trim();

        if (valorActual.includes(rut)) {
            alert('El alumno ' + nombre + ' ya está en la lista.');
            listaSugerencias.innerHTML = '';
            inputBuscador.value = '';
            return;
        }

        if (valorActual.length > 0 && !valorActual.endsWith(',')) {
            valorActual += ',';
        }

        inputRuts.value = valorActual + rut;

        listaSugerencias.innerHTML = '';
        inputBuscador.value = '';
        inputBuscador.focus();
    }
</script>

</body>
</html>
