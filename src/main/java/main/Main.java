package main;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.core.*;
import model.enums.*;
import model.espacios.Asiento;
import model.core.Cliente;
import model.core.Funcion;
import model.core.Venta;
import model.espacios.Sala;
import model.exceptions.*;

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
                        System.out.print("Nombre del cliente: ");
                        String nombreCliente = entradaTeclado.nextLine();
                        System.out.println("Tipo de entrada (1. GENERAL | 2. ESTUDIANTE | 3. NINO | 4. ADULTO_MAYOR): ");
                        int opcionTipoCliente = entradaTeclado.nextInt();
                        entradaTeclado.nextLine();

                        TipoCliente tipoCliente = TipoCliente.GENERAL;
                        if (opcionTipoCliente == 2) {
                            tipoCliente = TipoCliente.ESTUDIANTE;
                        } else if (opcionTipoCliente == 3) {
                            tipoCliente = TipoCliente.NINO;
                        } else if (opcionTipoCliente == 4) {
                            tipoCliente = TipoCliente.ADULTO_MAYOR;
                        }

                        clienteVenta = new Cliente(idCliente, nombreCliente, tipoCliente);
                        cinemaPrincipal.registrarCliente(clienteVenta);

                    } else {
                        System.out.println("Tipo de entrada (1. GENERAL | 2. ESTUDIANTE | 3. NINO | 4. ADULTO_MAYOR): ");
                        int opcionTipoCliente = entradaTeclado.nextInt();
                        entradaTeclado.nextLine();

                        TipoCliente tipoCliente = TipoCliente.GENERAL;
                        if (opcionTipoCliente == 2) {
                            tipoCliente = TipoCliente.ESTUDIANTE;
                        } else if (opcionTipoCliente == 3) {
                            tipoCliente = TipoCliente.NINO;
                        } else if (opcionTipoCliente == 4) {
                            tipoCliente = TipoCliente.ADULTO_MAYOR;
                        }
                        clienteVenta.setTipoCliente(tipoCliente);
                    }

                    if (funcionVenta == null) {
                        System.out.println("La función no existe.");

                    }else if (funcionVenta.getEstado() != EstadoFuncion.PROGRAMADA) {
                        System.out.println("La función no está disponible.");

                    }else if (funcionVenta.getSala().contarAsientosDisponibles() == 0) {
                        System.out.println("No hay asientos disponibles para esta función.");

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
                        } else if (asientoVenta.getEstado() == EstadoAsiento.OCUPADO) {
                            System.out.println("Ese asiento ya está ocupado.");
                        } else {
                            double valorPagado = cinemaPrincipal.calcularValorEntrada(funcionVenta, clienteVenta.getTipoCliente());
                            String codigoVenta = "V-" + (cinemaPrincipal.getListaVentas().size() + 1);
                            Venta venta = new Venta(codigoVenta, clienteVenta, funcionVenta,
                                    asientoVenta, LocalDateTime.now(), valorPagado, EstadoVenta.ACTIVA);
                            asientoVenta.ocuparAsiento();
                            cinemaPrincipal.getListaVentas().add(venta);
                            System.out.println("Venta exitosa. Código: " + venta.getCodigoVenta()
                                    + " | Cliente: " + clienteVenta.getNombre()
                                    + " | Película: " + funcionVenta.getPelicula().getNombrePelicula()
                                    + " | Asiento: " + asientoVenta.getCodigoAsiento()
                                    + " | Total pagado: $" + venta.getValorPagado());
                        }
                    }
                    break;

                case 2:
                    System.out.println("----- CANCELAR VENTA -----");

                    System.out.print("Código de la venta: ");
                    String codVentaCancelar = entradaTeclado.nextLine();

                    boolean ventaCancelada = true;

                    try {
                        Venta ventaACancelar = cinemaPrincipal.buscarVentaPorCodigo(codVentaCancelar);
                        if (ventaACancelar != null && ventaACancelar.getEstadoVenta() == EstadoVenta.CANCELADA) {
                            System.out.println("La venta " + codVentaCancelar + " ya se encontraba cancelada.");
                            ventaCancelada = false;
                        } else {
                            cinemaPrincipal.cancelarVenta(codVentaCancelar);
                        }
                    } catch (VentaInexistenteException excepcionVentaInexistente) {
                        System.out.println("Error: " + excepcionVentaInexistente.getMessage());
                        ventaCancelada = false;
                    }

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
                            System.out.println("Duración: " + pelicula.getDuracionMinutos() + " minutos");
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

                    try {
                        Funcion funcionDisponibilidad = cinemaPrincipal.buscarFuncionPorCodigo(codFuncionDisponibilidad);

                        if (funcionDisponibilidad == null) {
                            throw new FuncionInexistenteException("La función con código " + codFuncionDisponibilidad + " no existe.");
                        }

                        Sala salaFuncion = funcionDisponibilidad.getSala();

                        System.out.println("\nPelícula: " + funcionDisponibilidad.getPelicula().getNombrePelicula());
                        System.out.println("Sala: " + salaFuncion.getNombreONumero());
                        System.out.println("Total de asientos: " + salaFuncion.getCapacidad());
                        System.out.println("Asientos disponibles: " + salaFuncion.contarAsientosDisponibles());
                        System.out.println("Asientos ocupados: " + salaFuncion.contarAsientosOcupados());
                        System.out.println("\nEstado individual de los asientos:");

                        for (Asiento asiento : salaFuncion.getListaAsientos()) {
                            String estadoAsientoTexto = "Disponible";
                            if (asiento.getEstado() == EstadoAsiento.OCUPADO) {
                                estadoAsientoTexto = "Ocupado";
                            }
                            System.out.println(asiento.getCodigoAsiento() + " " + estadoAsientoTexto);
                        }
                    } catch (FuncionInexistenteException excepcionFuncionInexistente) {
                        System.out.println("Error: " + excepcionFuncionInexistente.getMessage());
                    }
                    break;

                case 6:
                    System.out.println("\n ---- ADMINISTRAR PELÍCULAS ----");
                    System.out.println("1. Registrar película");
                    System.out.println("2. Consultar películas");
                    System.out.println("3. Modificar película");
                    System.out.println("4. Deshabilitar o habilitar película");
                    System.out.print("Seleccione una opción: ");
                    int opcionAdministrarPeliculas = entradaTeclado.nextInt();
                    entradaTeclado.nextLine();

                    if (opcionAdministrarPeliculas == 1) {
                        System.out.print("Código de la película: ");
                        String codigoPelicula = entradaTeclado.nextLine();
                        System.out.print("Nombre de la película: ");
                        String nombrePelicula = entradaTeclado.nextLine();
                        System.out.print("Género de la película: ");
                        String generoPelicula = entradaTeclado.nextLine();
                        System.out.print("Duración en minutos: ");
                        int duracionMinutos = entradaTeclado.nextInt();
                        entradaTeclado.nextLine();

                        System.out.println("Clasificación (1. TODOS | 2. MAYORES_7 | 3. MAYORES_12 | 4. MAYORES_15 | 5. MAYORES_18): ");
                        int opcionClasificacion = entradaTeclado.nextInt();
                        entradaTeclado.nextLine();

                        Clasificacion clasificacionPelicula = Clasificacion.TODOS;
                        if (opcionClasificacion == 2) {
                            clasificacionPelicula = Clasificacion.MAYORES_7;
                        } else if (opcionClasificacion == 3) {
                            clasificacionPelicula = Clasificacion.MAYORES_12;
                        } else if (opcionClasificacion == 4) {
                            clasificacionPelicula = Clasificacion.MAYORES_15;
                        } else if (opcionClasificacion == 5) {
                            clasificacionPelicula = Clasificacion.MAYORES_18;
                        }

                        Pelicula peliculaNueva = new Pelicula(codigoPelicula, nombrePelicula, generoPelicula, duracionMinutos, clasificacionPelicula);

                        try {
                            cinemaPrincipal.registrarPelicula(peliculaNueva);
                            System.out.println("Película registrada exitosamente.");
                        } catch (CodigoPeliculaDuplicadoException excepcionCodigoDuplicado) {
                            System.out.println("Error: " + excepcionCodigoDuplicado.getMessage());
                        }

                    } else if (opcionAdministrarPeliculas == 2) {
                        if (cinemaPrincipal.getListaPeliculas().isEmpty()) {
                            System.out.println("No hay películas registradas.");
                        } else {
                            for (Pelicula pelicula : cinemaPrincipal.getListaPeliculas()) {
                                System.out.println("\nCódigo: " + pelicula.getCodigoPelicula());
                                System.out.println("Nombre: " + pelicula.getNombrePelicula());
                                System.out.println("Género: " + pelicula.getGenero());
                                System.out.println("Duración: " + pelicula.getDuracionMinutos() + " minutos");
                                System.out.println("Clasificación: " + pelicula.getClasificacion());
                                System.out.println("Estado: " + pelicula.getEstado());
                            }
                        }

                    } else if (opcionAdministrarPeliculas == 3) {
                        System.out.print("Código de la película a modificar: ");
                        String codigoPeliculaModificar = entradaTeclado.nextLine();
                        Pelicula peliculaModificar = cinemaPrincipal.buscarPeliculaPorCodigo(codigoPeliculaModificar);

                        if (peliculaModificar == null) {
                            System.out.println("No existe una película con el código " + codigoPeliculaModificar);
                        } else {
                            System.out.print("Nuevo nombre de la película: ");
                            String nuevoNombrePelicula = entradaTeclado.nextLine();
                            System.out.print("Nuevo género de la película: ");
                            String nuevoGeneroPelicula = entradaTeclado.nextLine();
                            System.out.print("Nueva duración en minutos: ");
                            int nuevaDuracionMinutos = entradaTeclado.nextInt();
                            entradaTeclado.nextLine();

                            System.out.println("Nueva clasificación (1. TODOS | 2. MAYORES_7 | 3. MAYORES_12 | 4. MAYORES_15 | 5. MAYORES_18): ");
                            int opcionNuevaClasificacion = entradaTeclado.nextInt();
                            entradaTeclado.nextLine();

                            Clasificacion nuevaClasificacionPelicula = Clasificacion.TODOS;
                            if (opcionNuevaClasificacion == 2) {
                                nuevaClasificacionPelicula = Clasificacion.MAYORES_7;
                            } else if (opcionNuevaClasificacion == 3) {
                                nuevaClasificacionPelicula = Clasificacion.MAYORES_12;
                            } else if (opcionNuevaClasificacion == 4) {
                                nuevaClasificacionPelicula = Clasificacion.MAYORES_15;
                            } else if (opcionNuevaClasificacion == 5) {
                                nuevaClasificacionPelicula = Clasificacion.MAYORES_18;
                            }

                            peliculaModificar.setNombrePelicula(nuevoNombrePelicula);
                            peliculaModificar.setGenero(nuevoGeneroPelicula);
                            peliculaModificar.setDuracionMinutos(nuevaDuracionMinutos);
                            peliculaModificar.setClasificacion(nuevaClasificacionPelicula);
                            System.out.println("Película modificada exitosamente.");
                        }

                    } else if (opcionAdministrarPeliculas == 4) {
                        System.out.print("Código de la película: ");
                        String codigoPeliculaEstado = entradaTeclado.nextLine();
                        Pelicula peliculaCambiarEstado = cinemaPrincipal.buscarPeliculaPorCodigo(codigoPeliculaEstado);

                        if (peliculaCambiarEstado == null) {
                            System.out.println("No existe una película con el código " + codigoPeliculaEstado);
                        } else {
                            System.out.println("Estado actual: " + peliculaCambiarEstado.getEstado());
                            System.out.println("1. DISPONIBLE (Habilitar)");
                            System.out.println("2. NO_DISPONIBLE (Deshabilitar)");
                            System.out.print("Seleccione el nuevo estado: ");
                            int opcionNuevoEstado = entradaTeclado.nextInt();
                            entradaTeclado.nextLine();

                            if (opcionNuevoEstado == 1) {
                                peliculaCambiarEstado.setEstado(EstadoPelicula.DISPONIBLE);
                                System.out.println("La película ahora está DISPONIBLE.");
                            } else if (opcionNuevoEstado == 2) {
                                cinemaPrincipal.deshabilitarPelicula(codigoPeliculaEstado);
                                System.out.println("La película ahora está NO_DISPONIBLE.");
                            } else {
                                System.out.println("Opción no válida.");
                            }
                        }

                    } else {
                        System.out.println("Opción no válida.");
                    }
                    break;

                case 7:
                    System.out.println("\n ---- ADMINISTRAR SALAS ----");
                    System.out.println("1. Registrar sala");
                    System.out.println("2. Consultar salas");
                    System.out.println("3. Modificar sala");
                    System.out.println("4. Deshabilitar o habilitar sala");
                    System.out.print("Seleccione una opción: ");
                    int opcionAdministrarSalas = entradaTeclado.nextInt();
                    entradaTeclado.nextLine();

                    if (opcionAdministrarSalas == 1) {
                        System.out.print("Código de la sala (ej: SALA-01): ");
                        String codigoSalaNueva = entradaTeclado.nextLine();
                        System.out.print("Nombre o número de la sala (ej: Sala 1): ");
                        String nombreSalaNueva = entradaTeclado.nextLine();
                        System.out.print("Cantidad de filas de la sala: ");
                        int cantidadFilasNueva = entradaTeclado.nextInt();
                        System.out.print("Cantidad de asientos por fila: ");
                        int asientosPorFilaNueva = entradaTeclado.nextInt();
                        entradaTeclado.nextLine();

                        int capacidadSalaNueva = cantidadFilasNueva * asientosPorFilaNueva;
                        List<Asiento> listaAsientosNueva = new ArrayList<>();

                        for (int indiceFila = 0; indiceFila < cantidadFilasNueva; indiceFila++) {
                            char letraFila = (char) ('A' + indiceFila);
                            String nombreFila = String.valueOf(letraFila);

                            for (int numeroAsiento = 1; numeroAsiento <= asientosPorFilaNueva; numeroAsiento++) {
                                String codigoAsientoGenerado = nombreFila + numeroAsiento;
                                Asiento asientoNuevo = new Asiento(codigoAsientoGenerado, nombreFila, numeroAsiento);
                                listaAsientosNueva.add(asientoNuevo);
                            }
                        }

                        Sala salaNueva = new Sala(
                                codigoSalaNueva,
                                nombreSalaNueva,
                                capacidadSalaNueva,
                                EstadoSala.DISPONIBLE,
                                listaAsientosNueva
                        );

                        try {
                            cinemaPrincipal.registrarSala(salaNueva);
                            System.out.println("Sala registrada exitosamente con capacidad de " + capacidadSalaNueva + " asientos.");
                        } catch (CodigoSalaDuplicadoException excepcionCodigoSalaDuplicado) {
                            System.out.println("Error: " + excepcionCodigoSalaDuplicado.getMessage());
                        }

                    } else if (opcionAdministrarSalas == 2) {
                        if (cinemaPrincipal.getListaSalas().isEmpty()) {
                            System.out.println("No hay salas registradas.");
                        } else {
                            for (Sala sala : cinemaPrincipal.getListaSalas()) {
                                System.out.println("\nCódigo: " + sala.getCodigo());
                                System.out.println("Nombre o número: " + sala.getNombreONumero());
                                System.out.println("Capacidad: " + sala.getCapacidad() + " asientos");
                                System.out.println("Asientos configurados: " + sala.getListaAsientos().size());
                                System.out.println("Estado: " + sala.getEstado());
                            }
                        }

                    } else if (opcionAdministrarSalas == 3) {
                        System.out.print("Código de la sala a modificar: ");
                        String codigoSalaModificar = entradaTeclado.nextLine();
                        Sala salaModificar = cinemaPrincipal.buscarSalaPorCodigo(codigoSalaModificar);

                        if (salaModificar == null) {
                            System.out.println("No existe una sala con el código " + codigoSalaModificar);
                        } else {
                            System.out.print("Nuevo nombre o número de la sala: ");
                            String nuevoNombreSala = entradaTeclado.nextLine();
                            System.out.print("Nueva cantidad de filas: ");
                            int nuevasFilasSala = entradaTeclado.nextInt();
                            System.out.print("Nueva cantidad de asientos por fila: ");
                            int nuevosAsientosPorFila = entradaTeclado.nextInt();
                            entradaTeclado.nextLine();

                            int nuevaCapacidadSala = nuevasFilasSala * nuevosAsientosPorFila;
                            List<Asiento> nuevaListaAsientos = new ArrayList<>();

                            for (int indiceFila = 0; indiceFila < nuevasFilasSala; indiceFila++) {
                                char letraFila = (char) ('A' + indiceFila);
                                String nombreFila = String.valueOf(letraFila);

                                for (int numeroAsiento = 1; numeroAsiento <= nuevosAsientosPorFila; numeroAsiento++) {
                                    String codigoAsientoGenerado = nombreFila + numeroAsiento;
                                    Asiento asientoNuevo = new Asiento(codigoAsientoGenerado, nombreFila, numeroAsiento);
                                    nuevaListaAsientos.add(asientoNuevo);
                                }
                            }

                            salaModificar.setNombreONumero(nuevoNombreSala);
                            salaModificar.setCapacidad(nuevaCapacidadSala);
                            salaModificar.setListaAsientos(nuevaListaAsientos);
                            System.out.println("Sala modificada exitosamente con nueva capacidad de " + nuevaCapacidadSala + " asientos.");
                        }

                    } else if (opcionAdministrarSalas == 4) {
                        System.out.print("Código de la sala: ");
                        String codigoSalaEstado = entradaTeclado.nextLine();
                        Sala salaCambiarEstado = cinemaPrincipal.buscarSalaPorCodigo(codigoSalaEstado);

                        if (salaCambiarEstado == null) {
                            System.out.println("No existe una sala con el código " + codigoSalaEstado);
                        } else {
                            System.out.println("Estado actual: " + salaCambiarEstado.getEstado());
                            System.out.println("1. DISPONIBLE (Habilitar)");
                            System.out.println("2. FUERA_DE_SERVICIO (Deshabilitar)");
                            System.out.print("Seleccione el nuevo estado: ");
                            int opcionNuevoEstadoSala = entradaTeclado.nextInt();
                            entradaTeclado.nextLine();

                            if (opcionNuevoEstadoSala == 1) {
                                salaCambiarEstado.setEstado(EstadoSala.DISPONIBLE);
                                System.out.println("La sala ahora está DISPONIBLE.");
                            } else if (opcionNuevoEstadoSala == 2) {
                                cinemaPrincipal.deshabilitarSala(codigoSalaEstado);
                                System.out.println("La sala ahora está FUERA_DE_SERVICIO.");
                            } else {
                                System.out.println("Opción no válida.");
                            }
                        }

                    } else {
                        System.out.println("Opción no válida.");
                    }
                    break;

                case 8:
                    System.out.println("\n ---- ADMINISTRAR FUNCIONES ----");
                    System.out.println("1. Programar función");
                    System.out.println("2. Consultar funciones");
                    System.out.println("3. Cambiar estado de una función (PROGRAMADA, FINALIZADA, CANCELADA)");
                    System.out.print("Seleccione una opción: ");
                    int opcionAdministrarFunciones = entradaTeclado.nextInt();
                    entradaTeclado.nextLine();

                    if (opcionAdministrarFunciones == 1) {
                        System.out.print("Código de la función (ej: FUNC-01): ");
                        String codigoFuncionNueva = entradaTeclado.nextLine();

                        if (cinemaPrincipal.buscarFuncionPorCodigo(codigoFuncionNueva) != null) {
                            System.out.println("Error: Ya existe una función con el código " + codigoFuncionNueva);
                        } else {
                            System.out.print("Código de la película: ");
                            String codigoPeliculaFuncion = entradaTeclado.nextLine();
                            Pelicula peliculaSeleccionada = cinemaPrincipal.buscarPeliculaPorCodigo(codigoPeliculaFuncion);

                            if (peliculaSeleccionada == null) {
                                System.out.println("Error: No existe una película con el código " + codigoPeliculaFuncion);
                            } else {
                                System.out.print("Código de la sala: ");
                                String codigoSalaFuncion = entradaTeclado.nextLine();
                                Sala salaSeleccionada = cinemaPrincipal.buscarSalaPorCodigo(codigoSalaFuncion);

                                if (salaSeleccionada == null) {
                                    System.out.println("Error: No existe una sala con el código " + codigoSalaFuncion);
                                } else {
                                    System.out.print("Fecha de la función (AAAA-MM-DD, ej: 2026-11-20): ");
                                    String fechaFuncionTexto = entradaTeclado.nextLine();
                                    LocalDate fechaFuncionNueva = LocalDate.parse(fechaFuncionTexto);

                                    System.out.print("Hora de la función (HH:MM, ej: 19:30): ");
                                    String horaFuncionTexto = entradaTeclado.nextLine();
                                    LocalTime horaFuncionNueva = LocalTime.parse(horaFuncionTexto);

                                    System.out.print("Valor base de la entrada: ");
                                    double valorBaseFuncion = entradaTeclado.nextDouble();
                                    entradaTeclado.nextLine();

                                    Funcion funcionNueva = new Funcion(
                                            codigoFuncionNueva,
                                            peliculaSeleccionada,
                                            salaSeleccionada,
                                            fechaFuncionNueva,
                                            horaFuncionNueva,
                                            valorBaseFuncion,
                                            EstadoFuncion.PROGRAMADA
                                    );

                                    try {
                                        cinemaPrincipal.programarFuncion(funcionNueva);
                                        System.out.println("Función programada exitosamente en estado PROGRAMADA.");
                                    } catch (SalaFueraDeServicioException excepcionSalaFueraDeServicio) {
                                        System.out.println("Error: " + excepcionSalaFueraDeServicio.getMessage());
                                    } catch (IllegalArgumentException excepcionPeliculaNoDisponible) {
                                        System.out.println("Error: " + excepcionPeliculaNoDisponible.getMessage());
                                    }
                                }
                            }
                        }

                    } else if (opcionAdministrarFunciones == 2) {
                        if (cinemaPrincipal.getListaFunciones().isEmpty()) {
                            System.out.println("No hay funciones registradas.");
                        } else {
                            for (Funcion funcion : cinemaPrincipal.getListaFunciones()) {
                                System.out.println("\nCódigo: " + funcion.getCodigoFuncion());
                                System.out.println("Película: " + funcion.getPelicula().getNombrePelicula());
                                System.out.println("Sala: " + funcion.getSala().getNombreONumero());
                                System.out.println("Fecha: " + funcion.getFecha());
                                System.out.println("Hora: " + funcion.getHora());
                                System.out.println("Valor base: $" + funcion.getValorBase());
                                System.out.println("Estado: " + funcion.getEstado());
                            }
                        }

                    } else if (opcionAdministrarFunciones == 3) {
                        System.out.print("Código de la función: ");
                        String codigoFuncionEstado = entradaTeclado.nextLine();
                        Funcion funcionActualizarEstado = cinemaPrincipal.buscarFuncionPorCodigo(codigoFuncionEstado);

                        if (funcionActualizarEstado == null) {
                            System.out.println("No existe una función con el código " + codigoFuncionEstado);
                        } else {
                            System.out.println("Estado actual: " + funcionActualizarEstado.getEstado());
                            System.out.println("1. PROGRAMADA");
                            System.out.println("2. FINALIZADA");
                            System.out.println("3. CANCELADA");
                            System.out.print("Seleccione el nuevo estado: ");
                            int opcionNuevoEstadoFuncion = entradaTeclado.nextInt();
                            entradaTeclado.nextLine();

                            if (opcionNuevoEstadoFuncion == 1) {
                                funcionActualizarEstado.setEstado(EstadoFuncion.PROGRAMADA);
                                System.out.println("La función ahora está PROGRAMADA.");
                            } else if (opcionNuevoEstadoFuncion == 2) {
                                funcionActualizarEstado.setEstado(EstadoFuncion.FINALIZADA);
                                System.out.println("La función ahora está FINALIZADA.");
                            } else if (opcionNuevoEstadoFuncion == 3) {
                                funcionActualizarEstado.setEstado(EstadoFuncion.CANCELADA);
                                System.out.println("La función ahora está CANCELADA.");
                            } else {
                                System.out.println("Opción no válida.");
                            }
                        }

                    } else {
                        System.out.println("Opción no válida.");
                    }
                    break;

                case 9:
                    System.out.println("\n ---- GESTIONAR TARIFAS ----");
                    System.out.println("1. Consultar tarifas por tipo de cliente");
                    System.out.println("2. Definir o actualizar tarifa base de una función");
                    System.out.println("3. Actualizar tarifa base de todas las funciones programadas");
                    System.out.print("Seleccione una opción: ");
                    int opcionGestionarTarifas = entradaTeclado.nextInt();
                    entradaTeclado.nextLine();

                    if (opcionGestionarTarifas == 1) {
                        System.out.print("Código de la función a consultar: ");
                        String codigoFuncionConsultar = entradaTeclado.nextLine();
                        Funcion funcionConsultar = cinemaPrincipal.buscarFuncionPorCodigo(codigoFuncionConsultar);

                        if (funcionConsultar == null) {
                            System.out.println("Error: No existe una función con el código " + codigoFuncionConsultar);
                        } else {
                            System.out.println("\nTarifas para la función " + funcionConsultar.getCodigoFuncion()
                                    + " (" + funcionConsultar.getPelicula().getNombrePelicula() + "):");
                            System.out.println("Valor base: $" + funcionConsultar.getValorBase());
                            System.out.println("- GENERAL (Tarifa plena): $"
                                    + cinemaPrincipal.calcularValorEntrada(funcionConsultar, TipoCliente.GENERAL));
                            System.out.println("- ESTUDIANTE (20% descuento): $"
                                    + cinemaPrincipal.calcularValorEntrada(funcionConsultar, TipoCliente.ESTUDIANTE));
                            System.out.println("- NINO (34% descuento): $"
                                    + cinemaPrincipal.calcularValorEntrada(funcionConsultar, TipoCliente.NINO));
                            System.out.println("- ADULTO_MAYOR (27% descuento): $"
                                    + cinemaPrincipal.calcularValorEntrada(funcionConsultar, TipoCliente.ADULTO_MAYOR));
                        }

                    } else if (opcionGestionarTarifas == 2) {
                        System.out.print("Código de la función: ");
                        String codigoFuncionActualizar = entradaTeclado.nextLine();
                        Funcion funcionActualizar = cinemaPrincipal.buscarFuncionPorCodigo(codigoFuncionActualizar);

                        if (funcionActualizar == null) {
                            System.out.println("Error: No existe una función con el código " + codigoFuncionActualizar);
                        } else {
                            System.out.println("Valor base actual: $" + funcionActualizar.getValorBase());
                            System.out.print("Ingrese el nuevo valor base de entrada: ");
                            double nuevoValorBase = entradaTeclado.nextDouble();
                            entradaTeclado.nextLine();

                            if (nuevoValorBase <= 0) {
                                System.out.println("Error: El valor base debe ser mayor que cero.");
                            } else {
                                funcionActualizar.setValorBase(nuevoValorBase);
                                System.out.println("\nTarifa actualizada exitosamente. Nuevos valores por tipo de cliente:");
                                System.out.println("- GENERAL: $"
                                        + cinemaPrincipal.calcularValorEntrada(funcionActualizar, TipoCliente.GENERAL));
                                System.out.println("- ESTUDIANTE: $"
                                        + cinemaPrincipal.calcularValorEntrada(funcionActualizar, TipoCliente.ESTUDIANTE));
                                System.out.println("- NINO: $"
                                        + cinemaPrincipal.calcularValorEntrada(funcionActualizar, TipoCliente.NINO));
                                System.out.println("- ADULTO_MAYOR: $"
                                        + cinemaPrincipal.calcularValorEntrada(funcionActualizar, TipoCliente.ADULTO_MAYOR));
                            }
                        }

                    } else if (opcionGestionarTarifas == 3) {
                        System.out.print("Ingrese el nuevo valor base para todas las funciones programadas: ");
                        double nuevoValorBaseGeneral = entradaTeclado.nextDouble();
                        entradaTeclado.nextLine();

                        if (nuevoValorBaseGeneral <= 0) {
                            System.out.println("Error: El valor base debe ser mayor que cero.");
                        } else {
                            int funcionesActualizadas = 0;
                            for (Funcion funcion : cinemaPrincipal.getListaFunciones()) {
                                if (funcion.getEstado() == EstadoFuncion.PROGRAMADA) {
                                    funcion.setValorBase(nuevoValorBaseGeneral);
                                    funcionesActualizadas++;
                                }
                            }

                            if (funcionesActualizadas == 0) {
                                System.out.println("No hay funciones programadas para actualizar.");
                            } else {
                                System.out.println("Se actualizaron las tarifas de " + funcionesActualizadas + " funciones programadas.");
                            }
                        }

                    } else {
                        System.out.println("Opción no válida.");
                    }
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