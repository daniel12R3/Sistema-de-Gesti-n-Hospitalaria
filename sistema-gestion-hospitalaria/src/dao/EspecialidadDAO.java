package dao;

import config.ConexionOracle;
import model.Especialidad;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EspecialidadDAO {

    public boolean insertar(Especialidad e) throws Exception {
        String sql = "INSERT INTO ESPECIALIDAD (NOMBREESPECIALIDAD) VALUES (?)";
        try (Connection c = ConexionOracle.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1,e.getNombreEspecialidad());
            return ps.executeUpdate() > 0;
        }
    }

    public Especialidad buscarPorId(int id) throws Exception {
        String sql = "SELECT * FROM ESPECIALIDAD WHERE IDESPECIALIDAD = ?";
        try (Connection c = ConexionOracle.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1,id);
            try (ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    Especialidad e = new Especialidad();
                    e.setIdEspecialidad(rs.getInt("IDESPECIALIDAD"));
                    e.setNombreEspecialidad(rs.getString("NOMBREESPECIALIDAD"));
                    return e;
                }
            }
        }
        return null;
    }

    public List<Especialidad> listar() throws Exception {
        List<Especialidad> out = new ArrayList<>();
        String sql = "SELECT * FROM ESPECIALIDAD ORDER BY NOMBREESPECIALIDAD";
        try (Connection c = ConexionOracle.conectar();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()){
            while(rs.next()){
                Especialidad e = new Especialidad();
                e.setIdEspecialidad(rs.getInt("IDESPECIALIDAD"));
                e.setNombreEspecialidad(rs.getString("NOMBREESPECIALIDAD"));
                out.add(e);
            }
        }
        return out;
    }

    // listar cantidad de medicos por especialidad
    public List<String> contarMedicos() throws Exception {
        List<String> out = new ArrayList<>();
        String sql = "SELECT e.nombreespecialidad, COUNT(m.idmedico) AS cantidad " +
                "FROM ESPECIALIDAD e LEFT JOIN MEDICO m ON e.idespecialidad = m.idespecialidad " +
                "GROUP BY e.nombreespecialidad ORDER BY cantidad DESC";
        try (Connection c = ConexionOracle.conectar();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()){
            while(rs.next()){
                out.add(rs.getString(1) + " -> " + rs.getInt(2));
            }
        }
        return out;
    }
}
