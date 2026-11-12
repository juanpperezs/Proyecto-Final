import model.core.Cliente;
import model.core.Funcion;
import model.core.Pelicula;
import model.core.Venta;
import model.enums.Clasificacion;
import model.enums.EstadoAsiento;
import model.enums.EstadoFuncion;
import model.enums.EstadoSala;
import model.enums.EstadoVenta;
import model.enums.TipoCliente;
import model.espacios.Asiento;
import model.espacios.Sala;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VentaTest {

    private Asiento asientoDePrueba;
    private Sala salaDePrueba;
    private Pelicula peliculaDePrueba;
    private Funcion funcionDePrueba;

    @BeforeEach
    void prepararDatosIniciales() {
        asientoDePrueba = new Asiento("B1", "B", 1);

        List<Asiento> listaAsientosDeLaSala = new ArrayList<>();
        listaAsientosDeLaSala.add(asientoDePrueba);

        salaDePrueba = new Sala("SALA-02", "Sala Dos", 1, EstadoSala.DISPONIBLE, listaAsientosDeLaSala);
        peliculaDePrueba = new Pelicula("PEL-02", "Matrix", "Accion", 136, Clasificacion.MAYORES_15);
        funcionDePrueba = new Funcion(
                "FUNC-02",
                peliculaDePrueba,
                salaDePrueba,
                "2026-11-16",
                "18:30",
                10000.0,
                EstadoFuncion.PROGRAMADA
        );
    }

    @Test
    void verificarDatosInicialesDeLaVenta() {
        Cliente clienteDePrueba = new Cliente("1000", "Santiago Herrera", TipoCliente.GENERAL);
        Venta ventaDePrueba = new Venta(
                "VENTA-00",
                clienteDePrueba,
                funcionDePrueba,
                asientoDePrueba,
                "2026-11-12 09:30",
                10000.0,
                EstadoVenta.ACTIVA
        );

        assertEquals("VENTA-00", ventaDePrueba.getCodigo());
        assertEquals(clienteDePrueba, ventaDePrueba.getCliente());
        assertEquals(funcionDePrueba, ventaDePrueba.getFuncion());
        assertEquals(asientoDePrueba, ventaDePrueba.getAsiento());
        assertEquals("2026-11-12 09:30", ventaDePrueba.getFechaYHora());
        assertEquals(10000.0, ventaDePrueba.getValorPagado());
        assertEquals(EstadoVenta.ACTIVA, ventaDePrueba.getEstadoVenta());
    }

    @Test
    void verificarCalculoDeValorPagadoParaClienteGeneral() {
        Cliente clienteTipoGeneral = new Cliente("1001", "Carlos Gomez", TipoCliente.GENERAL);
        Venta ventaClienteGeneral = new Venta(
                "VENTA-01",
                clienteTipoGeneral,
                funcionDePrueba,
                asientoDePrueba,
                "2026-11-12 10:00",
                0.0,
                EstadoVenta.ACTIVA
        );

        double valorCalculadoClienteGeneral = ventaClienteGeneral.calcularValorPagado();

        assertEquals(10000.0, valorCalculadoClienteGeneral);
        assertEquals(10000.0, ventaClienteGeneral.getValorPagado());
    }

    @Test
    void verificarCalculoDeValorPagadoParaClienteEstudiante() {
        Cliente clienteTipoEstudiante = new Cliente("1002", "Laura Martinez", TipoCliente.ESTUDIANTE);
        Venta ventaClienteEstudiante = new Venta(
                "VENTA-02",
                clienteTipoEstudiante,
                funcionDePrueba,
                asientoDePrueba,
                "2026-11-12 10:05",
                0.0,
                EstadoVenta.ACTIVA
        );

        double valorCalculadoClienteEstudiante = ventaClienteEstudiante.calcularValorPagado();

        assertEquals(8000.0, valorCalculadoClienteEstudiante);
        assertEquals(8000.0, ventaClienteEstudiante.getValorPagado());
    }

    @Test
    void verificarCalculoDeValorPagadoParaClienteNino() {
        Cliente clienteTipoNino = new Cliente("1003", "Mateo Ruiz", TipoCliente.NINO);
        Venta ventaClienteNino = new Venta(
                "VENTA-03",
                clienteTipoNino,
                funcionDePrueba,
                asientoDePrueba,
                "2026-11-12 10:10",
                0.0,
                EstadoVenta.ACTIVA
        );

        double valorCalculadoClienteNino = ventaClienteNino.calcularValorPagado();

        assertEquals(7000.0, valorCalculadoClienteNino);
        assertEquals(7000.0, ventaClienteNino.getValorPagado());
    }

    @Test
    void verificarCalculoDeValorPagadoParaClienteAdultoMayor() {
        Cliente clienteTipoAdultoMayor = new Cliente("1004", "Elena Torres", TipoCliente.ADULTO_MAYOR);
        Venta ventaClienteAdultoMayor = new Venta(
                "VENTA-04",
                clienteTipoAdultoMayor,
                funcionDePrueba,
                asientoDePrueba,
                "2026-11-12 10:15",
                0.0,
                EstadoVenta.ACTIVA
        );

        double valorCalculadoClienteAdultoMayor = ventaClienteAdultoMayor.calcularValorPagado();

        assertEquals(7500.0, valorCalculadoClienteAdultoMayor);
        assertEquals(7500.0, ventaClienteAdultoMayor.getValorPagado());
    }

    @Test
    void verificarQueCancelarVentaCambiaEstadoYLiberaAsiento() {
        Cliente clienteTipoGeneral = new Cliente("1005", "Andres Rojas", TipoCliente.GENERAL);
        asientoDePrueba.ocuparAsiento();

        Venta ventaParaCancelar = new Venta(
                "VENTA-05",
                clienteTipoGeneral,
                funcionDePrueba,
                asientoDePrueba,
                "2026-11-12 10:20",
                10000.0,
                EstadoVenta.ACTIVA
        );

        ventaParaCancelar.cancelarVenta();

        assertEquals(EstadoVenta.CANCELADA, ventaParaCancelar.getEstadoVenta());
        assertEquals(EstadoAsiento.DISPONIBLE, asientoDePrueba.getEstado());
    }
}