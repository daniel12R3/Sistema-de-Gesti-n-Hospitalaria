package service;

import dao.PagoPacienteDAO;
import model.PagoPaciente;
import java.util.List;

public class PagoPacienteService {

    private PagoPacienteDAO dao = new PagoPacienteDAO();

    public void registrar(PagoPaciente p) throws Exception {
        if (p.getMonto() <= 0) throw new Exception("Monto inválido");
        // validar tipos
        String t = p.getTipopago();
        if (t==null || !(t.equalsIgnoreCase("efectivo")||t.equalsIgnoreCase("tarjeta")||t.equalsIgnoreCase("yape")||t.equalsIgnoreCase("plin")))
            throw new Exception("Tipo de pago inválido");
        dao.insertar(p);
    }

    public List<PagoPaciente> listar() throws Exception { return dao.listar(); }
    public List<String> totalPorPaciente() throws Exception { return dao.totalPagadoPorPaciente(); }
    public List<PagoPaciente> listarPorCita(int id) throws Exception { return dao.listarPorCita(id); }
}
