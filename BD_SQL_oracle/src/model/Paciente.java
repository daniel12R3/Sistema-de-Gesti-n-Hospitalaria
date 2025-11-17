package model;

public class Paciente {
    private int idPaciente;
    private String dni;
    private String nombre;
    private String apellido;
    private String telefono;
    private String direccion;
    private String tipoPago;

    public Paciente() {}
    public Paciente(String dni, String nombre, String apellido,
                    String telefono, String direccion, String tipoPago) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.direccion = direccion;
        this.tipoPago = tipoPago;
    }

    // getters/setters...
    public int getIdPaciente(){return idPaciente;}
    public void setIdPaciente(int idPaciente){this.idPaciente=idPaciente;}
    public String getDni(){return dni;}
    public void setDni(String dni){this.dni=dni;}
    public String getNombre(){return nombre;}
    public void setNombre(String nombre){this.nombre=nombre;}
    public String getApellido(){return apellido;}
    public void setApellido(String apellido){this.apellido=apellido;}
    public String getTelefono(){return telefono;}
    public void setTelefono(String telefono){this.telefono=telefono;}
    public String getDireccion(){return direccion;}
    public void setDireccion(String direccion){this.direccion=direccion;}
    public String getTipoPago(){return tipoPago;}
    public void setTipoPago(String tipoPago){this.tipoPago=tipoPago;}
}
