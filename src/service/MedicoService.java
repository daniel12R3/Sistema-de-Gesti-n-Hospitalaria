package service;

import dao.MedicoDAO;
import dao.EspecialidadDAO;
import model.Medico;

import java.util.List;

public class MedicoService {

    private MedicoDAO dao = new MedicoDAO();
    private EspecialidadDAO espDao = new EspecialidadDAO();

    public void registrar(Medico m) throws Exception {
        if (m.getNombre() == null || m.getNombre().isBlank())
            throw new Exception("Nombre obligatorio");
        if (m.getApellido() == null || m.getApellido().isBlank())
            throw new Exception("Apellido obligatorio");
        if (m.getCmp() == null || m.getCmp().isBlank())
            throw new Exception("CMP obligatorio");
        if (espDao.buscarPorId(m.getIdEspecialidad()) == null)
            throw new Exception("Especialidad no existe");

        // Validación CMP único
        if (dao.buscarPorCMP(m.getCmp()) != null)
            throw new Exception("CMP ya registrado");

        dao.insertar(m);
    }

    public List<Medico> listar() throws Exception {
        return dao.listar();
    }

    public Medico buscarPorId(int id) throws Exception {
        return dao.buscarPorId(id);
    }

    public void actualizar(Medico m) throws Exception {
        if (m.getNombre().isBlank()) throw new Exception("Nombre obligatorio");
        if (m.getCmp().isBlank()) throw new Exception("CMP obligatorio");

        Medico existeCMP = dao.buscarPorCMP(m.getCmp());
        if (existeCMP != null && existeCMP.getIdMedico() != m.getIdMedico())
            throw new Exception("CMP perteneciente a otro médico");

        if (espDao.buscarPorId(m.getIdEspecialidad()) == null)
            throw new Exception("Especialidad no existe");

        dao.actualizar(m);
    }

    public boolean eliminar(int id) throws Exception {
        return dao.eliminar(id);
    }

    public List<String> totalGanado() throws Exception {
        return dao.totalGanadoPorMedico();
    }

    // Buscar médico por CMP
    public Medico buscarPorCMP(String cmp) throws Exception {
        if (cmp == null || cmp.trim().isEmpty())
            throw new Exception("CMP inválido");
        return dao.buscarPorCMP(cmp.trim().toUpperCase()); // forzar mayúsculas
    }


}
