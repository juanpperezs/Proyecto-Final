import model.core.Cinema;
import model.core.Cliente;
import model.core.Funcion;
import model.core.Pelicula;
import model.core.Venta;
import model.enums.Clasificacion;
import model.enums.EstadoAsiento;
import model.enums.EstadoFuncion;
import model.enums.EstadoPelicula;
import model.enums.EstadoSala;
import model.enums.EstadoVenta;
import model.enums.TipoCliente;
import model.espacios.Asiento;
import model.espacios.Sala;
import model.exceptions.AsientoOcupadoException;
import model.exceptions.CodigoPeliculaDuplicadoException;
import model.exceptions.CodigoSalaDuplicadoException;
import model.exceptions.FuncionInexistenteException;
import model.exceptions.SalaFueraDeServicioException;
import model.exceptions.VentaInexistenteException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CinemaTest {

    private Cinema cinemaPrincipal;
    private Pelicula peliculaPrincipal;
    private Pelicula peliculaSecundaria;
    private Sala salaPrincipal;
    private Asiento primerAsiento;
    private Asiento segundoAsiento;
    private Cliente clienteGeneral;
    private Cliente clienteEstudiante;
    private Cliente clienteNino;
    private Cliente clienteAdultoMayor;
    private Funcion funcionPrincipal;

    @BeforeEach
    void prepararEscenarioCompletoDeCinema() throws Exception {
        cinemaPrincipal = new Cinema("CinemaUQ");

        peliculaPrincipal = new Pelicula("PEL-01", "Interestelar", "Ciencia Ficcion", 169, Clasificacion.MAYORES_12);
        peliculaSecundaria = new Pelicula("PEL-02", "Intensamente 2", "Animacion", 96, Clasificacion.TODOS);
        cinemaPrincipal.registrarPelicula(peliculaPrincipal);
        cinemaPrincipal.registrarPelicula(peliculaSecundaria);

        primerAsiento = new Asiento("A1", "A", 1);
        segundoAsiento = new Asiento("A2", "A", 2);
        List<Asiento> listaAsientosSalaPrincipal = new ArrayList<>();
        listaAsientosSalaPrincipal.add(primerAsiento);
        listaAsientosSalaPrincipal.add(segundoAsiento);

        salaPrincipal = new Sala("SALA-01", "Sala Principal", 2, EstadoSala.DISPONIBLE, listaAsientosSalaPrincipal);
        cinemaPrincipal.registrarSala(salaPrincipal);

        clienteGeneral = new Cliente("1001", "Carlos Gomez", TipoCliente.GENERAL);
        clienteEstudiante = new Cliente("1002", "Laura Martinez", TipoCliente.ESTUDIANTE);
        clienteNino = new Cliente("1003", "Mateo Ruiz", TipoCliente.NINO);
        clienteAdultoMayor = new Cliente("1004", "Elena Torres", TipoCliente.ADULTO_MAYOR);
        cinemaPrincipal.registrarCliente(clienteGeneral);
        cinemaPrincipal.registrarCliente(clienteEstudiante);
        cinemaPrincipal.registrarCliente(clienteNino);
        cinemaPrincipal.registrarCliente(clienteAdultoMayor);

        funcionPrincipal = new Funcion(
                "FUNC-01",
                peliculaPrincipal,
                salaPrincipal,
                LocalDate.of(2026, 11, 20),
                LocalTime.of(20, 0),
                10000.0,
                EstadoFuncion.PROGRAMADA
        );
        cinemaPrincipal.programarFuncion(funcionPrincipal);
    }

    @Test
    void verificarBusquedaYDeshabilitacionDePelicula() {
        Pelicula peliculaEncontrada = cinemaPrincipal.buscarPeliculaPorCodigo("PEL-01");
        Pelicula peliculaInexistente = cinemaPrincipal.buscarPeliculaPorCodigo("PEL-99");

        assertNotNull(peliculaEncontrada);
        assertEquals("Interestelar", peliculaEncontrada.getNombrePelicula());
        assertNull(peliculaInexistente);

        boolean resultadoDeshabilitar = cinemaPrincipal.deshabilitarPelicula("PEL-01");
        boolean resultadoInexistente = cinemaPrincipal.deshabilitarPelicula("PEL-99");

        assertTrue(resultadoDeshabilitar);
        assertFalse(resultadoInexistente);
        assertEquals(EstadoPelicula.NO_DISPONIBLE, peliculaPrincipal.getEstado());
    }

    @Test
    void verificarExcepcionAlRegistrarPeliculaConCodigoDuplicado() {
        Pelicula peliculaDuplicada = new Pelicula("PEL-01", "Otra Pelicula", "Drama", 120, Clasificacion.TODOS);

        assertThrows(CodigoPeliculaDuplicadoException.class, () -> {
            cinemaPrincipal.registrarPelicula(peliculaDuplicada);
        });
    }

    @Test
    void verificarBusquedaYDeshabilitacionDeSala() {
        Sala salaEncontrada = cinemaPrincipal.buscarSalaPorCodigo("SALA-01");
        Sala salaInexistente = cinemaPrincipal.buscarSalaPorCodigo("SALA-99");

        assertNotNull(salaEncontrada);
        assertEquals("Sala Principal", salaEncontrada.getNombreONumero());
        assertNull(salaInexistente);

        boolean resultadoDeshabilitar = cinemaPrincipal.deshabilitarSala("SALA-01");
        boolean resultadoInexistente = cinemaPrincipal.deshabilitarSala("SALA-99");

        assertTrue(resultadoDeshabilitar);
        assertFalse(resultadoInexistente);
        assertEquals(EstadoSala.FUERA_DE_SERVICIO, salaPrincipal.getEstado());
    }

    @Test
    void verificarExcepcionAlRegistrarSalaConCodigoDuplicado() {
        List<Asiento> listaAsientosDuplicada = new ArrayList<>();
        listaAsientosDuplicada.add(new Asiento("B1", "B", 1));
        Sala salaDuplicada = new Sala("SALA-01", "Sala Repetida", 1, EstadoSala.DISPONIBLE, listaAsientosDuplicada);

        assertThrows(CodigoSalaDuplicadoException.class, () -> {
            cinemaPrincipal.registrarSala(salaDuplicada);
        });
    }

    @Test
    void verificarRegistroYBusquedaDeClientes() {
        Cliente clienteRepetido = new Cliente("1001", "Carlos Duplicado", TipoCliente.GENERAL);
        boolean resultadoRegistroRepetido = cinemaPrincipal.registrarCliente(clienteRepetido);

        assertFalse(resultadoRegistroRepetido);

        Cliente clienteEncontrado = cinemaPrincipal.buscarClientePorId("1002");
        Cliente clienteInexistente = cinemaPrincipal.buscarClientePorId("9999");

        assertNotNull(clienteEncontrado);
        assertEquals("Laura Martinez", clienteEncontrado.getNombre());
        assertNull(clienteInexistente);
    }

    @Test
    void verificarProgramacionYConsultaDeFuncionesPorFecha() throws Exception {
        Funcion funcionEncontrada = cinemaPrincipal.buscarFuncionPorCodigo("FUNC-01");
        Funcion funcionInexistente = cinemaPrincipal.buscarFuncionPorCodigo("FUNC-99");

        assertNotNull(funcionEncontrada);
        assertNull(funcionInexistente);

        Funcion funcionOtraFecha = new Funcion(
                "FUNC-02",
                peliculaSecundaria,
                salaPrincipal,
                LocalDate.of(2026, 11, 21),
                LocalTime.of(16, 0),
                10000.0,
                EstadoFuncion.PROGRAMADA
        );
        cinemaPrincipal.programarFuncion(funcionOtraFecha);

        List<Funcion> funcionesEncontradas = cinemaPrincipal.funcionesPorFecha(LocalDate.of(2026, 11, 20));
        List<Funcion> funcionesSinProgramacion = cinemaPrincipal.funcionesPorFecha(LocalDate.of(2026, 12, 25));

        assertEquals(1, funcionesEncontradas.size());
        assertEquals("FUNC-01", funcionesEncontradas.get(0).getCodigoFuncion());
        assertTrue(funcionesSinProgramacion.isEmpty());
    }

    @Test
    void verificarExcepcionesAlProgramarFuncionEnSalaFueraDeServicioOPeliculaNoDisponible() throws Exception {
        Asiento asientoMantenimiento = new Asiento("C1", "C", 1);
        List<Asiento> listaAsientosMantenimiento = new ArrayList<>();
        listaAsientosMantenimiento.add(asientoMantenimiento);

        Sala salaMantenimiento = new Sala("SALA-02", "Sala Mantenimiento", 1, EstadoSala.FUERA_DE_SERVICIO, listaAsientosMantenimiento);
        cinemaPrincipal.registrarSala(salaMantenimiento);

        Funcion funcionEnSalaFueraDeServicio = new Funcion(
                "FUNC-03",
                peliculaSecundaria,
                salaMantenimiento,
                LocalDate.of(2026, 11, 22),
                LocalTime.of(18, 0),
                10000.0,
                EstadoFuncion.PROGRAMADA
        );

        assertThrows(SalaFueraDeServicioException.class, () -> {
            cinemaPrincipal.programarFuncion(funcionEnSalaFueraDeServicio);
        });

        cinemaPrincipal.deshabilitarPelicula("PEL-02");
        Funcion funcionPeliculaNoDisponible = new Funcion(
                "FUNC-04",
                peliculaSecundaria,
                salaPrincipal,
                LocalDate.of(2026, 11, 22),
                LocalTime.of(21, 0),
                10000.0,
                EstadoFuncion.PROGRAMADA
        );

        assertThrows(IllegalArgumentException.class, () -> {
            cinemaPrincipal.programarFuncion(funcionPeliculaNoDisponible);
        });
    }

    @Test
    void verificarCalculoDeValorEntradaSegunTipoCliente() {
        double valorGeneral = cinemaPrincipal.calcularValorEntrada(funcionPrincipal, TipoCliente.GENERAL);
        double valorEstudiante = cinemaPrincipal.calcularValorEntrada(funcionPrincipal, TipoCliente.ESTUDIANTE);
        double valorNino = cinemaPrincipal.calcularValorEntrada(funcionPrincipal, TipoCliente.NINO);
        double valorAdultoMayor = cinemaPrincipal.calcularValorEntrada(funcionPrincipal, TipoCliente.ADULTO_MAYOR);

        assertEquals(10000.0, valorGeneral, 0.001);
        assertEquals(8000.0, valorEstudiante, 0.001);
        assertEquals(7000.0, valorNino, 0.001);
        assertEquals(7500.0, valorAdultoMayor, 0.001);
    }

    @Test
    void verificarVentaDeEntradaExitosaYExcepcionesDeVenta() throws Exception {
        Venta ventaRegistrada = cinemaPrincipal.venderEntrada("VENTA-01", "1002", "FUNC-01", "A1");

        assertNotNull(ventaRegistrada);
        assertEquals("VENTA-01", ventaRegistrada.getCodigoVenta());
        assertEquals(8000.0, ventaRegistrada.getValorPagado(), 0.001);
        assertEquals(EstadoVenta.ACTIVA, ventaRegistrada.getEstadoVenta());
        assertEquals(EstadoAsiento.OCUPADO, ventaRegistrada.getAsiento().getEstado());

        assertThrows(FuncionInexistenteException.class, () -> {
            cinemaPrincipal.venderEntrada("VENTA-02", "1001", "FUNC-INEXISTENTE", "A2");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            cinemaPrincipal.venderEntrada("VENTA-03", "CLIENTE-INEXISTENTE", "FUNC-01", "A2");
        });

        assertThrows(AsientoOcupadoException.class, () -> {
            cinemaPrincipal.venderEntrada("VENTA-04", "1001", "FUNC-01", "Z9");
        });

        assertThrows(AsientoOcupadoException.class, () -> {
            cinemaPrincipal.venderEntrada("VENTA-05", "1001", "FUNC-01", "A1");
        });
    }

    @Test
    void verificarCancelacionDeVentaYExcepcionSiVentaNoExiste() throws Exception {
        cinemaPrincipal.venderEntrada("VENTA-10", "1001", "FUNC-01", "A1");

        Venta ventaEncontrada = cinemaPrincipal.buscarVentaPorCodigo("VENTA-10");
        Venta ventaInexistente = cinemaPrincipal.buscarVentaPorCodigo("VENTA-99");

        assertNotNull(ventaEncontrada);
        assertNull(ventaInexistente);

        cinemaPrincipal.cancelarVenta("VENTA-10");

        assertEquals(EstadoVenta.CANCELADA, ventaEncontrada.getEstadoVenta());
        assertEquals(EstadoAsiento.DISPONIBLE, primerAsiento.getEstado());

        assertThrows(VentaInexistenteException.class, () -> {
            cinemaPrincipal.cancelarVenta("VENTA-FANTASMA");
        });
    }

    @Test
    void verificarReportesDeEntradasIngresosPeliculaMasVendidaYOcupacion() throws Exception {
        assertNull(cinemaPrincipal.peliculaMasVendida());

        Funcion funcionSecundaria = new Funcion(
                "FUNC-02",
                peliculaSecundaria,
                salaPrincipal,
                LocalDate.of(2026, 11, 21),
                LocalTime.of(17, 0),
                10000.0,
                EstadoFuncion.PROGRAMADA
        );
        cinemaPrincipal.programarFuncion(funcionSecundaria);

        cinemaPrincipal.venderEntrada("VENTA-20", "1001", "FUNC-01", "A1");
        cinemaPrincipal.venderEntrada("VENTA-21", "1002", "FUNC-01", "A2");
        cinemaPrincipal.cancelarVenta("VENTA-21");

        assertEquals(1, cinemaPrincipal.totalEntradasVendidas());
        assertEquals(10000.0, cinemaPrincipal.totalIngresos(), 0.001);

        Pelicula peliculaMasVendida = cinemaPrincipal.peliculaMasVendida();
        assertNotNull(peliculaMasVendida);
        assertEquals("PEL-01", peliculaMasVendida.getCodigoPelicula());

        String reporteOcupacion = cinemaPrincipal.ocupacionFuncion("FUNC-01");
        String reporteEsperado = "Funcion -->FUNC-01\nOcupados: 1\nDisponibles: 1\nCapacidad total: 2\nOcupación: 50.0%";
        assertEquals(reporteEsperado, reporteOcupacion);

        assertThrows(FuncionInexistenteException.class, () -> {
            cinemaPrincipal.ocupacionFuncion("FUNC-INEXISTENTE");
        });
    }
}
