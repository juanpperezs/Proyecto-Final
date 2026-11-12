import model.core.Funcion;
import model.core.Pelicula;
import model.enums.Clasificacion;
import model.enums.EstadoFuncion;
import model.enums.EstadoSala;
import model.espacios.Asiento;
import model.espacios.Sala;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FuncionTest {

    private Pelicula peliculaPrueba;
    private Sala salaPrueba;
    private Funcion funcionPrueba;
    private Asiento primerAsiento;
    private Asiento segundoAsiento;
    private Asiento tercerAsiento;

    @BeforeEach
    void prepararDatosIniciales() {
        primerAsiento = new Asiento("A1", "A", 1);
        segundoAsiento = new Asiento("A2", "A", 2);
        tercerAsiento = new Asiento("A3", "A", 3);

        List<Asiento> listaAsientosSala = new ArrayList<>();
        listaAsientosSala.add(primerAsiento);
        listaAsientosSala.add(segundoAsiento);
        listaAsientosSala.add(tercerAsiento);

        salaPrueba = new Sala("SALA-01", "Sala Principal", 3, EstadoSala.DISPONIBLE, listaAsientosSala);
        peliculaPrueba = new Pelicula("PEL-01", "Interestelar", "Ciencia Ficcion", 169, Clasificacion.TODOS);
        funcionPrueba = new Funcion("FUNC-01", peliculaPrueba, salaPrueba, LocalDate.of(2026,11,15), LocalTime.of(20,0), 12000.0, EstadoFuncion.PROGRAMADA);
    }

    @Test
    void verificarDatosInicialesDeLaFuncion() {
        assertEquals("FUNC-01", funcionPrueba.getCodigoFuncion());
        assertEquals(peliculaPrueba, funcionPrueba.getPelicula());
        assertEquals(salaPrueba, funcionPrueba.getSala());
        assertEquals(LocalDate.of(2026,11,15), funcionPrueba.getFecha());
        assertEquals(LocalTime.of(20,0), funcionPrueba.getHora());
        assertEquals(12000.0, funcionPrueba.getValorBase());
        assertEquals(EstadoFuncion.PROGRAMADA, funcionPrueba.getEstado());
    }

    @Test
    void verificarConteoDeAsientosDisponiblesYOcupados() {
        assertEquals(3, funcionPrueba.getCantidadAsientosDisponibles());
        assertEquals(0, funcionPrueba.getCantidadAsientosOcupados());

        primerAsiento.ocuparAsiento();
        segundoAsiento.ocuparAsiento();

        assertEquals(1, funcionPrueba.getCantidadAsientosDisponibles());
        assertEquals(2, funcionPrueba.getCantidadAsientosOcupados());
    }
}