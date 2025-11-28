package servicios;

import db.ConexionGenericaDB;

public class FactoriaServiciosImpl implements iFactoria {

    private static String motor;
    private static FactoriaServiciosImpl factoriaServicios;
    private static ConexionGenericaDB conexionGenericaDB;

    public void setMotor(String motor) {
        FactoriaServiciosImpl.motor = motor;
    }

    public synchronized static FactoriaServiciosImpl getFactoria() {

        if (factoriaServicios == null) {
            factoriaServicios = new FactoriaServiciosImpl();
        }
        return factoriaServicios;
    }

    @Override
    public synchronized ConexionGenericaDB getConexionDB() throws java.lang.ClassNotFoundException, java.lang.InstantiationException, java.lang.IllegalAccessException, java.lang.Exception {

        if (conexionGenericaDB == null) {
            switch (this.motor) {
                case "mysql":
                    conexionGenericaDB = (ConexionGenericaDB) Class.forName("db.ConexionMySql").newInstance();  //Netbeans 17
                    //conexionGenericaDB = (ConexionGenericaDB) Class.forName("db.ConexionMySql").getDeclaredConstructor().newInstance();
                    break;

                case "sqlite3":
                    conexionGenericaDB = (ConexionGenericaDB) Class.forName("db.ConexionSqlite3").newInstance();  //Netbeans 17
                    //conexionGenericaDB = (ConexionGenericaDB) Class.forName("db.ConexionSqlite3").getDeclaredConstructor().newInstance();
                    break;

                case "oracle":
                    conexionGenericaDB = (ConexionGenericaDB) Class.forName("db.ConexionOracleXE").newInstance(); //Netbeans 17
                    //conexionGenericaDB = (ConexionGenericaDB) Class.forName("db.ConexionOracleXE").getDeclaredConstructor().newInstance();
                    break;
            }
        }
        return conexionGenericaDB;
    }

}
