package dao;

import model.Cita;
import model.Medico;
import model.Paciente;
import config.ConexionOracle;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class CitaDAO {

    // =========================================================
    // INSERTAR CITA
    // =========================================================
    public void insertar(Cita c) throws Exception {
        String sql = "{ CALL sp_insertar_cita(?,?,?,?,?,?) }";

        try (Connection cn = ConexionOracle.conectar();
             CallableStatement cs = cn.prepareCall(sql)) {

            cs.setDate(1, Date.valueOf(c.getFecha()));
            cs.setString(2, c.getHoraInicio().toString());
            cs.setString(3, c.getHoraFin().toString());
            cs.setString(4, c.getEstado());
            cs.setInt(5, c.getPaciente().getIdPaciente());
            cs.setInt(6, c.getMedico().getIdMedico());

            cs.execute();
        }
    }

    // =========================================================
    // ACTUALIZAR ESTADO
    // =========================================================
    public void actualizarEstado(int idCita, String nuevoEstado) throws Exception {
        String sql = "{ CALL sp_actualizar_estado(?,?) }";

        try (Connection cn = ConexionOracle.conectar();
             CallableStatement cs = cn.prepareCall(sql)) {

            cs.setInt(1, idCita);
            cs.setString(2, nuevoEstado);
            cs.execute();
        }
    }

    // =========================================================
    // REPROGRAMAR CITA
    // =========================================================
    public void reprogramar(Cita c) throws Exception {
        String sql = "{ CALL sp_reprogramar_cita(?,?,?,?,?,?) }";

        try (Connection cn = ConexionOracle.conectar();
             CallableStatement cs = cn.prepareCall(sql)) {

            cs.setInt(1, c.getIdCita());
            cs.setDate(2, Date.valueOf(c.getFecha()));
            cs.setString(3, c.getHoraInicio().toString());
            cs.setString(4, c.getHoraFin().toString());
            cs.setInt(5, c.getPaciente().getIdPaciente());
            cs.setInt(6, c.getMedico().getIdMedico());

            cs.execute();
        }
    }

    // =========================================================
    // LISTAR CITAS (simple)
    // =========================================================
    public List<Cita> listar() throws Exception {
        List<Cita> lista = new ArrayList<>();
        String sql = "SELECT * FROM CITA ORDER BY FECHA DESC, HORAINICIO";

        try (Connection cn = ConexionOracle.conectar();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Cita c = new Cita();
                c.setIdCita(rs.getInt("idcita"));
                c.setFecha(rs.getDate("fecha").toLocalDate());
                c.setHoraInicio(LocalTime.parse(rs.getString("horainicio")));
                c.setHoraFin(LocalTime.parse(rs.getString("horafin")));
                c.setEstado(rs.getString("estado"));

                lista.add(c);
            }
        }
        return lista;
    }

    // =========================================================
    // LISTAR DETALLADO (usa vista)
    // =========================================================
    public List<Cita> listarDetallado() throws Exception {
        List<Cita> lista = new ArrayList<>();
        String sql = "SELECT * FROM vw_citas_detalle";

        try (Connection cn = ConexionOracle.conectar();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Paciente p = new Paciente();
                p.setNombre(rs.getString("paciente"));

                Medico m = new Medico();
                m.setNombre(rs.getString("medico"));

                Cita c = new Cita();
                c.setIdCita(rs.getInt("idcita"));
                c.setFecha(LocalDate.parse(rs.getString("fecha_txt")));
                c.setHoraInicio(LocalTime.parse(rs.getString("horainicio")));
                c.setHoraFin(LocalTime.parse(rs.getString("horafin")));
                c.setEstado(rs.getString("estado"));
                c.setPaciente(p);
                c.setMedico(m);

                lista.add(c);
            }
        }
        return lista;
    }

    // =========================================================
    // ELIMINAR CITA
    // =========================================================
    public void eliminar(int idCita) throws Exception {
        String sql = "{ CALL sp_eliminar_cita(?) }";

        try (Connection cn = ConexionOracle.conectar();
             CallableStatement cs = cn.prepareCall(sql)) {

            cs.setInt(1, idCita);
            cs.execute();
        }
    }

    // =========================================================
    // LISTAR LOG
    // =========================================================
    public List<String> listarLog() throws Exception {
        List<String> lista = new ArrayList<>();
        String sql = "SELECT * FROM LOG_CITA ORDER BY fecha_mod DESC";

        try (Connection c = ConexionOracle.conectar();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String linea =
                        "LOG " + rs.getInt("idlog") +
                                " | Cita: " + rs.getInt("idcita") +
                                " | " + rs.getString("estado_anterior") +
                                " -> " + rs.getString("estado_nuevo") +
                                " | " + rs.getDate("fecha_mod") +
                                " | " + rs.getString("usuario");

                lista.add(linea);
            }
        }
        return lista;
    }
}


