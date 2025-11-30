package dao;

import config.ConexionOracle;
import model.PagoMedico;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PagoMedicoDAO {

    // === INSERTAR usando procedimiento almacenado ===
    public boolean insertar(PagoMedico pm) throws Exception {
        String sql = "{ call sp_insertar_pagomedico(?, ?, ?, ?) }";
        try (Connection c = ConexionOracle.conectar();
             CallableStatement cs = c.prepareCall(sql)) {

            cs.setDouble(1, pm.getMontototal());
            cs.setString(2, pm.getPeriodopago());
            cs.setDate(3, Date.valueOf(pm.getFechageneracion()));
            cs.setInt(4, pm.getIdmedico());

            cs.execute();
            return true;
        }
    }

    // === LISTAR NORMAL ===
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
                if(d != null) pm.setFechageneracion(d.toLocalDate());

                pm.setIdmedico(rs.getInt("IDMEDICO"));
                out.add(pm);
            }
        }
        return out;
    }

    // === LISTAR usando la vista vw_pago_medico_detalle ===
    public List<String> listarConMedico() throws Exception {
        List<String> out = new ArrayList<>();
        String sql = "SELECT * FROM vw_pago_medico_detalle ORDER BY fechageneracion DESC";

        try (Connection c = ConexionOracle.conectar();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()){

            while(rs.next()){
                out.add(
                        "PagoMedico " + rs.getInt("idpagomedico") +
                                " | Monto: " + rs.getDouble("montototal") +
                                " | Periodo: " + rs.getString("periodopago") +
                                " | Fecha: " + rs.getString("fechageneracion") +
                                " | Medico: " + rs.getString("medico")
                );
            }
        }
        return out;
    }
}
