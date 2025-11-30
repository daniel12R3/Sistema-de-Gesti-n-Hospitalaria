package model;

import java.time.LocalDate;

public class PagoMedico {
    private int idpagomedico;
    private double montototal;
    private String periodopago;
    private LocalDate fechageneracion;
    private int idmedico;

    public PagoMedico() {}

    public PagoMedico(double montototal, String periodopago,
                      LocalDate fechageneracion, int idmedico){
        this.montototal=montototal;
        this.periodopago=periodopago;
        this.fechageneracion=fechageneracion;
        this.idmedico=idmedico;
    }

    // getters/setters...
    public int getIdpagomedico(){return idpagomedico;}
    public void setIdpagomedico(int idpagomedico){this.idpagomedico=idpagomedico;}
    public double getMontototal(){return montototal;}
    public void setMontototal(double montototal){this.montototal=montototal;}
    public String getPeriodopago(){return periodopago;}
    public void setPeriodopago(String periodopago){this.periodopago=periodopago;}
    public LocalDate getFechageneracion(){return fechageneracion;}
    public void setFechageneracion(LocalDate fechageneracion){this.fechageneracion=fechageneracion;}
    public int getIdmedico(){return idmedico;}
    public void setIdmedico(int idmedico){this.idmedico=idmedico;}
}
