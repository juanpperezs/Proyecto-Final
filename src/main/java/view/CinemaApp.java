package view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import model.core.Cinema;
import model.core.Cliente;
import model.core.Funcion;
import model.core.ICinema;
import model.core.Pelicula;
import model.core.Venta;
import model.enums.Clasificacion;
import model.enums.EstadoAsiento;
import model.enums.EstadoFuncion;
import model.enums.TipoCliente;
import model.espacios.Asiento;
import model.espacios.Sala;

import java.time.LocalDate;
import java.time.LocalTime;

public class CinemaApp extends Application {

    private final ICinema sistemaCinema = new Cinema("CinemaUQ");
    private String codigoAsientoSeleccionado = null;

    private final ComboBox<Funcion> selectorFuncionesTaquilla = new ComboBox<>();
    private final ComboBox<Cliente> selectorClientesTaquilla = new ComboBox<>();
    private final FlowPane contenedorMapaAsientos = new FlowPane(8, 8);
    private final Label etiquetaAsientoSeleccionado = new Label("Asiento seleccionado: Ninguno");
    private final Label etiquetaValorAPagar = new Label("Valor a pagar: $0.0");
    private final TextField campoCodigoVenta = new TextField("VENTA-03");
    private final ListView<Venta> listaVisualVentas = new ListView<>();

    private final ListView<Pelicula> listaVisualPeliculas = new ListView<>();
    private final ListView<Sala> listaVisualSalas = new ListView<>();
    private final ListView<Funcion> listaVisualFuncionesAdministracion = new ListView<>();
    private final ComboBox<Pelicula> selectorPeliculaParaFuncion = new ComboBox<>();
    private final ComboBox<Sala> selectorSalaParaFuncion = new ComboBox<>();

    private final Label etiquetaTotalEntradasVendidas = new Label("Entradas activas: 0");
    private final Label etiquetaTotalIngresosRecaudados = new Label("Ingresos totales: $0.0");
    private final Label etiquetaPeliculaMasVendida = new Label("Película más vendida: Ninguna");
    private final ListView<String> listaVisualOcupacionFunciones = new ListView<>();

    @Override
    public void start(Stage escenarioPrincipal) {
        cargarDatosIniciales();
        configurarFormatosVisuales();

        TabPane panelPestanas = new TabPane();
        panelPestanas.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        panelPestanas.getTabs().addAll(
                new Tab("Taquilla y Asientos", construirPestanaTaquilla()),
                new Tab("Administración", construirPestanaAdministracion()),
                new Tab("Reportes", construirPestanaReportes())
        );

        actualizarTodaLaInterfaz();

        Scene escenaPrincipal = new Scene(panelPestanas, 1060, 660);
        escenarioPrincipal.setTitle("CinemaUQ - Sistema de Gestión");
        escenarioPrincipal.setScene(escenaPrincipal);
        escenarioPrincipal.show();
    }

