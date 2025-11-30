package dao;

import config.ConexionOracle;
import model.Paciente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PacienteDAO {

    public boolean insertar(Paciente p) throws Exception {
        String sql = "{ call sp_insertar_paciente(?, ?, ?, ?, ?, ?) }";

        try (Connection conn = ConexionOracle.conectar();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setString(1, p.getDni());
            cs.setString(2, p.getNombre());
            cs.setString(3, p.getApellido());
            cs.setString(4, p.getTelefono());
            cs.setString(5, p.getDireccion());
            cs.setString(6, p.getTipoPago());

            return cs.executeUpdate() > 0;
        }
    }

    public Paciente buscarPorId(int id) throws Exception {
        String sql = "SELECT * FROM PACIENTE WHERE IDPACIENTE = ?";
        try (Connection c = ConexionOracle.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Paciente p = new Paciente();
                    p.setIdPaciente(rs.getInt("IDPACIENTE"));
                    p.setDni(rs.getString("DNI"));
                    p.setNombre(rs.getString("NOMBRE"));
                    p.setApellido(rs.getString("APELLIDO"));
                    p.setTelefono(rs.getString("TELEFONO"));
                    p.setDireccion(rs.getString("DIRECCION"));
                    p.setTipoPago(rs.getString("TIPOPAGO"));
                    return p;
                }
            }
        }
        return null;
    }

    public Paciente buscarPorDni(String dni) throws Exception {
        String sql = "{ call sp_buscar_paciente(?, ?) }";

        try (Connection conn = ConexionOracle.conectar();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setString(1, dni);
            cs.registerOutParameter(2, oracle.jdbc.OracleTypes.CURSOR);

            cs.execute();

            ResultSet rs = (ResultSet) cs.getObject(2);

            if (rs.next()) {
                Paciente p = new Paciente();
                p.setIdPaciente(rs.getInt("IDPACIENTE"));
                p.setDni(rs.getString("DNI"));
                p.setNombre(rs.getString("NOMBRE"));
                p.setApellido(rs.getString("APELLIDO"));
                p.setTelefono(rs.getString("TELEFONO"));
                p.setDireccion(rs.getString("DIRECCION"));
                p.setTipoPago(rs.getString("TIPOPAGO"));
                return p;
            }
        }
        return null;
    }

    public List<Paciente> listar() throws Exception {
        List<Paciente> lista = new ArrayList<>();
        String sql = "SELECT * FROM PACIENTE ORDER BY NOMBRE, APELLIDO";

        try (Connection c = ConexionOracle.conectar();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Paciente p = new Paciente();
                p.setIdPaciente(rs.getInt("IDPACIENTE"));
                p.setDni(rs.getString("DNI"));
                p.setNombre(rs.getString("NOMBRE"));
                p.setApellido(rs.getString("APELLIDO"));
                p.setTelefono(rs.getString("TELEFONO"));
                p.setDireccion(rs.getString("DIRECCION"));
                p.setTipoPago(rs.getString("TIPOPAGO"));

                lista.add(p);
            }
        }
        return lista;
    }

    public List<String> resumenPagosPacientes() throws Exception {
        List<String> out = new ArrayList<>();

        String sql =
                "SELECT * FROM VW_RESUMEN_PAGOS_PACIENTE ORDER BY total DESC";

        try (Connection c = ConexionOracle.conectar();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String s = rs.getInt("idpaciente") + " | " +
                        rs.getString("paciente") +
                        " | total: " + rs.getDouble("total");
                out.add(s);
            }
        }
        return out;
    }

    public boolean eliminar(int idPaciente) throws Exception {
        String sql = "{ call sp_eliminar_paciente(?) }";

        try (Connection conn = ConexionOracle.conectar();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, idPaciente);
            return cs.executeUpdate() > 0;
        }
    }

    public boolean actualizar(Paciente p) throws Exception {
        String sql = "{ call sp_actualizar_paciente(?,?,?,?,?,?) }";

        try (Connection c = ConexionOracle.conectar();
             CallableStatement cs = c.prepareCall(sql)) {

            cs.setInt(1, p.getIdPaciente());
            cs.setString(2, p.getNombre());
            cs.setString(3, p.getApellido());
            cs.setString(4, p.getTelefono());
            cs.setString(5, p.getDireccion());
            cs.setString(6, p.getTipoPago());

            return cs.executeUpdate() > 0;
        }
    }
}
