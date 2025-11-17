package model;

public class Especialidad {
    private int idEspecialidad;
    private String nombreEspecialidad;

    public Especialidad() {}

    public Especialidad(String nombreEspecialidad){
        this.nombreEspecialidad=nombreEspecialidad;
    }

    // getters/setters...
    public int getIdEspecialidad(){return idEspecialidad;}
    public void setIdEspecialidad(int idEspecialidad){this.idEspecialidad=idEspecialidad;}
    public String getNombreEspecialidad(){return nombreEspecialidad;}
    public void setNombreEspecialidad(String nombreEspecialidad){this.nombreEspecialidad=nombreEspecialidad;}
}
