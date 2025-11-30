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
    private static PagoPacienteService pagoPacienteService = new PagoPacienteService();
    private static PagoMedicoService pagoMedicoService = new PagoMedicoService();

    public static void main(String[] args) {
        while (true) {
            try {
                System.out.println("\n=== SISTEMA CLÍNICA ===");
                System.out.println("1. Pacientes");
                System.out.println("2. Médicos");
                System.out.println("3. Especialidades");
                System.out.println("4. Citas");
                System.out.println("5. Pagos Paciente");
                System.out.println("6. Pagos Médico");
                System.out.println("0. Salir");
                System.out.print("Seleccione opción: ");
                int opcion = Integer.parseInt(sc.nextLine());

                switch (opcion) {
                    case 1 -> menuPacientes();
                    case 2 -> menuMedicos();
                    case 3 -> menuEspecialidades();
                    case 4 -> menuCitas();
                    case 5 -> menuPagosPaciente();
                    case 6 -> menuPagosMedico();
                    case 0 -> {
                        System.out.println("Saliendo...");
                        return;
                    }
                    default -> System.out.println("Opción inválida.");
                }

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    // ===========================
    // MENÚ PACIENTES
    // ===========================
    private static void menuPacientes() throws Exception {
        while (true) {
            System.out.println("\n--- Pacientes ---");
            System.out.println("1. Listar");
            System.out.println("2. Registrar");
            System.out.println("3. Actualizar");
            System.out.println("4. Eliminar");
            System.out.println("5. Buscar por ID");
            System.out.println("6. Buscar por DNI");
            System.out.println("7. Resumen de pagos por paciente");
            System.out.println("0. Regresar al menú principal");
            System.out.print("Opción: ");
            int op = Integer.parseInt(sc.nextLine());

            switch (op) {
                case 1 -> {
                    List<Paciente> pacientes = pacienteService.listar();

                    System.out.printf("%-5s | %-15s | %-15s | %-10s | %-15s | %-30s | %-10s\n",
                            "ID", "Nombre", "Apellido", "DNI", "Teléfono", "Dirección", "Pago");
                    System.out.println("-".repeat(115));

                    for (int i = 0; i < pacientes.size(); i++) {
                        Paciente p = pacientes.get(i);
                        System.out.printf("%-5d | %-15s | %-15s | %-10s | %-15s | %-30s | %-10s\n",
                                p.getIdPaciente(),
                                p.getNombre(),
                                p.getApellido(),
                                p.getDni(),
                                p.getTelefono(),
                                p.getDireccion(),
                                p.getTipoPago());

                        // Solo imprime la línea separadora si no es el último registro
                        if (i != pacientes.size() - 1) {
                            System.out.println("-".repeat(115));
                        }
                    }

                }
                case 2 -> {
                    Paciente p = new Paciente();
                    System.out.print("DNI: "); p.setDni(sc.nextLine());
                    System.out.print("Nombre: "); p.setNombre(sc.nextLine());
                    System.out.print("Apellido: "); p.setApellido(sc.nextLine());
                    System.out.print("Teléfono: "); p.setTelefono(sc.nextLine());
                    System.out.print("Dirección: "); p.setDireccion(sc.nextLine());
                    System.out.print("Tipo de pago (EFECTIVO/TARJETA/YAPE/PLIN): "); p.setTipoPago(sc.nextLine());
                    pacienteService.registrar(p);
                    System.out.println("Paciente registrado!");
                }
                case 3 -> {
                    System.out.print("ID Paciente a actualizar: "); int id = Integer.parseInt(sc.nextLine());
                    Paciente p = pacienteService.buscarPorId(id);
                    if (p == null) { System.out.println("Paciente no encontrado"); continue; }
                    System.out.print("Nuevo nombre [" + p.getNombre() + "]: "); String nom = sc.nextLine(); if(!nom.isBlank()) p.setNombre(nom);
                    System.out.print("Nuevo apellido [" + p.getApellido() + "]: "); String ap = sc.nextLine(); if(!ap.isBlank()) p.setApellido(ap);
                    System.out.print("Nuevo teléfono [" + p.getTelefono() + "]: "); String tel = sc.nextLine(); if(!tel.isBlank()) p.setTelefono(tel);
                    System.out.print("Nueva dirección [" + p.getDireccion() + "]: "); String dir = sc.nextLine(); if(!dir.isBlank()) p.setDireccion(dir);
                    System.out.print("Tipo de pago [" + p.getTipoPago() + "]: "); String tp = sc.nextLine(); if(!tp.isBlank()) p.setTipoPago(tp);
                    pacienteService.actualizarPaciente(p);
                    System.out.println("Paciente actualizado!");
                }
                case 4 -> {
                    System.out.print("ID Paciente a eliminar: "); int id = Integer.parseInt(sc.nextLine());
                    pacienteService.eliminarPaciente(id);
                    System.out.println("Paciente eliminado!");
                }
                case 5 -> {
                    System.out.print("ID Paciente a buscar: ");
                    int id = Integer.parseInt(sc.nextLine());
                    Paciente p = pacienteService.buscarPorId(id);

                    if (p != null) {
                        System.out.println("ID        : " + p.getIdPaciente());
                        System.out.println("Nombre    : " + p.getNombre());
                        System.out.println("Apellido  : " + p.getApellido());
                        System.out.println("DNI       : " + p.getDni());
                        System.out.println("Teléfono  : " + p.getTelefono());
                        System.out.println("Dirección : " + p.getDireccion());
                        System.out.println("Pago      : " + p.getTipoPago());
                        System.out.println("--------------------------------------------------");
                    } else {
                        System.out.println("Paciente no encontrado");
                    }
                }

                case 6 -> {
                    String dni;
                    while (true) {
                        System.out.print("DNI Paciente a buscar: ");
                        dni = sc.nextLine().trim();

                        if (dni.length() == 8 && dni.matches("\\d{8}")) {
                            break; // DNI válido, salimos del bucle
                        } else {
                            System.out.println("DNI inválido. Debe tener 8 dígitos numéricos. Intente nuevamente.");
                        }
                    }

                    Paciente p = pacienteService.buscarPorDni(dni);
                    if (p != null)
                        System.out.println(p.getIdPaciente() + " | " + p.getNombre() + " " + p.getApellido());
                    else
                        System.out.println("Paciente no encontrado");
                }


                case 7 -> pacienteService.totalPagos().forEach(System.out::println);
                case 0 -> {
                    System.out.println("Regresando al menú principal...");
                    return;
                }
                default -> System.out.println("Opción inválida.");
            }
        }
    }

    // ===========================
    // MENÚ MÉDICOS
    // ===========================
    private static void menuMedicos() throws Exception {
        while (true) {
            System.out.println("\n--- Médicos ---");
            System.out.println("1. Listar");
            System.out.println("2. Registrar");
            System.out.println("3. Actualizar");
            System.out.println("4. Eliminar");
            System.out.println("5. Buscar por ID");
            System.out.println("6. Buscar por CMP");
            System.out.println("7. Total ganado por médico");
            System.out.println("0. Regresar al menú principal");
            System.out.print("Opción: ");
            int op = Integer.parseInt(sc.nextLine());

            switch (op) {
                case 1 -> {
                    List<Medico> medicos = medicoService.listar();
                    System.out.printf("%-5s | %-15s | %-15s | %-10s | %-15s%n",
                            "ID", "Nombre", "Apellido", "CMP", "ID Especialidad");
                    System.out.println("---------------------------------------------------------------");
                    for (Medico m : medicos) {
                        System.out.printf("%-5d | %-15s | %-15s | %-10s | %-15d%n",
                                m.getIdMedico(),
                                m.getNombre(),
                                m.getApellido(),
                                m.getCmp(),
                                m.getIdEspecialidad());
                    }
                    System.out.println("---------------------------------------------------------------");
                }
                case 2 -> {
                    Medico m = new Medico();
                    System.out.print("Nombre: "); m.setNombre(sc.nextLine());
                    System.out.print("Apellido: "); m.setApellido(sc.nextLine());
                    System.out.print("CMP: "); m.setCmp(sc.nextLine());
                    System.out.print("ID Especialidad: "); m.setIdEspecialidad(Integer.parseInt(sc.nextLine()));
                    medicoService.registrar(m);
                    System.out.println("Médico registrado!");
                }
                case 3 -> {
                    System.out.print("ID Médico a actualizar: "); int id = Integer.parseInt(sc.nextLine());
                    Medico m = medicoService.buscarPorId(id);
                    if (m == null) { System.out.println("Médico no encontrado"); continue; }
                    System.out.print("Nuevo nombre [" + m.getNombre() + "]: "); String nom = sc.nextLine(); if(!nom.isBlank()) m.setNombre(nom);
                    System.out.print("Nuevo apellido [" + m.getApellido() + "]: "); String ap = sc.nextLine(); if(!ap.isBlank()) m.setApellido(ap);
                    System.out.print("CMP [" + m.getCmp() + "]: "); String cmp = sc.nextLine(); if(!cmp.isBlank()) m.setCmp(cmp);
                    System.out.print("ID Especialidad [" + m.getIdEspecialidad() + "]: "); String esp = sc.nextLine(); if(!esp.isBlank()) m.setIdEspecialidad(Integer.parseInt(esp));
                    medicoService.actualizar(m);
                    System.out.println("Médico actualizado!");
                }
                case 4 -> {
                    System.out.print("ID Médico a eliminar: "); int id = Integer.parseInt(sc.nextLine());
                    medicoService.eliminar(id);
                    System.out.println("Médico eliminado!");
                }
                case 5 -> {
                    System.out.print("ID Médico a buscar: ");
                    int id = Integer.parseInt(sc.nextLine());
                    Medico m = medicoService.buscarPorId(id);
                    if (m != null) {
                        System.out.println("ID        : " + m.getIdMedico());
                        System.out.println("Nombre    : " + m.getNombre());
                        System.out.println("Apellido  : " + m.getApellido());
                        System.out.println("CMP       : " + m.getCmp());
                        System.out.println("Especialidad ID : " + m.getIdEspecialidad());
                        System.out.println("--------------------------------------------------");
                    } else {
                        System.out.println("Médico no encontrado");
                    }
                }
                case 6 -> {
                    System.out.print("CMP Médico a buscar: ");
                    String cmp = sc.nextLine(); // el service se encarga de mayúsculas
                    Medico m = medicoService.buscarPorCMP(cmp);
                    if (m != null) {
                        System.out.println("ID        : " + m.getIdMedico());
                        System.out.println("Nombre    : " + m.getNombre());
                        System.out.println("Apellido  : " + m.getApellido());
                        System.out.println("CMP       : " + m.getCmp());
                        System.out.println("Especialidad ID : " + m.getIdEspecialidad());
                        System.out.println("--------------------------------------------------");
                    } else {
                        System.out.println("Médico no encontrado");
                    }
                }

                case 7 -> medicoService.totalGanado().forEach(System.out::println);
                case 0 -> { System.out.println("Regresando al menú principal..."); return; }
                default -> System.out.println("Opción inválida.");
            }
        }
    }

    // ===========================
    // MENÚ ESPECIALIDADES
    // ===========================
    private static void menuEspecialidades() throws Exception {
        while (true) {
            System.out.println("\n--- Especialidades ---");
            System.out.println("1. Listar");
            System.out.println("2. Registrar");
            System.out.println("3. Actualizar");
            System.out.println("4. Eliminar");
            System.out.println("5. Buscar por ID");
            System.out.println("6. Contar médicos por especialidad");
            System.out.println("0. Regresar al menú principal");
            System.out.print("Opción: ");
            int op = Integer.parseInt(sc.nextLine());

            switch(op){
                case 1 -> especialidadService.listar().forEach(e -> System.out.println(e.getIdEspecialidad() + " | " + e.getNombreEspecialidad()));
                case 2 -> {
                    Especialidad e = new Especialidad();
                    System.out.print("Nombre especialidad: "); e.setNombreEspecialidad(sc.nextLine());
                    especialidadService.registrar(e);
                    System.out.println("Especialidad registrada!");
                }
                case 3 -> {
                    System.out.print("ID especialidad a actualizar: "); int id = Integer.parseInt(sc.nextLine());
                    Especialidad e = especialidadService.buscarPorId(id);
                    if(e == null){ System.out.println("No existe"); continue; }
                    System.out.print("Nuevo nombre [" + e.getNombreEspecialidad() + "]: "); String nom = sc.nextLine(); if(!nom.isBlank()) e.setNombreEspecialidad(nom);
                    especialidadService.actualizar(e);
                    System.out.println("Especialidad actualizada!");
                }
                case 4 -> {
                    System.out.print("ID especialidad a eliminar: "); int id = Integer.parseInt(sc.nextLine());
                    especialidadService.eliminar(id);
                    System.out.println("Especialidad eliminada!");
                }
                case 5 -> {
                    System.out.print("ID especialidad a buscar: "); int id = Integer.parseInt(sc.nextLine());
                    Especialidad e = especialidadService.buscarPorId(id);
                    if(e != null) System.out.println(e.getIdEspecialidad() + " | " + e.getNombreEspecialidad());
                    else System.out.println("No existe");
                }
                case 6 -> especialidadService.contarMedicos().forEach(System.out::println);
                case 0 -> { System.out.println("Regresando al menú principal..."); return; }
                default -> System.out.println("Opción inválida.");
            }
        }
    }

    // ===========================
    // MENÚ CITAS
    // ===========================
    private static void menuCitas() throws Exception {
        while (true) {
            System.out.println("\n--- Citas ---");
            System.out.println("1. Listar detallado");
            System.out.println("2. Registrar");
            System.out.println("3. Reprogramar");
            System.out.println("4. Actualizar estado");
            System.out.println("5. Eliminar");
            System.out.println("6. Listar log de citas");
            System.out.println("0. Regresar al menú principal");
            System.out.print("Opción: ");
            int op = Integer.parseInt(sc.nextLine());

            switch(op){
                case 1 -> {
                    System.out.println("1. Listar todo");
                    System.out.println("2. Filtrar por paciente");
                    System.out.println("3. Filtrar por médico");
                    System.out.println("4. Filtrar por fecha");
                    System.out.println("5. Filtrar por estado");
                    System.out.print("Opción: "); int f = Integer.parseInt(sc.nextLine());

                    List<Cita> citas = List.of(); // lista vacía por defecto

                    switch(f){
                        case 1 -> citas = citaService.listarDetallado();
                        case 2 -> {
                            System.out.print("ID Paciente: "); int idP = Integer.parseInt(sc.nextLine());
                            citas = citaService.listarPorPaciente(idP);
                        }
                        case 3 -> {
                            System.out.print("ID Médico: "); int idM = Integer.parseInt(sc.nextLine());
                            citas = citaService.listarPorMedico(idM);
                        }
                        case 4 -> {
                            System.out.print("Fecha (yyyy-mm-dd): "); LocalDate fecha = LocalDate.parse(sc.nextLine());
                            citas = citaService.listarPorFecha(fecha);
                        }
                        case 5 -> {
                            System.out.print("Estado (PENDIENTE/ATENDIDA/CANCELADA): "); String est = sc.nextLine();
                            citas = citaService.listarPorEstado(est);
                        }
                        default -> {
                            System.out.println("Opción inválida");
                            return; // salimos del submenú para evitar error
                        }
                    }

                    citas.forEach(c -> System.out.println(
                            c.getIdCita() + " | " + c.getFecha() + " | " + c.getHoraInicio() + "-" + c.getHoraFin() +
                                    " | " + c.getEstado() + " | Paciente: " + (c.getPaciente() != null ? c.getPaciente().getNombre() : "") +
                                    " | Médico: " + (c.getMedico() != null ? c.getMedico().getNombre() : ""))
                    );

                }
                case 2 -> {
                    Cita c = new Cita();
                    System.out.print("Fecha (yyyy-mm-dd): "); c.setFecha(LocalDate.parse(sc.nextLine()));
                    System.out.print("Hora inicio (HH:mm): "); c.setHoraInicio(LocalTime.parse(sc.nextLine()));
                    System.out.print("Hora fin (HH:mm): "); c.setHoraFin(LocalTime.parse(sc.nextLine()));
                    System.out.print("ID Paciente: "); int idP = Integer.parseInt(sc.nextLine()); c.setPaciente(pacienteService.buscarPorId(idP));
                    System.out.print("ID Médico: "); int idM = Integer.parseInt(sc.nextLine()); c.setMedico(medicoService.buscarPorId(idM));
                    c.setEstado("PENDIENTE");
                    citaService.registrar(c);
                    System.out.println("Cita registrada!");
                }
                case 3 -> {
                    System.out.print("ID Cita a reprogramar: "); int idC = Integer.parseInt(sc.nextLine());
                    Cita c = citaService.listarPorCita(idC).get(0);
                    System.out.print("Nueva fecha (yyyy-mm-dd) [" + c.getFecha() + "]: "); String f = sc.nextLine(); if(!f.isBlank()) c.setFecha(LocalDate.parse(f));
                    System.out.print("Nueva hora inicio (HH:mm) [" + c.getHoraInicio() + "]: "); String hi = sc.nextLine(); if(!hi.isBlank()) c.setHoraInicio(LocalTime.parse(hi));
                    System.out.print("Nueva hora fin (HH:mm) [" + c.getHoraFin() + "]: "); String hf = sc.nextLine(); if(!hf.isBlank()) c.setHoraFin(LocalTime.parse(hf));
                    citaService.reprogramar(c);
                    System.out.println("Cita reprogramada!");
                }
                case 4 -> {
                    System.out.print("ID Cita a actualizar estado: "); int idC = Integer.parseInt(sc.nextLine());
                    System.out.print("Nuevo estado (PENDIENTE/ATENDIDA/CANCELADA): "); String est = sc.nextLine();
                    citaService.actualizarEstado(idC, est);
                    System.out.println("Estado actualizado!");
                }
                case 5 -> {
                    System.out.print("ID Cita a eliminar: "); int idC = Integer.parseInt(sc.nextLine());
                    citaService.eliminar(idC);
                    System.out.println("Cita eliminada!");
                }
                case 6 -> citaService.listarLog().forEach(System.out::println);
                case 0 -> { System.out.println("Regresando al menú principal..."); return; }
                default -> System.out.println("Opción inválida.");
            }
        }
    }

    // ===========================
    // MENÚ PAGOS PACIENTE
    // ===========================
    private static void menuPagosPaciente() throws Exception {
        while (true) {
            System.out.println("\n--- Pagos Paciente ---");
            System.out.println("1. Listar");
            System.out.println("2. Registrar");
            System.out.println("3. Listar por cita");
            System.out.println("4. Total pagado por paciente");
            System.out.println("0. Regresar al menú principal");
            System.out.print("Opción: ");
            int op = Integer.parseInt(sc.nextLine());

            switch(op){
                case 1 -> pagoPacienteService.listar().forEach(p ->
                        System.out.println(p.getIdpagopaciente() + " | " + p.getMonto() + " | " + p.getFechapago() + " | " + p.getTipopago() + " | Cita: " + p.getIdcita())
                );
                case 2 -> {
                    PagoPaciente p = new PagoPaciente();
                    System.out.print("Monto: "); p.setMonto(Double.parseDouble(sc.nextLine()));
                    System.out.print("Fecha (yyyy-mm-dd): "); p.setFechapago(LocalDate.parse(sc.nextLine()));
                    System.out.print("Tipo de pago (EFECTIVO/TARJETA/YAPE/PLIN): "); p.setTipopago(sc.nextLine());
                    System.out.print("ID Cita: "); p.setIdcita(Integer.parseInt(sc.nextLine()));
                    pagoPacienteService.registrar(p);
                    System.out.println("Pago registrado!");
                }
                case 3 -> {
                    System.out.print("ID Cita: "); int id = Integer.parseInt(sc.nextLine());
                    pagoPacienteService.listarPorCita(id).forEach(p ->
                            System.out.println(p.getIdpagopaciente() + " | " + p.getMonto() + " | " + p.getFechapago() + " | " + p.getTipopago())
                    );
                }
                case 4 -> pagoPacienteService.totalPorPaciente().forEach(System.out::println);
                case 0 -> { System.out.println("Regresando al menú principal..."); return; }
                default -> System.out.println("Opción inválida.");
            }
        }
    }

    // ===========================
    // MENÚ PAGOS MÉDICO
    // ===========================
    private static void menuPagosMedico() throws Exception {
        while (true) {
            System.out.println("\n--- Pagos Médico ---");
            System.out.println("1. Listar");
            System.out.println("2. Registrar");
            System.out.println("3. Listar con médico");
            System.out.println("0. Regresar al menú principal");
            System.out.print("Opción: ");
            int op = Integer.parseInt(sc.nextLine());

            switch(op){
                case 1 -> pagoMedicoService.listar().forEach(p ->
                        System.out.println(p.getIdpagomedico() + " | " + p.getMontototal() + " | " + p.getPeriodopago() + " | " + p.getFechageneracion() + " | Médico: " + p.getIdmedico())
                );
                case 2 -> {
                    PagoMedico p = new PagoMedico();
                    System.out.print("Monto total: "); p.setMontototal(Double.parseDouble(sc.nextLine()));
                    System.out.print("Período pago: "); p.setPeriodopago(sc.nextLine());
                    System.out.print("Fecha generación (yyyy-mm-dd): "); p.setFechageneracion(LocalDate.parse(sc.nextLine()));
                    System.out.print("ID Médico: "); p.setIdmedico(Integer.parseInt(sc.nextLine()));
                    pagoMedicoService.registrar(p);
                    System.out.println("Pago registrado!");
                }
                case 3 -> pagoMedicoService.listarConMedico().forEach(System.out::println);
                case 0 -> { System.out.println("Regresando al menú principal..."); return; }
                default -> System.out.println("Opción inválida.");
            }
        }
    }
}