    private BorderPane construirPestanaTaquilla() {
        BorderPane panelPrincipal = new BorderPane();
        panelPrincipal.setPadding(new Insets(15));

        VBox panelIzquierdoAsientos = new VBox(12);
        panelIzquierdoAsientos.setPrefWidth(540);
        panelIzquierdoAsientos.setStyle("-fx-border-color: #cccccc; -fx-border-radius: 6; -fx-padding: 12;");

        selectorFuncionesTaquilla.setMaxWidth(Double.MAX_VALUE);
        selectorFuncionesTaquilla.setOnAction(eventoSeleccion -> {
            codigoAsientoSeleccionado = null;
            etiquetaAsientoSeleccionado.setText("Asiento seleccionado: Ninguno");
            actualizarMapaAsientos();
            actualizarPrecioEntrada();
        });

        contenedorMapaAsientos.setPadding(new Insets(10));
        contenedorMapaAsientos.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: #dddddd;");

        panelIzquierdoAsientos.getChildren().addAll(
                new Label("1. Seleccione una Función:"),
                selectorFuncionesTaquilla,
                new Label("2. Haga clic en un asiento disponible (Verde = Libre, Rojo = Ocupado):"),
                contenedorMapaAsientos,
                etiquetaAsientoSeleccionado
        );

        VBox panelDerechoVentas = new VBox(10);
        panelDerechoVentas.setPrefWidth(470);
        panelDerechoVentas.setStyle("-fx-border-color: #cccccc; -fx-border-radius: 6; -fx-padding: 12;");

        selectorClientesTaquilla.setMaxWidth(Double.MAX_VALUE);
        selectorClientesTaquilla.setOnAction(eventoSeleccion -> actualizarPrecioEntrada());

        TextField campoIdentificacionCliente = new TextField();
        campoIdentificacionCliente.setPromptText("ID Cliente");
        campoIdentificacionCliente.setPrefWidth(100);

        TextField campoNombreCliente = new TextField();
        campoNombreCliente.setPromptText("Nombre");
        campoNombreCliente.setPrefWidth(130);

        ComboBox<TipoCliente> selectorTipoCliente = new ComboBox<>();
        selectorTipoCliente.getItems().setAll(TipoCliente.values());
        selectorTipoCliente.setValue(TipoCliente.GENERAL);

        Button botonRegistrarClienteRapido = new Button("+ Cliente");
        botonRegistrarClienteRapido.setOnAction(eventoClic -> {
            if (campoIdentificacionCliente.getText().isBlank() || campoNombreCliente.getText().isBlank()) {
                return;
            }
            Cliente nuevoCliente = new Cliente(
                    campoIdentificacionCliente.getText().trim(),
                    campoNombreCliente.getText().trim(),
                    selectorTipoCliente.getValue()
            );
            sistemaCinema.registrarCliente(nuevoCliente);
            campoIdentificacionCliente.clear();
            campoNombreCliente.clear();
            actualizarTodaLaInterfaz();
        });

        HBox filaRegistroClienteRapido = new HBox(
                6,
                campoIdentificacionCliente,
                campoNombreCliente,
                selectorTipoCliente,
                botonRegistrarClienteRapido
        );

        etiquetaValorAPagar.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: #1b5e20;");

        Button botonConfirmarVenta = new Button("Confirmar Venta");
        botonConfirmarVenta.setStyle("-fx-background-color: #2e7d32; -fx-text-fill: white; -fx-font-weight: bold;");
        botonConfirmarVenta.setMaxWidth(Double.MAX_VALUE);
        botonConfirmarVenta.setOnAction(eventoClic -> {
            Funcion funcionSeleccionada = selectorFuncionesTaquilla.getValue();
            Cliente clienteSeleccionado = selectorClientesTaquilla.getValue();
            if (funcionSeleccionada == null || clienteSeleccionado == null || codigoAsientoSeleccionado == null) {
                mostrarVentanaAlerta("Datos incompletos", "Seleccione función, cliente y un asiento verde.");
                return;
            }
            try {
                sistemaCinema.venderEntrada(
                        campoCodigoVenta.getText().trim(),
                        clienteSeleccionado.getIdentificacionCliente(),
                        funcionSeleccionada.getCodigoFuncion(),
                        codigoAsientoSeleccionado
                );
                campoCodigoVenta.setText("VENTA-0" + (sistemaCinema.getListaVentas().size() + 1));
                codigoAsientoSeleccionado = null;
                etiquetaAsientoSeleccionado.setText("Asiento seleccionado: Ninguno");
                actualizarTodaLaInterfaz();
            } catch (Exception excepcion) {
                mostrarVentanaAlerta("No se pudo vender", excepcion.getMessage());
            }
        });

        listaVisualVentas.setPrefHeight(175);

        Button botonCancelarVentaSeleccionada = new Button("Cancelar Venta Seleccionada");
        botonCancelarVentaSeleccionada.setStyle("-fx-background-color: #c62828; -fx-text-fill: white;");
        botonCancelarVentaSeleccionada.setMaxWidth(Double.MAX_VALUE);
        botonCancelarVentaSeleccionada.setOnAction(eventoClic -> {
            Venta ventaSeleccionada = listaVisualVentas.getSelectionModel().getSelectedItem();
            if (ventaSeleccionada == null) {
                return;
            }
            try {
                sistemaCinema.cancelarVenta(ventaSeleccionada.getCodigoVenta());
                actualizarTodaLaInterfaz();
            } catch (Exception excepcion) {
                mostrarVentanaAlerta("Error al cancelar", excepcion.getMessage());
            }
        });

        panelDerechoVentas.getChildren().addAll(
                new Label("Código de Venta:"),
                campoCodigoVenta,
                new Label("Cliente:"),
                selectorClientesTaquilla,
                new Label("Registro rápido de cliente:"),
                filaRegistroClienteRapido,
                etiquetaValorAPagar,
                botonConfirmarVenta,
                new Separator(),
                new Label("Historial de Ventas:"),
                listaVisualVentas,
                botonCancelarVentaSeleccionada
        );

        panelPrincipal.setLeft(panelIzquierdoAsientos);
        panelPrincipal.setRight(panelDerechoVentas);
        return panelPrincipal;
    }

