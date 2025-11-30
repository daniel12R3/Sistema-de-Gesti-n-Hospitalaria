package dao;

import config.ConexionOracle;
import model.Especialidad;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EspecialidadDAO {

    // INSERTAR (SP)
    public boolean insertar(Especialidad e) throws Exception {
        String sql = "{ call sp_insertar_especialidad(?) }";
        try (Connection c = ConexionOracle.conectar();
             CallableStatement cs = c.prepareCall(sql)) {

            cs.setString(1, e.getNombreEspecialidad());
            return cs.executeUpdate() > 0;
        }
    }

    // ACTUALIZAR (SP)
    public boolean actualizar(Especialidad e) throws Exception {
        String sql = "{ call sp_actualizar_especialidad(?, ?) }";
        try (Connection c = ConexionOracle.conectar();
             CallableStatement cs = c.prepareCall(sql)) {

            cs.setInt(1, e.getIdEspecialidad());
            cs.setString(2, e.getNombreEspecialidad());
            return cs.executeUpdate() > 0;
        }
    }

    // ELIMINAR (SP)
    public boolean eliminar(int id) throws Exception {
        String sql = "{ call sp_eliminar_especialidad(?) }";
        try (Connection c = ConexionOracle.conectar();
             CallableStatement cs = c.prepareCall(sql)) {

            cs.setInt(1, id);
            return cs.executeUpdate() > 0;
        }
    }

    // BUSCAR POR ID (SP)
    public Especialidad buscarPorId(int id) throws Exception {
        String sql = "{ call sp_buscar_especialidad(?, ?) }";

        try (Connection c = ConexionOracle.conectar();
             CallableStatement cs = c.prepareCall(sql)) {

            cs.setInt(1, id);
            cs.registerOutParameter(2, oracle.jdbc.OracleTypes.CURSOR);

            cs.execute();
            ResultSet rs = (ResultSet) cs.getObject(2);

            if (rs.next()) {
                Especialidad e = new Especialidad();
                e.setIdEspecialidad(rs.getInt("IDESPECIALIDAD"));
                e.setNombreEspecialidad(rs.getString("NOMBREESPECIALIDAD"));
                return e;
            }
        }
        return null;
    }

    // LISTAR DESDE VISTA
    public List<Especialidad> listar() throws Exception {
        List<Especialidad> lista = new ArrayList<>();
        String sql = "SELECT * FROM vw_especialidad";

        try (Connection c = ConexionOracle.conectar();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Especialidad e = new Especialidad();
                e.setIdEspecialidad(rs.getInt("IDESPECIALIDAD"));
                e.setNombreEspecialidad(rs.getString("NOMBREESPECIALIDAD"));
                lista.add(e);
            }
        }
        return lista;
    }

    // CONTAR MÉDICOS POR ESPECIALIDAD (SP)
    public List<String> contarMedicos() throws Exception {
        List<String> lista = new ArrayList<>();
        String sql = "{ call sp_contar_medicos_especialidad(?) }";

        try (Connection c = ConexionOracle.conectar();
             CallableStatement cs = c.prepareCall(sql)) {

            cs.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);

            cs.execute();
            ResultSet rs = (ResultSet) cs.getObject(1);

            while (rs.next()) {
                String linea = rs.getString("NOMBREESPECIALIDAD") +
                        " | Cantidad médicos: " + rs.getInt("CANTIDAD");
                lista.add(linea);
            }
        }
        return lista;
    }
}


