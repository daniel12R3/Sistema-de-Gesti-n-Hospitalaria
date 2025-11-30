package service;

import dao.PacienteDAO;
import model.Paciente;

import java.util.List;

public class PacienteService {

    private PacienteDAO dao = new PacienteDAO();

    public void registrar(Paciente p) throws Exception {
        if (p.getDni() == null || p.getDni().trim().length() != 8)
            throw new Exception("DNI inválido (8 dígitos)");

        if (p.getNombre() == null || p.getNombre().trim().isEmpty())
            throw new Exception("Nombre obligatorio");

        if (dao.buscarPorDni(p.getDni()) != null)
            throw new Exception("DNI ya registrado");

        // Normalizar tipo de pago y validar
        if (p.getTipoPago() == null || p.getTipoPago().trim().isEmpty())
            throw new Exception("Tipo de pago obligatorio");

        String tp = p.getTipoPago().trim().toUpperCase();
        if (!tp.matches("EFECTIVO|TARJETA|YAPE|PLIN"))
            throw new Exception("Tipo de pago inválido. Debe ser EFECTIVO, TARJETA, YAPE o PLIN");

        p.setTipoPago(tp);

        dao.insertar(p);
    }


    public List<Paciente> listar() throws Exception {
        return dao.listar();
    }

    public Paciente buscarPorId(int id) throws Exception {
        return dao.buscarPorId(id);
    }

    public Paciente buscarPorDni(String dni) throws Exception {
        if (dni == null || dni.trim().isEmpty()) throw new Exception("DNI inválido");
        return dao.buscarPorDni(dni);
    }

    public boolean eliminarPaciente(int idPaciente) throws Exception {
        return dao.eliminar(idPaciente);
    }

    public void actualizarPaciente(Paciente p) throws Exception {
        if (p.getNombre() == null || p.getNombre().isBlank())
            throw new Exception("Nombre obligatorio");

        if (p.getDni() == null || p.getDni().trim().length() != 8)
            throw new Exception("DNI inválido");


        // Normalizar tipo de pago y validar
        if (p.getTipoPago() != null && !p.getTipoPago().isBlank()) {
            String tp = p.getTipoPago().trim().toUpperCase();
            if (!tp.matches("EFECTIVO|TARJETA|YAPE|PLIN"))
                throw new Exception("Tipo de pago inválido. Debe ser EFECTIVO, TARJETA, YAPE o PLIN");
            p.setTipoPago(tp);
        }

        dao.actualizar(p);
    }

    public List<String> totalPagos() throws Exception {
        return dao.resumenPagosPacientes();
    }

}


