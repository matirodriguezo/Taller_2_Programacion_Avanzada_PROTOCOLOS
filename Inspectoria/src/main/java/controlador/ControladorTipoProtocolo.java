package controlador;

import dao.TipoProtocoloDAO;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;    
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.TipoProtocolo;

@WebServlet(name = "ControladorTipoProtocolo", urlPatterns = {"/ControladorTipoProtocolo"})
public class ControladorTipoProtocolo extends HttpServlet {

    private final TipoProtocoloDAO dao = new TipoProtocoloDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");
        if (accion == null) {
            accion = "listar";
        }

        switch (accion) {
            case "listar":
                listarTipos(request, response);
                break;
            default:
                
                response.sendRedirect("index.jsp");
        }
    }

    private void listarTipos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<TipoProtocolo> lista = dao.listar();
        request.setAttribute("listaTipos", lista);
        request.getRequestDispatcher("tiposProtocolos.jsp").forward(request, response);
    }
}

