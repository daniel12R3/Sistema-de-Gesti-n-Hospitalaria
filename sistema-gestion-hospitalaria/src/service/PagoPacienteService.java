package service;

import dao.PagoPacienteDAO;
import model.PagoPaciente;

import java.time.LocalDate;
import java.util.List;

public class PagoPacienteService {

    private PagoPacienteDAO dao = new PagoPacienteDAO();

    public void registrar(PagoPaciente p) throws Exception {

        // Validar monto
        if (p.getMonto() <= 0)
            throw new Exception("Monto inválido (debe ser mayor a 0)");

        // Validar fecha de pago
        if (p.getFechapago() == null || p.getFechapago().isAfter(LocalDate.now()))
            throw new Exception("Fecha de pago inválida");

        // Normalizar y validar tipo de pago
        String t = p.getTipopago() == null ? "" : p.getTipopago().trim().toUpperCase();
        if (!(t.equals("EFECTIVO") || t.equals("TARJETA") || t.equals("YAPE") || t.equals("PLIN"))) {
            throw new Exception("Tipo de pago no permitido");
        }
        p.setTipopago(t); // Guardamos en mayúsculas

        // Validar ID de cita
        if (p.getIdcita() <= 0)
            throw new Exception("Debe asignar una cita válida");

        // Insertar usando DAO
        dao.insertar(p);
    }

    public List<PagoPaciente> listar() throws Exception {
        return dao.listar();
    }

    public List<String> totalPorPaciente() throws Exception {
        return dao.totalPagadoPorPaciente();
    }

    public List<PagoPaciente> listarPorCita(int id) throws Exception {
        if (id <= 0) throw new Exception("ID de cita inválido");
        return dao.listarPorCita(id);
    }
}
