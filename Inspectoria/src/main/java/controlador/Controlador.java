package controlador;

import modelo.Persona;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.ExcepcionPersonalizada;


public class Controlador extends HttpServlet {

    private String urlVistaListar="vistas/read.jsp";
    private String urlVistaAgregar="vistas/create.jsp";
    private String urlVistaActualizar="vistas/update.jsp";
    
    private Persona modelo=new Persona();

    private int id;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Controlador Controlador.java</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Controlador at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

/*
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        PrintWriter out = response.getWriter();
        String urlRuta="";
        String action=request.getParameter("accion");
        
        try
        {
            if(action.equalsIgnoreCase("listar"))
            {
                ArrayList aRPersonas = (ArrayList) modelo.listar();
                
                request.setAttribute("aRPersonas", aRPersonas); 
                
                urlRuta=urlVistaListar;            
            }
            
            if(action.equalsIgnoreCase("add"))
            {
                urlRuta=urlVistaAgregar;
            }
        
            if(action.equalsIgnoreCase("Agregar"))
            {
                String dni=request.getParameter("txtDni");
                String nom=request.getParameter("txtNom");
             
                urlRuta = urlVistaAgregar;
                
                modelo.setDni(dni);
                modelo.setNombre(nom);
                
                
                int result = modelo.agregar();
                
                if (result==1)
                {
                    request.setAttribute("alertaMensaje", "Registro agregado exitosamente");
                    urlRuta=urlVistaListar;
                }
                
                ArrayList aRPersonas = (ArrayList) modelo.listar();
                
                request.setAttribute("aRPersonas", aRPersonas); 
                
                
            } 
        
            if(action.equalsIgnoreCase("Editar"))
            {
                request.setAttribute("idper",request.getParameter("id"));
                
                Persona p = modelo.obtener(Integer.parseInt(request.getParameter("id")));
                
                request.setAttribute("persona",p); 
                
                urlRuta=urlVistaActualizar;
            }

            if(action.equalsIgnoreCase("Actualizar"))
            {
                id=Integer.parseInt(request.getParameter("txtid"));

                String dni=request.getParameter("txtDni");
                String nom=request.getParameter("txtNom");

                modelo.setId(id);
                modelo.setDni(dni);
                modelo.setNombre(nom);

                if (modelo.actualizar()==1)
                {
                    request.setAttribute("alertaMensaje", "Registro actualizado exitosamente");
                }
                
                ArrayList aRPersonas = (ArrayList) modelo.listar();
                
                request.setAttribute("aRPersonas", aRPersonas); 

                urlRuta=urlVistaListar;
            }

            if(action.equalsIgnoreCase("Eliminar"))
            {
                id=Integer.parseInt(request.getParameter("id"));

                modelo.setId(id);

                if (modelo.eliminar()==1)
                {
                    request.setAttribute("alertaMensaje", "Registro eliminado exitosamente");
                }
                
                ArrayList aRPersonas = (ArrayList) modelo.listar();
                
                request.setAttribute("aRPersonas", aRPersonas); 

                urlRuta=urlVistaListar;
            }
        }
        /*
        catch (org.sqlite.SQLiteException ex)
        {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("alertaMensaje", "EXCEPCION:"+ ex.getMessage());
        }
        
        catch (IllegalAccessException  | java.lang.ClassNotFoundException | java.lang.InstantiationException ex)
        {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("alertaMensaje", "EXCEPCION:"+ ex.getMessage());
        }
        catch (ExcepcionPersonalizada ex)
        {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("alertaMensaje", "EXCEPCION: " + ex.getMessage());;
        }
        catch (Exception ex)
        {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("alertaMensaje", "EXCEPCION:"+ ex.getMessage());;
        }
        
        RequestDispatcher vista=request.getRequestDispatcher(urlRuta);
        
        vista.forward(request, response);
    }

*/
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

   
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