    private HBox construirPestanaAdministracion() {
        HBox panelPrincipal = new HBox(12);
        panelPrincipal.setPadding(new Insets(15));

        VBox columnaGestionPeliculas = new VBox(8);
        columnaGestionPeliculas.setPrefWidth(335);

        TextField campoCodigoPelicula = new TextField();
        campoCodigoPelicula.setPromptText("Código (ej: PEL-03)");

        TextField campoNombrePelicula = new TextField();
        campoNombrePelicula.setPromptText("Nombre película");

        TextField campoGeneroPelicula = new TextField();
        campoGeneroPelicula.setPromptText("Género");

        TextField campoDuracionPelicula = new TextField();
        campoDuracionPelicula.setPromptText("Duración (min)");

        ComboBox<Clasificacion> selectorClasificacionPelicula = new ComboBox<>();
        selectorClasificacionPelicula.getItems().setAll(Clasificacion.values());
        selectorClasificacionPelicula.setValue(Clasificacion.TODOS);

        Button botonRegistrarPelicula = new Button("Registrar Película");
        botonRegistrarPelicula.setMaxWidth(Double.MAX_VALUE);
        botonRegistrarPelicula.setOnAction(eventoClic -> {
            try {
                int duracionEnMinutos = Integer.parseInt(campoDuracionPelicula.getText().trim());
                Pelicula nuevaPelicula = new Pelicula(
                        campoCodigoPelicula.getText().trim(),
                        campoNombrePelicula.getText().trim(),
                        campoGeneroPelicula.getText().trim(),
                        duracionEnMinutos,
                        selectorClasificacionPelicula.getValue()
                );
                sistemaCinema.registrarPelicula(nuevaPelicula);
                campoCodigoPelicula.clear();
                campoNombrePelicula.clear();
                campoGeneroPelicula.clear();
                campoDuracionPelicula.clear();
                actualizarTodaLaInterfaz();
            } catch (Exception excepcion) {
                mostrarVentanaAlerta("Error en Película", excepcion.getMessage());
            }
        });

        Button botonDeshabilitarPelicula = new Button("Deshabilitar Seleccionada");
        botonDeshabilitarPelicula.setMaxWidth(Double.MAX_VALUE);
        botonDeshabilitarPelicula.setOnAction(eventoClic -> {
            Pelicula peliculaSeleccionada = listaVisualPeliculas.getSelectionModel().getSelectedItem();
            if (peliculaSeleccionada != null) {
                sistemaCinema.deshabilitarPelicula(peliculaSeleccionada.getCodigoPelicula());
                actualizarTodaLaInterfaz();
            }
        });

        columnaGestionPeliculas.getChildren().addAll(
                new Label("PELÍCULAS"),
                campoCodigoPelicula,
                campoNombrePelicula,
                campoGeneroPelicula,
                campoDuracionPelicula,
                selectorClasificacionPelicula,
                botonRegistrarPelicula,
                listaVisualPeliculas,
                botonDeshabilitarPelicula
        );

        VBox columnaGestionSalas = new VBox(8);
        columnaGestionSalas.setPrefWidth(335);

        TextField campoCodigoSala = new TextField();
        campoCodigoSala.setPromptText("Código (ej: SALA-03)");

        TextField campoNombreSala = new TextField();
        campoNombreSala.setPromptText("Nombre (ej: Sala 3)");

        TextField campoCapacidadSala = new TextField();
        campoCapacidadSala.setPromptText("Capacidad (ej: 12)");

        Button botonRegistrarSala = new Button("Registrar Sala");
        botonRegistrarSala.setMaxWidth(Double.MAX_VALUE);
        botonRegistrarSala.setOnAction(eventoClic -> {
            try {
                int capacidadSala = Integer.parseInt(campoCapacidadSala.getText().trim());
                Sala nuevaSala = new Sala(
                        campoCodigoSala.getText().trim(),
                        campoNombreSala.getText().trim(),
                        capacidadSala
                );
                if (nuevaSala.getListaAsientos().isEmpty()) {
                    nuevaSala.generarAsientos();
                }
                sistemaCinema.registrarSala(nuevaSala);
                campoCodigoSala.clear();
                campoNombreSala.clear();
                campoCapacidadSala.clear();
                actualizarTodaLaInterfaz();
            } catch (Exception excepcion) {
                mostrarVentanaAlerta("Error en Sala", excepcion.getMessage());
            }
        });

        Button botonDeshabilitarSala = new Button("Deshabilitar Seleccionada");
        botonDeshabilitarSala.setMaxWidth(Double.MAX_VALUE);
        botonDeshabilitarSala.setOnAction(eventoClic -> {
            Sala salaSeleccionada = listaVisualSalas.getSelectionModel().getSelectedItem();
            if (salaSeleccionada != null) {
                sistemaCinema.deshabilitarSala(salaSeleccionada.getCodigo());
                actualizarTodaLaInterfaz();
            }
        });

        columnaGestionSalas.getChildren().addAll(
                new Label("SALAS"),
                campoCodigoSala,
                campoNombreSala,
                campoCapacidadSala,
                botonRegistrarSala,
                listaVisualSalas,
                botonDeshabilitarSala
        );

        VBox columnaGestionFunciones = new VBox(8);
        columnaGestionFunciones.setPrefWidth(340);

        TextField campoCodigoFuncion = new TextField();
        campoCodigoFuncion.setPromptText("Código (ej: FUNC-03)");

        selectorPeliculaParaFuncion.setMaxWidth(Double.MAX_VALUE);
        selectorSalaParaFuncion.setMaxWidth(Double.MAX_VALUE);

        DatePicker selectorFechaFuncion = new DatePicker(LocalDate.now());
        TextField campoHoraFuncion = new TextField("19:00");
        TextField campoValorBaseFuncion = new TextField("10000");

        Button botonProgramarFuncion = new Button("Programar Función");
        botonProgramarFuncion.setMaxWidth(Double.MAX_VALUE);
        botonProgramarFuncion.setOnAction(eventoClic -> {
            try {
                Funcion nuevaFuncion = new Funcion(
                        campoCodigoFuncion.getText().trim(),
                        selectorPeliculaParaFuncion.getValue(),
                        selectorSalaParaFuncion.getValue(),
                        selectorFechaFuncion.getValue(),
                        LocalTime.parse(campoHoraFuncion.getText().trim()),
                        Double.parseDouble(campoValorBaseFuncion.getText().trim()),
                        EstadoFuncion.PROGRAMADA
                );
                sistemaCinema.programarFuncion(nuevaFuncion);
                campoCodigoFuncion.clear();
                actualizarTodaLaInterfaz();
            } catch (Exception excepcion) {
                mostrarVentanaAlerta("Error en Función", excepcion.getMessage());
            }
        });

        columnaGestionFunciones.getChildren().addAll(
                new Label("FUNCIONES"),
                campoCodigoFuncion,
                selectorPeliculaParaFuncion,
                selectorSalaParaFuncion,
                selectorFechaFuncion,
                campoHoraFuncion,
                campoValorBaseFuncion,
                botonProgramarFuncion,
                listaVisualFuncionesAdministracion
        );

        panelPrincipal.getChildren().addAll(
                columnaGestionPeliculas,
                columnaGestionSalas,
                columnaGestionFunciones
        );
        return panelPrincipal;
    }

