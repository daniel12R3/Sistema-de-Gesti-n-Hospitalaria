package dao;

import config.ConexionOracle;
import model.PagoMedico;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PagoMedicoDAO {

    public boolean insertar(PagoMedico pm) throws Exception {
        String sql = "INSERT INTO PAGOMEDICO (MONTOTOTAL, PERIODOPAGO, FECHAGENERACION, IDMEDICO) VALUES (?, ?, ?, ?)";
        try (Connection c = ConexionOracle.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setDouble(1, pm.getMontototal());
            ps.setString(2, pm.getPeriodopago());
            ps.setDate(3, Date.valueOf(pm.getFechageneracion()));
            ps.setInt(4, pm.getIdmedico());
            return ps.executeUpdate() > 0;
        }
    }

    public List<PagoMedico> listar() throws Exception {
        List<PagoMedico> out = new ArrayList<>();
        String sql = "SELECT * FROM PAGOMEDICO ORDER BY FECHAGENERACION DESC";
        try (Connection c = ConexionOracle.conectar();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()){
            while(rs.next()){
                PagoMedico pm = new PagoMedico();
                pm.setIdpagomedico(rs.getInt("IDPAGOMEDICO"));
                pm.setMontototal(rs.getDouble("MONTOTOTAL"));
                pm.setPeriodopago(rs.getString("PERIODOPAGO"));
                Date d = rs.getDate("FECHAGENERACION");
                if(d!=null) pm.setFechageneracion(d.toLocalDate());
                pm.setIdmedico(rs.getInt("IDMEDICO"));
                out.add(pm);
            }
        }
        return out;
    }

    // join con medico para reporte
    public List<String> listarConMedico() throws Exception {
        List<String> out = new ArrayList<>();
        String sql = "SELECT pm.idpagomedico, pm.montototal, pm.periodopago, TO_CHAR(pm.fechageneracion,'DD/MM/YYYY') AS f, m.nombre || ' ' || m.apellido AS medico " +
                "FROM PAGOMEDICO pm JOIN MEDICO m ON pm.idmedico = m.idmedico ORDER BY pm.fechageneracion DESC";
        try (Connection c = ConexionOracle.conectar();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()){
            while(rs.next()){
                out.add("PagoMedico " + rs.getInt(1) + " | Monto: " + rs.getDouble(2) +
                        " | Periodo: " + rs.getString(3) + " | Fecha: " + rs.getString("f") +
                        " | Medico: " + rs.getString("medico"));
            }
        }
        return out;
    }
}
