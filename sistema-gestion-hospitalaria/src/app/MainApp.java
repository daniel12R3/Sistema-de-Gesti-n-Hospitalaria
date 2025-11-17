package app;

import model.*;
import service.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

public class MainApp {

    private static Scanner sc = new Scanner(System.in);

    private static PacienteService pacienteService = new PacienteService();
    private static MedicoService medicoService = new MedicoService();
    private static EspecialidadService especialidadService = new EspecialidadService();
    private static CitaService citaService = new CitaService();
    private static PagoMedicoService pagoMedicoService = new PagoMedicoService();
    private static PagoPacienteService pagoPacienteService = new PagoPacienteService();

    public static void main(String[] args) {
        int opcion;
        do {
            System.out.println("\n==== MENÚ PRINCIPAL ====");
            System.out.println("1. Gestionar Pacientes");
            System.out.println("2. Gestionar Médicos");
            System.out.println("3. Gestionar Especialidades");
            System.out.println("4. Gestionar Citas");
            System.out.println("5. Gestionar Pagos Médicos");
            System.out.println("6. Gestionar Pagos Pacientes");
            System.out.println("0. Salir");
            System.out.print("Opción: ");
            opcion = Integer.parseInt(sc.nextLine());
            try {
                switch(opcion) {
                    case 1 -> gestionarPacientes();
                    case 2 -> gestionarMedicos();
                    case 3 -> gestionarEspecialidades();
                    case 4 -> gestionarCitas();
                    case 5 -> gestionarPagosMedicos();
                    case 6 -> gestionarPagosPacientes();
                    case 0 -> System.out.println("Saliendo...");
                    default -> System.out.println("Opción inválida");
                }
            } catch(Exception e) { System.out.println("Error: " + e.getMessage()); }
        } while(opcion != 0);
    }

    // ===================== GESTION PACIENTES =====================
    private static void gestionarPacientes() {
        int op;
        do {
            System.out.println("\n-- Pacientes --");
            System.out.println("1. Registrar");
            System.out.println("2. Listar");
            System.out.println("3. Buscar por ID");
            System.out.println("4. Actualizar");
            System.out.println("5. Eliminar");
            System.out.println("6. Resumen Pagos (Informe)");
            System.out.println("0. Volver");
            System.out.print("Opción: "); op = Integer.parseInt(sc.nextLine());
            try {
                switch(op) {
                    case 1 -> registrarPaciente();
                    case 2 -> listarPacientes();
                    case 3 -> buscarPaciente();
                    case 4 -> actualizarPaciente();
                    case 5 -> eliminarPaciente();
                    case 6 -> resumenPagosPacientes();
                }
            } catch(Exception e) { System.out.println("Error: " + e.getMessage()); }
        } while(op != 0);
    }

    private static void registrarPaciente() throws Exception {
        System.out.print("DNI: "); String dni = sc.nextLine();
        System.out.print("Nombre: "); String nombre = sc.nextLine();
        System.out.print("Apellido: "); String apellido = sc.nextLine();
        System.out.print("Teléfono: "); String telefono = sc.nextLine();
        System.out.print("Dirección: "); String direccion = sc.nextLine();
        System.out.print("Tipo de Pago: "); String tipoPago = sc.nextLine();
        pacienteService.registrar(new Paciente(dni,nombre,apellido,telefono,direccion,tipoPago));
        System.out.println("Paciente registrado.");
    }

    private static void listarPacientes() throws Exception {
        List<Paciente> lista = pacienteService.listar();
        lista.forEach(p -> System.out.println(p.getIdPaciente() + " | " + p.getNombre() + " " + p.getApellido()));
    }

    private static void buscarPaciente() throws Exception {
        System.out.print("ID Paciente: "); int id = Integer.parseInt(sc.nextLine());
        Paciente p = pacienteService.buscarPorId(id);
        if(p!=null) System.out.println(p.getIdPaciente() + " | " + p.getNombre() + " " + p.getApellido() + " | Tipo Pago: " + p.getTipoPago());
        else System.out.println("No encontrado.");
    }

