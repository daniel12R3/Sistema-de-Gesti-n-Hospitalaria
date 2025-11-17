package dao;

import config.ConexionOracle;
import model.Paciente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PacienteDAO {

    public boolean insertar(Paciente p) throws Exception {
        String sql = "INSERT INTO PACIENTE (DNI, NOMBRE, APELLIDO, TELEFONO, DIRECCION, TIPOPAGO) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection c = ConexionOracle.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, p.getDni());
            ps.setString(2, p.getNombre());
            ps.setString(3, p.getApellido());
            ps.setString(4, p.getTelefono());
            ps.setString(5, p.getDireccion());
            ps.setString(6, p.getTipoPago());
            return ps.executeUpdate() > 0;
        }
    }

    public Paciente buscarPorId(int id) throws Exception {
        String sql = "SELECT * FROM PACIENTE WHERE IDPACIENTE = ?";
        try (Connection c = ConexionOracle.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1,id);
            try (ResultSet rs = ps.executeQuery()){
                if(rs.next()){
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
        String sql = "SELECT * FROM PACIENTE WHERE DNI = ?";
        try (Connection c = ConexionOracle.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1,dni);
            try (ResultSet rs = ps.executeQuery()){
                if(rs.next()){
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

    public List<Paciente> listar() throws Exception {
        List<Paciente> lista = new ArrayList<>();
        String sql = "SELECT * FROM PACIENTE ORDER BY NOMBRE, APELLIDO";
        try (Connection c = ConexionOracle.conectar();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()){
            while(rs.next()){
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

    // ejemplo join: paciente con total pagado (usa PAGOPACIENTE -> CITA -> PACIENTE)
    public List<String> resumenPagosPacientes() throws Exception {
        List<String> out = new ArrayList<>();
        String sql = "SELECT pac.idpaciente, pac.nombre || ' ' || pac.apellido AS paciente, NVL(SUM(pp.monto),0) AS total " +
                "FROM PACIENTE pac " +
                "LEFT JOIN CITA c ON pac.idpaciente = c.idpaciente " +
                "LEFT JOIN PAGOPACIENTE pp ON c.idcita = pp.idcita " +
                "GROUP BY pac.idpaciente, pac.nombre, pac.apellido ORDER BY total DESC";
        try (Connection c = ConexionOracle.conectar();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()){
            while(rs.next()){
                String s = rs.getInt(1) + " | " + rs.getString("paciente") + " | total: " + rs.getDouble("total");
                out.add(s);
            }
        }
        return out;
    }

    public boolean eliminar(int idPaciente) throws Exception {
        String sql = "DELETE FROM paciente WHERE idpaciente = ?";
        try (Connection con = ConexionOracle.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idPaciente);
            int filas = ps.executeUpdate();
            return filas > 0;
        }
    }

    public boolean actualizar(Paciente p) throws Exception {
        String sql = "UPDATE PACIENTE SET NOMBRE=?, APELLIDO=?, TELEFONO=?, DIRECCION=?, TIPOPAGO=? WHERE IDPACIENTE=?";
        try (Connection c = ConexionOracle.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getApellido());
            ps.setString(3, p.getTelefono());
            ps.setString(4, p.getDireccion());
            ps.setString(5, p.getTipoPago());
            ps.setInt(6, p.getIdPaciente());
            return ps.executeUpdate() > 0;
        }
    }

}