    private VBox construirPestanaReportes() {
        VBox panelPrincipal = new VBox(14);
        panelPrincipal.setPadding(new Insets(20));

        etiquetaTotalEntradasVendidas.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        etiquetaTotalIngresosRecaudados.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #1b5e20;");
        etiquetaPeliculaMasVendida.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #0d47a1;");

        Button botonActualizarReportes = new Button("Actualizar Reportes");
        botonActualizarReportes.setOnAction(eventoClic -> actualizarReportes());

        panelPrincipal.getChildren().addAll(
                new Label("RESUMEN GERENCIAL CINEMAUQ"),
                etiquetaTotalEntradasVendidas,
                etiquetaTotalIngresosRecaudados,
                etiquetaPeliculaMasVendida,
                new Separator(),
                new Label("Ocupación detallada por función:"),
                listaVisualOcupacionFunciones,
                botonActualizarReportes
        );
        return panelPrincipal;
    }

    private void actualizarMapaAsientos() {
        contenedorMapaAsientos.getChildren().clear();
        Funcion funcionSeleccionada = selectorFuncionesTaquilla.getValue();
        if (funcionSeleccionada == null) {
            return;
        }

        for (Asiento asiento : funcionSeleccionada.getListaAsientos()) {
            Button botonAsiento = new Button(asiento.getCodigoAsiento());
            botonAsiento.setPrefSize(62, 42);
            if (asiento.getEstado() == EstadoAsiento.OCUPADO) {
                botonAsiento.setStyle("-fx-background-color: #e53935; -fx-text-fill: white; -fx-font-weight: bold;");
                botonAsiento.setDisable(true);
            } else {
                botonAsiento.setStyle("-fx-background-color: #43a047; -fx-text-fill: white; -fx-font-weight: bold;");
                botonAsiento.setOnAction(eventoSeleccionAsiento -> {
                    codigoAsientoSeleccionado = asiento.getCodigoAsiento();
                    etiquetaAsientoSeleccionado.setText("Asiento seleccionado: " + codigoAsientoSeleccionado);
                });
            }
            contenedorMapaAsientos.getChildren().add(botonAsiento);
        }
    }