    private static void actualizarPaciente() throws Exception {
        System.out.print("ID Paciente a actualizar: ");
        int id = Integer.parseInt(sc.nextLine());
        Paciente p = pacienteService.buscarPorId(id);
        if(p == null) { System.out.println("No encontrado."); return; }

        System.out.print("Nombre (" + p.getNombre() + "): ");
        String nombre = sc.nextLine();
        if(!nombre.isBlank()) p.setNombre(nombre);

        System.out.print("Apellido (" + p.getApellido() + "): ");
        String apellido = sc.nextLine();
        if(!apellido.isBlank()) p.setApellido(apellido);

        System.out.print("Teléfono (" + p.getTelefono() + "): ");
        String telefono = sc.nextLine();
        if(!telefono.isBlank()) p.setTelefono(telefono);

        System.out.print("Dirección (" + p.getDireccion() + "): ");
        String direccion = sc.nextLine();
        if(!direccion.isBlank()) p.setDireccion(direccion);

        System.out.print("Tipo Pago (" + p.getTipoPago() + "): ");
        String tipoPago = sc.nextLine();
        if(!tipoPago.isBlank()) p.setTipoPago(tipoPago);

        pacienteService.actualizarPaciente(p); // ahora sí se actualiza correctamente
        System.out.println("Paciente actualizado.");
    }


    private static void eliminarPaciente() throws Exception {
        System.out.print("ID Paciente a eliminar: ");
        int id = Integer.parseInt(sc.nextLine());
        boolean eliminado = pacienteService.eliminarPaciente(id);
        if(eliminado) System.out.println("Paciente eliminado correctamente.");
        else System.out.println("Paciente no encontrado o no se pudo eliminar.");
    }


    private static void resumenPagosPacientes() throws Exception {
        List<String> lista = pacienteService.listar().stream().map(p -> p.getIdPaciente() + " | " + p.getNombre() + " " + p.getApellido()).toList();
        System.out.println("-- Resumen Pagos --");
        List<String> pagos = new PagoPacienteService().totalPorPaciente();
        pagos.forEach(System.out::println);
    }

    // ===================== GESTION MEDICOS =====================
    private static void gestionarMedicos() {
        int op;
        do {
            System.out.println("\n-- Médicos --");
            System.out.println("1. Registrar");
            System.out.println("2. Listar");
            System.out.println("3. Buscar por ID");
            System.out.println("4. Actualizar");
            System.out.println("5. Eliminar");
            System.out.println("6. Informe Total Ganado");
            System.out.println("0. Volver");
            System.out.print("Opción: "); op = Integer.parseInt(sc.nextLine());
            try {
                switch(op) {
                    case 1 -> registrarMedico();
                    case 2 -> listarMedicos();
                    case 3 -> buscarMedico();
                    case 4 -> actualizarMedico();
                    case 5 -> eliminarMedico();
                    case 6 -> informeTotalGanadoMedicos();
                }
            } catch(Exception e) { System.out.println("Error: " + e.getMessage()); }
        } while(op != 0);
    }

    private static void registrarMedico() throws Exception {
        System.out.print("Nombre: "); String nombre = sc.nextLine();
        System.out.print("Apellido: "); String apellido = sc.nextLine();
        System.out.print("CMP: "); String cmp = sc.nextLine();
        System.out.print("ID Especialidad: "); int idEsp = Integer.parseInt(sc.nextLine());
        medicoService.registrar(new Medico(nombre,apellido,cmp,idEsp));
        System.out.println("Médico registrado.");
    }

    private static void listarMedicos() throws Exception {
        List<Medico> lista = medicoService.listar();
        lista.forEach(m -> System.out.println(m.getIdMedico() + " | " + m.getNombre() + " " + m.getApellido() + " | CMP: " + m.getCmp()));
    }

    private static void buscarMedico() throws Exception {
        System.out.print("ID Médico: "); int id = Integer.parseInt(sc.nextLine());
        Medico m = medicoService.buscarPorId(id);
        if(m!=null) System.out.println(m.getIdMedico() + " | " + m.getNombre() + " " + m.getApellido() + " | CMP: " + m.getCmp());
        else System.out.println("No encontrado.");
    }

