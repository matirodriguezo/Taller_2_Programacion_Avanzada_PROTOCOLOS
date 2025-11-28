package db;

import java.lang.reflect.InvocationTargetException;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConexionSqlite3 extends ConexionGenericaDB
{

    public ConexionSqlite3() throws java.lang.ClassNotFoundException, java.lang.InstantiationException, java.lang.IllegalAccessException, java.sql.SQLException, java.lang.Exception
    {
        this("jdbc:sqlite:", "org.sqlite.JDBC", "c:/sqlite/", ""     , "prgavz.db"        , "", ""         , "");
    } 
  

    public ConexionSqlite3(String jdbc, String driverClass, String host, String port, String database, String opciones, String username, String password) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
        super(jdbc, driverClass, host, port, database, opciones, username, password);
        super.conexion = DriverManager.getConnection(super.url, username, password);
        super.databaseMetadata = conexion.getMetaData();
    }
  
}
