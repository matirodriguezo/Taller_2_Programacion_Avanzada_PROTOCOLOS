package db;

import java.sql.DriverManager;

public final class ConexionMySql extends ConexionGenericaDB
{
    
    public ConexionMySql() throws java.lang.ClassNotFoundException, java.lang.InstantiationException, java.lang.IllegalAccessException, java.sql.SQLException, java.lang.Exception
    {
this("jdbc:mysql://", "com.mysql.cj.jdbc.Driver", "localhost", ":3306",
     "prgavz", "?useSSL=false&serverTimezone=America/Santiago&characterEncoding=latin1",
     "root", "1234");
    } 
    

    public ConexionMySql(String jdbc, String driverClass, String host, String port, String database, String opciones, String username, String password) throws java.lang.ClassNotFoundException, java.lang.InstantiationException, java.lang.IllegalAccessException, java.sql.SQLException, java.lang.Exception
    {
        super(jdbc, driverClass, host, port, database, opciones, username, password);
        super.conexion = DriverManager.getConnection(super.url, username, password);
        super.databaseMetadata = conexion.getMetaData();
    }
      
}
