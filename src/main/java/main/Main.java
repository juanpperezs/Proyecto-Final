package main;

import java.time.LocalDateTime;
import java.util.Scanner;

import model.core.*;
import model.enums.EstadoAsiento;
import model.enums.EstadoFuncion;
import model.enums.EstadoPelicula;
import model.enums.EstadoVenta;
import model.espacios.Asiento;

public class Main {

    public static void main(String[] args) {
        Scanner entradaTeclado = new Scanner(System.in);
        Cinema cinemaPrincipal = new Cinema("CinemaUQ");
        int opcionSeleccionada;

        do {
            System.out.println("\n---- Menú CINEMAUQ ----");
            System.out.println("=== MÓDULO DE OPERADOR ===");
            System.out.println("1. Vender entrada");
            System.out.println("2. Cancelar venta");
            System.out.println("3. Consultar películas disponibles");
            System.out.println("4. Consultar funciones programadas");
            System.out.println("5. Consultar disponibilidad de una función");
            System.out.println("------------------------------------");
            System.out.println("=== MÓDULO DE ADMINISTRADOR ===");
            System.out.println("6. Administrar películas (Registrar, consultar, modificar, deshabilitar)");
            System.out.println("7. Administrar salas (Registrar, consultar, modificar, deshabilitar)");
            System.out.println("8. Administrar funciones (Programar, finalizar, cancelar)");
            System.out.println("9. Gestionar tarifas por tipo de cliente");
            System.out.println("------------------------------------");
            System.out.println("=== MÓDULO DE REPORTES ===");
            System.out.println("10. Reporte: Total de entradas vendidas");
            System.out.println("11. Reporte: Total de ingresos");
            System.out.println("12. Reporte: Película con mayor cantidad de entradas vendidas");
            System.out.println("13. Reporte: Ocupación de una función");
            System.out.println("14. Reporte: Funciones programadas por fecha");
            System.out.println("------------------------------------");
            System.out.println("15. Salir");
            System.out.print("\nIngrese una opción: ");
            opcionSeleccionada = entradaTeclado.nextInt();
            entradaTeclado.nextLine();

            switch (opcionSeleccionada) {
                case 1:
                    System.out.println("\n ---- VENDER ENTRADA ----");
                    System.out.println("\nIdentificación del cliente: ");

                    String idCliente = entradaTeclado.nextLine();
                    Cliente clienteVenta = null;

                    for (Cliente cliente : cinemaPrincipal.getListaClientes()) {
                        if (cliente.getIdentificacionCliente().equalsIgnoreCase(idCliente)) {
                            clienteVenta = cliente;
                        }
                    }
                        System.out.println("Código de la función: ");

                        String codFuncion = entradaTeclado.nextLine();
                        Funcion funcionVenta = null;

                        for (Funcion funcion : cinemaPrincipal.getListaFunciones()) {
                            if (funcion.getCodigoFuncion().equalsIgnoreCase(codFuncion)) {
                                funcionVenta = funcion;
                            }
                        }

                    if(clienteVenta == null) {
                    System.out.println("El cliente no está registrado.");

                    }else if (funcionVenta == null) {
                    System.out.println("La función no existe.");

                    }else {
                        System.out.print("Película: " + funcionVenta.getPelicula().getNombrePelicula() + " | Sala: " + funcionVenta.getSala().getNombreONumero() + " | Hora: " + funcionVenta.getHora());
                        System.out.println("Asientos disponibles:");

                        for (Asiento asiento : funcionVenta.getSala().getListaAsientos()) {
                            if (asiento.getEstado() == EstadoAsiento.DISPONIBLE) {
                                System.out.print(asiento.getCodigoAsiento() + " ");
                            }
                        }
                        System.out.println(" ");

                        System.out.print("Código del asiento: ");
                        String codAsiento = entradaTeclado.nextLine();

                        Asiento asientoVenta = null;

                        for (Asiento asiento : funcionVenta.getSala().getListaAsientos()) {
                            if (asiento.getCodigoAsiento().equalsIgnoreCase(codAsiento)) {
                                asientoVenta = asiento;
                            }
                        }

                        if (asientoVenta == null) {
                            System.out.println("Ese asiento no existe en la sala.");
                        } else {
                            String codigoVenta = "V-" + (cinemaPrincipal.getListaVentas().size() + 1);
                            Venta venta = new Venta(codigoVenta, clienteVenta, funcionVenta,
                                    asientoVenta, LocalDateTime.now(), 0, EstadoVenta.ACTIVA);
                        }
                    }
                    break;

                case 2:
                    System.out.println("----- CANCELAR VENTA -----");

                    System.out.print("Código de la venta: ");
                    String codVentaCancelar = entradaTeclado.nextLine();

                    boolean ventaCancelada = true;

                //Faltan las excepciones

                    if(ventaCancelada) {
                        System.out.println("Venta " + codVentaCancelar + " cancelada. El asiento quedó disponible.");
                    }
                    break;

                case 3:
                    System.out.println("\n ---- CONSULTAR PELÍCULAS ----");

                    boolean hayPeliculas = false;

                    for (Pelicula pelicula : cinemaPrincipal.getListaPeliculas()) {
                        if(pelicula.getEstado() == EstadoPelicula.DISPONIBLE) {
                            hayPeliculas = true;
                            System.out.println("\nNombre: " + pelicula.getNombrePelicula());
                            System.out.println("Género: " + pelicula.getGenero());
                            System.out.println("Duración. " + pelicula.getDuracionMinutos() + " minutos");
                            System.out.println("Clasificación: " + pelicula.getClasificacion());
                        }
                    }

                    if (!hayPeliculas) {
                        System.out.println("No hay películas disponibles.");
                    }
                    break;

                case 4:
                    System.out.println("\n ---- CONSULTAR FUNCIONES ----");

                    boolean hayFunciones = false;

                    for (Funcion funcion : cinemaPrincipal.getListaFunciones()) {
                        if(funcion.getEstado() == EstadoFuncion.PROGRAMADA) {
                            hayFunciones = true;

                            System.out.println("\nCódigo: " + funcion.getCodigoFuncion());
                            System.out.println("Película: " + funcion.getPelicula().getNombrePelicula());
                            System.out.println("Sala: " + funcion.getSala().getNombreONumero());
                            System.out.println("Fecha: " + funcion.getFecha());
                            System.out.println("Hora: " + funcion.getHora());
                            System.out.println("Valor de la entrada. $" + funcion.getValorBase());
                            System.out.println("Asientos disponibles: " + funcion.getCantidadAsientosDisponibles());
                        }
                    }

                    if(!hayFunciones) {
                        System.out.println("No hay funciones programadas.");
                    }

                    break;

                case 5:
                    System.out.println("\n ---- CONSULTAR DISPONIBILIDAD ----");

                    System.out.print("Código de la función: ");
                    String codFuncionDisponibilidad = entradaTeclado.nextLine();

                    //Excepciones

                    break;
                case 6:
                    break;
                case 7:
                    break;
                case 8:
                    break;
                case 9:
                    break;
                case 10:
                    break;
                case 11:
                    break;
                case 12:
                    break;
                case 13:
                    break;
                case 14:
                    break;
                case 15:
                    System.out.println("Saliendo del sistema CinemaUQ...");
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, ingrese una opción del menú.");
                    break;
            }
        } while (opcionSeleccionada != 15);
        entradaTeclado.close();
    }
}