    private void actualizarPrecioEntrada() {
        Funcion funcionSeleccionada = selectorFuncionesTaquilla.getValue();
        Cliente clienteSeleccionado = selectorClientesTaquilla.getValue();
        if (funcionSeleccionada != null && clienteSeleccionado != null) {
            double valorCalculado = sistemaCinema.calcularValorEntrada(
                    funcionSeleccionada,
                    clienteSeleccionado.getTipoCliente()
            );
            etiquetaValorAPagar.setText("Valor a pagar (" + clienteSeleccionado.getTipoCliente() + "): $" + valorCalculado);
        }
    }

    private void actualizarReportes() {
        etiquetaTotalEntradasVendidas.setText("Entradas activas vendidas: " + sistemaCinema.totalEntradasVendidas());
        etiquetaTotalIngresosRecaudados.setText("Ingresos totales recaudados: $" + sistemaCinema.totalIngresos());
        Pelicula peliculaLider = sistemaCinema.peliculaMasVendida();
        String nombrePeliculaLider = peliculaLider != null ? peliculaLider.getNombrePelicula() : "Sin ventas";
        etiquetaPeliculaMasVendida.setText("Película más vendida: " + nombrePeliculaLider);

        listaVisualOcupacionFunciones.getItems().clear();
        for (Funcion funcion : sistemaCinema.getListaFunciones()) {
            try {
                listaVisualOcupacionFunciones.getItems().add(sistemaCinema.ocupacionFuncion(funcion.getCodigoFuncion()));
            } catch (Exception excepcionReporte) {
                listaVisualOcupacionFunciones.getItems().add("Error al consultar ocupación de " + funcion.getCodigoFuncion());
            }
        }
    }

