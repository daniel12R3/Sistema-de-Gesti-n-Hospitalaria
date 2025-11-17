package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Cita {
    private int idCita;
    private LocalDate fecha;       // fecha (DD/MM/YYYY mapped in SQL display)
    private LocalTime horaInicio;  // stored as VARCHAR 'HH:MM:SS' in DB
    private LocalTime horaFin;     // stored as VARCHAR
    private String estado;
    private int idPaciente;
    private int idMedico;

    public Cita(){}
    public Cita(LocalDate fecha, LocalTime horaInicio, LocalTime horaFin,
                String estado, int idPaciente, int idMedico) {
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.estado = estado;
        this.idPaciente = idPaciente;
        this.idMedico = idMedico;
    }

    // getters/setters...
    public int getIdCita(){return idCita;}
    public void setIdCita(int idCita){this.idCita=idCita;}
    public LocalDate getFecha(){return fecha;}
    public void setFecha(LocalDate fecha){this.fecha=fecha;}
    public LocalTime getHoraInicio(){return horaInicio;}
    public void setHoraInicio(LocalTime horaInicio){this.horaInicio=horaInicio;}
    public LocalTime getHoraFin(){return horaFin;}
    public void setHoraFin(LocalTime horaFin){this.horaFin=horaFin;}
    public String getEstado(){return estado;}
    public void setEstado(String estado){this.estado=estado;}
    public int getIdPaciente(){return idPaciente;}
    public void setIdPaciente(int idPaciente){this.idPaciente=idPaciente;}
    public int getIdMedico(){return idMedico;}
    public void setIdMedico(int idMedico){this.idMedico=idMedico;}
}
