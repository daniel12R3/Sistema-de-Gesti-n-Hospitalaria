package dao;

import config.ConexionOracle;
import model.PagoPaciente;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PagoPacienteDAO {

    // ---------------------------------------------------------------
    // INSERTAR usando Stored Procedure con validación extra
    // ---------------------------------------------------------------
    public boolean insertar(PagoPaciente p) throws Exception {

        // Validación estricta en DAO (redundante pero seguro)
        String t = p.getTipopago() == null ? "" : p.getTipopago().trim().toUpperCase();
        if (!(t.equals("EFECTIVO") || t.equals("TARJETA") || t.equals("YAPE") || t.equals("PLIN"))) {
            throw new SQLException("Tipo de pago no permitido en DAO");
        }
        p.setTipopago(t); // Aseguramos mayúsculas

        String sql = "{ call SP_INSERTAR_PAGO_PACIENTE(?,?,?,?) }";
        try (Connection c = ConexionOracle.conectar();
             CallableStatement cs = c.prepareCall(sql)) {

            cs.setDouble(1, p.getMonto());
            cs.setDate(2, Date.valueOf(p.getFechapago()));
            cs.setString(3, p.getTipopago());
            cs.setInt(4, p.getIdcita());

            cs.execute();
            return true;
        }
    }

    // ----------------------- Resto de métodos sin cambios -----------------------
    public List<PagoPaciente> listar() throws Exception {
        List<PagoPaciente> out = new ArrayList<>();
        String sql = "SELECT * FROM VW_PAGO_PACIENTE";
        try (Connection c = ConexionOracle.conectar();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                PagoPaciente p = new PagoPaciente();
                p.setIdpagopaciente(rs.getInt("IDPAGOPACIENTE"));
                p.setMonto(rs.getDouble("MONTO"));

                Date d = rs.getDate("FECHAPAGO");
                if (d != null) p.setFechapago(d.toLocalDate());

                p.setTipopago(rs.getString("TIPOPAGO"));
                p.setIdcita(rs.getInt("IDCITA"));
                out.add(p);
            }
        }
        return out;
    }

    public List<String> totalPagadoPorPaciente() throws Exception {
        List<String> out = new ArrayList<>();
        String sql = "SELECT * FROM VW_TOTAL_PAGADO_PACIENTE ORDER BY TOTAL DESC";

        try (Connection c = ConexionOracle.conectar();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                out.add(
                        rs.getInt("IDPACIENTE") + " | " +
                                rs.getString("PACIENTE") + " | total: " +
                                rs.getDouble("TOTAL")
                );
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

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PagoPaciente p = new PagoPaciente();
                    p.setIdpagopaciente(rs.getInt("IDPAGOPACIENTE"));
                    p.setMonto(rs.getDouble("MONTO"));

                    Date d = rs.getDate("FECHAPAGO");
                    if (d != null) p.setFechapago(d.toLocalDate());

                    p.setTipopago(rs.getString("TIPOPAGO"));
                    p.setIdcita(rs.getInt("IDCITA"));
                    out.add(p);
                }
            }
        }
        return out;
    }
}
