package service;

import dao.MedicoDAO;
import dao.EspecialidadDAO;
import model.Medico;

import java.util.List;

public class MedicoService {

    private MedicoDAO dao = new MedicoDAO();
    private EspecialidadDAO espDao = new EspecialidadDAO();

    public void registrar(Medico m) throws Exception {
        if (m.getNombre()==null || m.getNombre().isBlank()) throw new Exception("Nombre obligatorio");
        if (m.getCmp()==null || m.getCmp().isBlank()) throw new Exception("CMP obligatorio");
        if (espDao.buscarPorId(m.getIdEspecialidad())==null) throw new Exception("Especialidad no existe");
        dao.insertar(m);
    }

    public List<Medico> listar() throws Exception { return dao.listar(); }
    public Medico buscarPorId(int id) throws Exception { return dao.buscarPorId(id); }
    public List<String> totalGanado() throws Exception { return dao.totalGanadoPorMedico(); }

    // Eliminar médico
    public boolean eliminar(int id) throws Exception {
        Medico m = dao.buscarPorId(id);
        if (m == null) {
            return false; // No existe
        }
        return dao.eliminar(id);
    }

    public void actualizar(Medico m) throws Exception {
        if (m.getNombre() == null || m.getNombre().isBlank())
            throw new Exception("Nombre obligatorio");
        if (m.getCmp() == null || m.getCmp().isBlank())
            throw new Exception("CMP obligatorio");

        // Verificar CMP único
        Medico existente = dao.buscarPorCMP(m.getCmp());
        if (existente != null && existente.getIdMedico() != m.getIdMedico()) {
            throw new Exception("El CMP ya está registrado en otro médico");
        }

        if (espDao.buscarPorId(m.getIdEspecialidad()) == null)
            throw new Exception("Especialidad no existe");

        dao.actualizar(m); // Llama al DAO
    }

}
