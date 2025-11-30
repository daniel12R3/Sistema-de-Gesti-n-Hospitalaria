package model;

public class Medico {
    private int idMedico;
    private String nombre;
    private String apellido;
    private String cmp;
    private int idEspecialidad;

    public Medico() {}
    public Medico(String nombre, String apellido, String cmp, int idEspecialidad){
        this.nombre=nombre;
        this.apellido=apellido;
        this.cmp=cmp;
        this.idEspecialidad=idEspecialidad;
    }

    // getters/setters...
    public int getIdMedico(){return idMedico;}
    public void setIdMedico(int idMedico){this.idMedico=idMedico;}
    public String getNombre(){return nombre;}
    public void setNombre(String nombre){this.nombre=nombre;}
    public String getApellido(){return apellido;}
    public void setApellido(String apellido){this.apellido=apellido;}
    public String getCmp(){return cmp;}
    public void setCmp(String cmp){this.cmp=cmp;}
    public int getIdEspecialidad(){return idEspecialidad;}
    public void setIdEspecialidad(int idEspecialidad){this.idEspecialidad=idEspecialidad;}
}
