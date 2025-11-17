package model;

import java.time.LocalDate;

public class PagoPaciente {
    private int idpagopaciente;
    private double monto;
    private LocalDate fechapago;
    private String tipopago;
    private int idcita;

    public PagoPaciente() {}
    public PagoPaciente(double monto, LocalDate fechapago,
                        String tipopago, int idcita){
        this.monto=monto;
        this.fechapago=fechapago;
        this.tipopago=tipopago;
        this.idcita=idcita;
    }

    // getters/setters...
    public int getIdpagopaciente(){return idpagopaciente;}
    public void setIdpagopaciente(int idpagopaciente){this.idpagopaciente=idpagopaciente;}
    public double getMonto(){return monto;}
    public void setMonto(double monto){this.monto=monto;}
    public LocalDate getFechapago(){return fechapago;}
    public void setFechapago(LocalDate fechapago){this.fechapago=fechapago;}
    public String getTipopago(){return tipopago;}
    public void setTipopago(String tipopago){this.tipopago=tipopago;}
    public int getIdcita(){return idcita;}
    public void setIdcita(int idcita){this.idcita=idcita;}
}
