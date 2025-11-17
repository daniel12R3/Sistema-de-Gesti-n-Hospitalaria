package service;

import dao.CitaDAO;
import dao.PacienteDAO;
import dao.MedicoDAO;
import model.Cita;

import java.time.LocalTime;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

public class CitaService {

    private CitaDAO dao = new CitaDAO();
    private PacienteDAO pacienteDao = new PacienteDAO();
    private MedicoDAO medicoDao = new MedicoDAO();

    public void registrarCita(Cita c) throws Exception {
        if (c.getFecha()==null) throw new Exception("Fecha obligatoria");
        if (c.getHoraInicio()==null || c.getHoraFin()==null) throw new Exception("Horas obligatorias");
        if (!c.getHoraInicio().isBefore(c.getHoraFin())) throw new Exception("Hora inicio debe ser anterior a hora fin");
        if (pacienteDao.buscarPorId(c.getIdPaciente())==null) throw new Exception("Paciente no existe");
        if (medicoDao.buscarPorId(c.getIdMedico())==null) throw new Exception("Médico no existe");
        // validar solapamiento
        if (dao.medicoOcupado(c.getIdMedico(), c.getFecha(), c.getHoraInicio(), c.getHoraFin())) throw new Exception("Medico ocupado en ese horario");
        dao.insertar(c);
    }

    public List<Cita> listar() throws Exception { return dao.listar(); }
    public Cita buscarPorId(int id) throws Exception { return dao.buscarPorId(id); }
    public List<String> listarDetalladas() throws Exception { return dao.listarCitasDetalladas(); }
}
