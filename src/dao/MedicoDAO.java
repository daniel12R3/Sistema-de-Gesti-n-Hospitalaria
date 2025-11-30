package dao;

import config.ConexionOracle;
import model.Medico;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicoDAO {

    // ============================================================
    // INSERTAR MÉDICO (PROCEDIMIENTO)
    // ============================================================
    public boolean insertar(Medico m) throws Exception {
        String sql = "{ call sp_insertar_medico(?, ?, ?, ?) }";

        try (Connection c = ConexionOracle.conectar();
             CallableStatement cs = c.prepareCall(sql)){

            cs.setString(1, m.getNombre());
            cs.setString(2, m.getApellido());
            cs.setString(3, m.getCmp());
            cs.setInt(4, m.getIdEspecialidad());

            return cs.executeUpdate() > 0;
        }
    }

    // ============================================================
    // BUSCAR POR ID
    // ============================================================
    public Medico buscarPorId(int idMedico) throws Exception {
        String sql = "SELECT * FROM MEDICO WHERE IDMEDICO = ?";

        try (Connection c = ConexionOracle.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, idMedico);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Medico m = new Medico();
                    m.setIdMedico(rs.getInt("IDMEDICO"));
                    m.setNombre(rs.getString("NOMBRE"));
                    m.setApellido(rs.getString("APELLIDO"));
                    m.setCmp(rs.getString("CMP"));
                    m.setIdEspecialidad(rs.getInt("IDESPECIALIDAD"));
                    return m;
                }
            }
        }
        return null;
    }

    // ============================================================
    // BUSCAR POR CMP
    // ============================================================
    public Medico buscarPorCMP(String cmp) throws Exception {
        String sql = "SELECT * FROM MEDICO WHERE UPPER(cmp) = UPPER(?)";

        try (Connection c = ConexionOracle.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, cmp);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Medico m = new Medico();
                m.setIdMedico(rs.getInt("IDMEDICO"));
                m.setNombre(rs.getString("NOMBRE"));
                m.setApellido(rs.getString("APELLIDO"));
                m.setCmp(rs.getString("CMP"));
                m.setIdEspecialidad(rs.getInt("IDESPECIALIDAD"));
                return m;
            }
        }
        return null;
    }

    // ============================================================
    // LISTAR
    // ============================================================
    public List<Medico> listar() throws Exception {
        List<Medico> lista = new ArrayList<>();
        String sql = "SELECT * FROM MEDICO ORDER BY NOMBRE, APELLIDO";

        try (Connection c = ConexionOracle.conectar();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Medico m = new Medico();
                m.setIdMedico(rs.getInt("IDMEDICO"));
                m.setNombre(rs.getString("NOMBRE"));
                m.setApellido(rs.getString("APELLIDO"));
                m.setCmp(rs.getString("CMP"));
                m.setIdEspecialidad(rs.getInt("IDESPECIALIDAD"));
                lista.add(m);
            }
        }
        return lista;
    }

    // ============================================================
    // ACTUALIZAR (PROCEDIMIENTO)
    // ============================================================
    public boolean actualizar(Medico m) throws Exception {
        String sql = "{ call sp_actualizar_medico(?, ?, ?, ?, ?) }";

        try (Connection c = ConexionOracle.conectar();
             CallableStatement cs = c.prepareCall(sql)){

            cs.setInt(1, m.getIdMedico());
            cs.setString(2, m.getNombre());
            cs.setString(3, m.getApellido());
            cs.setString(4, m.getCmp());
            cs.setInt(5, m.getIdEspecialidad());

            return cs.executeUpdate() > 0;
        }
    }

    // ============================================================
    // ELIMINAR (PROCEDIMIENTO)
    // ============================================================
    public boolean eliminar(int idMedico) throws Exception {
        String sql = "{ call sp_eliminar_medico(?) }";

        try (Connection c = ConexionOracle.conectar();
             CallableStatement cs = c.prepareCall(sql)) {

            cs.setInt(1, idMedico);
            return cs.executeUpdate() > 0;
        }
    }

    // ============================================================
    // MOSTRAR TOTAL GANADO (JOIN con PagoMedico)
    // ============================================================
    public List<String> totalGanadoPorMedico() throws Exception {
        List<String> out = new ArrayList<>();

        String sql =
                "SELECT m.idmedico, m.nombre || ' ' || m.apellido AS medico, " +
                        "NVL(SUM(pm.montototal),0) AS total " +
                        "FROM MEDICO m LEFT JOIN PAGOMEDICO pm ON m.idmedico = pm.idmedico " +
                        "GROUP BY m.idmedico, m.nombre, m.apellido " +
                        "ORDER BY total DESC";

        try (Connection c = ConexionOracle.conectar();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String linea = rs.getInt("IDMEDICO") + " | " +
                        rs.getString("medico") + " | total: " +
                        rs.getDouble("total");
                out.add(linea);
            }
        }
        return out;
    }
}
