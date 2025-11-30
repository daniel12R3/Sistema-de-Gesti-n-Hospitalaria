package service;

import dao.PagoMedicoDAO;
import model.PagoMedico;

import java.util.List;

public class PagoMedicoService {

    private PagoMedicoDAO dao = new PagoMedicoDAO();

    public void registrar(PagoMedico pm) throws Exception {

        if(pm.getMontototal() <= 0)
            throw new Exception("El monto debe ser mayor a 0.");

        if(pm.getPeriodopago() == null || pm.getPeriodopago().isBlank())
            throw new Exception("El período de pago es obligatorio.");

        if(pm.getFechageneracion() == null)
            throw new Exception("La fecha de generación es obligatoria.");

        dao.insertar(pm);
    }

    public List<PagoMedico> listar() throws Exception {
        return dao.listar();
    }

    public List<String> listarConMedico() throws Exception {
        return dao.listarConMedico();
    }
}