    private void actualizarTodaLaInterfaz() {
        Funcion funcionPreviamenteSeleccionada = selectorFuncionesTaquilla.getValue();
        selectorFuncionesTaquilla.getItems().setAll(sistemaCinema.getListaFunciones());
        if (funcionPreviamenteSeleccionada != null) {
            selectorFuncionesTaquilla.setValue(funcionPreviamenteSeleccionada);
        } else if (!sistemaCinema.getListaFunciones().isEmpty()) {
            selectorFuncionesTaquilla.getSelectionModel().selectFirst();
        }

        selectorClientesTaquilla.getItems().setAll(sistemaCinema.getListaClientes());
        if (!sistemaCinema.getListaClientes().isEmpty() && selectorClientesTaquilla.getValue() == null) {
            selectorClientesTaquilla.getSelectionModel().selectFirst();
        }

        listaVisualVentas.getItems().setAll(sistemaCinema.getListaVentas());
        listaVisualPeliculas.getItems().setAll(sistemaCinema.getListaPeliculas());
        listaVisualSalas.getItems().setAll(sistemaCinema.getListaSalas());
        listaVisualFuncionesAdministracion.getItems().setAll(sistemaCinema.getListaFunciones());
        selectorPeliculaParaFuncion.getItems().setAll(sistemaCinema.getListaPeliculas());
        selectorSalaParaFuncion.getItems().setAll(sistemaCinema.getListaSalas());

        actualizarMapaAsientos();
        actualizarPrecioEntrada();
        actualizarReportes();
    }

    private void configurarFormatosVisuales() {
        StringConverter<Funcion> convertidorTextoFuncion = new StringConverter<>() {
            @Override
            public String toString(Funcion funcion) {
                if (funcion == null) {
                    return "";
                }
                return funcion.getCodigoFuncion() + " - " + funcion.getPelicula().getNombrePelicula()
                        + " (" + funcion.getSala().getNombreONumero() + " | " + funcion.getFecha() + " " + funcion.getHora() + ")";
            }

            @Override
            public Funcion fromString(String texto) {
                return null;
            }
        };
        selectorFuncionesTaquilla.setConverter(convertidorTextoFuncion);

        StringConverter<Cliente> convertidorTextoCliente = new StringConverter<>() {
            @Override
            public String toString(Cliente cliente) {
                if (cliente == null) {
                    return "";
                }
                return cliente.getIdentificacionCliente() + " - " + cliente.getNombre() + " [" + cliente.getTipoCliente() + "]";
            }

            @Override
            public Cliente fromString(String texto) {
                return null;
            }
        };
        selectorClientesTaquilla.setConverter(convertidorTextoCliente);

        StringConverter<Pelicula> convertidorTextoPelicula = new StringConverter<>() {
            @Override
            public String toString(Pelicula pelicula) {
                if (pelicula == null) {
                    return "";
                }
                return pelicula.getCodigoPelicula() + " - " + pelicula.getNombrePelicula() + " (" + pelicula.getEstado() + ")";
            }

            @Override
            public Pelicula fromString(String texto) {
                return null;
            }
        };
        selectorPeliculaParaFuncion.setConverter(convertidorTextoPelicula);

        StringConverter<Sala> convertidorTextoSala = new StringConverter<>() {
            @Override
            public String toString(Sala sala) {
                if (sala == null) {
                    return "";
                }
                return sala.getCodigo() + " - " + sala.getNombreONumero() + " (" + sala.getEstado() + ")";
            }

            @Override
            public Sala fromString(String texto) {
                return null;
            }
        };
        selectorSalaParaFuncion.setConverter(convertidorTextoSala);

        listaVisualPeliculas.setCellFactory(listaVisual -> new ListCell<>() {
            @Override
            protected void updateItem(Pelicula pelicula, boolean estaVacia) {
                super.updateItem(pelicula, estaVacia);
                if (estaVacia || pelicula == null) {
                    setText(null);
                } else {
                    setText(pelicula.getCodigoPelicula() + " | " + pelicula.getNombrePelicula() + " [" + pelicula.getEstado() + "]");
                }
            }
        });

        listaVisualSalas.setCellFactory(listaVisual -> new ListCell<>() {
            @Override
            protected void updateItem(Sala sala, boolean estaVacia) {
                super.updateItem(sala, estaVacia);
                if (estaVacia || sala == null) {
                    setText(null);
                } else {
                    setText(sala.getCodigo() + " | " + sala.getNombreONumero() + " (Cap: " + sala.getCapacidad() + ") [" + sala.getEstado() + "]");
                }
            }
        });

        listaVisualFuncionesAdministracion.setCellFactory(listaVisual -> new ListCell<>() {
            @Override
            protected void updateItem(Funcion funcion, boolean estaVacia) {
                super.updateItem(funcion, estaVacia);
                if (estaVacia || funcion == null) {
                    setText(null);
                } else {
                    setText(convertidorTextoFuncion.toString(funcion));
                }
            }
        });

        listaVisualVentas.setCellFactory(listaVisual -> new ListCell<>() {
            @Override
            protected void updateItem(Venta venta, boolean estaVacia) {
                super.updateItem(venta, estaVacia);
                if (estaVacia || venta == null) {
                    setText(null);
                } else {
                    setText(venta.getCodigoVenta() + " | " + venta.getCliente().getNombre()
                            + " | Silla " + venta.getAsiento().getCodigoAsiento() + " | $" + venta.getValorPagado()
                            + " [" + venta.getEstadoVenta() + "]");
                }
            }
        });
    }

