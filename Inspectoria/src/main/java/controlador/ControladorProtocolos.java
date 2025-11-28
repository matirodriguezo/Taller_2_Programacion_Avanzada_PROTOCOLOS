package controlador;

import dao.AlumnoDAO;
import dao.ObservacionProtocoloDAO;
import dao.ProtocoloAlumnoDAO;
import dao.ProtocoloDAO;
import dao.TipoProtocoloDAO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.Alumno;
import modelo.ObservacionProtocolo;
import modelo.Protocolo;
import modelo.TipoProtocolo;
import modelo.Usuario;
import java.util.Map;
import java.util.HashMap;

@WebServlet(name = "ControladorProtocolos", urlPatterns = {"/ControladorProtocolos"})
public class ControladorProtocolos extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");
        if (accion == null || accion.trim().isEmpty()) {
            accion = "menu";
        }

        switch (accion) {
            case "menu":
                irAlMenu(request, response);
                break;

            case "registrarNuevo":
                mostrarFormularioNuevo(request, response);
                break;

            case "guardarNuevo":
                guardarNuevoProtocolo(request, response);
                break;

            case "listar":
                listarProtocolos(request, response);
                break;

            case "verDetalle":
                verDetalleProtocolo(request, response);
                break;

            case "actualizar":
                mostrarFormularioActualizar(request, response);
                break;

            case "guardarActualizacion":
                guardarActualizacion(request, response);
                break;

            case "cambiarEstado":
                cambiarEstadoProtocolo(request, response);
                break;

            case "anular":
                mostrarFormularioAnular(request, response);
                break;

            case "guardarAnulacion":
                guardarAnulacion(request, response);
                break;
            case "buscarAlumnosJSON":
                buscarAlumnosJSON(request, response);
                break;

            default:
                System.out.println("Acción no reconocida en ControladorProtocolos: " + accion);
                response.sendRedirect("index.jsp");
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    private void irAlMenu(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("menuProtocolos.jsp");
        rd.forward(request, response);
    }

    private void mostrarFormularioNuevo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        TipoProtocoloDAO tpDAO = new TipoProtocoloDAO();
        List<TipoProtocolo> listaTipos = tpDAO.listar();
        request.setAttribute("listaTipos", listaTipos);

        RequestDispatcher rd = request.getRequestDispatcher("registrarNuevo.jsp");
        rd.forward(request, response);
    }

    private void guardarNuevoProtocolo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sesion = request.getSession(false);
        if (sesion == null || sesion.getAttribute("usuarioLogeado") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        Usuario usuario = (Usuario) sesion.getAttribute("usuarioLogeado");
        int idUsuario = usuario.getId();

        String idTipoStr = request.getParameter("idTipoProtocolo");
        String descripcion = request.getParameter("descripcion");
        String rutsAlumnosStr = request.getParameter("rutsAlumnos");

        if (idTipoStr == null || idTipoStr.isEmpty()
                || descripcion == null || descripcion.trim().isEmpty()) {

            request.setAttribute("error", "Debe seleccionar un tipo de protocolo y escribir la descripción del hecho.");
            mostrarFormularioNuevo(request, response);
            return;
        }

        int idTipo = Integer.parseInt(idTipoStr);

        Protocolo protocolo = new Protocolo();
        protocolo.setEstado("NUEVO");
        protocolo.setIdTipoProtocolo(idTipo);
        protocolo.setIdUsuarioCreador(idUsuario);

        ProtocoloDAO pDAO = new ProtocoloDAO();
        int idGenerado = pDAO.crear(protocolo);

        if (idGenerado <= 0) {
            request.setAttribute("error", "No se pudo guardar el protocolo. Intente nuevamente.");
            irAlMenu(request, response);
            return;
        }

        ObservacionProtocolo obs = new ObservacionProtocolo();
        obs.setIdProtocolo(idGenerado);
        obs.setIdUsuario(idUsuario);
        obs.setTexto(descripcion.trim());
        obs.setTipo("INICIAL");

        ObservacionProtocoloDAO oDAO = new ObservacionProtocoloDAO();
        boolean creada = oDAO.crear(obs);

        if (creada) {
            request.setAttribute("mensaje", "Protocolo registrado correctamente con ID " + idGenerado + ".");
        } else {
            request.setAttribute("error", "El protocolo se creó, pero ocurrió un problema al guardar la observación inicial.");
        }

        if (rutsAlumnosStr != null && !rutsAlumnosStr.trim().isEmpty()) {

            AlumnoDAO alumnoDAO = new AlumnoDAO();
            ProtocoloAlumnoDAO paDAO = new ProtocoloAlumnoDAO();

            String[] ruts = rutsAlumnosStr.split(",");
            Set<Integer> idsAgregados = new HashSet<>();
            List<String> rutsNoEncontrados = new ArrayList<>();
            List<String> rutsDuplicados = new ArrayList<>();
            int okCount = 0;

            for (String rutRaw : ruts) {
                String rut = rutRaw.trim();
                if (rut.isEmpty()) {
                    continue;
                }

                Alumno al = alumnoDAO.buscarPorRut(rut);
                if (al == null) {
                    rutsNoEncontrados.add(rut);
                    continue;
                }

                int idAl = al.getIdAlumno();
                if (idsAgregados.contains(idAl)) {
                    rutsDuplicados.add(rut);
                    continue;
                }

                boolean asociado = paDAO.agregar(idGenerado, idAl);
                if (asociado) {
                    idsAgregados.add(idAl);
                    okCount++;
                }
            }

            String baseMsg = (String) request.getAttribute("mensaje");
            if (baseMsg == null) {
                baseMsg = "";
            }

            if (okCount > 0) {
                baseMsg += " Se asociaron " + okCount + " alumno(s) al protocolo.";
            }

            if (!rutsDuplicados.isEmpty()) {
                baseMsg += " Los siguientes RUT estaban repetidos y se ignoraron: " + String.join(", ", rutsDuplicados) + ".";
            }

            if (!rutsNoEncontrados.isEmpty()) {
                baseMsg += " Los siguientes RUT no se encontraron en el sistema: " + String.join(", ", rutsNoEncontrados) + ".";
            }

            if (!baseMsg.isEmpty()) {
                request.setAttribute("mensaje", baseMsg.trim());
            }
        }

        request.setAttribute("idProtocolo", String.valueOf(idGenerado));
        verDetalleProtocolo(request, response);
    }

    private void listarProtocolos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ProtocoloDAO pDAO = new ProtocoloDAO();
        TipoProtocoloDAO tpDAO = new TipoProtocoloDAO();

        List<TipoProtocolo> listaTipos = tpDAO.listar();
        request.setAttribute("listaTipos", listaTipos);

        String textoAlumno = request.getParameter("textoAlumno");
        String fechaDesde = request.getParameter("fechaDesde");
        String fechaHasta = request.getParameter("fechaHasta");
        String idTipoStr = request.getParameter("idTipoFiltro");
        String estado = request.getParameter("estado");

        if (textoAlumno != null && textoAlumno.trim().isEmpty()) {
            textoAlumno = null;
        }
        if (fechaDesde != null && fechaDesde.trim().isEmpty()) {
            fechaDesde = null;
        }
        if (fechaHasta != null && fechaHasta.trim().isEmpty()) {
            fechaHasta = null;
        }
        if (estado != null) {
            if (estado.trim().isEmpty() || "TODOS".equals(estado)) {
                estado = null;
            }
        }

        Integer idTipoFiltro = null;
        if (idTipoStr != null && !idTipoStr.trim().isEmpty()) {
            try {
                idTipoFiltro = Integer.parseInt(idTipoStr);
            } catch (NumberFormatException e) {
                idTipoFiltro = null;
            }
        }

        boolean hayFiltros = (textoAlumno != null)
                || (fechaDesde != null)
                || (fechaHasta != null)
                || (idTipoFiltro != null)
                || (estado != null);

        List<Protocolo> lista;

        if (hayFiltros) {
            lista = pDAO.buscarConFiltros(textoAlumno, fechaDesde, fechaHasta, idTipoFiltro, estado);
            if (lista == null || lista.isEmpty()) {
                request.setAttribute("mensaje", "No se encontraron protocolos para los filtros seleccionados.");
            } else {
                request.setAttribute("mensaje", "Se encontraron " + lista.size() + " protocolo(s).");
            }
        } else {
            lista = pDAO.listarTodos();
        }

        Map<Integer, String> mapaTipos = new HashMap<>();
        if (listaTipos != null) {
            for (TipoProtocolo tp : listaTipos) {
                mapaTipos.put(tp.getIdTipoProtocolo(), tp.getNombreProtocolo());
            }
        }
        request.setAttribute("mapaTipos", mapaTipos);

        request.setAttribute("listaProtocolos", lista);

        RequestDispatcher rd = request.getRequestDispatcher("listarProtocolos.jsp");
        rd.forward(request, response);
    }

    private void verDetalleProtocolo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idStr = request.getParameter("idProtocolo");
        if (idStr == null || idStr.trim().isEmpty()) {
            Object attr = request.getAttribute("idProtocolo");
            if (attr != null) {
                idStr = attr.toString();
            }
        }

        if (idStr == null || idStr.trim().isEmpty()) {
            request.setAttribute("error", "No se recibió el ID del protocolo.");
            listarProtocolos(request, response);
            return;
        }

        int idProtocolo;
        try {
            idProtocolo = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "El ID de protocolo no es válido.");
            listarProtocolos(request, response);
            return;
        }

        ProtocoloDAO pDAO = new ProtocoloDAO();
        ObservacionProtocoloDAO oDAO = new ObservacionProtocoloDAO();
        TipoProtocoloDAO tDAO = new TipoProtocoloDAO();
        ProtocoloAlumnoDAO paDAO = new ProtocoloAlumnoDAO();

        Protocolo protocolo = pDAO.buscarPorId(idProtocolo);
        if (protocolo == null) {
            request.setAttribute("error", "No se encontró un protocolo con el ID especificado.");
            listarProtocolos(request, response);
            return;
        }

        TipoProtocolo tipo = tDAO.buscarPorId(protocolo.getIdTipoProtocolo());
        List<ObservacionProtocolo> listaObs = oDAO.listarPorProtocolo(idProtocolo);
        List<Alumno> listaAlumnos = paDAO.listarAlumnosPorProtocolo(idProtocolo);

        request.setAttribute("protocolo", protocolo);
        request.setAttribute("tipoProtocolo", tipo);
        request.setAttribute("listaObservaciones", listaObs);
        request.setAttribute("listaAlumnosProtocolo", listaAlumnos);

        RequestDispatcher rd = request.getRequestDispatcher("verDetalleProtocolo.jsp");
        rd.forward(request, response);
    }

    private void mostrarFormularioActualizar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idStr = request.getParameter("idProtocolo");
        if (idStr == null) {
            request.setAttribute("error", "No se recibió el ID.");
            listarProtocolos(request, response);
            return;
        }

        int id = Integer.parseInt(idStr);

        ProtocoloDAO pDAO = new ProtocoloDAO();
        Protocolo p = pDAO.buscarPorId(id);

        if (p == null) {
            request.setAttribute("error", "No se encontró el protocolo.");
            listarProtocolos(request, response);
            return;
        }

        if (p.getEstado().equals("ANULADO") || p.getEstado().equals("CERRADO")) {
            request.setAttribute("error", "Este protocolo no puede ser actualizado porque está "
                    + p.getEstado() + ".");
            request.setAttribute("idProtocolo", String.valueOf(id));
            verDetalleProtocolo(request, response);
            return;
        }

        request.setAttribute("protocolo", p);
        RequestDispatcher rd = request.getRequestDispatcher("registrarActualizacion.jsp");
        rd.forward(request, response);
    }

    private void guardarActualizacion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sesion = request.getSession(false);
        if (sesion == null || sesion.getAttribute("usuarioLogeado") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        Usuario usuario = (Usuario) sesion.getAttribute("usuarioLogeado");
        int idUsuario = usuario.getId();

        int idProtocolo = Integer.parseInt(request.getParameter("idProtocolo"));
        String texto = request.getParameter("texto");
        String rutsAlumnosStr = request.getParameter("rutsAlumnos");

        if (texto == null || texto.trim().isEmpty()) {
            request.setAttribute("error", "Debe escribir una observación.");
            mostrarFormularioActualizar(request, response);
            return;
        }

        ObservacionProtocolo obs = new ObservacionProtocolo(
                idProtocolo,
                idUsuario,
                texto.trim(),
                "SEGUIMIENTO"
        );

        ObservacionProtocoloDAO oDAO = new ObservacionProtocoloDAO();
        boolean ok = oDAO.crear(obs);

        if (!ok) {
            request.setAttribute("error", "No se pudo registrar la actualización.");
        } else {
            request.setAttribute("mensaje", "Actualización registrada correctamente.");
        }

        if (rutsAlumnosStr != null && !rutsAlumnosStr.trim().isEmpty()) {

            AlumnoDAO alumnoDAO = new AlumnoDAO();
            ProtocoloAlumnoDAO paDAO = new ProtocoloAlumnoDAO();

            java.util.List<modelo.Alumno> actuales = paDAO.listarAlumnosPorProtocolo(idProtocolo);
            java.util.Set<Integer> idsYaAsociados = new java.util.HashSet<>();
            for (modelo.Alumno a : actuales) {
                idsYaAsociados.add(a.getIdAlumno());
            }

            String[] ruts = rutsAlumnosStr.split(",");
            java.util.List<String> rutsNoEncontrados = new java.util.ArrayList<>();
            java.util.List<String> rutsDuplicados = new java.util.ArrayList<>();
            int okCount = 0;

            for (String rutRaw : ruts) {
                String rut = rutRaw.trim();
                if (rut.isEmpty()) {
                    continue;
                }

                modelo.Alumno al = alumnoDAO.buscarPorRut(rut);
                if (al == null) {
                    rutsNoEncontrados.add(rut);
                    continue;
                }

                int idAl = al.getIdAlumno();
                if (idsYaAsociados.contains(idAl)) {
                    rutsDuplicados.add(rut);
                    continue;
                }

                boolean asociado = paDAO.agregar(idProtocolo, idAl);
                if (asociado) {
                    idsYaAsociados.add(idAl);
                    okCount++;
                }
            }

            String baseMsg = (String) request.getAttribute("mensaje");
            if (baseMsg == null) {
                baseMsg = "";
            }

            if (okCount > 0) {
                baseMsg += " Se asociaron " + okCount + " nuevo(s) alumno(s) al protocolo.";
            }
            if (!rutsDuplicados.isEmpty()) {
                baseMsg += " Los siguientes RUT ya estaban asociados y se ignoraron: "
                        + String.join(", ", rutsDuplicados) + ".";
            }
            if (!rutsNoEncontrados.isEmpty()) {
                baseMsg += " Los siguientes RUT no se encontraron en el sistema: "
                        + String.join(", ", rutsNoEncontrados) + ".";
            }

            if (!baseMsg.isEmpty()) {
                request.setAttribute("mensaje", baseMsg.trim());
            }
        }

        request.setAttribute("idProtocolo", String.valueOf(idProtocolo));
        verDetalleProtocolo(request, response);
    }

    private void mostrarFormularioAnular(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("idProtocolo"));
        ProtocoloDAO pDAO = new ProtocoloDAO();
        Protocolo p = pDAO.buscarPorId(id);

        if (p == null) {
            request.setAttribute("error", "No se encontró el protocolo.");
            listarProtocolos(request, response);
            return;
        }

        if (p.getEstado().equals("ANULADO") || p.getEstado().equals("CERRADO")) {
            request.setAttribute("error", "Este protocolo no admite anulación porque está "
                    + p.getEstado() + ".");
            request.setAttribute("idProtocolo", String.valueOf(id));
            verDetalleProtocolo(request, response);
            return;
        }

        request.setAttribute("protocolo", p);
        request.getRequestDispatcher("anularProtocolo.jsp").forward(request, response);
    }

    private void guardarAnulacion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sesion = request.getSession(false);
        Usuario u = (Usuario) sesion.getAttribute("usuarioLogeado");
        int idUsuario = u.getId();

        int idProtocolo = Integer.parseInt(request.getParameter("idProtocolo"));
        String motivo = request.getParameter("motivo");

        if (motivo == null || motivo.trim().isEmpty()) {
            request.setAttribute("error", "Debe escribir el motivo.");
            mostrarFormularioAnular(request, response);
            return;
        }

        ObservacionProtocoloDAO oDAO = new ObservacionProtocoloDAO();
        ProtocoloDAO pDAO = new ProtocoloDAO();

        ObservacionProtocolo obs = new ObservacionProtocolo(
                idProtocolo,
                idUsuario,
                motivo.trim(),
                "ANULACION"
        );

        boolean ok1 = oDAO.crear(obs);
        boolean ok2 = pDAO.cambiarEstado(idProtocolo, "ANULADO");

        if (ok1 && ok2) {
            request.setAttribute("mensaje", "Protocolo anulado correctamente.");
        } else {
            request.setAttribute("error", "Hubo un problema al anular el protocolo.");
        }

        request.setAttribute("idProtocolo", String.valueOf(idProtocolo));
        verDetalleProtocolo(request, response);
    }

    private void cambiarEstadoProtocolo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idStr = request.getParameter("idProtocolo");
        String nuevoEstado = request.getParameter("nuevoEstado");

        if (idStr == null || idStr.trim().isEmpty() || nuevoEstado == null || nuevoEstado.trim().isEmpty()) {
            request.setAttribute("error", "No se recibió correctamente el protocolo o el nuevo estado.");
            listarProtocolos(request, response);
            return;
        }

        int idProtocolo;
        try {
            idProtocolo = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "El ID de protocolo no es válido.");
            listarProtocolos(request, response);
            return;
        }

        ProtocoloDAO pDAO = new ProtocoloDAO();
        Protocolo p = pDAO.buscarPorId(idProtocolo);

        if (p == null) {
            request.setAttribute("error", "No se encontró el protocolo indicado.");
            listarProtocolos(request, response);
            return;
        }

        if ("ANULADO".equals(p.getEstado()) || "CERRADO".equals(p.getEstado())) {
            request.setAttribute("error", "Este protocolo ya no admite cambios de estado.");
            request.setAttribute("idProtocolo", String.valueOf(idProtocolo));
            verDetalleProtocolo(request, response);
            return;
        }

        if (!"NUEVO".equals(nuevoEstado)
                && !"EN_INVESTIGACION".equals(nuevoEstado)
                && !"CERRADO".equals(nuevoEstado)) {
            request.setAttribute("error", "El estado indicado no es válido.");
            request.setAttribute("idProtocolo", String.valueOf(idProtocolo));
            verDetalleProtocolo(request, response);
            return;
        }

        String estadoActual = p.getEstado();
        boolean transicionValida = false;

        if ("NUEVO".equals(estadoActual)) {
            if ("EN_INVESTIGACION".equals(nuevoEstado) || "CERRADO".equals(nuevoEstado)) {
                transicionValida = true;
            }
        } else if ("EN_INVESTIGACION".equals(estadoActual)) {
            if ("CERRADO".equals(nuevoEstado)) {
                transicionValida = true;
            }
        }

        if (!transicionValida) {
            request.setAttribute("error", "La transición de estado no es válida desde "
                    + estadoActual + " a " + nuevoEstado + ".");
            request.setAttribute("idProtocolo", String.valueOf(idProtocolo));
            verDetalleProtocolo(request, response);
            return;
        }

        boolean okEstado = pDAO.cambiarEstado(idProtocolo, nuevoEstado);

        if (okEstado) {
            HttpSession sesion = request.getSession(false);
            if (sesion != null && sesion.getAttribute("usuarioLogeado") != null) {
                Usuario u = (Usuario) sesion.getAttribute("usuarioLogeado");
                int idUsuario = u.getId();
                String textoCambio = "Cambio de estado: " + estadoActual + " → " + nuevoEstado;

                ObservacionProtocolo obs = new ObservacionProtocolo(
                        idProtocolo,
                        idUsuario,
                        textoCambio,
                        "SEGUIMIENTO"
                );
                ObservacionProtocoloDAO oDAO = new ObservacionProtocoloDAO();
                oDAO.crear(obs);
            }

            request.setAttribute("mensaje", "Estado actualizado correctamente a " + nuevoEstado + ".");
        } else {
            request.setAttribute("error", "No se pudo actualizar el estado del protocolo.");
        }

        request.setAttribute("idProtocolo", String.valueOf(idProtocolo));
        verDetalleProtocolo(request, response);
    }

    private void buscarAlumnosJSON(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String termino = request.getParameter("q");

        if (termino == null || termino.trim().isEmpty()) {
            response.getWriter().write("[]");
            return;
        }

        AlumnoDAO aDAO = new AlumnoDAO();
        List<Alumno> lista = aDAO.buscarPorTermino(termino); // OJO: Este método debe existir en el DAO

        StringBuilder json = new StringBuilder();
        json.append("[");
        for (int i = 0; i < lista.size(); i++) {
            Alumno a = lista.get(i);
            json.append("{");
            json.append("\"rut\":\"").append(a.getRut()).append("\",");
            json.append("\"nombre\":\"").append(a.getNombreCompleto()).append("\",");
            json.append("\"curso\":\"").append(a.getCurso()).append("\"");
            json.append("}");
            if (i < lista.size() - 1) {
                json.append(",");
            }
        }
        json.append("]");
        response.getWriter().write(json.toString());
    }
}
