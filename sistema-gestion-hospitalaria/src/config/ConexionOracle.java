package config;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionOracle {

    private static final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
    private static final String USER = "millones";
    private static final String PASSWORD = "Oracle123";

    public static Connection conectar() throws Exception {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

