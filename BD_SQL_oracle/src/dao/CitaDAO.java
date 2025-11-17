package dao;

import config.ConexionOracle;
import model.Cita;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class CitaDAO {

    public boolean insertar(Cita c) throws Exception {
        String sql = "INSERT INTO CITA (FECHA, HORAINICIO, HORAFIN, ESTADO, IDPACIENTE, IDMEDICO) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexionOracle.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // fecha -> java.sql.Date
            ps.setDate(1, Date.valueOf(c.getFecha()));

            // horas -> se guardan como string HH:MM:SS en columnas HORAINICIO/HORAFIN (VARCHAR2)
            ps.setString(2, c.getHoraInicio().toString()); // e.g. "14:30:00"
            ps.setString(3, c.getHoraFin().toString());

            ps.setString(4, c.getEstado());
            ps.setInt(5, c.getIdPaciente());
            ps.setInt(6, c.getIdMedico());

            return ps.executeUpdate() > 0;
        }
    }

    public Cita buscarPorId(int id) throws Exception {
        String sql = "SELECT * FROM CITA WHERE IDCITA = ?";
        try (Connection c = ConexionOracle.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1,id);
            try (ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    Cita cita = new Cita();
                    cita.setIdCita(rs.getInt("IDCITA"));
                    Date d = rs.getDate("FECHA"); if(d!=null) cita.setFecha(d.toLocalDate());

                    Timestamp tsInicio = rs.getTimestamp("HORAINICIO");
                    if (tsInicio != null) cita.setHoraInicio(tsInicio.toLocalDateTime().toLocalTime());
                    Timestamp tsFin = rs.getTimestamp("HORAFIN");
                    if (tsFin != null) cita.setHoraFin(tsFin.toLocalDateTime().toLocalTime());

                    cita.setEstado(rs.getString("ESTADO"));
                    cita.setIdPaciente(rs.getInt("IDPACIENTE"));
                    cita.setIdMedico(rs.getInt("IDMEDICO"));
                    return cita;
                }
            }
        }
        return null;
    }

    public List<Cita> listar() throws Exception {
        List<Cita> out = new ArrayList<>();
        String sql = "SELECT * FROM CITA ORDER BY FECHA DESC";
        try (Connection c = ConexionOracle.conectar();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()){
            while(rs.next()){
                Cita cita = new Cita();
                cita.setIdCita(rs.getInt("IDCITA"));
                Date d = rs.getDate("FECHA"); if(d!=null) cita.setFecha(d.toLocalDate());
                String hi = rs.getString("HORAINICIO"); if(hi!=null) cita.setHoraInicio(LocalTime.parse(hi));
                String hf = rs.getString("HORAFIN"); if(hf!=null) cita.setHoraFin(LocalTime.parse(hf));
                cita.setEstado(rs.getString("ESTADO"));
                cita.setIdPaciente(rs.getInt("IDPACIENTE"));
                cita.setIdMedico(rs.getInt("IDMEDICO"));
                out.add(cita);
            }
        }
        return out;
    }

    // listar citas con paciente y medico y formatear fecha/hora para presentación
    public List<String> listarCitasDetalladas() throws Exception {
        List<String> out = new ArrayList<>();
        String sql = "SELECT c.idcita, TO_CHAR(c.fecha,'DD/MM/YYYY') AS fecha_txt, c.horainicio, c.horafin, c.estado, " +
                "p.nombre || ' ' || p.apellido AS paciente, m.nombre || ' ' || m.apellido AS medico " +
                "FROM CITA c JOIN PACIENTE p ON c.idpaciente = p.idpaciente JOIN MEDICO m ON c.idmedico = m.idmedico " +
                "ORDER BY c.fecha DESC, c.horainicio";
        try (Connection conn = ConexionOracle.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()){
            while(rs.next()){
                String line = "Cita " + rs.getInt("idcita") + " | Fecha: " + rs.getString("fecha_txt") +
                        " | Inicio: " + rs.getString("horainicio") + " | Fin: " + rs.getString("horafin") +
                        " | Estado: " + rs.getString("estado") +
                        " | Paciente: " + rs.getString("paciente") + " | Medico: " + rs.getString("medico");
                out.add(line);
            }
        }
        return out;
    }

    // validar solapamiento de horas para medico
    public boolean medicoOcupado(int idMedico, LocalDate fecha, LocalTime inicio, LocalTime fin) throws Exception {
        String sql = "SELECT COUNT(*) FROM CITA WHERE IDMEDICO = ? AND FECHA = ? " +
                "AND ( (HORAINICIO <= ? AND HORAFIN >= ?) OR (HORAINICIO <= ? AND HORAFIN >= ?) OR (? <= HORAINICIO AND ? >= HORAFIN) )";
        try (Connection conn = ConexionOracle.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idMedico);
            ps.setDate(2, Date.valueOf(fecha));
            String sInicio = inicio.toString(); String sFin = fin.toString();
            ps.setString(3, sInicio); ps.setString(4, sInicio);
            ps.setString(5, sFin);   ps.setString(6, sFin);
            ps.setString(7, sInicio); ps.setString(8, sFin);
            try (ResultSet rs = ps.executeQuery()){
                rs.next();
                return rs.getInt(1) > 0;
            }
        }
    }
}