    private static void actualizarMedico() throws Exception {
        System.out.print("ID Médico a actualizar: ");
        int id = Integer.parseInt(sc.nextLine());

        Medico m = medicoService.buscarPorId(id);
        if (m == null) {
            System.out.println("No encontrado.");
            return;
        }

        System.out.print("Nombre (" + m.getNombre() + "): ");
        String nombre = sc.nextLine();
        if (!nombre.isBlank()) m.setNombre(nombre);

        System.out.print("Apellido (" + m.getApellido() + "): ");
        String apellido = sc.nextLine();
        if (!apellido.isBlank()) m.setApellido(apellido);

        // Si decides permitir cambiar CMP
        System.out.print("CMP (" + m.getCmp() + "): ");
        String cmp = sc.nextLine();
        if (!cmp.isBlank()) m.setCmp(cmp);

        System.out.print("ID Especialidad (" + m.getIdEspecialidad() + "): ");
        String idEsp = sc.nextLine();
        if (!idEsp.isBlank()) m.setIdEspecialidad(Integer.parseInt(idEsp));

        try {
            medicoService.actualizar(m);
            System.out.println("Médico actualizado correctamente.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    private static void eliminarMedico() throws Exception {
        System.out.print("ID Médico a eliminar: ");
        int id = Integer.parseInt(sc.nextLine());

        boolean eliminado = medicoService.eliminar(id);
        if (eliminado) {
            System.out.println("Médico eliminado correctamente.");
        } else {
            System.out.println("Médico no encontrado.");
        }
    }

    private static void informeTotalGanadoMedicos() throws Exception {
        List<String> lista = medicoService.totalGanado();
        System.out.println("-- Informe Total Ganado por Médico --");
        lista.forEach(System.out::println);
    }

    // ===================== GESTION ESPECIALIDADES =====================
    private static void gestionarEspecialidades() {
        int op;
        do {
            System.out.println("\n-- Especialidades --");
            System.out.println("1. Registrar");
            System.out.println("2. Listar");
            System.out.println("3. Buscar por ID");
            System.out.println("4. Informe: Cantidad Médicos por Especialidad");
            System.out.println("0. Volver");
            System.out.print("Opción: "); op = Integer.parseInt(sc.nextLine());
            try {
                switch(op) {
                    case 1 -> registrarEspecialidad();
                    case 2 -> listarEspecialidades();
                    case 3 -> buscarEspecialidad();
                    case 4 -> informeMedicosPorEspecialidad();
                }
            } catch(Exception e) { System.out.println("Error: " + e.getMessage()); }
        } while(op != 0);
    }

    private static void registrarEspecialidad() throws Exception {
        System.out.print("Nombre Especialidad: "); String nombre = sc.nextLine();
        especialidadService.registrar(new Especialidad(nombre));
        System.out.println("Especialidad registrada.");
    }

    private static void listarEspecialidades() throws Exception {
        List<Especialidad> lista = especialidadService.listar();
        lista.forEach(e -> System.out.println(e.getIdEspecialidad() + " | " + e.getNombreEspecialidad()));
    }

    private static void buscarEspecialidad() throws Exception {
        System.out.print("ID Especialidad: "); int id = Integer.parseInt(sc.nextLine());
        Especialidad e = especialidadService.buscarPorId(id);
        if(e!=null) System.out.println(e.getIdEspecialidad() + " | " + e.getNombreEspecialidad());
        else System.out.println("No encontrado.");
    }

    private static void informeMedicosPorEspecialidad() throws Exception {
        List<String> lista = especialidadService.contarMedicos();
        System.out.println("-- Médicos por Especialidad --");
        lista.forEach(System.out::println);
    }

    // ===================== GESTION CITAS =====================
    private static void gestionarCitas() {
        int op;
        do {
            System.out.println("\n-- Citas --");
            System.out.println("1. Registrar");
            System.out.println("2. Listar Detalladas");
            System.out.println("3. Buscar por ID");
            System.out.println("0. Volver");
            System.out.print("Opción: "); op = Integer.parseInt(sc.nextLine());
            try {
                switch(op) {
                    case 1 -> registrarCita();
                    case 2 -> listarCitasDetalladas();
                    case 3 -> buscarCita();
                }
            } catch(Exception e) { System.out.println("Error: " + e.getMessage()); }
        } while(op != 0);
    }

    private static void registrarCita() throws Exception {
        System.out.print("ID Paciente: "); int idPaciente = Integer.parseInt(sc.nextLine());
        System.out.print("ID Médico: "); int idMedico = Integer.parseInt(sc.nextLine());
        System.out.print("Fecha (YYYY-MM-DD): "); LocalDate fecha = LocalDate.parse(sc.nextLine());
        System.out.print("Hora Inicio (HH:MM): "); LocalTime hi = LocalTime.parse(sc.nextLine()+":00");
        System.out.print("Hora Fin (HH:MM): "); LocalTime hf = LocalTime.parse(sc.nextLine()+":00");
        System.out.print("Estado: "); String estado = sc.nextLine();
        citaService.registrarCita(new Cita(fecha, hi, hf, estado, idPaciente, idMedico));
        System.out.println("Cita registrada.");
    }

    private static void listarCitasDetalladas() throws Exception {
        List<String> lista = citaService.listarDetalladas();
        lista.forEach(System.out::println);
    }

    private static void buscarCita() throws Exception {
        System.out.print("ID Cita: ");
        int id = Integer.parseInt(sc.nextLine());
        Cita c = citaService.buscarPorId(id);
        if(c != null) {
            System.out.println(
                    c.getIdCita() + " | Fecha: " + c.getFecha() +
                            " | Inicio: " + c.getHoraInicio() +
                            " | Fin: " + c.getHoraFin() +
                            " | Estado: " + c.getEstado()
            );
        } else {
            System.out.println("No encontrada.");
        }
    }


    // ===================== GESTION PAGOS MEDICOS =====================
    private static void gestionarPagosMedicos() {
        int op;
        do {
            System.out.println("\n-- Pagos Médicos --");
            System.out.println("1. Registrar");
            System.out.println("2. Listar");
            System.out.println("3. Listar con Médico (Informe)");
            System.out.println("0. Volver");
            System.out.print("Opción: "); op = Integer.parseInt(sc.nextLine());
            try {
                switch(op) {
                    case 1 -> registrarPagoMedico();
                    case 2 -> listarPagosMedicos();
                    case 3 -> listarPagosMedicosConInforme();
                }
            } catch(Exception e) { System.out.println("Error: " + e.getMessage()); }
        } while(op != 0);
    }

    private static void registrarPagoMedico() throws Exception {
        System.out.print("Monto Total: "); double monto = Double.parseDouble(sc.nextLine());
        System.out.print("Periodo Pago: "); String periodo = sc.nextLine();
        System.out.print("Fecha Generación (YYYY-MM-DD): "); LocalDate fecha = LocalDate.parse(sc.nextLine());
        System.out.print("ID Médico: "); int idMed = Integer.parseInt(sc.nextLine());
        pagoMedicoService.registrar(new PagoMedico(monto, periodo, fecha, idMed));
        System.out.println("Pago registrado.");
    }

    private static void listarPagosMedicos() throws Exception {
        List<PagoMedico> lista = pagoMedicoService.listar();
        lista.forEach(pm -> System.out.println(pm.getIdpagomedico() + " | Monto: " + pm.getMontototal() + " | Periodo: " + pm.getPeriodopago()));
    }

    private static void listarPagosMedicosConInforme() throws Exception {
        List<String> lista = pagoMedicoService.listarConMedico();
        lista.forEach(System.out::println);
    }

    // ===================== GESTION PAGOS PACIENTES =====================
    private static void gestionarPagosPacientes() {
        int op;
        do {
            System.out.println("\n-- Pagos Pacientes --");
            System.out.println("1. Registrar");
            System.out.println("2. Listar");
            System.out.println("3. Total Pagado por Paciente (Informe)");
            System.out.println("4. Listar por Cita");
            System.out.println("0. Volver");
            System.out.print("Opción: "); op = Integer.parseInt(sc.nextLine());
            try {
                switch(op) {
                    case 1 -> registrarPagoPaciente();
                    case 2 -> listarPagosPacientes();
                    case 3 -> totalPagadoPorPaciente();
                    case 4 -> listarPagosPorCita();
                }
            } catch(Exception e) { System.out.println("Error: " + e.getMessage()); }
        } while(op != 0);
    }

    private static void registrarPagoPaciente() throws Exception {
        System.out.print("Monto: "); double monto = Double.parseDouble(sc.nextLine());
        System.out.print("Fecha Pago (YYYY-MM-DD): "); LocalDate fecha = LocalDate.parse(sc.nextLine());
        System.out.print("Tipo Pago: "); String tipo = sc.nextLine();
        System.out.print("ID Cita: "); int idCita = Integer.parseInt(sc.nextLine());
        pagoPacienteService.registrar(new PagoPaciente(monto, fecha, tipo, idCita));
        System.out.println("Pago registrado.");
    }

    private static void listarPagosPacientes() throws Exception {
        List<PagoPaciente> lista = pagoPacienteService.listar();
        lista.forEach(p -> System.out.println(p.getIdpagopaciente() + " | Monto: " + p.getMonto() + " | Fecha: " + p.getFechapago() + " | Tipo: " + p.getTipopago()));
    }

    private static void totalPagadoPorPaciente() throws Exception {
        List<String> lista = pagoPacienteService.totalPorPaciente();
        lista.forEach(System.out::println);
    }

    private static void listarPagosPorCita() throws Exception {
        System.out.print("ID Cita: "); int idCita = Integer.parseInt(sc.nextLine());
        List<PagoPaciente> lista = pagoPacienteService.listarPorCita(idCita);
        lista.forEach(p -> System.out.println(p.getIdpagopaciente() + " | Monto: " + p.getMonto() + " | Fecha: " + p.getFechapago() + " | Tipo: " + p.getTipopago()));
    }
}
