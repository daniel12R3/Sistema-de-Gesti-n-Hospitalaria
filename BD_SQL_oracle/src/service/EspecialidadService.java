package service;

import dao.EspecialidadDAO;
import model.Especialidad;
import java.util.List;

public class EspecialidadService {

    private EspecialidadDAO dao = new EspecialidadDAO();

    public void registrar(Especialidad e) throws Exception {
        if (e.getNombreEspecialidad()==null || e.getNombreEspecialidad().isBlank()) throw new Exception("Nombre obligatorio");
        // evitar duplicados sencillo
        List<Especialidad> existentes = dao.listar();
        for(Especialidad ex: existentes) if(ex.getNombreEspecialidad().equalsIgnoreCase(e.getNombreEspecialidad())) throw new Exception("Especialidad ya existe");
        dao.insertar(e);
    }

    public List<Especialidad> listar() throws Exception { return dao.listar(); }
    public Especialidad buscarPorId(int id) throws Exception { return dao.buscarPorId(id); }
    public List<String> contarMedicos() throws Exception { return dao.contarMedicos(); }
}
