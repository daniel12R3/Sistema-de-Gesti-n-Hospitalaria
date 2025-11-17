package service;

import dao.PacienteDAO;
import model.Paciente;

import java.sql.Connection;
import java.util.List;

public class PacienteService {

    private PacienteDAO dao = new PacienteDAO();

    public void registrar(Paciente p) throws Exception {
        if (p.getDni() == null || p.getDni().trim().length() != 8) throw new Exception("DNI inválido (8 dígitos)");
        if (p.getNombre() == null || p.getNombre().trim().isEmpty()) throw new Exception("Nombre obligatorio");
        if (dao.buscarPorDni(p.getDni()) != null) throw new Exception("DNI ya registrado");
        dao.insertar(p);
    }

    public List<Paciente> listar() throws Exception { return dao.listar(); }

    public Paciente buscarPorId(int id) throws Exception { return dao.buscarPorId(id); }

    public boolean eliminarPaciente(int idPaciente) throws Exception {
        return dao.eliminar(idPaciente);
    }

    public void actualizarPaciente(Paciente p) throws Exception {
        if (p.getNombre()==null || p.getNombre().isBlank()) throw new Exception("Nombre obligatorio");
        if (p.getDni()==null || p.getDni().trim().length()!=8) throw new Exception("DNI inválido");
        dao.actualizar(p);
    }

}
