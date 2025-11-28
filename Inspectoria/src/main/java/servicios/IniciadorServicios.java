package servicios;

//import db.ConexionMySql;
//import db.ConexionOracleXE;
import db.ConexionMySql;
import db.ConexionSqlite3;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.logging.Level;
import java.util.logging.Logger;


@WebListener
public class IniciadorServicios implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("La aplicacion web ha iniciado.");
        inicializarRecursosAplicacion();
    }

    
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("La aplicacion web se está deteniendo.");
        liberarRecursosAplicacion();
    }

    
    private void inicializarRecursosAplicacion() {
        System.out.println("Inicializando recursos...");        
        try
        {
            //sqlite3
            //FactoriaServiciosImpl.getFactoria().setMotor("sqlite3");
            //ConexionSqlite3 cbd = (ConexionSqlite3)FactoriaServiciosImpl.getFactoria().getConexionDB();
            
            //myql
            FactoriaServiciosImpl.getFactoria().setMotor("mysql");
            ConexionMySql cbd = (ConexionMySql)FactoriaServiciosImpl.getFactoria().getConexionDB();
            
            //Oracle
            //FactoriaServiciosImpl.getFactoria().setMotor("oracle");
            //ConexionOracleXE cbd = (ConexionOracleXE)FactoriaServiciosImpl.getFactoria().getConexionDB();

        }
        catch (ClassNotFoundException ex)
        {
            Logger.getLogger(IniciadorServicios.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (InstantiationException | IllegalAccessException | java.lang.reflect.InvocationTargetException ex)
        {
            Logger.getLogger(IniciadorServicios.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (Exception ex)
        {
            Logger.getLogger(IniciadorServicios.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }

    
    private void liberarRecursosAplicacion() {
        // Código para limpiar recursos
        System.out.println("Limpiando recursos...");
    }
}