package db;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class ConexionOracleXE extends ConexionGenericaDB
{
    
    public ConexionOracleXE() throws java.lang.ClassNotFoundException, java.lang.InstantiationException, java.lang.IllegalAccessException, java.sql.SQLException, java.lang.Exception
    {
        this("jdbc:oracle:thin:@", "oracle.jdbc.driver.OracleDriver", "localhost", "1521", "XE", "", "leo", "leo");
    } 
    

    public ConexionOracleXE(String jdbc, String driverClass, String host, String port, String database, String opciones, String username, String password) throws java.lang.ClassNotFoundException, java.lang.InstantiationException, java.lang.IllegalAccessException, java.sql.SQLException, java.lang.Exception
    {
        super(jdbc, driverClass, host, port, database, opciones, username, password);
        conexion = DriverManager.getConnection(super.url, username, password);
        databaseMetadata = conexion.getMetaData();
    }
    
}
