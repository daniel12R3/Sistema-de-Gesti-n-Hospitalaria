package dao;

import config.ConexionOracle;
import model.Medico;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicoDAO {

    public boolean insertar(Medico m) throws Exception {
        String sql = "INSERT INTO MEDICO (NOMBRE, APELLIDO, CMP, IDESPECIALIDAD) VALUES (?, ?, ?, ?)";
        try (Connection c = ConexionOracle.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1,m.getNombre());
            ps.setString(2,m.getApellido());
            ps.setString(3,m.getCmp());
            ps.setInt(4,m.getIdEspecialidad());
            return ps.executeUpdate() > 0;
        }
    }

    public Medico buscarPorId(int id) throws Exception {
        String sql = "SELECT * FROM MEDICO WHERE IDMEDICO = ?";
        try (Connection c = ConexionOracle.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1,id);
            try (ResultSet rs = ps.executeQuery()){
                if(rs.next()){
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

    public List<Medico> listar() throws Exception {
        List<Medico> list = new ArrayList<>();
        String sql = "SELECT * FROM MEDICO ORDER BY NOMBRE, APELLIDO";
        try (Connection c = ConexionOracle.conectar();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()){
            while(rs.next()){
                Medico m = new Medico();
                m.setIdMedico(rs.getInt("IDMEDICO"));
                m.setNombre(rs.getString("NOMBRE"));
                m.setApellido(rs.getString("APELLIDO"));
                m.setCmp(rs.getString("CMP"));
                m.setIdEspecialidad(rs.getInt("IDESPECIALIDAD"));
                list.add(m);
            }
        }
        return list;
    }

    // join: medico y total ganado (pagomedico)
    public List<String> totalGanadoPorMedico() throws Exception {
        List<String> out = new ArrayList<>();
        String sql = "SELECT m.idmedico, m.nombre || ' ' || m.apellido AS medico, NVL(SUM(pm.montototal),0) AS total " +
                "FROM MEDICO m LEFT JOIN PAGOMEDICO pm ON m.idmedico = pm.idmedico " +
                "GROUP BY m.idmedico, m.nombre, m.apellido ORDER BY total DESC";
        try (Connection c = ConexionOracle.conectar();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()){
            while(rs.next()){
                out.add(rs.getInt(1) + " | " + rs.getString("medico") + " | total: " + rs.getDouble("total"));
            }
        }
        return out;
    }

    public boolean eliminar(int id) throws Exception {
        String sql = "DELETE FROM MEDICO WHERE idmedico = ?";

        try (Connection con = ConexionOracle.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            int filas = ps.executeUpdate();
            return filas > 0; // Devuelve true si eliminó algún registro
        }
    }

    public boolean actualizar(Medico m) throws Exception {
        String sql = "UPDATE MEDICO SET NOMBRE = ?, APELLIDO = ?, CMP = ?, IDESPECIALIDAD = ? WHERE IDMEDICO = ?";

        try (Connection con = ConexionOracle.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, m.getNombre());
            ps.setString(2, m.getApellido());
            ps.setString(3, m.getCmp());
            ps.setInt(4, m.getIdEspecialidad());
            ps.setInt(5, m.getIdMedico());

            int filas = ps.executeUpdate();
            return filas > 0;
        }
    }

    public Medico buscarPorCMP(String cmp) throws Exception {
        String sql = "SELECT * FROM MEDICO WHERE CMP = ?";

        try (Connection con = ConexionOracle.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, cmp);
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


}
