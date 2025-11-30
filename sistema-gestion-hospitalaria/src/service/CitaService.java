package service;

import dao.CitaDAO;
import model.Cita;

import java.util.List;
import java.time.LocalDate;

public class CitaService {

    private CitaDAO dao = new CitaDAO();

    public void registrar(Cita c) throws Exception {
        validar(c);
        dao.insertar(c);
    }

    public void actualizarEstado(int id, String estado) throws Exception {
        if (estado == null || estado.isBlank())
            throw new Exception("Estado no puede estar vacío");

        if (!estado.matches("PENDIENTE|ATENDIDA|CANCELADA"))
            throw new Exception("Estado inválido");

        dao.actualizarEstado(id, estado);
    }

    public void reprogramar(Cita c) throws Exception {
        validar(c);
        dao.reprogramar(c);
    }

    public void eliminar(int id) throws Exception {
        dao.eliminar(id);
    }

    private void validar(Cita c) throws Exception {
        if (c.getFecha() == null)
            throw new Exception("Fecha requerida");

        if (c.getHoraInicio() == null || c.getHoraFin() == null)
            throw new Exception("Horas requeridas");

        if (c.getHoraFin().isBefore(c.getHoraInicio()))
            throw new Exception("Hora fin no puede ser antes que hora inicio");

        if (c.getPaciente() == null)
            throw new Exception("Debe seleccionar paciente");

        if (c.getMedico() == null)
            throw new Exception("Debe seleccionar médico");

        if (!c.getEstado().matches("PENDIENTE|ATENDIDA|CANCELADA"))
            throw new Exception("Estado inválido");
    }

    // Métodos de listado
    public List<Cita> listar() throws Exception { return dao.listar(); }
    public List<Cita> listarDetallado() throws Exception { return dao.listarDetallado(); }
    public List<String> listarLog() throws Exception { return dao.listarLog(); }

    // Métodos de filtrado
    public List<Cita> listarPorPaciente(int idPaciente) throws Exception {
        if (idPaciente <= 0) throw new Exception("ID paciente inválido");
        return dao.listarDetallado().stream()
                .filter(c -> c.getPaciente() != null && c.getPaciente().getIdPaciente() == idPaciente)
                .toList();
    }

    public List<Cita> listarPorMedico(int idMedico) throws Exception {
        if (idMedico <= 0) throw new Exception("ID médico inválido");
        return dao.listarDetallado().stream()
                .filter(c -> c.getMedico() != null && c.getMedico().getIdMedico() == idMedico)
                .toList();
    }

    public List<Cita> listarPorFecha(LocalDate fecha) throws Exception {
        if (fecha == null) throw new Exception("Fecha inválida");
        return dao.listarDetallado().stream()
                .filter(c -> c.getFecha().isEqual(fecha))
                .toList();
    }

    public List<Cita> listarPorEstado(String estado) throws Exception {
        if (estado == null || estado.isBlank()) throw new Exception("Estado inválido");
        return dao.listarDetallado().stream()
                .filter(c -> c.getEstado().equalsIgnoreCase(estado))
                .toList();
    }

    // Nuevo método agregado
    public List<Cita> listarPorCita(int idCita) throws Exception {
        if (idCita <= 0) throw new Exception("ID Cita inválido");
        return dao.listarDetallado().stream()
                .filter(c -> c.getIdCita() == idCita)
                .toList();
    }
}
