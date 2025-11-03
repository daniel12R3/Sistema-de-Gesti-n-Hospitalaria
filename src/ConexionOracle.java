import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ConexionOracle {

    public static void main(String[] args) {

        // Datos de conexión
        String url = "jdbc:oracle:thin:@localhost:1521:XE";
        String user = "system";
        String password = "Oracle123"; // según tu configuración

        Connection conn = null;

        try {
            // Cargar el driver JDBC de Oracle
            Class.forName("oracle.jdbc.driver.OracleDriver");
            System.out.println("Driver cargado correctamente ✅");

            // Conectar a Oracle
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Conexión exitosa a Oracle ✅");

            // Crear consulta
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM PACIENTE");

            // Mostrar los datos de la tabla PACIENTE
            System.out.println("\n📌 LISTA DE PACIENTES:");
            System.out.println("----------------------------");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("ID_PACIENTE") +
                        ", Nombre: " + rs.getString("NOMBRE") +
                        ", Apellido: " + rs.getString("APELLIDO"));
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            try {
                if (conn != null) conn.close();
                System.out.println("\nConexión cerrada ✅");
            } catch (Exception ex) {
                System.out.println("Error al cerrar: " + ex.getMessage());
            }
        }
    }
}
