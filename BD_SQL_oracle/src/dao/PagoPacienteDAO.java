package dao;

import config.ConexionOracle;
import model.PagoPaciente;
import model.Paciente;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PagoPacienteDAO {

    public boolean insertar(PagoPaciente p) throws Exception {
        String sql = "INSERT INTO PAGOPACIENTE (MONTO, FECHAPAGO, TIPOPAGO, IDCITA) VALUES (?, ?, ?, ?)";
        try (Connection c = ConexionOracle.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setDouble(1, p.getMonto());
            ps.setDate(2, Date.valueOf(p.getFechapago()));
            ps.setString(3, p.getTipopago());
            ps.setInt(4, p.getIdcita());
            return ps.executeUpdate() > 0;
        }
    }

    public List<PagoPaciente> listar() throws Exception {
        List<PagoPaciente> out = new ArrayList<>();
        String sql = "SELECT * FROM PAGOPACIENTE ORDER BY FECHAPAGO DESC";
        try (Connection c = ConexionOracle.conectar();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()){
            while(rs.next()){
                PagoPaciente p = new PagoPaciente();
                p.setIdpagopaciente(rs.getInt("IDPAGOPACIENTE"));
                p.setMonto(rs.getDouble("MONTO"));
                Date d = rs.getDate("FECHAPAGO"); if(d!=null) p.setFechapago(d.toLocalDate());
                p.setTipopago(rs.getString("TIPOPAGO"));
                p.setIdcita(rs.getInt("IDCITA"));
                out.add(p);
            }
        }
        return out;
    }

    // total pagado por paciente (JOIN CITA -> PAGOPACIENTE -> PACIENTE) con formato fecha
    public List<String> totalPagadoPorPaciente() throws Exception {
        List<String> out = new ArrayList<>();
        String sql = "SELECT pac.idpaciente, pac.nombre || ' ' || pac.apellido AS paciente, NVL(SUM(pp.monto),0) AS total " +
                "FROM PACIENTE pac LEFT JOIN CITA c ON pac.idpaciente = c.idpaciente LEFT JOIN PAGOPACIENTE pp ON c.idcita = pp.idcita " +
                "GROUP BY pac.idpaciente, pac.nombre, pac.apellido ORDER BY total DESC";
        try (Connection c = ConexionOracle.conectar();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()){
            while(rs.next()){
                out.add(rs.getInt(1) + " | " + rs.getString("paciente") + " | total: " + rs.getDouble("total"));
            }
        }
        return out;
    }

    public List<PagoPaciente> listarPorCita(int idCita) throws Exception {
        List<PagoPaciente> out = new ArrayList<>();
        String sql = "SELECT * FROM PAGOPACIENTE WHERE IDCITA = ? ORDER BY FECHAPAGO";
        try (Connection c = ConexionOracle.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, idCita);
            try (ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    PagoPaciente p = new PagoPaciente();
                    p.setIdpagopaciente(rs.getInt("IDPAGOPACIENTE"));
                    p.setMonto(rs.getDouble("MONTO"));
                    Date d = rs.getDate("FECHAPAGO"); if(d!=null) p.setFechapago(d.toLocalDate());
                    p.setTipopago(rs.getString("TIPOPAGO"));
                    p.setIdcita(rs.getInt("IDCITA"));
                    out.add(p);
                }
            }
        }
        return out;
    }
}
