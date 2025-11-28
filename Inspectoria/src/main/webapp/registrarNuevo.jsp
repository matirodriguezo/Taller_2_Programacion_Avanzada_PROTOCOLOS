<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
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
    Date horaLogin  = (Date) sesion.getAttribute("horaLogin");
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Registrar nuevo protocolo</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

    
    <div style="position: fixed; top: 10px; right: 20px; text-align: right;
                z-index: 999; font-size: 0.85rem; background: #fff;
                padding: 6px 12px; border-radius: 8px;
                box-shadow: 0 2px 8px rgba(0,0,0,0.15);">
        Usuario: <strong><%= (usuario != null ? usuario.getUsuario() : "N/D") %></strong><br>
        Inicio sesión: <%= (horaLogin != null ? sdf.format(horaLogin) : "N/D") %>
    </div>

    <div class="container mt-5">

        <h1 class="text-center mb-4 text-primary">Registrar Nuevo Protocolo</h1>

        <%
            String error   = (String) request.getAttribute("error");
            String mensaje = (String) request.getAttribute("mensaje");
            if (error != null) {
        %>
        <div class="alert alert-danger"><%= error %></div>
        <% } %>
        <% if (mensaje != null) { %>
        <div class="alert alert-success"><%= mensaje %></div>
        <% } %>

        <form action="ControladorProtocolos" method="post" class="shadow p-4 bg-white rounded">

            <input type="hidden" name="accion" value="guardarNuevo">

           
            <div class="mb-3">
                <label class="form-label">Tipo de protocolo</label>
                <select name="idTipoProtocolo" class="form-select" required>
                    <option value="">Seleccione el tipo de protocolo</option>
                    <%
                        List<TipoProtocolo> listaTipos =
                            (List<TipoProtocolo>) request.getAttribute("listaTipos");
                        if (listaTipos != null) {
                            for (TipoProtocolo tp : listaTipos) {
                    %>
                    <option value="<%= tp.getIdTipoProtocolo() %>">
                        <%= tp.getNombreProtocolo() %>
                    </option>
                    <%
                            }
                        }
                    %>
                </select>
            </div>

            
            <div class="mb-3">
                <label class="form-label">Descripción u observación inicial</label>
                <textarea name="descripcion" class="form-control" rows="4"
                          placeholder="Describa el hecho que ocurrió para la activación del protocolo"
                          required></textarea>
            </div>

            <hr>

           
            <h5 class="text-secondary">Alumnos involucrados</h5>

            <div class="mb-3 position-relative">
                <label class="form-label">Buscar y agregar alumnos</label>
                <input type="text" class="form-control" id="buscadorAlumnos"
                       placeholder="Escriba nombre, apellido o RUT para buscar..." autocomplete="off">

                <div id="listaSugerencias"
                     class="list-group position-absolute w-100"
                     style="z-index: 1000; max-height: 200px; overflow-y: auto;">
                </div>
            </div>

            <div class="mb-3">
                <label class="form-label">Alumnos seleccionados (RUTs)</label>
                <input type="text" class="form-control" name="rutsAlumnos" id="inputRuts"
                       placeholder="Los RUTs aparecerán aquí...">
                <small class="form-text text-muted">
                    Puede escribir manualmente (separado por comas) o usar el buscador de arriba.
                </small>
            </div>

            <div class="d-flex justify-content-between mt-4">
                <button type="submit" class="btn btn-primary">
                    <i class="fas fa-save"></i> Guardar registro de protocolo
                </button>
                <a href="ControladorProtocolos?accion=menu" class="btn btn-secondary">
                    <i class="fas fa-arrow-left"></i> Volver a Gestión de Protocolos
                </a>
            </div>
        </form>
    </div>

    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://kit.fontawesome.com/a076d05399.js"></script>

    <script>
        const inputBuscador    = document.getElementById('buscadorAlumnos');
        const listaSugerencias = document.getElementById('listaSugerencias');
        const inputRuts        = document.getElementById('inputRuts');

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
                        throw new Error("El servidor no devolvió JSON. Probablemente redirigió al index.jsp");
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
                .catch(err => console.error('Error:', err));
        });

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

        document.addEventListener('click', function (e) {
            if (e.target !== inputBuscador) {
                listaSugerencias.innerHTML = '';
            }
        });
    </script>

</body>
</html>
