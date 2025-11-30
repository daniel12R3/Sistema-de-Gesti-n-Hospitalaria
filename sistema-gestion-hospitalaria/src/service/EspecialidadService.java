package service;

import dao.EspecialidadDAO;
import model.Especialidad;

import java.util.List;

public class EspecialidadService {

    private EspecialidadDAO dao = new EspecialidadDAO();

    public void registrar(Especialidad e) throws Exception {
        if (e.getNombreEspecialidad() == null || e.getNombreEspecialidad().isBlank())
            throw new Exception("El nombre de la especialidad es obligatorio");

        // validar duplicado
        List<Especialidad> lista = dao.listar();
        for (Especialidad esp : lista)
            if (esp.getNombreEspecialidad().equalsIgnoreCase(e.getNombreEspecialidad()))
                throw new Exception("La especialidad ya existe");

        dao.insertar(e);
    }

    public void actualizar(Especialidad e) throws Exception {
        if (e.getNombreEspecialidad() == null || e.getNombreEspecialidad().isBlank())
            throw new Exception("El nombre es obligatorio");

        if (dao.buscarPorId(e.getIdEspecialidad()) == null)
            throw new Exception("La especialidad no existe");

        dao.actualizar(e);
    }

    public boolean eliminar(int id) throws Exception {
        if (dao.buscarPorId(id) == null)
            throw new Exception("Especialidad no encontrada");

        return dao.eliminar(id);
    }

    public List<Especialidad> listar() throws Exception {
        return dao.listar();
    }

    public Especialidad buscarPorId(int id) throws Exception {
        return dao.buscarPorId(id);
    }

    public List<String> contarMedicos() throws Exception {
        return dao.contarMedicos();
    }
}
