package service;

import dao.PagoMedicoDAO;
import model.PagoMedico;
import java.util.List;

public class PagoMedicoService {

    private PagoMedicoDAO dao = new PagoMedicoDAO();

    public void registrar(PagoMedico pm) throws Exception {
        if (pm.getMontototal() <= 0) throw new Exception("Monto inválido");
        dao.insertar(pm);
    }

    public List<PagoMedico> listar() throws Exception { return dao.listar(); }
    public List<String> listarConMedico() throws Exception { return dao.listarConMedico(); }
}