    private void cargarDatosIniciales() {
        try {
            Pelicula peliculaInterstellar = new Pelicula("PEL-01", "Interstellar", "Ciencia Ficción", 169, Clasificacion.MAYORES_12);
            Pelicula peliculaToyStory = new Pelicula("PEL-02", "Toy Story", "Animación", 81, Clasificacion.TODOS);
            sistemaCinema.registrarPelicula(peliculaInterstellar);
            sistemaCinema.registrarPelicula(peliculaToyStory);

            Sala salaPrincipal = new Sala("SALA-01", "Sala 1", 12);
            if (salaPrincipal.getListaAsientos().isEmpty()) {
                salaPrincipal.generarAsientos();
            }
            sistemaCinema.registrarSala(salaPrincipal);

            sistemaCinema.registrarCliente(new Cliente("1001", "Juan Pérez", TipoCliente.ESTUDIANTE));
            sistemaCinema.registrarCliente(new Cliente("1002", "María Gómez", TipoCliente.GENERAL));
            sistemaCinema.registrarCliente(new Cliente("1003", "Carlos Ruiz", TipoCliente.NINO));

            Funcion funcionTarde = new Funcion("FUNC-01", peliculaInterstellar, salaPrincipal, LocalDate.now(), LocalTime.of(18, 30), 10000.0, EstadoFuncion.PROGRAMADA);
            Funcion funcionNoche = new Funcion("FUNC-02", peliculaToyStory, salaPrincipal, LocalDate.now(), LocalTime.of(21, 0), 10000.0, EstadoFuncion.PROGRAMADA);
            sistemaCinema.programarFuncion(funcionTarde);
            sistemaCinema.programarFuncion(funcionNoche);

            sistemaCinema.venderEntrada("VENTA-01", "1001", "FUNC-01", "A1");
            sistemaCinema.venderEntrada("VENTA-02", "1003", "FUNC-02", "B1");
        } catch (Exception excepcionInicializacion) {
        }
    }

    private void mostrarVentanaAlerta(String titulo, String mensaje) {
        Alert ventanaAlerta = new Alert(Alert.AlertType.INFORMATION);
        ventanaAlerta.setTitle(titulo);
        ventanaAlerta.setHeaderText(null);
        ventanaAlerta.setContentText(mensaje);
        ventanaAlerta.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}